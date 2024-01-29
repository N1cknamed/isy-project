package battleship.players;

import battleship.Boat;
import framework.Player;

import java.awt.*;
import java.util.Collection;

public interface BattleshipPlayer extends Player {
    void placeBoats();
    char shoot(Point move);
    boolean isAlive();
    Collection<Boat> getPlacedBoats();
}
