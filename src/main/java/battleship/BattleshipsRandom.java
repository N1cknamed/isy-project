package battleship;

import framework.Game;
import framework.Board;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class BattleshipsRandom {

    Boolean foundShip = false;
    Point lastSHot = new Point(-1,-1);
    ArrayList<Point> lastHits = new ArrayList<>();

    Board localBoard = new Board(8,8);



    public Point getMove(Game game) {
        Board board = game.getBoard();
        Point move;

        if (board.get(lastSHot) == 'h') {
            localBoard.set(lastSHot, 'x');
            lastHits.add(lastSHot);
            foundShip = true;
        } else if (Character.isDigit(board.get(lastSHot))) {
            for (int i = 0; i < lastHits.size(); i++) {
                Point p = lastHits.get(i);
                localBoard.set(p, 'x');
                localBoard.set(p.x-1, p.y, 'x');
                localBoard.set(p.x+1, p.y, 'x');
                localBoard.set(p.x, p.y+1, 'x');
                localBoard.set(p.x, p.y-1, 'x');
            }
            lastHits.clear();
            foundShip = false;
        }

        if (foundShip) {
            if (lastHits.size() == 1){
                move = new Point(lastHits.get(0).x + 1, lastHits.get(0).y);
                if (board.get(move) == 0 && localBoard.get(move) == 0 && game.isValidMove(move)) {
                    lastSHot = move;
                    localBoard.set(move, 's');
                    return move;
                }
                move = new Point(lastHits.get(0).x - 1, lastHits.get(0).y);
                if (board.get(move) == 0 && localBoard.get(move) == 0 && game.isValidMove(move)) {
                    lastSHot = move;
                    localBoard.set(move, 's');
                    return move;
                }
                move = new Point(lastHits.get(0).x, lastHits.get(0).y + 1);
                if (board.get(move) == 0 && localBoard.get(move) == 0 && game.isValidMove(move)) {
                    lastSHot = move;
                    localBoard.set(move, 's');
                    return move;
                }
                move = new Point(lastHits.get(0).x, lastHits.get(0).y - 1);
                if (board.get(move) == 0 && localBoard.get(move) == 0 && game.isValidMove(move)) {
                    lastSHot = move;
                    localBoard.set(move, 's');
                    return move;
                }
            } else {
                int direction = lastHits.get(0).x == lastHits.get(1).x ? 1 : 0;
                for (int i = 0; i < lastHits.size(); i++) {
                    if (direction == 0) {
                        move = new Point(lastHits.get(i).x + 1, lastHits.get(i).y);
                        if (board.get(move) == 0 && localBoard.get(move) == 0 && game.isValidMove(move)) {
                            lastSHot = move;
                            localBoard.set(move, 's');
                            return move;
                        }
                        move = new Point(lastHits.get(i).x - 1, lastHits.get(i).y);
                        if (board.get(move) == 0 && localBoard.get(move) == 0 && game.isValidMove(move)) {
                            lastSHot = move;
                            localBoard.set(move, 's');
                            return move;
                        }
                    } else {
                        move = new Point(lastHits.get(i).x, lastHits.get(i).y + 1);
                        if (board.get(move) == 0 && localBoard.get(move) == 0 && game.isValidMove(move)) {
                            lastSHot = move;
                            localBoard.set(move, 's');
                            return move;
                        }
                        move = new Point(lastHits.get(i).x, lastHits.get(i).y - 1);
                        if (board.get(move) == 0 && localBoard.get(move) == 0 && game.isValidMove(move)) {
                            lastSHot = move;
                            localBoard.set(move, 's');
                            return move;
                        }
                    }
                }
            }
        }



        Random random = new Random();
        do {
            // random move
            move = new Point(random.nextInt(board.getBoardWidth()), random.nextInt(board.getBoardHeight()));
        } while (!game.isValidMove(move) && localBoard.get(move) != 0);

        lastSHot = move;
        localBoard.set(move, 's');
        return move;
    }
}
