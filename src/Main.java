/*import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {
    private Tictactoe game = new Tictactoe();
    private Button[][] buttons = new Button[3][3];
    private boolean gameOver = false;

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

        Scene scene = new Scene(grid, 320, 320);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleButtonClick(int row, int col) {
        if (!gameOver && game.move(row, col)) {
            buttons[row][col].setText(String.valueOf(game.getPlayer()));
            if (game.checkWin(game.getPlayer())) {
                gameOver = true;
                System.out.println("Player " + game.getPlayer() + " wins!");
            } else if (!game.isBoardFull()) {
                gameOver = true;
                System.out.println("It's a draw!");
            }
            game.switchPlayer();
        }
    }
}
*/

public class Main {
    public static void main(String[] args) {
        Tictactoe game = new Tictactoe();
        game.playGame();
    }
}
