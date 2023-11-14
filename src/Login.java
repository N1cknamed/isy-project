import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Login extends Application {

    private static TextField textField; // Declare the TextField as a class variable

    public static void main(String[] args) {
        launch(args);

        // After the JavaFX application is launched, you can get the text
        String enteredText = getEnteredText();
        System.out.println("Entered Text: " + enteredText);
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a TextField
        textField = new TextField();

        // Create a Submit Button
        Button submitButton = new Button("Submit");

        // Create an event handler for the button click
        submitButton.setOnAction(e -> handleButtonClick());

        // Create a layout and add the TextField and Submit Button
        VBox root = new VBox(textField, submitButton);

        // Create a Scene
        Scene scene = new Scene(root, 300, 200);

        // Set the Scene to the Stage
        primaryStage.setScene(scene);

        // Set the title of the Stage
        primaryStage.setTitle("Text Input Example");

        // Show the Stage
        primaryStage.show();
    }

    private void handleButtonClick() {
        // Close the window
        Stage stage = (Stage) textField.getScene().getWindow();
        stage.close();
    }

    // Getter method to retrieve the text from the TextField
    public static String getEnteredText() {
        return textField.getText();
    }
}