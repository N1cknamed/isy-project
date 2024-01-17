package framework;

import server.Command;
import server.Response;
import server.ServerController;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.function.Supplier;

public class ServerGameController {
    private final ArrayList<GameSubscriber> subscribers = new ArrayList<>();
    private final Supplier<ServerGame> gameSupplier;
    private final String host;
    private final int port;
    private final String teamName;
    private final PlayerFactory playerFactory;

    private ServerController serverController;
    private ServerGame game;

    private final BlockingQueue<Response> yourTurnQueue = new SynchronousQueue<>();
    private final BlockingQueue<Response> matchResponseQueue = new SynchronousQueue<>();

    private Thread gameThread;

    public ServerGameController(Supplier<ServerGame> gameSupplier, String host, int port, String teamName, PlayerFactory playerFactory) {
        this.gameSupplier = gameSupplier;
        this.host = host;
        this.port = port;
        this.teamName = teamName;
        this.playerFactory = playerFactory;
    }

    private void serverLoop() {
        while (true) {
            Response response = serverController.getMessage().pop();
            switch (response.getCommand()) {
                case MATCH:
                    try {
                        matchResponseQueue.put(response);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case YOURTURN:
                    assertGameStarted();
                    try {
                        yourTurnQueue.put(response);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case MOVE:
                    assertGameStarted();
                    if (!response.getStringValue("PLAYER").equals(teamName)) {
                        Point move = calculatePoint(response.getIntValue("MOVE"));
                        game.getServerPlayer().setNextMove(move);
                    }
                    break;
                case LOSS:
                    assertGameStarted();
                    game.forceWin(game.getServerPlayer());
                    gameThread.interrupt();
                    break;
                case WIN:
                    assertGameStarted();
                    game.forceWin(game.getLocalPlayer());
                    gameThread.interrupt();
                    break;
                case DRAW:
                    assertGameStarted();
                    game.forceWin(null);
                    gameThread.interrupt();
                    break;
                default:
                    assertGameStarted();
                    game.handleServerResponse(response);
            }
        }
    }

    private void playGame(String opponentName, String playerToMove) {
        gameThread = Thread.currentThread();

        // Wait until we are challenged
        Response matchResponse = null;
        while (matchResponse == null ||
                matchResponse.getCommand() != Command.MATCH ||
                !matchResponse.getStringValue("GAMETYPE").equals(game.getGameType())) {
            matchResponse = serverController.getMessage().pop();
        }

        System.out.println("opponentName = " + opponentName);

        // Check who is starting
        if (playerToMove.equals(opponentName)) {
            System.out.println("Opponent is starting");
            game.start(ServerPlayer::new, playerFactory);
        } else {
            System.out.println("We are starting");
            game.start(playerFactory, ServerPlayer::new);
        }

        for (GameSubscriber i : subscribers) {
            i.onGameStarted(game);
        }

        while (!game.hasEnded()) {
            Player currentPlayer = game.getCurrentPlayer();

            if (currentPlayer.getPlayerType().isLocal()) {
                System.out.println("Waiting until it's our turn");
                // Wait until it's our turn
                try {
                    yourTurnQueue.take();
                } catch (InterruptedException e) {
                    System.out.println("Received an interrupt while waiting for our turn. Exiting game loop.");
                    break;
                }

                System.out.println("It's our turn!");
            }

            Point move;
            try {
                move = currentPlayer.doMove(game);
            } catch (InterruptedException e) {
                System.out.println("Received an interrupt while waiting for player's move. Exiting game loop.");
                break;
            }

            if (game.doMove(move)) {
                throw new RuntimeException("illegal move (" + move.toString() + ")");
            }

            if (currentPlayer.getPlayerType().isLocal()) {
                int moveIndex = move.y * game.getBoard().getBoardWidth() + move.x;
                serverController.sendMessage("MOVE " + moveIndex);
            }

            for (GameSubscriber i : subscribers) {
                i.onGameUpdated(game);
                i.onPlayerMove(currentPlayer, move);
            }
        }

        for (GameSubscriber i : subscribers) {
            i.onGameEnded(game);
        }
    }

    public void gameLoop() {
        serverController = new ServerController(host, port);

        serverController.sendMessage("login " + teamName);
        new Thread(this::serverLoop).start();

        while (true) {
            Response matchResponse;
            try {
                matchResponse = matchResponseQueue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            game = gameSupplier.get();
            String serverGameType = matchResponse.getStringValue("GAMETYPE");
            if (!serverGameType.equals(game.getGameType())) {
                System.out.println("Received game type '" + serverGameType + "' but expected '" + game.getGameType() + "'. Ignoring MATCH response.");
                game = null;
                continue;
            }

            playGame(matchResponse.getStringValue("OPPONENT"), matchResponse.getStringValue("PLAYERTOMOVE"));
            System.out.println("Restarting game (waiting for match)");
        }
    }

    public void registerSubscriber(GameSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    private Point calculatePoint(int input) {
        int x = input % game.getBoard().getBoardWidth();
        int y = input / game.getBoard().getBoardWidth();

        return new Point(x, y);
    }

    private void assertGameStarted() {
        if (game == null) {
            throw new IllegalStateException("Expected game to be running, but no game object found.");
        }
    }
}
