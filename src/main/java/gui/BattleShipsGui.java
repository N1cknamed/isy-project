package gui;

import battleship.BattleshipGame;
import battleship.players.BattleshipGuiPlayer;
import battleship.players.BattleshipPlayerType;
import framework.Board;
import framework.Game;
import framework.PlayerType;
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
    private static final Button[][] buttons = new Button[8][8];
    private boolean gameOver = false;
    private boolean againstAI = false;
    private int direction = 0;
    private static int shooting = 0;
    private static int boatsPlaced = 0;


    public static void updateButtonsFromOutside(Game game) {
        Platform.runLater(() -> updateButtons((BattleshipGame) game));
    }

    public static void updateWinningBoard(Game game){
        Platform.runLater(() -> winningButtons(game));
    }

    private static void winningButtons(Game game){
        Board winning = null;
        BattleshipGame btlshpgm = (BattleshipGame) game;
        if(btlshpgm.getWinner()==btlshpgm.getCurrentPlayer()){
            winning = btlshpgm.getCurrentBoard();
        } else{
            winning = btlshpgm.getOpponentBoard();}

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (buttons[row][col] != null) {
                    //buttons[row][col].getStyleClass().add("game-button");
                    buttons[row][col].getStyleClass().remove("winning-button");
                    buttons[row][col].getStyleClass().remove("winning-button2");
                }
            }
        }

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (buttons[row][col] != null) {
                    String value = String.valueOf(winning.get(row, col));
                    buttons[row][col].setText("");
                    if (value.charAt(0) == 'm'){buttons[row][col].getStyleClass().add("winning-button");}
                    else if (value.charAt(0) == 'h' || value.charAt(0) == '2' || value.charAt(0) == '3'
                    || value.charAt(0) == '4' || value.charAt(0) == '6'){buttons[row][col].getStyleClass().add("winning-button2");}
                     }
                }
            }
        }


    private static void updateButtons(BattleshipGame game) {
//        Board board = game.getBoard();
//        for (int row = 0; row < 8; row++) {
//            for (int col = 0; col < 8; col++) {
//                if (buttons[row][col] != null) {
//                    buttons[row][col].setText(String.valueOf(board.get(row, col)));
//                }
//            }
//        }

        BattleshipGame btlshpgm = (BattleshipGame) game;
//        if(btlshpgm.getCurrentPlayer().getPlayerType().isLocal()){
//            guiboard = btlshpgm.getCurrentBoard();
//        }
//        else{
//            guiboard = null;}
        Board guiboard = btlshpgm.getCurrentBoard();
        if (btlshpgm.getCurrentPlayer().getPlayerType() == BattleshipPlayerType.GUI) {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (buttons[row][col] != null) {
                        //buttons[row][col].getStyleClass().add("game-button");
                        buttons[row][col].getStyleClass().remove("winning-button");
                        buttons[row][col].getStyleClass().remove("winning-button2");
                    }
                }
            }

            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (buttons[row][col] != null) {
                        String value = String.valueOf(guiboard.get(row, col));
                        buttons[row][col].setText("");
                        if (value.charAt(0) == 'm') {
                            buttons[row][col].getStyleClass().add("winning-button");
                        }
                        if (value.charAt(0) == 'h') {
                            buttons[row][col].getStyleClass().add("winning-button2");
                        }
                        else if (value.charAt(0) == '2' || value.charAt(0) == '3'
                                || value.charAt(0) == '4' || value.charAt(0) == '6') {
                            buttons[row][col].getStyleClass().add("winning-button3");
                        }
                    }
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
                button.setOnAction(e -> handleButtonClickPlacing(finalRow, finalCol));
                grid.add(button, row, col);
            }
        }

        HBox messageBox = new HBox();
        messageBox.setPadding(new Insets(10, 10, 10, 10));
        Label titleLabel = new Label();
        titleLabel.setText("Place your boats");
        titleLabel.getStyleClass().add("title");
        messageBox.getChildren().addAll(titleLabel);

        Button horizontalButton = new Button();
        horizontalButton.setText("Horizontal");
        horizontalButton.getStyleClass().add("horizontalButton");
        messageBox.getChildren().addAll(horizontalButton);
        horizontalButton.setOnAction(e -> setDirection(0));

        Button verticalButton = new Button();
        verticalButton.setText("Vertical");
        verticalButton.getStyleClass().add("verticalButton");
        messageBox.getChildren().addAll(verticalButton);
        verticalButton.setOnAction(e -> setDirection(1));

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

    private void setToShooting() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                int finalRow = row;
                int finalCol = col;
                if (buttons[row][col] != null) {
                    buttons[row][col].getStyleClass().remove("winning-button2");
                    buttons[row][col].setOnAction(e -> handleButtonClickShooting(finalRow, finalCol));
                }
            }
        }
    }
    
    private void placeBoat(int row, int col, int size) {
        String text = String.valueOf(size);
        if (direction == 0) {
            for (int i = row; i < row + size; i++) {
//                buttons[i][col].setText(text);
                buttons[i][col].getStyleClass().add("winning-button2");
            }
        } else {
            for (int i = col; i < col + size; i++) {
//                buttons[row][i].setText(text);
                buttons[i][col].getStyleClass().add("winning-button2");
            }
        }
        boatsPlaced++;
    }

    private boolean isValidPlacement(int row, int col, int size) {
        if (row < 0 || col < 0) {
            return false;
        } else if (direction == 0) {
            if (row + size > 8) {
                return false;
            }
            for (int i = row; i < row + size; i++) {
                if (buttons[i][col].getText() != "") {
                    return false;
                }
                if (i - 1 >= 0 && buttons[i - 1][col].getText() != "") {
                    return false;
                }
                if (col > 0 && buttons[i][col - 1].getText() != "") {
                    return false;
                }
                if (i + 1 < 8 && buttons[i + 1][col].getText() != "") {
                    return false;
                }
                if (col + 1 < 8 && buttons[i][col + 1].getText() != "") {
                    return false;
                }
            }
        } else {
            if (col + size > 8) {
                return false;
            }
            for (int i = col; i < col + size; i++) {
                if (buttons[row][i].getText() == "s") {
                    return false;
                }
                if (row - 1 >= 0 && buttons[row - 1][i].getText() != "") {
                    return false;
                }
                if (i > 0 && buttons[row][i - 1].getText() != "") {
                    return false;
                }
                if (row + 1 < 8 && buttons[row + 1][i].getText() != "") {
                    return false;
                }
                if (i + 1 < 8 && buttons[row][i + 1].getText() != "") {
                    return false;
                }
            }
        }
        return true;
    }

    public void ConfirmButton(){

    }

    private void handleButtonClickPlacing(int row, int col) {
        int size = 0;
        if (boatsPlaced == 0) {size = 2;}
        if (boatsPlaced == 1) {size = 3;}
        if (boatsPlaced == 2) {size = 4;}
        if (boatsPlaced == 3) {size = 6;}

        if (boatsPlaced < 4) {
            if (isValidPlacement(row, col, size)) {
                placeBoat(row, col, size);
                BattleshipGuiPlayer.setMove(row, col);
            }
            
        }
        if (boatsPlaced >= 4) {
            setToShooting();
        }
        
    }

    private void handleButtonClickShooting(int row, int col) {
        BattleshipGuiPlayer.setMove(row, col);
    }

    private void setDirection(int direction) {
        this.direction = direction;
        BattleshipGuiPlayer.setDirection(direction);
    }

}


