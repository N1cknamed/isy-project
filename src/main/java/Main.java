import Gui.*;
import Ai.*;
import Games.*;
import Server.*;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        // login page
//        Login.launch(Login.class, args);
//        String enteredText = Login.getEnteredText();
//        System.out.println("Entered Text: " + enteredText);

        // tic tac toe gui (can choose to play against ai) TODO: reset game after switching game type
//        TictactoeGui.launch(TictactoeGui.class, args);

        // tic tac toe ai cli (x is player o is ai)
//        Tictactoe game = new Tictactoe();
//        game.playGame();

        // Battleships
//        BattleShips game = new BattleShips();
//        game.printBoard();
        
        // server
        /*
        to start the server in cmd type: java -jar newgameserver-1.0.jar
        then get the ip and port of the server and type those in the appropriate inputs
        */
        ServerController x = new ServerController("localhost", 7789);
        Message message = x.getMessage();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String userInput = scanner.nextLine();
            x.sendMessage(userInput);
            synchronized (message) {
                try {
                    message.wait();
                    System.out.println(message.getMessage());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
