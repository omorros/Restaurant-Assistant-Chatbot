import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
This class provides the interface for the user to login.
Enables users to input their username and password.
 */
public class LoginForm extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Create a label and input field for the username.
        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        // Create a label and input field for the password (hidden characters)
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        // Button for logging in.
        Button loginButton = new Button("Login");
        // Button to go back to the initial page.
        Button backButton = new Button("Back");

        // Login action.
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Validate login credentials using the UserDAO class.
            UserDAO userDAO = new UserDAO();
            if (userDAO.loginUser(username, password)) {
                // If credentials are valid, navigate to the ChatbotInterface.
                ChatbotInterface chatbotInterface = new ChatbotInterface(username);
                try {
                    chatbotInterface.start(primaryStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password. Please try again.");
            }
        });

        // Back button action to return to the initial page.
        backButton.setOnAction(e -> {
            InitialPage initialPage = new InitialPage();
            try {
                initialPage.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Create a vertical box layout for the login form.
        VBox layout = new VBox(10, usernameLabel, usernameField, passwordLabel, passwordField, loginButton, backButton);
        layout.setPrefSize(300, 200);

        // Create a scene with the layout.
        Scene scene = new Scene(layout);

        primaryStage.setTitle("Login"); // Set the window title.
        primaryStage.setScene(scene); // Add the scene to the primary stage.
        primaryStage.show(); // Display the stage.
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title); // Set title of the alert.
        alert.setContentText(message); // Set message content.
        alert.showAndWait(); // Display alert and wait for user response.
    }
}
