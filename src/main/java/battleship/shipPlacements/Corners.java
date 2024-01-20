package battleship.shipPlacements;

import java.awt.*;
import java.util.ArrayList;

public class Corners implements PlacementStrategy {
    @Override
    public ArrayList<ShipPlacement> getShips() {
        ArrayList<ShipPlacement> ships = new ArrayList<>();
        ships.add(new ShipPlacement(new Point(0, 0), 0, 2));
        ships.add(new ShipPlacement(new Point(0, 5), 1, 3));
        ships.add(new ShipPlacement(new Point(7, 0), 1, 4));
        ships.add(new ShipPlacement(new Point(2, 7), 0, 6));
        return ships;
    }
}
