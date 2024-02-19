package gui;

import battleship.BattleshipGame;
import framework.Board;
import framework.Game;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class BattleShipsGui extends Application {

    private final BattleshipGame game = new BattleshipGame();
    private static final Button[][] buttons = new Button[10][10];
    private boolean gameOver = false;
    private boolean againstAI = false;

    public static void updateButtonsFromOutside(Game game) {
        Platform.runLater(() -> updateButtons((BattleshipGame) game));
    }

    private static void updateButtons(BattleshipGame game) {

        //print test 10 times

        Board board = game.getBoard();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (buttons[row][col] != null) {
                    buttons[row][col].setText(String.valueOf(board.get(col, row)));
                }
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Place your boats");


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setHgap(5);
        grid.setVgap(5);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Button button = new Button();
                button.setMinSize(40, 40);
                button.getStyleClass().add("game-button2");
                buttons[row][col] = button;

                int finalRow = row;
                int finalCol = col;
                button.setOnAction(e -> handleButtonClick(finalRow, finalCol));
                grid.add(button, col, row);
            }
        }

        HBox messageBox = new HBox();
        messageBox.setPadding(new Insets(10, 10, 10, 10));
        Label titleLabel = new Label();
        titleLabel.setText("Place your boats");
        titleLabel.getStyleClass().add("title");
        messageBox.getChildren().addAll(titleLabel);

        Button confirmButton = new Button();
        confirmButton.setText("Confirm");
        confirmButton.getStyleClass().add("confirmButton");
        messageBox.getChildren().addAll(confirmButton);

        Button exitButton =  new Button();
        exitButton.setText("Exit Game");
        exitButton.getStyleClass().add("exitButton");
        messageBox.getChildren().addAll(exitButton);

        VBox root = new VBox();
        root.getChildren().addAll(messageBox, grid, exitButton, confirmButton);

        Scene scene = new Scene(root, 375, 465); // Increased height to accommodate mode selector

        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void ConfirmButton(){

    }



    private void handleButtonClick(int row, int col) {

    }
}


