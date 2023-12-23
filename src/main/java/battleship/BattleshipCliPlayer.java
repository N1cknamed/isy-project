package battleship;

import Framework.Game;
import Framework.Player;

import java.awt.*;

public class BattleshipCliPlayer implements Player {
    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public char getSymbol() {
        return 0;
    }

    @Override
    public Point doMove(Game game) {
        return null;
    }
}
