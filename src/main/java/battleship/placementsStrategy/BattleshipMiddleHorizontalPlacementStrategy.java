package battleship.placementsStrategy;

import battleship.Boat;

import java.awt.*;
import java.util.ArrayList;

public class BattleshipMiddleHorizontalPlacementStrategy implements BattleshipPlacementStrategy{
    @Override
    public ArrayList<Boat> getShips() {
        ArrayList<Boat> ships = new ArrayList<>();
        ships.add(new Boat(new Point(1, 1), 0, 6));
        ships.add(new Boat(new Point(3, 3), 0, 2));
        ships.add(new Boat(new Point(5, 4), 1, 3));
        ships.add(new Boat(new Point(0, 5), 0, 4));
        return ships;
    }
}
