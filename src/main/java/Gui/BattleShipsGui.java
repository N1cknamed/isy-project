package Gui;

import Games.BattleShips;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class BattleShipsGui extends Application {
    private final BattleShips game = new BattleShips();
    private final Button[][] buttons = new Button[10][10];
    private boolean gameOver = false;
    private boolean againstAI = false;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Battleships Game");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(5);
        grid.setVgap(5);

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Button button = new Button();
                button.setMinSize(40, 40);
                buttons[row][col] = button;

                int finalRow = row;
                int finalCol = col;
                button.setOnAction(e -> handleButtonClick(finalRow, finalCol));
                grid.add(button, col, row);
            }
        }

        HBox messageBox = new HBox();
        messageBox.setPadding(new Insets(10, 10, 10, 10));

        ComboBox<String> modeSelector = new ComboBox<>();
        modeSelector.getItems().addAll("Two Players", "Against AI");
        modeSelector.setValue("Two Players"); // Default selection
        modeSelector.setOnAction(e -> {
            String selectedMode = modeSelector.getValue();
            againstAI = selectedMode.equals("Against AI");
        });

        VBox root = new VBox();
        root.getChildren().addAll(modeSelector, grid, messageBox);

        Scene scene = new Scene(root, 465, 500); // Increased height to accommodate mode selector

        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void checkWin() {
        if (game.checkWin(game.getPlayer())) {
            gameOver = true;
            // Handle win logic for Battleships
            System.out.println("Player " + game.getPlayer() + " wins!");
        } else if (game.isGameOver()) {
            gameOver = true;
            // Handle game over logic, e.g., display a message, disable buttons, etc.
            System.out.println("Game Over!");
        }
    }
    private void handleButtonClick(int row, int col) {
        if (!gameOver && game.makeMove(row, col)) {
            buttons[row][col].setText("X"); // Update text or styling for player move
            game.switchPlayer();
            if (againstAI && !gameOver && game.getPlayer() == 'o') {
                RandomAI randomAI = new RandomAI();
                int[] aiMove = randomAI.generateRandomMove();
                int aiRow = aiMove[0];
                int aiCol = aiMove[1];
                if (game.makeMove(aiRow, aiCol)) {
                    buttons[aiRow][aiCol].setText(String.valueOf(game.getPlayer()));
                    game.switchPlayer();
                }
            }

            checkWin();
        }
    }
}


