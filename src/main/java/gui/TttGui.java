package gui;

import framework.Board;
import framework.Game;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ttt.TttGuiPlayer;
import ttt.TttGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TttGui extends Application {
    private static final Button[][] buttons = new Button[3][3];
    private static final List<TttGuiPlayer> players = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    public static void updateButtonsFromOutside(Game game) {
        Platform.runLater(() -> updateButtons(game));
    }

    public static void winningButtonsFromOutside() {
        Platform.runLater(TttGui::winningButtons);
    }

    /**
     * winningButtons is for highlighting the winning coords in the gui
     */
    private static void winningButtons() {
//        int[][] winning = TttGame.getWinningCoords();
//
//        for(int[] winningCoords: winning){
//            int wRow = winningCoords[0];
//            int wCol = winningCoords[1];
//            buttons[wRow][wCol].getStyleClass().add("winning-button");
//        }
        showGameOverAlert();
    }

    private static void updateButtons(Game game) {
        Board board = game.getBoard();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col] != null) {
                    buttons[row][col].setText(String.valueOf(board.get(col, row)));
                }
            }
        }

        //TODO: maybe make separate function when game has ended that it runs that function
//        if (game.winner) {
//            for (int i = 0; i < game.winningCoords.length; i++) {
//                int wRow = game.winningCoords[i][0];
//                int wCol = game.winningCoords[i][1];
//                buttons[wRow][wCol].getStyleClass().add("winning-button");
//            }
//        }

        //TODO: maybe make seperate function when game has ended that it runs that functon
        if (board.isBoardFull()){
            System.out.println("The Game is over! " + board.isBoardFull());
            showGameOverAlert();
        }
    }
    private static void showGameOverAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("The Game is over!");
        alert.showAndWait();
    }

    public static void registerPlayer(TttGuiPlayer player) {
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
                button.getStyleClass().add("game-button");
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

//       Increased height to accommodate mode selector
        Scene scene = new Scene(root, 335, 360);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleButtonClick(int row, int col) {
        players.forEach(player -> TttGuiPlayer.setMove(row, col));
    }
}