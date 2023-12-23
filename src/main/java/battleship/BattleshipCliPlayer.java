package battleship;

import Framework.Game;
import Framework.Player;

import java.awt.*;
import java.util.Scanner;

public class BattleshipCliPlayer implements BattleshipPlayer {

    private final char symbol;
    private final Scanner scanner;

    public BattleshipCliPlayer(char symbol) {
        this.symbol = symbol;
        this.scanner = new Scanner(System.in);
    }


    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public char getSymbol() {
        return 0;
    }

    @Override
    public Point doMove(Game game) {
        int x, y;
        do {
            System.out.printf("Player %s's turn\n", symbol);
            System.out.print("Enter row (0-2): ");
            y = scanner.nextInt();
            System.out.print("Enter column (0-2): ");
            x = scanner.nextInt();
        } while (!game.isValidMove(new Point(x, y)));

        return new Point(x, y);
    }

    @Override
    public void placeBoats() {
        System.out.println("test");
    }
}
