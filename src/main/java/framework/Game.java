package framework;

import java.awt.*;
import java.util.Collection;

public interface Game {

    void start(PlayerFactory playerFactory1, PlayerFactory playerFactory2);
    boolean hasEnded();
    Player getCurrentPlayer();
    Board getBoard();

    void doMove(Point move);
    void nextPlayer();
    boolean isValidMove(Point move);
    Player getWinner();
    Player getLoser();

    Collection<Player> getAllPlayers();

}
