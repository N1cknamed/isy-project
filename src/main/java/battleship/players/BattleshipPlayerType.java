package battleship.players;

import battleship.placementsStrategy.BattleshipCornersPlacementStrategy;
import battleship.placementsStrategy.BattleshipPlacementStrategy;
import battleship.shootingAi.*;
import framework.PlayerType;

import java.util.function.Supplier;

public class BattleshipPlayerType extends PlayerType {

    public static BattleshipPlayerType AI_SEQUENTIAL = new BattleshipPlayerType(
            "AI_SEQUENTIAL",
            true,
            BattleshipCornersPlacementStrategy::new,
            BattleshipSequentialShootingAi::new
    );



    public static BattleshipPlayerType AI_TRUE_RANDOM = new BattleshipPlayerType(
            "AI_TRUE_RANDOM",
            true,
            BattleshipCornersPlacementStrategy::new,
            BattleshipTrueRandomShootingAi::new
    );

    public static BattleshipPlayerType AI_OPTIMIZED_SEQUENTIAL_RANDOM = new BattleshipPlayerType(
            "AI_OPTIMIZED_SEQUENTIAL_RANDOM",
            true,
            BattleshipCornersPlacementStrategy::new,
            BattleshipOptimizedRandomShootingAi::new
    );

    public static BattleshipPlayerType AI_OPTIMIZED_RANDOM = new BattleshipPlayerType(
            "AI_OPTIMIZED_RANDOM",
            true,
            BattleshipCornersPlacementStrategy::new,
            BattleshipOptimizedRandomSequentialShootingAi::new
    );

    public static BattleshipPlayerType CLI = new BattleshipPlayerType(
            "CLI",
            true
    );

    public static BattleshipPlayerType GUI = new BattleshipPlayerType("GUI", true);

    public static BattleshipPlayerType SERVER = new BattleshipPlayerType("SERVER", false);


    private final Supplier<BattleshipPlacementStrategy> placementStrategy;
    private final Supplier<BattleshipShootingAi> shootingAi;

    public BattleshipPlayerType(String name, boolean isLocal,
                                Supplier<BattleshipPlacementStrategy> placementStrategy,
                                Supplier<BattleshipShootingAi> shootingAi) {
        super(name, isLocal);

        this.placementStrategy = placementStrategy;
        this.shootingAi = shootingAi;
    }

    public BattleshipPlayerType(String name, boolean isLocal) {
        this(name, isLocal, null, null);
    }

    public BattleshipPlacementStrategy createPlacementStrategy() {
        if (placementStrategy == null) {
            throw new IllegalStateException();
        }
        return placementStrategy.get();
    }

    public BattleshipShootingAi createShootingAi() {
        if (shootingAi == null) {
            throw new IllegalStateException();
        }

        return shootingAi.get();
    }

    public static BattleshipPlayerType[] getPlayerTypes() {
        return new BattleshipPlayerType[] {
                AI_SEQUENTIAL,
                AI_TRUE_RANDOM,
                AI_OPTIMIZED_SEQUENTIAL_RANDOM,
                AI_OPTIMIZED_RANDOM,
                CLI,
                GUI,
                SERVER
        };
    }

    public static BattleshipPlayerType getPlayerTypeByName(String name) {
        for (BattleshipPlayerType playerType : getPlayerTypes()) {
            if (playerType.getName().equals(name)) {
                return playerType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return getName();
    }
}
