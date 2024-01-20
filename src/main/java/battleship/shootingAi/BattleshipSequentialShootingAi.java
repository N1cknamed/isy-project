package battleship.shootingAi;

import framework.Game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BattleshipSequentialShootingAi implements BattleshipShootingAi {

    private List<Point> pointPool = null;

    @Override
    public Point getMove(Game game) {
        if (pointPool == null) {
            buildPointPool(game.getBoard().getBoardWidth(), game.getBoard().getBoardHeight());
        }

        Point point = pointPool.get(0);
        pointPool.remove(0);

        return point;
    }

    private void buildPointPool(int width, int height) {
        pointPool = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pointPool.add(new Point(x, y));
            }
        }
    }
}
