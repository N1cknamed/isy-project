import battleship.BattleshipGame;
import battleship.BattleshipServerGame;
import battleship.shootingAi.*;
import framework.Heatmap;
import battleship.placementsStrategy.*;
import battleship.players.BattleshipPlayerFactory;
import battleship.players.BattleshipPlayerType;
import battleship.subscribers.BattleshipCliSubscriber;
import battleship.subscribers.BattleshipCsvSubscriber;
import battleship.subscribers.BattleshipGuiSubscriber;
import framework.Board;
import framework.Game;
import framework.GameController;
import framework.GameSubscriber;
import framework.server.ServerGameController;
import gui.BattleShipsGui;
import gui.HomeGui;
import gui.TttGui;
import ttt.TttGame;
import ttt.TttServerGame;
import ttt.players.TttPlayerFactory;
import ttt.players.TttPlayerType;
import ttt.subscribers.TttCliSubscriber;
import ttt.subscribers.TttGuiSubscriber;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

public class Main {

    private static final String TEAM_NAME = readTeamName(new File("teamname.txt"));
    private static final String SERVER_HOST = "94.214.0.206";
    private static final int SERVER_PORT = 7789;

    public static void main(String[] args) {
//        runTttCli();
//        runBattleshipCli();
//        runBattleshipCsv();
//        battleshipMatrix();
//        runBattleshipStats();
//        runBattleshipStats();
//        runTttGui();
//        runBattleshipGui();
//        runHomeGui();

//        runServerTtt();
       runServerBattleship();

//    new Thread(() -> runServerBattleship("p1", BattleshipPlayerType.AI_HEATMAP)).start();
//    new Thread(() -> runServerBattleship("p2", BattleshipPlayerType.GUI)).start();
    }

    private static void battleshipMatrix() {
        List<Supplier<BattleshipPlacementStrategy>> placementStrategies = List.of(
                BattleshipAllAroundPlacementStrategy::new,
                BattleshipBottomPlacementStrategy::new,
                BattleshipCornersPlacementStrategy::new,
                BattleshipHorizontalPlacementStrategy::new,
                BattleshipMiddleHorizontalPlacementStrategy::new,
                BattleshipMiddleVerticalPlacementStrategy::new,
                BattleshipNoSidesTouchingPlacementStrategy::new,
                BattleshipPureRandomPlacementStrategy::new,
                BattleshipSidesPlacementStrategy::new,
                BattleshipVerticalPlacementStrategy::new
        );

        List<Supplier<BattleshipShootingAi>> shootingAis = List.of(
                BattleshipTrueRandomShootingAi::new,
                BattleshipHeatmapShootingAi::new,
                BattleshipMoreOptimizedRandomCheckerboardShootingAi::new
        );

        Heatmap matrix = new Heatmap(placementStrategies.size(), placementStrategies.size());

        int runs = 1000000;

        for (int i = 0; i < placementStrategies.size(); i++) {
            BattleshipPlayerType player1Type = new BattleshipPlayerType("player1", true, true, placementStrategies.get(i), BattleshipSequentialShootingAi::new);
            for (int j = 0; j < shootingAis.size(); j++) {
                BattleshipPlayerType player2Type = new BattleshipPlayerType("player2", true, true, BattleshipCornersPlacementStrategy::new, shootingAis.get(j));

                int player1WinningShots = 0;

                for (int k = 0; k < runs; k++) {
                    BattleshipGame game = new BattleshipGame();
                    GameController controller = new GameController(game, new BattleshipPlayerFactory(player1Type), new BattleshipPlayerFactory(player2Type));
                    controller.gameLoop();

                    Board board = game.getOpponentBoard();

                    if (game.getWinner().getPlayerType() == player1Type) {
//                        System.out.println("Player 2 won");
//                        System.out.println(shootingAis.get(j).get().getClass());
//                        System.out.println(placementStrategies.get(j).get().getClass());
                        k--;
                        continue;
                    }

                    for (int x = 0; x < board.getBoardWidth(); x++) {
                        for (int y = 0; y < board.getBoardHeight(); y++) {
                            if (board.get(x, y) == 'm') {
                                player1WinningShots++;
                            } else if (board.get(x, y) == 'h' || Character.isDigit(board.get(x, y))) {
                                player1WinningShots++;
                            }
                        }
                    }
                }

                matrix.setValue(j, i, player1WinningShots / runs);
            }
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("data/matrix.csv"));
            writer.write("X");
            for (int i = 0; i < shootingAis.size(); i++) {
                writer.write("," + shootingAis.get(i).get().getClass().getSimpleName().replace("Battleship", "").replace("ShootingAi", ""));
            }
            writer.write("\n");

            for (int j = 0; j < placementStrategies.size(); j++) {
                writer.write(placementStrategies.get(j).get().getClass().getSimpleName().replace("Battleship", "").replace("PlacementStrategy", ""));
                for (int i = 0; i < shootingAis.size(); i++) {
                    writer.write(",");
                    writer.write(String.valueOf(matrix.getValue(i, j)));
                }

                writer.write("\n");
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void runServerTtt() {
        // Build the game classes and use the player types to create PlayerFactory objects
        ServerGameController controller = new ServerGameController(
                TttServerGame::new,
                "Tic-tac-toe",
                SERVER_HOST,
                SERVER_PORT,
                TEAM_NAME,
                new TttPlayerFactory(TttPlayerType.AI),
                new TttPlayerFactory(TttPlayerType.SERVER)
        );

        controller.registerSubscriber(new TttCliSubscriber());

        // Start the game
        controller.gameLoop();
    }

    private static void runServerBattleship() {
        runServerBattleship(TEAM_NAME, BattleshipPlayerType.GUI);
    }

    private static void runServerBattleship(String teamName, BattleshipPlayerType localPlayerType) {
        // Build the game classes and use the player types to create PlayerFactory objects
        ServerGameController controller = new ServerGameController(
                BattleshipServerGame::new,
                "Battleship",
                SERVER_HOST,
                SERVER_PORT,
                teamName,
                new BattleshipPlayerFactory(localPlayerType),
                new BattleshipPlayerFactory(BattleshipPlayerType.SERVER)
        );
        controller.registerSubscriber(new BattleshipCliSubscriber());
        controller.registerSubscriber(new BattleshipGuiSubscriber());

        Thread t = new Thread(() -> {
            BattleShipsGui.launch(BattleShipsGui.class);
        });
        t.start();


        // Start the game
        controller.gameLoop();
    }

    private static void runTttCli() {
        // Change the player types according to what the user wants (GUI buttons)
        TttPlayerType player1Type = TttPlayerType.AI;
        TttPlayerType player2Type = TttPlayerType.CLI;

        // Build the game classes and use the player types to create PlayerFactory objects
        Game game = new TttGame();
        GameController controller = new GameController(
                game,
                new TttPlayerFactory(player1Type),
                new TttPlayerFactory(player2Type)
        );
        controller.registerSubscriber(new TttCliSubscriber());

        // Start the game
        controller.gameLoop();
    }

    private static void runBattleshipGui() {
        // Change the player types according to what the user wants (GUI buttons)
        BattleshipPlayerType player1Type = BattleshipPlayerType.AI_HEATMAP;
        BattleshipPlayerType player2Type = BattleshipPlayerType.GUI;

        // Build the game classes and use the player types to create PlayerFactory objects
        Game game = new BattleshipGame();
        GameController controller = new GameController(
                game,
                new BattleshipPlayerFactory(player1Type),
                new BattleshipPlayerFactory(player2Type)
        );
        controller.registerSubscriber(new BattleshipCliSubscriber());
        controller.registerSubscriber(new BattleshipGuiSubscriber());

        Thread t = new Thread(() -> {
            BattleShipsGui.launch(BattleShipsGui.class);
        });
        t.start();

        // Start the game
        controller.gameLoop();
    }

    private static void runBattleshipCli() {
        Game game = new BattleshipGame();
        GameController controller = new GameController(
                game,
                new BattleshipPlayerFactory(BattleshipPlayerType.CLI),
                new BattleshipPlayerFactory(BattleshipPlayerType.AI_HEATMAP)
        );
        controller.registerSubscriber(new BattleshipCliSubscriber());
        controller.registerSubscriber(new BattleshipGuiSubscriber());
        controller.gameLoop();
    }

    private static void runBattleshipCsv() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("CSV file name: ");
        String csvFileName = scanner.nextLine();

        System.out.println("Available player types:");
        int counter = 0;
        for (BattleshipPlayerType type : BattleshipPlayerType.getPlayerTypes()) {
            System.out.print(counter++);
            System.out.println(" - " + type);
        }

        BattleshipPlayerType[] playerTypes = BattleshipPlayerType.getPlayerTypes();

        System.out.print("Player 1 type: ");
        BattleshipPlayerType player1Type = playerTypes[scanner.nextInt()];

        System.out.print("Player 2 type: ");
        BattleshipPlayerType player2Type = playerTypes[scanner.nextInt()];

        System.out.print("Number of runs: ");
        int runs = scanner.nextInt();

        GameSubscriber csv = new BattleshipCsvSubscriber(csvFileName);

        for (int i = 0; i < runs; i++) {
            if (i % (runs / 100) == 0) {
                System.out.println(i / (runs / 100) + "%");
            }
            Game game = new BattleshipGame();
            GameController controller = new GameController(
                    game,
                    new BattleshipPlayerFactory(player1Type),
                    new BattleshipPlayerFactory(player2Type)
            );
            controller.registerSubscriber(csv);
            controller.gameLoop();
        }
    }

    private static void runBattleshipStats() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Available player types:");
        int counter = 0;
        for (BattleshipPlayerType type : BattleshipPlayerType.getPlayerTypes()) {
            System.out.print(counter++);
            System.out.println(" - " + type);
        }

        BattleshipPlayerType[] playerTypes = BattleshipPlayerType.getPlayerTypes();

        System.out.print("Player 1 type: ");
        BattleshipPlayerType player1Type = playerTypes[scanner.nextInt()];

        System.out.print("Player 2 type: ");
        BattleshipPlayerType player2Type = playerTypes[scanner.nextInt()];

        System.out.print("Number of runs: ");
        int runs = scanner.nextInt();

        int player1Wins = 0;
        int player2Wins = 0;

        int player1Winninghits = 0;
        int player2Winninghits = 0;

        int player1Losinghits = 0;
        int player2Losinghits = 0;

        int player1WinningMisses = 0;
        int player2WinningMisses = 0;

        int player1LosingMisses = 0;
        int player2LosingMisses = 0;

        for (int i = 0; i < runs; i++) {
            if (i % (runs / 100) == 0) {
                System.out.println(i / (runs / 100) + "%");
            }

            BattleshipGame game = new BattleshipGame();
            GameController controller = new GameController(
                    game,
                    new BattleshipPlayerFactory(i % 2 == 0 ? player1Type : player2Type),
                    new BattleshipPlayerFactory(i % 2 == 1 ? player1Type : player2Type)
            );
            controller.gameLoop();

            Board board = game.getOpponentBoard();
            // count misses and hits
            int winnerMisses = 0;
            int winnerHits = 0;

            for (int x = 0; x < board.getBoardWidth(); x++) {
                for (int y = 0; y < board.getBoardHeight(); y++) {
                    if (board.get(x, y) == 'm') {
                        winnerMisses++;
                    } else if (board.get(x, y) == 'h' || Character.isDigit(board.get(x, y))) {
                        winnerHits++;
                    }
                }
            }

            Board loserBoard = game.getCurrentBoard();

            int loserMisses = 0;
            int loserHits = 0;

            for (int x = 0; x < loserBoard.getBoardWidth(); x++) {
                for (int y = 0; y < loserBoard.getBoardHeight(); y++) {
                    if (loserBoard.get(x, y) == 'm') {
                        loserMisses++;
                    } else if (loserBoard.get(x, y) == 'h' || Character.isDigit(loserBoard.get(x, y))) {
                        loserHits++;
                    }
                }
            }

            if (game.getWinner().getPlayerType() == player1Type) {
                player1Wins++;
                player1Winninghits += winnerHits;
                player1WinningMisses += winnerMisses;
                player2Losinghits += loserHits;
                player2LosingMisses += loserMisses;
            } else {
                player2Wins++;
                player2Winninghits += winnerHits;
                player2WinningMisses += winnerMisses;
                player1Losinghits += loserHits;
                player1LosingMisses += loserMisses;
            }
        }

        System.out.println(player1Type);
        System.out.println("Total wins: " + player1Wins);
        System.out.println("Win precentage: " + (double) player1Wins / runs * 100 + "%");
        System.out.println("Average winning hits: " + (double) player1Winninghits / player1Wins);
        System.out.println("Average winning misses: " + (double) player1WinningMisses / player1Wins);
        System.out.println("Average losing hits: " + (double) player1Losinghits / player2Wins);
        System.out.println("Average losing misses: " + (double) player1LosingMisses / player2Wins);
        System.out.println();

        System.out.println(player2Type);
        System.out.println("Total wins: " + player2Wins);
        System.out.println("Win precentage: " + (double) player2Wins / runs * 100 + "%");
        System.out.println("Average winning hits: " + (double) player2Winninghits / player2Wins);
        System.out.println("Average winning misses: " + (double) player2WinningMisses / player2Wins);
        System.out.println("Average losing hits: " + (double) player2Losinghits / player1Wins);
        System.out.println("Average losing misses: " + (double) player2LosingMisses / player1Wins);
    }

    private static void runTttGui() {
        Game game = new TttGame();
        GameController controller = new GameController(
                game,
                new TttPlayerFactory(TttPlayerType.GUI),
                new TttPlayerFactory(TttPlayerType.GUI)
        );

        Thread t = new Thread(() -> {
            TttGui.launch(TttGui.class);
        });
        t.start();

        controller.registerSubscriber(new TttCliSubscriber());
        controller.registerSubscriber(new TttGuiSubscriber());
        controller.gameLoop();
    }

    private static void runServerTttGui() {
        ServerGameController controller = new ServerGameController(
                TttServerGame::new,
                "Tic-tac-toe",
                SERVER_HOST,
                SERVER_PORT,
                TEAM_NAME,
                new TttPlayerFactory(TttPlayerType.GUI),
                new TttPlayerFactory(TttPlayerType.SERVER)
        );

        Thread t = new Thread(() -> {
            TttGui.launch(TttGui.class);
        });
        t.start();

        // Handle challenge requests
        controller.setServerChallengeHandler(challenge -> {
            System.out.println("WE RECEIVED A CHALLENGE BY " + challenge.getChallenger());

            // TODO Open GUI popup or something to accept/decline the challenge
            boolean accept = false;

            if (accept) {
                challenge.accept();
            }
        });

        // Send challenges
        // controller.challengePlayer("albert");

        controller.registerSubscriber(new TttCliSubscriber());
        controller.registerSubscriber(new TttGuiSubscriber());
        controller.gameLoop();
    }

    private static void runHomeGui() {
        HomeGui.launch(HomeGui.class);
    }

    private static String readTeamName(File file) {
        try {
            if (!file.exists()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write("groep2\n");
                writer.close();

                System.out.println("Generated file " + file.getName());
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String teamName = reader.readLine();
            reader.close();

            System.out.println("Read teamname from file " + file.getName() + ": " + teamName);

            return teamName;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
