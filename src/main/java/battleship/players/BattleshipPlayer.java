package battleship.players;

import battleship.Boat;
import framework.Player;

import java.awt.*;
import java.util.Collection;

public interface BattleshipPlayer extends Player {
    public void placeBoats() throws RuntimeException;
    public char shoot(Point move);
    boolean isAlive();
    public Collection<Boat> getPlacedBoats();
}
