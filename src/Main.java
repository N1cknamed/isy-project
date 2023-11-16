import Server.ServerController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // login page
//        Login.launch(Login.class, args);
//        String enteredText = Login.getEnteredText();
//        System.out.println("Entered Text: " + enteredText);

        // tic tac toe gui
//        TictactoeGui.launch(TictactoeGui.class, args);

        // tic tac toe ai cli (x is player o is ai)
//        Tictactoe game = new Tictactoe();
//        game.playGame();

        // Battleships
//        BattleShips game = new BattleShips();
//        game.printBoard();
        ServerController x = new ServerController("localhost", 7789);
        String message = x.getMessage();

        while (true) {
            synchronized (message) {
                try {
                    message.wait();
                    System.out.println(message);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}