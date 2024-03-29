package framework.server;

import framework.Game;
import framework.Player;
import framework.PlayerType;

import java.awt.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public abstract class ServerPlayer implements Player {

    private final char symbol;

    private final BlockingQueue<Point> nextMoveQueue = new SynchronousQueue<>();

    public ServerPlayer(char symbol) {
        this.symbol = symbol;
    }

    public abstract PlayerType getPlayerType();

    @Override
    public char getSymbol() {
        return symbol;
    }

    @Override
    public Point doMove(Game game) throws InterruptedException {
        return nextMoveQueue.take();
    }

    public void setNextMove(Point nextMove) {
        try {
            nextMoveQueue.put(nextMove);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
