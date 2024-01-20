package battleship;

import java.awt.*;

public class Boat {

    private final Point startCord;
    private final int direction; // 0 = horizontal, 1 = vertical
    private final int length;

    public Boat(Point startCord, int direction, int length) {
        if (direction < 0 || direction > 1) {
            throw new IllegalArgumentException("Direction has to be 0 or 1");
        }
        this.startCord = startCord;
        this.direction = direction;
        this.length = length;
    }

    public Point getEndCords() {
        Point endCord;
        if (direction == 0) {
            endCord = new Point(startCord.x+length-1, startCord.y);
        } else {
            endCord = new Point(startCord.x, startCord.y+length-1);
        }
        return endCord;
    }

    public int getDirection() {
        return direction;
    }

    public int getLength() {
        return length;
    }

    public Point getStartCord() {
        return startCord;
    }
}
