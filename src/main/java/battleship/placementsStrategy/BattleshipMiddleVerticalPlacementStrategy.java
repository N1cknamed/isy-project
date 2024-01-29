package battleship.placementsStrategy;

import battleship.Boat;

import java.awt.*;
import java.util.ArrayList;

public class BattleshipMiddleVerticalPlacementStrategy implements BattleshipPlacementStrategy{
    @Override
    public ArrayList<Boat> getShips() {
        ArrayList<Boat> ships = new ArrayList<>();
        ships.add(new Boat(new Point(2, 0), 1, 4));
        ships.add(new Boat(new Point(6, 1), 1, 6));
        ships.add(new Boat(new Point(4, 3), 1, 2));
        ships.add(new Boat(new Point(1, 5), 0, 3));
        return ships;
    }
}
