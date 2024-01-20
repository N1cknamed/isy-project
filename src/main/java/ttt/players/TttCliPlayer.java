package ttt.players;

import framework.Game;
import framework.Player;
import framework.PlayerType;

import java.awt.*;
import java.util.Scanner;

public class TttCliPlayer implements Player {
    private final char symbol;
    private final Scanner scanner;

    public TttCliPlayer(char symbol) {
        this.symbol = symbol;
        this.scanner = new Scanner(System.in);
    }


    @Override
    public PlayerType getPlayerType() {
        return PlayerType.CLI;
    }

    @Override
    public char getSymbol() {
        return symbol;
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
}
