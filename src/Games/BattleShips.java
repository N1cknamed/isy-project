package Games;

public class BattleShips extends Board{
    private final char[][] board;
    public BattleShips() {
        super(10, 10);
        this.board = super.getBoard();
    }
    public BattleShips(char[][] board) {
        // make a game with existing board
        super(board);
        this.board = super.getBoard();
    }
}
