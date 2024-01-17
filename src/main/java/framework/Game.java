package framework;

import java.awt.*;
import java.util.Collection;

public interface Game {

    public void start(PlayerFactory playerFactory1, PlayerFactory playerFactory2);
    public boolean hasEnded();
    public Player getCurrentPlayer();
    public Board getBoard();

    /**
     * @return True if something went wrong, false if otherwise.
     */
    public boolean doMove(Point move);
    public boolean isValidMove(Point move);
    public Player getWinner();

    public Collection<Player> getAllPlayers();

}
