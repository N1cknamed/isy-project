package battleship.placementsStrategy;

import battleship.Boat;

import java.awt.*;
import java.util.ArrayList;

public class BattleshipNoSidesTouchingPlacementStrategy implements BattleshipPlacementStrategy{
    @Override
    public ArrayList<Boat> getShips() {
        ArrayList<Boat> ships = new ArrayList<>();
        ships.add(new Boat(new Point(1, 1), 1, 6));
        ships.add(new Boat(new Point(3, 1), 0, 4));
        ships.add(new Boat(new Point(6, 3), 1, 3));
        ships.add(new Boat(new Point(3, 5), 0, 2));
        return ships;
    }
}