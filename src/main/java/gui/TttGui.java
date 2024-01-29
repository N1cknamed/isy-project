package gui;

import framework.Board;
import framework.Game;
import framework.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ttt.TttGame;
import ttt.players.TttGuiPlayer;

import java.util.Objects;

public class TttGui extends Application {
    private static final Button[][] buttons = new Button[3][3];
   // private static final List<TttGame> players = new ArrayList<>();
    private static Label player1Label;
    private static Label player2Label;
    private static Label currentPlayerLabel;


    public static void main(String[] args) {
        launch(args);
    }

    public static void updateButtonsFromOutside(Game game) {
        Platform.runLater(() -> updateButtons((TttGame) game));
    }

    public static void winningButtonsFromOutside(TttGame game) {
        Platform.runLater(() ->winningButtons(game));
    }

    /**
     * winningButtons is for highlighting the winning coords in the gui
     */
    private static void winningButtons(TttGame game) {
        int[][] winning = TttGame.getWinningCoords();

        for(int[] winningCoords: winning){
            int wRow = winningCoords[0];
            int wCol = winningCoords[1];
            buttons[wRow][wCol].getStyleClass().add("winning-button");
        }
        showGameOverAlert(game);
    }

    private static void updateButtons(TttGame game) {
        Board board = game.getBoard();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (buttons[row][col] != null) {
                    buttons[row][col].setText(String.valueOf(board.get(col, row)));
                }
            }
        }

        //TODO: maybe make separate function when game has ended that it runs that function

        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        player1Label.setText("Player 1: " + player1.getSymbol());
        player2Label.setText("Player 2: " + player2.getSymbol());
        if (game.getCurrentPlayer().getSymbol() == player1.getSymbol()) {
            currentPlayerLabel.setText("Current player = " + player1.getSymbol());
        } else {
            currentPlayerLabel.setText("Current player = " + player2.getSymbol());

        }
        //TODO: maybe make seperate function when game has ended that it runs that functon
        if (board.isBoardFull()){
           // System.out.println("The Game is over! " + board.isBoardFull());
            showGameOverAlert(game);}
    }
    private static void showGameOverAlert(Game game){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        if (game.getWinner()==null){alert.setContentText("It's a draw!");}
        else {
        alert.setContentText("The Game is over!\n" + "Winning player = " + game.getWinner().getSymbol());}

        ButtonType stopButton = new ButtonType("Stop");
        ButtonType replayButton = new ButtonType("Reset");

        alert.getButtonTypes().setAll(stopButton, replayButton);
        ButtonType result = alert.showAndWait().orElse(null);

        if (result == stopButton) {
            Platform.exit();
        }
        if (result == replayButton) {
            updateButtons((TttGame) game);
//            HomeGui.launch(HomeGui.class);
        }
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

        player1Label = new Label("Player 1: " );
        player2Label = new Label(" Player 2: " );
        currentPlayerLabel = new Label(" CurrentPlayer");
        messageBox.getChildren().addAll(player1Label, player2Label, currentPlayerLabel);

        VBox root = new VBox();
        root.getChildren().addAll(grid, messageBox);

//       Increased height to accommodate mode selector
        Scene scene = new Scene(root, 335, 360);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleButtonClick(int row, int col) {
        // Perform the move for the current player
        TttGuiPlayer.setMove(row, col);
    }
}