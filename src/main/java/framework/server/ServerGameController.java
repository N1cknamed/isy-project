package framework.server;

import framework.GameSubscriber;
import framework.Player;
import framework.PlayerFactory;
import framework.server.communication.Response;
import framework.server.communication.ServerController;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.function.Function;

public class ServerGameController {
    private final ArrayList<GameSubscriber> subscribers = new ArrayList<>();
    private final Function<ServerGameController, ServerGame> gameSupplier;
    private final String gameType;
    private final String host;
    private final int port;
    private final String teamName;
    private String opponentName;
    private final PlayerFactory localPlayerFactory;
    private final PlayerFactory serverPlayerFactory;
    private ServerController serverController;
    private ServerGame game;
    private final BlockingQueue<Response> yourTurnQueue = new SynchronousQueue<>();
    private final BlockingQueue<Response> matchResponseQueue = new SynchronousQueue<>();
    private Thread gameThread;
    private ServerChallengeHandler serverChallengeHandler;

    public ServerGameController(Function<ServerGameController, ServerGame> gameSupplier, String gameType, String host, int port, String teamName, PlayerFactory playerFactory, PlayerFactory serverPlayerFactory) {
        this.gameSupplier = gameSupplier;
        this.gameType = gameType;
        this.host = host;
        this.port = port;
        this.teamName = teamName;
        this.localPlayerFactory = playerFactory;
        this.serverPlayerFactory = serverPlayerFactory;
    }

    private void serverLoop() {
        while (true) {
            Response response = serverController.getMessage().pop();

            if (game != null) {
                game.handleServerResponse(response);
            }

            switch (response.getCommand()) {
                case CHALLENGE:
                    String challenger = response.getStringValue("CHALLENGER");
                    String challengeGameType = response.getStringValue("GAMETYPE");
                    int challengeNumber = response.getIntValue("CHALLENGENUMBER");

                    if (serverChallengeHandler == null) {
                        System.out.println("Ignoring challenge by " + challenger + ": no challenge handler registered.");
                    } else if (!challengeGameType.equals(gameType)) {
                        System.out.println("Ignoring challenge by " + challenger + ": game type mismatch.");
                    } else {
                        serverChallengeHandler.handleChallengeReceived(new ServerChallenge(
                                challenger,
                                challengeGameType,
                                challengeNumber,
                                this::acceptChallenge
                        ));
                    }
                    break;
                case MATCH:
                    createGame(response);
                    try {
                        matchResponseQueue.put(response);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case YOURTURN:
                    try {
                        yourTurnQueue.put(response);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case MOVE:
                    assertGameStarted();
                    if (!response.getStringValue("PLAYER").equals(teamName)) {
                        Point move = indexToPoint(response.getIntValue("MOVE"));
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
            }
        }
    }

    private void createGame(Response matchResponse) {
        String serverGameType = matchResponse.getStringValue("GAMETYPE");
        if (!serverGameType.equals(gameType)) {
            throw new IllegalStateException("Received game type '" + serverGameType + "' but expected '" + gameType + "'. Ignoring MATCH response.");
        }

        opponentName = matchResponse.getStringValue("OPPONENT");
        String playerToMove = matchResponse.getStringValue("PLAYERTOMOVE");

        game = gameSupplier.apply(this);

        System.out.println("Our name: " + teamName);
        System.out.println("Opponent name: " + opponentName);

        // Check who is starting
        if (playerToMove.equals(opponentName)) {
            System.out.println("Opponent is starting (" + opponentName + " v.s. " + teamName + ")");
            game.start(serverPlayerFactory, localPlayerFactory);
        } else {
            System.out.println("We are starting (" + teamName + " v.s. " + opponentName + ")");
            game.start(localPlayerFactory, serverPlayerFactory);
        }

        for (GameSubscriber i : subscribers) {
            i.onGameStarted(game);
        }
    }

    private void playGame() {
        gameThread = Thread.currentThread();
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

            boolean doMove = game.prePlayerMove(currentPlayer);
            if (doMove) {
                Point move;
                try {
                    move = currentPlayer.doMove(game);
                } catch (InterruptedException e) {
                    System.out.println("Received an interrupt while waiting for player's move. Exiting game loop.");
                    break;
                }

                if (!game.isValidMove(move)) {
                    throw new RuntimeException("illegal move (" + move + ")");
                }

                if (currentPlayer.getPlayerType().isLocal()) {
                    int moveIndex = pointToIndex(move);
                    serverController.sendMessage("MOVE " + moveIndex);
                }

                game.doMove(move);

                for (GameSubscriber i : subscribers) {
                    i.onPlayerMove(currentPlayer, move);
                }
            }
            for (GameSubscriber i : subscribers) {
                i.onGameUpdated(game);
            }
            game.nextPlayer();
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
            try {
                matchResponseQueue.take();
            } catch (InterruptedException e) {
                System.out.println("Ignoring interrupt exception while waiting for MATCH response.");
                continue;
            }

            playGame();
            System.out.println("Restarting game (waiting for match)");
        }
    }

    public void registerSubscriber(GameSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void setServerChallengeHandler(ServerChallengeHandler serverChallengeHandler) {
        this.serverChallengeHandler = serverChallengeHandler;
    }

    public void challengePlayer(String playerName) {
        if (serverController != null) {
            serverController.sendMessage("challenge \"" + playerName + "\" \"" + gameType + "\"");
        }
    }

    public void acceptChallenge(ServerChallenge challenge) {
        if (serverController != null) {
            serverController.sendMessage("challenge accept " + challenge.getChallengeNumber());
        }
    }

    public Point indexToPoint(int input) {
        int x = input % game.getBoard().getBoardWidth();
        int y = input / game.getBoard().getBoardWidth();

        return new Point(x, y);
    }

    private void assertGameStarted() {
        if (game == null) {
            throw new IllegalStateException("Expected game to be running, but no game object found.");
        }
    }

    public int pointToIndex(Point cord) {
        return cord.y * game.getBoard().getBoardWidth() + cord.x;
    }

    public ServerController getServerController() {
        return serverController;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getOpponentName() {
        return opponentName;
    }
}
