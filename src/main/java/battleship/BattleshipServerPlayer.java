package battleship;

import framework.server.ServerPlayer;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class BattleshipServerPlayer extends ServerPlayer implements BattleshipPlayer {

    private final BlockingQueue<Character> shootResult = new SynchronousQueue<>();

    public BattleshipServerPlayer(char symbol) {
        super(symbol);
    }

    @Override
    public void placeBoats() {
    }

    @Override
    public char shoot(Point move) {
        try {
            return this.shootResult.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public Collection<Boat> getPlacedBoats() {
        return Collections.emptyList();
    }

    public void setShootResult(char shootResult) {
        try {
            this.shootResult.put(shootResult);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
