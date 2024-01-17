package battleship;

import framework.Game;
import games.Board;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class BattleshipsAi {
    private final Board board;
    private final HashMap<Character, Integer> ships = new HashMap<Character, Integer>();

    public BattleshipsAi() {
        this.board = new Board(8, 8);
    }

    public void addShip(char symbol, int size) {
        ships.put(symbol, size);
    }

    public int[][] getHeatMap() {
        int[][] heatMap = new int[board.getBoardWidth()][board.getBoardHeight()];

        for (int x = 0; x < board.getBoardWidth(); x++) {
            for (int y = 0; y < board.getBoardHeight(); y++) {
                heatMap[x][y] = 0;
            }
        }

        for (int x = 0; x < board.getBoardWidth(); x++) {
            for (int y = 0; y < board.getBoardHeight(); y++) {
                if (board.get(x, y) == 'm') {
                    heatMap[x][y] = -1;
                } else if (board.get(x, y) == 'h') {
                    heatMap[x][y] = -1;
                    if (x > 0 && heatMap[x - 1][y] != -1) {
                        heatMap[x - 1][y]++;
                    }
                    if (x < board.getBoardWidth() - 1 && heatMap[x + 1][y] != -1) {
                        heatMap[x + 1][y]++;
                    }
                    if (y > 0 && heatMap[x][y - 1] != -1) {
                        heatMap[x][y - 1]++;
                    }
                    if (y < board.getBoardHeight() - 1 && heatMap[x][y + 1] != -1) {
                        heatMap[x][y + 1]++;
                    }
                }
            }
        }

        return heatMap;
    }

    public Point getMove(Game game) {
        Point move;
        Random random = new Random();
        do {
            // random move
            move = new Point(random.nextInt(board.getBoardWidth()), random.nextInt(board.getBoardHeight()));
            System.out.println("AI move: " + move);
        } while (!game.isValidMove(move));

        return move;
    }
}
