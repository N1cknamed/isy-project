package battleship.subscribers;

import battleship.BattleshipGame;
import battleship.players.BattleshipPlayer;
import framework.Board;
import framework.Game;
import framework.GameSubscriber;
import framework.Player;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class BattleshipCsvSubscriber implements GameSubscriber {
    BattleshipPlayer playerBoatPlacement = null;
    BattleshipPlayer playerShootingAlgorithm = null;

    @Override
    public void onGameStarted(Game game) {
        BattleshipGame battleshipGame = (BattleshipGame) game;
        this.playerBoatPlacement = (BattleshipPlayer) battleshipGame.getCurrentPlayer();
        this.playerShootingAlgorithm = (BattleshipPlayer) battleshipGame.getOpponentPlayer();
    }

    @Override
    public void onGameUpdated(Game game) {

    }

    @Override
    public void onGameEnded(Game game) {
        Board board = game.getBoard();
        // count misses and hits
        int misses = 0;
        int hits = 0;

        for (int x = 0; x < board.getBoardWidth(); x++) {
            for (int y = 0; y < board.getBoardHeight(); y++) {
                if (board.get(x, y) == 'm') {
                    misses++;
                } else if (board.get(x, y) == 'h' || Character.isDigit(board.get(x, y))){
                    hits++;
                }
            }
        }
        writeToCsv(playerBoatPlacement.getSymbol(), playerShootingAlgorithm.getSymbol(), hits, misses);
    }

    @Override
    public void onPlayerMove(Player player, Point move) {

    }
    public void writeToCsv(char boatAi, char shooterAi, int hits, int misses) {
        // CSV file path
        String csvFilePath = "data/games.csv";

        // Create CSV file
        try (FileWriter writer = new FileWriter(csvFilePath, true)) {

            // Write data
            writer.append(String.valueOf(boatAi)).append(",").append(String.valueOf(shooterAi)).append(",").append(String.valueOf(hits)).append(",").append(String.valueOf(misses)).append("\n");

        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
