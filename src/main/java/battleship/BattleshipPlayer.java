package battleship;

import framework.Player;

import java.awt.*;
import java.util.Collection;

public interface BattleshipPlayer extends Player {
    public void placeBoats();
    public char shoot(Point move);
    boolean isAlive();
    public Collection<Boat> getPlacedBoats();
}
