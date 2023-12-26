package games;

public interface Player {
    boolean isHuman();
    int[] makeMove(Tictactoe game);

    void updateBoard(Tictactoe game);


}