package battleship.placementsStrategy;

import battleship.Boat;

import java.awt.*;
import java.util.ArrayList;

public class BattleshipHorizontalPlacementStrategy implements BattleshipPlacementStrategy{
    @Override
    public ArrayList<Boat> getShips() {
        ArrayList<Boat> ships = new ArrayList<>();
        ships.add(new Boat(new Point(1, 3), 0, 6));
        ships.add(new Boat(new Point(3, 5), 0, 4));
        ships.add(new Boat(new Point(2, 1), 0, 3));
        ships.add(new Boat(new Point(0, 5), 0, 2));
        return ships;
    }
}
