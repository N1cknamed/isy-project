package framework;

import java.awt.*;

public interface Player {
    PlayerType getPlayerType();
    char getSymbol();
    Point doMove(Game game) throws InterruptedException;
}
