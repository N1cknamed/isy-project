package battleship.players;

import framework.Player;

import java.awt.*;

public interface BattleshipPlayer extends Player {
    public void placeBoats() throws RuntimeException;
    public char shoot(Point move);
    boolean isAlive();
}
