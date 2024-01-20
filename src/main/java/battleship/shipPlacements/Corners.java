package battleship.shipPlacements;

import battleship.Boat;

import java.awt.*;
import java.util.ArrayList;

public class Corners implements PlacementStrategy {
    @Override
    public ArrayList<Boat> getShips() {
        ArrayList<Boat> ships = new ArrayList<>();
        ships.add(new Boat(new Point(0, 0), 0, 2));
        ships.add(new Boat(new Point(0, 5), 1, 3));
        ships.add(new Boat(new Point(7, 0), 1, 4));
        ships.add(new Boat(new Point(2, 7), 0, 6));
        return ships;
    }
}
