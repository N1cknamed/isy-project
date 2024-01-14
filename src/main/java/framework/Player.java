package framework;

import java.awt.*;

public interface Player {
    PlayerType getPlayerType();
    public char getSymbol();
    Point doMove(Game game);
}
