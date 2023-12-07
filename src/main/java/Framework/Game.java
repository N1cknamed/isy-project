package Framework;

import java.awt.*;

public interface Game {

    public void start(Player player1, Player player2);
    public boolean hasEnded();
    public Player getCurrentPlayer();

    /**
     * @return True if something went wrong, false if otherwise.
     */
    public boolean doMove(Point move);
    public boolean isValidMove(Point move);
    public Player getWinner();

}
