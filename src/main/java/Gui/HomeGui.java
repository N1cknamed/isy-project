package Gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import Gui.TictactoeGui;


public class HomeGui extends Application {
    @FXML
    private RadioButton tictactoe;
    @FXML
    private RadioButton battleship;

    @FXML
    private ToggleGroup toggleGroup;

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

        if (toggleGroup.getSelectedToggle()==null){
                // No radio button selected, show an error or take appropriate action
                System.out.println("Please select a radio button before starting.");
                return;
            }

        if (toggleGroup.getSelectedToggle() == tictactoe) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("test.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 300
            );

        } else if (toggleGroup.getSelectedToggle() == battleship) {
            // Implement online navigation logic here
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("test.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 500, 500
            );

            // Perform any initialization or configuration of the controller here if needed.

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    public void btnCancel(ActionEvent actionEvent){
        System.out.println("Cancel button clicked");
        // Exit the application
        Platform.exit();
        System.exit(0);
    }
}

