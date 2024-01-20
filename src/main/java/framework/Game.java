package framework;

import java.awt.*;
import java.util.Collection;

public interface Game {

    public void start(PlayerFactory playerFactory1, PlayerFactory playerFactory2);
    public boolean hasEnded();
    public Player getCurrentPlayer();
    public Board getBoard();

    public void doMove(Point move);
    public void nextPlayer();
    public boolean isValidMove(Point move);
    public Player getWinner();
    public Player getLoser();

    public Collection<Player> getAllPlayers();

}
