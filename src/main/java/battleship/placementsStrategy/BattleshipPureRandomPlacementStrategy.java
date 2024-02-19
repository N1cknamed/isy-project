package battleship.placementsStrategy;

import battleship.Boat;
import framework.Board;

import java.awt.*;
import java.util.*;
import java.util.List;

public class BattleshipPureRandomPlacementStrategy implements BattleshipPlacementStrategy {

    @Override
    public ArrayList<Boat> getShips() {
        ArrayList<Boat> res = null;
        while (res == null) {
            res = attemptBoatPlacement(List.of(2, 3, 4, 6));
        }

        return res;
    }

    private ArrayList<Boat> attemptBoatPlacement(List<Integer> boatLengths) {
        boatLengths = new ArrayList<>(boatLengths);
        Board board = new Board(8, 8);
        ArrayList<Boat> res = new ArrayList<>();
        while (!boatLengths.isEmpty()) {
            int lengthIndex = (int) (Math.random() * boatLengths.size());
            int length = boatLengths.get(lengthIndex);
            boatLengths.remove(lengthIndex);

            List<Boat> possiblePlacements = getPossibleBoatPlacements(board, length);
            if (possiblePlacements.isEmpty()) {
                return null;
            }

            Boat placement = possiblePlacements.get((int) (Math.random() * possiblePlacements.size()));
            placeBoat(board, placement);
            res.add(placement);
        }

        return res;
    }

    private List<Boat> getPossibleBoatPlacements(Board board, int boatLength) {
        List<Boat> res = new ArrayList<>();
        for (int y = 0; y < board.getBoardHeight(); y++) {
            for (int x = 0; x < board.getBoardWidth(); x++) {
                for (int direction = 0; direction <= 1; direction++) {
                    if (canPlaceBoat(board, x, y, direction, boatLength)) {
                        res.add(new Boat(new Point(x, y), direction, boatLength));
                    }
                }
            }
        }
        return res;
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
                if (c == 'x') {
                    return false;
                }
            }
        }

        return true;
    }

    private void placeBoat(Board board, Boat boat) {
        int bx = boat.getStartCord().x;
        int by = boat.getStartCord().y;
        int dir = boat.getDirection();
        int length = boat.getLength();

        int width = (dir == 0 ? length : 1);
        int height = (dir == 1 ? length : 1);

        for (int x = bx; x < bx + width; x++) {
            for (int y = by; y < by + height; y++) {
                board.set(x, y, 'x');
            }
        }
    }
}
