package battleship.placementsStrategy;

import battleship.Boat;

import java.awt.*;
import java.util.ArrayList;

public class BattleshipSidesPlacementStrategy implements BattleshipPlacementStrategy{
    @Override
    public ArrayList<Boat> getShips() {
        ArrayList<Boat> ships = new ArrayList<>();
        ships.add(new Boat(new Point(0, 0), 0, 6));
        ships.add(new Boat(new Point(6, 1), 0, 2));
        ships.add(new Boat(new Point(1, 6), 0, 3));
        ships.add(new Boat(new Point(4, 7), 0, 4));
        return ships;
    }
}
