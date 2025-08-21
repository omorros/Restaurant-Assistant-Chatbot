import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/*
The initial page represents the starting point of the application.
It provides options for users to Register or Login.
 */
public class InitialPage extends Application {

    //The main entry point for the JavaFX application setting up the login and register options.
    @Override
    public void start(Stage primaryStage) {
        // Buttons for login and registration.
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        // Redirect to LoginForm.
        loginButton.setOnAction(e -> {
            LoginForm loginForm = new LoginForm();
            try {
                loginForm.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Redirect to RegistrationForm.
        registerButton.setOnAction(e -> {
            RegistrationForm registrationForm = new RegistrationForm();
            try {
                registrationForm.start(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Create a vertical box layout for the initial page.
        VBox layout = new VBox(20, loginButton, registerButton);
        layout.setPrefSize(300, 200);

        Scene scene = new Scene(layout);

        primaryStage.setTitle("Welcome"); // Set the title of the window
        primaryStage.setScene(scene); // Add the scene to the primary stage
        primaryStage.show(); // Display the stage
    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
