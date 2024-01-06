package ttt;
import framework.Game;
import framework.Player;
import gui.TttGui;

import java.awt.*;

public class TttGuiPlayer implements Player {
    private final char symbol;

    //this is because i can't easily get the player out of the game.
    private static int row = -1;
    private static int col = -1;

    public TttGuiPlayer(char symbol) {
        this.symbol = symbol;
        TttGui.registerPlayer(this);
    }

    @Override
    public boolean isHuman() {
        return true;
    }

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public Point doMove(Game game) {
        row = -1;
        col = -1;
        while ((row == -1 || col == -1) || !game.isValidMove(new Point(col, row))) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new Point(col, row);
    }

    public static void setMove(int row, int col) {
        TttGuiPlayer.row = row;
        TttGuiPlayer.col = col;
    }
}
