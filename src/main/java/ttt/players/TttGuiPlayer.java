package ttt.players;
import framework.Game;
import framework.Player;
import framework.PlayerType;
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
    public PlayerType getPlayerType() {
        return PlayerType.GUI;
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
