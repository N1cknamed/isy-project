package Games;

public interface Player {
    boolean isHuman();
    int[] makeMove(Tictactoe game);

    void updateBoard(Tictactoe game);


}