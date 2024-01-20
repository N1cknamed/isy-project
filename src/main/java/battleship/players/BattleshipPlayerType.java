package battleship.players;

import battleship.placementsStrategy.BattleshipCornersPlacementStrategy;
import battleship.placementsStrategy.BattleshipPlacementStrategy;
import battleship.shootingAi.BattleshipOptimizedRandomShootingAi;
import battleship.shootingAi.BattleshipSequentialShootingAi;
import battleship.shootingAi.BattleshipShootingAi;
import battleship.shootingAi.BattleshipTrueRandomShootingAi;
import framework.PlayerType;

public class BattleshipPlayerType extends PlayerType {

    public static BattleshipPlayerType AI_SEQUENTIAL = new BattleshipPlayerType(
            "AI_SEQUENTIAL",
            true,
            new BattleshipCornersPlacementStrategy(),
            new BattleshipSequentialShootingAi()
    );

    public static BattleshipPlayerType AI_TRUE_RANDOM = new BattleshipPlayerType(
            "AI_TRUE_RANDOM",
            true,
            new BattleshipCornersPlacementStrategy(),
            new BattleshipTrueRandomShootingAi()
    );

    public static BattleshipPlayerType AI_OPTIMIZED_RANDOM = new BattleshipPlayerType(
            "AI_OPTIMIZED_RANDOM",
            true,
            new BattleshipCornersPlacementStrategy(),
            new BattleshipOptimizedRandomShootingAi()
    );

    public static BattleshipPlayerType CLI = new BattleshipPlayerType(
            "CLI",
            true
    );

    public static BattleshipPlayerType GUI = new BattleshipPlayerType("GUI", true);

    public static BattleshipPlayerType SERVER = new BattleshipPlayerType("SERVER", false);


    private final BattleshipPlacementStrategy placementStrategy;
    private final BattleshipShootingAi shootingAi;

    public BattleshipPlayerType(String name, boolean isLocal, BattleshipPlacementStrategy placementStrategy, BattleshipShootingAi shootingAi) {
        super(name, isLocal);

        this.placementStrategy = placementStrategy;
        this.shootingAi = shootingAi;
    }

    public BattleshipPlayerType(String name, boolean isLocal) {
        this(name, isLocal, null, null);
    }

    public BattleshipPlacementStrategy getPlacementStrategy() {
        if (placementStrategy == null) {
            throw new IllegalStateException();
        }
        return placementStrategy;
    }

    public BattleshipShootingAi getShootingAi() {
        if (shootingAi == null) {
            throw new IllegalStateException();
        }

        return shootingAi;
    }
}
