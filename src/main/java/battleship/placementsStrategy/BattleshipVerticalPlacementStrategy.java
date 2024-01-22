package battleship.placementsStrategy;

import battleship.Boat;

import java.awt.*;
import java.util.ArrayList;

public class BattleshipVerticalPlacementStrategy implements BattleshipPlacementStrategy{
    @Override
    public ArrayList<Boat> getShips() {
        ArrayList<Boat> ships = new ArrayList<>();
        ships.add(new Boat(new Point(3, 1), 1, 6));
        ships.add(new Boat(new Point(5, 1), 1, 4));
        ships.add(new Boat(new Point(1, 3), 1, 3));
        ships.add(new Boat(new Point(5, 6), 1, 2));
        return ships;
    }
}
