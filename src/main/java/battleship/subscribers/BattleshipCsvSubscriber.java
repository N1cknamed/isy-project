package battleship.subscribers;

import battleship.BattleshipGame;
import battleship.players.BattleshipPlayer;
import framework.Board;
import framework.Game;
import framework.GameSubscriber;
import framework.Player;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BattleshipCsvSubscriber implements GameSubscriber {
    String csvFileName;

    public BattleshipCsvSubscriber(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    @Override
    public void onGameStarted(Game game) {
        BattleshipGame battleshipGame = (BattleshipGame) game;
    }

    @Override
    public void onGameUpdated(Game game) {

    }

    @Override
    public void onGameEnded(Game game) {
        BattleshipGame battleshipGame = (BattleshipGame) game;
        BattleshipPlayer winner = (BattleshipPlayer) battleshipGame.getWinner();
        BattleshipPlayer loser = (BattleshipPlayer) battleshipGame.getLoser();
        Board board = battleshipGame.getOpponentBoard();
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

        Board loserBoard = battleshipGame.getCurrentBoard();

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
        writeToCsv(winner.getPlayerType().getName(), loser.getPlayerType().getName(), winnerHits, winnerMisses, loserHits, loserMisses);
    }

    @Override
    public void onPlayerMove(Player player, Point move) {

    }

    public void writeToCsv(String winner, String loser, int winnerHits, int winnerMisses, int loserHits, int loserMisses) {
        // CSV file path
        File csvFile = new File("data/" + csvFileName + ".csv");

        try {
            if (!csvFile.exists()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
                writer.write("winner,loser,winnerHits,winnerMisses,WinnerShots,loserHits,loserMisses,loserShots\n");
                writer.close();

                System.out.println("Generated file " + csvFile.getName());
            }
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
            e.printStackTrace();
        }

        // Create CSV file
        try (FileWriter writer = new FileWriter(csvFile, true)) {

            // Write data
            writer.append(String.valueOf(winner)).append(",")
                    .append(String.valueOf(loser)).append(",")
                    .append(String.valueOf(winnerHits)).append(",")
                    .append(String.valueOf(winnerMisses)).append(",")
                    .append(String.valueOf(winnerHits+winnerMisses)).append(",")
                    .append(String.valueOf(loserHits)).append(",")
                    .append(String.valueOf(loserMisses)).append(",")
                    .append(String.valueOf(loserHits+loserMisses)).append("\n");

        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
