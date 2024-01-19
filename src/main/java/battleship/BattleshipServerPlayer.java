package battleship;

import framework.server.ServerPlayer;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;

public class BattleshipServerPlayer extends ServerPlayer implements BattleshipPlayer {
    public BattleshipServerPlayer(char symbol) {
        super(symbol);
    }

    @Override
    public void placeBoats() {

    }

    @Override
    public char shoot(Point move) {
        return 0;
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public Collection<Boat> getPlacedBoats() {
        return Collections.emptyList();
    }
}
