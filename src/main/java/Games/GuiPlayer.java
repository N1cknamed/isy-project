package Games;

import Gui.TictactoeGui;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Objects;

public class GuiPlayer implements Player {
    private final Tictactoe game = new Tictactoe(new CliPlayer(), new CliPlayer());
    private final Button[][] buttons = new Button[3][3];
    private boolean gameOver = false;
    private boolean isTurn = false;

    private int selectedRow = -1;
    private int selectedCol = -1;

    public GuiPlayer() {
        TictactoeGui.registerPlayer(this);
    }

    @Override
    public boolean isHuman() {
        return true;
    }

    @Override
    public int[] makeMove(Tictactoe game) {
        isTurn = true;
        // Wait for the player to make a move via GUI
        while (selectedRow == -1 || selectedCol == -1) {
            try {
                // Sleep to avoid busy-waiting
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int[] move = new int[]{selectedRow, selectedCol};

        // Reset selectedRow and selectedCol for the next move
        selectedRow = -1;
        selectedCol = -1;
        isTurn = false;

        return move;
    }

    @Override
    public void updateBoard(Tictactoe game) {
        TictactoeGui.updateButtonsFromOutside(game);
    }

    // Method to set the selected row and column from GUI
    public void setSelectedMove(int row, int col) {
        selectedRow = row;
        selectedCol = col;
    }

    public boolean getIsTurn() {
        return isTurn;
    }
}