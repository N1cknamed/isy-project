package games;

public class GuiPlayer implements Player {
    private boolean isTurn = false;

    private int selectedRow = -1;
    private int selectedCol = -1;

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
        //TictactoeGui.updateButtonsFromOutside(game);
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