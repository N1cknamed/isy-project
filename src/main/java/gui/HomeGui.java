package gui;

import framework.Game;
import framework.GameController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ttt.players.TttAiPlayer;
import ttt.players.TttGuiPlayer;

import java.io.IOException;
import java.net.URL;

import ttt.TttGame;
import ttt.subscribers.TttGuiSubscriber;


public class HomeGui extends Application {
    @FXML
    private RadioButton tictactoe;
    @FXML
    private RadioButton battleship;

    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private ComboBox<String> modeSelector;

    @Override
    public void start(Stage stage) throws IOException {
        URL url = getClass().getResource("main-menu-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Tictactoe / Reversi client");
        stage.setScene(scene);
        stage.show();

    }

    public void navigateOnline(ActionEvent actionEvent) {
        // Implement online navigation logic here
    }

    public void navigateOffline(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 300
        );

        // Perform any initialization or configuration of the controller here if needed.

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    public void btnStart(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        if (toggleGroup.getSelectedToggle() == null || modeSelector.getValue() == null) {
            // No radio button selected, show an error or take appropriate action
            System.out.println("Please select a radio button or make a choice before starting.");
            return;
        }

        if (toggleGroup.getSelectedToggle() == tictactoe && modeSelector.getValue().equals("PVP")) {
            Game game = new TttGame();
            // TODO add player selection
            GameController controller = new GameController(game, TttGuiPlayer::new, TttGuiPlayer::new);

            TttGui tttGui = new TttGui();
            tttGui.start(stage);
            controller.registerSubscriber(new TttGuiSubscriber());
            Thread t = new Thread(() -> {
                controller.gameLoop();
                // TODO return to home screen
            });
            t.start();

        } else if (toggleGroup.getSelectedToggle() == battleship) {
            BattleShipsGui battleShipsGui = new BattleShipsGui();
            battleShipsGui.start(stage);
        }
        else if (toggleGroup.getSelectedToggle() == tictactoe && modeSelector.getValue().equals("AI")) {
            Game game = new TttGame();
            // TODO add player selection
            GameController controller = new GameController(game, TttAiPlayer::new, TttGuiPlayer::new);

            TttGui tttGui = new TttGui();
            tttGui.start(stage);
            controller.registerSubscriber(new TttGuiSubscriber());
            Thread t = new Thread(() -> {
                controller.gameLoop();
                // TODO return to home screen
            });
            t.start();



            stage.show();

    }}

    public void modeSelector(ActionEvent actionEvent) {

    }


    public void btnCancel(ActionEvent actionEvent) {
        System.out.println("Cancel button clicked");
        // Exit the application
        Platform.exit();
        System.exit(0);
    }
}

