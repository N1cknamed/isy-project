package battleship.shipPlacements;

import java.awt.*;

public class ShipPlacement {
    Point location;
    int rotation;
    int length;

    public ShipPlacement(Point location, int rotation, int length) {
        this.location = location;
        this.rotation = rotation;
        this.length = length;
    }

    public Point getLocation() {
        return location;
    }

    public int getRotation() {
        return rotation;
    }

    public int getLength() {
        return length;
    }
}
