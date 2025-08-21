import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
RegistrationForm provides the interface for new user registration.
Users are required to input their details such as full name, username, email, and password.
 */

public class RegistrationForm extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Input field for the user's full name.
        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Full Name");

        // Input field for the username.
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");

        // Input field for the user's email address.
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        // Password field for the user's password (hidden characters).
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        // Button for submitting the registration form.
        Button registerButton = new Button("Register");
        // Button to return to the initial page.
        Button backButton = new Button("Back");

        // Registration action.
        registerButton.setOnAction(e -> {
            String fullName = fullNameField.getText();
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            // Use the UserDAO class to attempt user registration.
            UserDAO userDAO = new UserDAO();
            boolean success = userDAO.registerUser(fullName, username, email, password, null, null);

            if (success) {
                // If registration is successful, navigate to the ChatbotInterface.
                ChatbotInterface chatbotInterface = new ChatbotInterface(username);
                try {
                    chatbotInterface.start(primaryStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                // Show an error alert if registration fails
                showAlert(Alert.AlertType.ERROR, "Registration Failed", "Could not register. Please try again.");
            }
        });

        // Back button action to return to the initial page
        backButton.setOnAction(e -> {
            InitialPage initialPage = new InitialPage();
            try {
                initialPage.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Layout for the registration form.
        VBox layout = new VBox(10, fullNameField, usernameField, emailField, passwordField, registerButton, backButton);
        layout.setPrefSize(300, 250);

        Scene scene = new Scene(layout);

        primaryStage.setTitle("Register"); // Set the window title.
        primaryStage.setScene(scene); // Add the scene to the primary stage.
        primaryStage.show(); // Display the stage.

    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType); // Create a new alert.
        alert.setTitle(title); // Set the title of the alert.
        alert.setContentText(message); // Set the message content.
        alert.showAndWait(); // Display the alert.
    }
}
