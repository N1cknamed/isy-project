package battleship.shootingAi;

import framework.Board;
import framework.Game;
import framework.Heatmap;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Character.isDigit;

public class BattleshipRecursiveHeatmapShootingAi implements BattleshipShootingAi {

    private static final boolean MULTI_THREADED = true;
    private static final int MAX_THREADS = 6;

    @Override
    public Point getMove(Game game) {
        List<Point> bestMoves = getBestMovesFromGameBoard(game.getBoard());
        int index = (int) (Math.random() * bestMoves.size());
        return bestMoves.get(index);
    }

    private List<Point> getBestMovesFromGameBoard(Board gameBoard) {
        List<Integer> boatsAvailable = getBoatsAvailable(gameBoard);
        Board board = convertBoard(gameBoard);

        Heatmap heatmap;
        if (MULTI_THREADED) {
            heatmap = generateHeatMapMultiThreaded(board, boatsAvailable);
        } else {
            heatmap = generateHeatMapSingleThreaded(board, boatsAvailable);
        }

        removeAlreadyHitFromHeatmap(board, heatmap);
        return getBestMoves(heatmap);
    }

    private List<Point> getBestMoves(Heatmap heatmap) {
        List<Point> bestMoves = new ArrayList<>();
        int bestMoveAmount = 0;

        for (int y = 0; y < heatmap.getHeight(); y++) {
            for (int x = 0; x < heatmap.getWidth(); x++) {
                int value = heatmap.getValue(x, y);
                if (value > bestMoveAmount) {
                    bestMoveAmount = value;
                    bestMoves.clear();
                }

                if (value == bestMoveAmount) {
                    bestMoves.add(new Point(x, y));
                }
            }
        }

        return bestMoves;
    }

    private void removeAlreadyHitFromHeatmap(Board board, Heatmap heatmap) {
        for (int y = 0; y < board.getBoardHeight(); y++) {
            for (int x = 0; x < board.getBoardWidth(); x++) {
                char c = board.get(x, y);
                if (c == 'x' || c == 'h') {
                    heatmap.reset(x, y);
                }
            }
        }
    }

    private Heatmap generateHeatMapSingleThreaded(Board board, List<Integer> boatsAvailable) {
        Heatmap heatmap = new Heatmap(board.getBoardWidth(), board.getBoardHeight());
        generateHeatMap(board.copy(), boatsAvailable, heatmap);
        return heatmap;
    }

    private Heatmap generateHeatMapMultiThreaded(Board board, List<Integer> boatsAvailable) {
        Heatmap heatmap = new Heatmap(board.getBoardWidth(), board.getBoardHeight());

        int currentBoatLength = boatsAvailable.get(0);

        ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREADS);

        for (int y = 0; y < board.getBoardHeight(); y++) {
            for (int x = 0; x < board.getBoardWidth(); x++) {
                for (int direction = 0; direction <= 1; direction++) {
                    if (canPlaceBoat(board, x, y, direction, currentBoatLength)) {
                        final int _x = x;
                        final int _y = y;
                        final int _direction = direction;

                        executorService.submit(() -> {
                            Board copy = board.copy();
                            placeBoat(copy, _x, _y, _direction, currentBoatLength);
                            generateHeatMap(copy, boatsAvailable.subList(1, boatsAvailable.size()), heatmap);
                        });
                    }
                }
            }
        }

        executorService.shutdown();

        boolean success;
        try {
            success = executorService.awaitTermination(3, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            success = false;
        }

        if (!success) {
            System.out.println("WARNING! Could not finish generating heatmap before timeout. Result might not be the optimal move.");
        }

        return heatmap;
    }

    private void generateHeatMap(Board board, List<Integer> boatsAvailable, Heatmap heatmap) {
        if (boatsAvailable.isEmpty()) {
            // All boats have been placed.
            if (isValidEndOfHeatmap(board)) {
                for (int y = 0; y < board.getBoardHeight(); y++) {
                    for (int x = 0; x < board.getBoardWidth(); x++) {
                        if (board.get(x, y) == 'x') {
                            heatmap.increase(x, y);
                        }
                    }
                }
            }

            return;
        }

        int currentBoatLength = boatsAvailable.get(0);

        for (int y = 0; y < board.getBoardHeight(); y++) {
            for (int x = 0; x < board.getBoardWidth(); x++) {
                for (int direction = 0; direction <= 1; direction++) {
                    if (canPlaceBoat(board, x, y, direction, currentBoatLength)) {
                        Board copy = board.copy();

                        placeBoat(copy, x, y, direction, currentBoatLength);

                        List<Integer> copyBoatsAvailable = boatsAvailable.subList(1, boatsAvailable.size());
                        generateHeatMap(copy, copyBoatsAvailable, heatmap);
                    }
                }
            }
        }
    }

    private boolean isValidEndOfHeatmap(Board board) {
        for (int y = 0; y < board.getBoardHeight(); y++) {
            for (int x = 0; x < board.getBoardWidth(); x++) {
                if (board.get(x, y) == 'h') {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean canPlaceBoat(Board board, int bx, int by, int dir, int length) {
        if (bx < 0 || by < 0) {
            return false;
        }

        int width = (dir == 0 ? length : 1);
        int height = (dir == 1 ? length : 1);

        if (bx + width - 1 >= board.getBoardWidth() || by + height - 1 >= board.getBoardHeight()) {
            return false;
        }

        int sx = bx - 1;
        int sy = by - 1;

        int ex = bx + width;
        int ey = by + height;

        for (int x = sx; x <= ex; x++) {
            for (int y = sy; y <= ey; y++) {
                if ((x == sx && y == sy) || (x == sx && y == ey) || (x == ex && y == sy) || (x == ex && y == ey)) {
                    // Ignore corner
                    continue;
                }

                char c = board.get(x, y);
                if (x > sx && x < ex && y > sy && y < ey) {
                    // Boat spot
                    if (c == 'x' || c == 'm') {
                        return false;
                    }
                } else {
                    // Outer spot
                    if (c == 'x' || c == 'h') {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void placeBoat(Board board, int bx, int by, int dir, int length) {
        int width = (dir == 0 ? length : 1);
        int height = (dir == 1 ? length : 1);

        for (int x = bx; x < bx + width; x++) {
            for (int y = by; y < by + height; y++) {
                board.set(x, y, 'x');
            }
        }
    }

    private void unplaceBoat(Board original, Board board, int bx, int by, int dir, int length) {
        int width = (dir == 0 ? length : 1);
        int height = (dir == 1 ? length : 1);

        for (int x = bx; x < bx + width; x++) {
            for (int y = by; y < by + height; y++) {
                board.set(x, y, original.get(x, y));
            }
        }
    }

    private List<Integer> getBoatsAvailable(Board gameBoard) {
        List<Integer> boatsAvailable = new ArrayList<>(List.of(2, 3, 4, 6));

        for (int y = 0; y < gameBoard.getBoardHeight(); y++) {
            for (int x = 0; x < gameBoard.getBoardWidth(); x++) {
                char c = gameBoard.get(x, y);
                if (isDigit(c)) {
                    boatsAvailable.remove((Integer) (c - '0'));
                }
            }
        }

        return boatsAvailable;
    }

    private Board convertBoard(Board gameBoard) {
        Board board = new Board(gameBoard.getBoardWidth(), gameBoard.getBoardHeight());

        for (int y = 0; y < board.getBoardHeight(); y++) {
            for (int x = 0; x < board.getBoardWidth(); x++) {
                char c = gameBoard.get(x, y);
                if (c == 'm') {
                    board.set(x, y, 'm');
                } else if (isPartOfSankBoat(gameBoard, x, y)) {
                    board.set(x, y, 'x');
                } else if (c == 'h') {
                    board.set(x, y, 'h');
                }
            }
        }

        return board;
    }

    private boolean isPartOfSankBoat(Board gameBoard, int x, int y) {
        return isPartOfSankBoat(gameBoard, new Point(x, y), new HashSet<>());
    }

    private boolean isPartOfSankBoat(Board gameBoard, Point point, Set<Point> visited) {
        char c = gameBoard.get(point);
        if (c == 'm') {
            return false;
        } else if (isDigit(c)) {
            return true;
        } else if (c == 'h') {
            Set<Point> neighbors = Set.of(
                    new Point(point.x - 1, point.y),
                    new Point(point.x + 1, point.y),
                    new Point(point.x, point.y - 1),
                    new Point(point.x, point.y + 1)
            );

            for (Point neighbor : neighbors) {
                if (visited.contains(neighbor)) continue;
                visited.add(neighbor);
                if (isPartOfSankBoat(gameBoard, neighbor, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static class RecursiveState {

        private final Board board;
        private final Set<Integer> availableBoats;

        private RecursiveState(Board board, Set<Integer> availableBoats) {
            this.board = board;
            this.availableBoats = availableBoats;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RecursiveState that = (RecursiveState) o;
            return Objects.equals(board, that.board) && Objects.equals(availableBoats, that.availableBoats);
        }

        @Override
        public int hashCode() {
            return Objects.hash(board, availableBoats);
        }
    }
}
