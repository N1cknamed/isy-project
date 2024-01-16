package framework;

import server.Command;
import server.Response;
import server.ServerController;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerGameController {
    private final ArrayList<GameSubscriber> subscribers = new ArrayList<>();
    private final ServerGame game;
    private final String host;
    private final int port;
    private final String teamName;
    private final PlayerFactory playerFactory;

    private ServerController serverController;

    private final AtomicBoolean yourTurn = new AtomicBoolean();

    public ServerGameController(ServerGame game, String host, int port, String teamName, PlayerFactory playerFactory) {
        this.game = game;
        this.host = host;
        this.port = port;
        this.teamName = teamName;
        this.playerFactory = playerFactory;
    }

    private void serverLoop() {
        while (true) {
            Response response = serverController.getMessage().pop();
            switch (response.getCommand()) {
                case MOVE:
                    Point move = calculatePoint(response.getIntValue("MOVE"));
                    game.getServerPlayer().setNextMove(move);
                    break;
                case LOSS:
                    game.forceWin(game.getServerPlayer());
                    break;
                case WIN:
                    game.forceWin(game.getLocalPlayer());
                    break;
                case YOURTURN:
                    yourTurn.set(true);
                    yourTurn.notify();
                    break;
                default:
                    game.handleServerResponse(response);
            }
        }
    }

    public void gameLoop() {
        serverController = new ServerController(host, port);

        serverController.sendMessage("login " + teamName);

        // Wait until we are challenged
        Response matchResponse = null;
        while (matchResponse == null ||
                matchResponse.getCommand() != Command.MATCH ||
                !matchResponse.getStringValue("GAMETYPE").equals(game.getGameType())) {
            matchResponse = serverController.getMessage().pop();
        }

        String opponentName = matchResponse.getStringValue("OPPONENT");
        String playerToMove = matchResponse.getStringValue("PLAYERTOMOVE");

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

        new Thread(this::serverLoop).start();

        while (!game.hasEnded()) {
            Player currentPlayer = game.getCurrentPlayer();

            if (currentPlayer.getPlayerType().isLocal()) {
                System.out.println("Waiting until it's our turn");
                // Wait until it's our turn
                synchronized (yourTurn) {
                    while (!yourTurn.get()) {
                        try {
                            yourTurn.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                System.out.println("It's our turn!");
            }

            Point move = currentPlayer.doMove(game);
            if (game.doMove(move)) {
                throw new RuntimeException("illegal move");
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

    public void registerSubscriber(GameSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    private Point calculatePoint(int input) {
        int x = (input - 1) % game.getBoard().getBoardWidth();
        int y = (input - 1) / game.getBoard().getBoardWidth();

        return new Point(x, y);
    }
}
