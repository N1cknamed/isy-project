package Gui;

import Ai.TttAI;
import Games.Tictactoe;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TictactoeGui extends Application {
    private final Tictactoe game = new Tictactoe();
    private final Button[][] buttons = new Button[3][3];
    private boolean gameOver = false;
    private boolean againstAI = false;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tic-Tac-Toe");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(5);
        grid.setVgap(5);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = new Button();
                button.setMinSize(100, 100);
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

        Scene scene = new Scene(root, 320, 360); // Increased height to accommodate mode selector

        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void checkWin() {
        if (game.checkWin(game.getPlayer())) {
            gameOver = true;
            for (int i = 0; i < game.winningCoords.length; i++) {
                int wRow = game.winningCoords[i][0];
                int wCol = game.winningCoords[i][1];
                buttons[wRow][wCol].getStyleClass().add("winning-button");
            }
            System.out.println("Player " + game.getPlayer() + " wins!");
        } else if (game.isBoardFull()) {
            gameOver = true;
            System.out.println("It's a draw!");
        }
    }

    private void handleButtonClick(int row, int col) {
        if (!gameOver && game.move(row, col)) {
            buttons[row][col].setText(String.valueOf(game.getPlayer()));
            checkWin();
            game.switchPlayer();

            if (againstAI && !gameOver && game.getPlayer() == 'o') { // If playing against AI
                int[] aiMove = new TttAI('o', 'x').findBestMove(game);
                int aiRow = aiMove[1];
                int aiCol = aiMove[2];
                if (game.move(aiRow, aiCol)) {
                    buttons[aiRow][aiCol].setText(String.valueOf(game.getPlayer()));
                    checkWin();
                    game.switchPlayer();
                }
            }
        }
    }
}