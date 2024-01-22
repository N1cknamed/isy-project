package battleship.players;

import battleship.placementsStrategy.BattleshipAllAroundPlacementStrategy;
import battleship.placementsStrategy.BattleshipCornersPlacementStrategy;
import battleship.placementsStrategy.BattleshipPlacementStrategy;
import battleship.shootingAi.*;
import framework.PlayerType;

import java.util.function.Supplier;

public class BattleshipPlayerType extends PlayerType {

    public static BattleshipPlayerType AI_SEQUENTIAL = new BattleshipPlayerType(
            "AI_SEQUENTIAL",
            true,
            true,
            BattleshipCornersPlacementStrategy::new,
            BattleshipSequentialShootingAi::new
    );

    public static BattleshipPlayerType AI_TRUE_RANDOM = new BattleshipPlayerType(
            "AI_TRUE_RANDOM",
            true,
            true,
            BattleshipCornersPlacementStrategy::new,
            BattleshipTrueRandomShootingAi::new
    );

    public static BattleshipPlayerType AI_OPTIMIZED_SEQUENTIAL_RANDOM = new BattleshipPlayerType(
            "AI_OPTIMIZED_SEQUENTIAL_RANDOM",
            true,
            true,
            BattleshipCornersPlacementStrategy::new,
            BattleshipOptimizedRandomShootingAi::new
    );

    public static BattleshipPlayerType AI_OPTIMIZED_RANDOM = new BattleshipPlayerType(
            "AI_OPTIMIZED_RANDOM",
            true,
            true,
            BattleshipCornersPlacementStrategy::new,
            BattleshipOptimizedRandomSequentialShootingAi::new
    );

    public static BattleshipPlayerType AI_OPTIMIZED_CHECKERBOARD_RANDOM = new BattleshipPlayerType(
            "AI_OPTIMIZED_CHECKERBOARD_RANDOM",
            true,
            true,
            BattleshipCornersPlacementStrategy::new,
            BattleshipOptimizedRandomCheckerboardShootingAi::new
    );


    public static BattleshipPlayerType AI_MORE_OPTIMIZED_CHECKERBOARD_RANDOM = new BattleshipPlayerType(
            "AI_MORE_OPTIMIZED_CHECKERBOARD_RANDOM",
            true,
            true,
            BattleshipCornersPlacementStrategy::new,
            BattleshipMoreOptimizedRandomCheckerboardShootingAi::new
    );

    public static BattleshipPlayerType AI_HEATMAP = new BattleshipPlayerType(
            "AI_HEATMAP",
            true,
            true,
            BattleshipCornersPlacementStrategy::new,
            BattleshipHeatmapShootingAi::new
    );

    public static BattleshipPlayerType AI_RECURSIVE_HEATMAP = new BattleshipPlayerType(
            "AI_RECURSIVE_HEATMAP",
            true,
            true,
            BattleshipCornersPlacementStrategy::new,
            BattleshipRecursiveHeatmapShootingAi::new
    );

    public static BattleshipPlayerType CLI = new BattleshipPlayerType("CLI", true, false);

    public static BattleshipPlayerType GUI = new BattleshipPlayerType("GUI", true, false);

    public static BattleshipPlayerType SERVER = new BattleshipPlayerType("SERVER", false, false);


    private final boolean isAi;

    private final Supplier<BattleshipPlacementStrategy> placementStrategy;
    private final Supplier<BattleshipShootingAi> shootingAi;

    public BattleshipPlayerType(String name, boolean isLocal, boolean isAi,
                                Supplier<BattleshipPlacementStrategy> placementStrategy,
                                Supplier<BattleshipShootingAi> shootingAi) {
        super(name, isLocal);

        this.isAi = isAi;
        this.placementStrategy = placementStrategy;
        this.shootingAi = shootingAi;
    }

    public BattleshipPlayerType(String name, boolean isLocal, boolean isAi) {
        this(name, isLocal, isAi, null, null);
    }

    public boolean isAi() {
        return isAi;
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
                AI_OPTIMIZED_CHECKERBOARD_RANDOM,
                AI_MORE_OPTIMIZED_CHECKERBOARD_RANDOM,
                AI_HEATMAP,
                AI_RECURSIVE_HEATMAP,
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
