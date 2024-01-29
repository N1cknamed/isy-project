package gui;

import battleship.BattleshipGame;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class BattleShipsGui extends Application {
    private final BattleshipGame game = new BattleshipGame();
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

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
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

        VBox root = new VBox();
        root.getChildren().addAll(grid, messageBox);

        Scene scene = new Scene(root, 375, 400); // Increased height to accommodate mode selector

        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void handleButtonClick(int row, int col) {
//        for (BattleshipCliPlayer player : players) {
//            player.setMove(row, col);
//        }
    }
}


