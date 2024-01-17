package battleship;

import framework.Player;

import java.awt.*;

public interface BattleshipPlayer extends Player {
    public void placeBoats();
    public char shoot(Point move);
    boolean isAlive();
}
