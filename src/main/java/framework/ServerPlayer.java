package framework;

import java.awt.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class ServerPlayer implements Player {

    private final char symbol;

    private final BlockingQueue<Point> nextMoveQueue = new SynchronousQueue<>();

    public ServerPlayer(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public PlayerType getPlayerType() {
        return PlayerType.SERVER;
    }

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
