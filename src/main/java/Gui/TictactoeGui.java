package Gui;

import Games.CliPlayer;
import Games.GuiPlayer;
import Games.Tictactoe;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class TictactoeGui extends Application {
    private final Tictactoe game = new Tictactoe(new CliPlayer(), new CliPlayer());
    private static final Button[][] buttons = new Button[3][3];

    private static final List<GuiPlayer> players = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    public static void updateButtonsFromOutside(Tictactoe game) {
        Platform.runLater(() -> {
            updateButtons(game);
        });
    }

    private static void updateButtons(Tictactoe game) {
        char[][] board = game.getBoard();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText(String.valueOf(board[row][col]));
            }
        }

        if (game.winner) {
            for (int i = 0; i < game.winningCoords.length; i++) {
                int wRow = game.winningCoords[i][0];
                int wCol = game.winningCoords[i][1];
                buttons[wRow][wCol].getStyleClass().add("winning-button");
            }
        }
    }

    public static void registerPlayer(GuiPlayer player) {
        players.add(player);
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


        VBox root = new VBox();
        root.getChildren().addAll(grid, messageBox);

        Scene scene = new Scene(root, 320, 360); // Increased height to accommodate mode selector

        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void checkWin() {
        if (game.checkWin(game.getPlayer())) {
            for (int i = 0; i < game.winningCoords.length; i++) {
                int wRow = game.winningCoords[i][0];
                int wCol = game.winningCoords[i][1];
                buttons[wRow][wCol].getStyleClass().add("winning-button");
            }
            System.out.println("Player " + game.getPlayer() + " wins!");
        } else if (game.isBoardFull()) {
            System.out.println("It's a draw!");
        }
    }

    private void handleButtonClick(int row, int col) {
        for (GuiPlayer player : players) {
            if (player.getIsTurn()) player.setSelectedMove(row, col);
        }
    }
}