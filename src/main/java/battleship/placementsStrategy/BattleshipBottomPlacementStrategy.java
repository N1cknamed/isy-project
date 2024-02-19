package battleship.placementsStrategy;

import battleship.Boat;

import java.awt.*;
import java.util.ArrayList;

public class BattleshipBottomPlacementStrategy implements BattleshipPlacementStrategy{
    @Override
    public ArrayList<Boat> getShips() {
        ArrayList<Boat> ships = new ArrayList<>();
        ships.add(new Boat(new Point(0, 3), 0, 6));
        ships.add(new Boat(new Point(1, 5), 0, 4));
        ships.add(new Boat(new Point(0, 6), 1, 2));
        ships.add(new Boat(new Point(5, 6), 0, 3));
        return ships;
    }
}
