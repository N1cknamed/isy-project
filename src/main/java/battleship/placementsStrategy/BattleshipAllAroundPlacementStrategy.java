package battleship.placementsStrategy;

import battleship.Boat;

import java.awt.*;
import java.util.ArrayList;

public class BattleshipAllAroundPlacementStrategy implements BattleshipPlacementStrategy{
    @Override
    public ArrayList<Boat> getShips() {
        ArrayList<Boat> ships = new ArrayList<>();
        ships.add(new Boat(new Point(0, 0), 0, 6));
        ships.add(new Boat(new Point(7, 0), 1, 4));
        ships.add(new Boat(new Point(0, 6), 1, 2));
        ships.add(new Boat(new Point(5, 7), 0, 3));
        return ships;
    }
}
