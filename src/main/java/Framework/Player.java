package Framework;

import java.awt.*;

public interface Player {
    boolean isHuman();
    public char getSymbol();
    Point doMove(Game game);
}
