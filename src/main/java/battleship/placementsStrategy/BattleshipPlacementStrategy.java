package battleship.placementsStrategy;

import battleship.Boat;

import java.util.ArrayList;

/*
stap 1:
new classe BattleshipPlacementStrategy

stap 2:
in BattleshipPlayerType new playertype aanmaken (vergeet niet ook de naam te veranderen),
en in getplayertypes the naam bij zetten

voor placement strategien kiezen we de slechste shooting ai "BattleshipSequentialShootingAi::new"

stap 3:
nieuwe else if statement met player type toevoegen.
*/

public interface BattleshipPlacementStrategy {
    ArrayList<Boat> getShips();
}
