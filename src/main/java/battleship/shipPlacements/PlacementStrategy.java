package battleship.shipPlacements;

import battleship.Boat;

import java.util.ArrayList;

public interface PlacementStrategy {
    ArrayList<Boat> getShips();
}
