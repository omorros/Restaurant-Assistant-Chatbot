import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
ChatbotInterface class provides the main user interface for interacting with the restaurant chatbot.
Users can navigate between tabs for various functionalities.
 */

public class ChatbotInterface extends Application {
    private String username;

    public ChatbotInterface(String username) {
        this.username = username;
    }

    public ChatbotInterface() {
        this.username = "DefaultUser"; // Default username if none provided
    }

    @Override
    public void start(Stage primaryStage) {
        // Create a TabPane to organize different functionalities in tabs.
        TabPane tabPane = new TabPane();

        // Chat Tab
        Tab chatTab = new Tab("Chatbot");
        VBox chatLayout = new VBox(10);
        TextArea chatArea = new TextArea();
        chatArea.setEditable(false); // Prevent editing chat history.
        TextField userInput = new TextField();
        userInput.setPromptText("Type your question here...");
        userInput.setPrefWidth(400); // Make the input field longer.
        Button sendButton = new Button("Send");
        chatLayout.getChildren().addAll(chatArea, new HBox(10, userInput, sendButton));
        chatTab.setContent(chatLayout);

        // FAQs Tab.
        Tab faqTab = new Tab("FAQs");
        TextArea faqArea = new TextArea(getFAQsFullQuestions());
        faqArea.setEditable(false);
        faqTab.setContent(new VBox(10, faqArea));

        // Reservations Tab.
        Tab reservationTab = new Tab("Reservations");
        VBox reservationLayout = new VBox(10);
        Button makeReservationButton = new Button("Make Reservation");
        makeReservationButton.setOnAction(e -> openReservationWindow());
        Button viewReservationsButton = new Button("View Reservations");
        viewReservationsButton.setOnAction(e -> openViewReservationsWindow());
        Button cancelReservationButton = new Button("Cancel Reservation");
        cancelReservationButton.setOnAction(e -> openCancelReservationWindow());
        reservationLayout.getChildren().addAll(makeReservationButton, viewReservationsButton, cancelReservationButton);
        reservationTab.setContent(reservationLayout);

        // Menu Tab.
        Tab menuTab = new Tab("Menu");
        TextArea menuArea = new TextArea(getMenu());
        menuArea.setEditable(false);
        menuTab.setContent(menuArea);

        // Feedback Tab.
        Tab feedbackTab = new Tab("Feedback");
        VBox feedbackLayout = new VBox(10);
        Label ratingLabel = new Label("Rate your experience (1-5):");
        ChoiceBox<Integer> ratingChoiceBox = new ChoiceBox<>();
        ratingChoiceBox.getItems().addAll(1, 2, 3, 4, 5);
        TextArea commentArea = new TextArea();
        commentArea.setPromptText("Leave your feedback here...");
        Button submitFeedbackButton = new Button("Submit Feedback");
        submitFeedbackButton.setOnAction(e -> saveFeedback(ratingChoiceBox.getValue(), commentArea.getText()));
        feedbackLayout.getChildren().addAll(ratingLabel, ratingChoiceBox, commentArea, submitFeedbackButton);
        feedbackTab.setContent(feedbackLayout);

        // History Tab.
        Tab historyTab = new Tab("Conversation History");
        TextArea historyArea = new TextArea(getConversationHistory());
        historyArea.setEditable(false);
        historyTab.setContent(historyArea);

        // Log Out Button.
        Button logoutButton = new Button("Log Out");
        logoutButton.setOnAction(e -> {
            // Close current window and open login/register window.
            primaryStage.close();
            Platform.runLater(() -> new LoginForm().start(new Stage()));
        });

        // Add all tabs and logout button to layout.
        tabPane.getTabs().addAll(chatTab, faqTab, reservationTab, menuTab, feedbackTab, historyTab);
        VBox mainLayout = new VBox(10, tabPane, logoutButton);

        // Set Scene and Stage.
        Scene scene = new Scene(mainLayout, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Restaurant Chatbot Interface");
        primaryStage.show();

        // Chat functionality with improved response matching.
        sendButton.setOnAction(e -> {
            String userQuery = userInput.getText().toLowerCase().trim();
            if (!userQuery.isEmpty()) {
                chatArea.appendText("You: " + userQuery + "\n");
                String response = handleUserQuery(userQuery);
                chatArea.appendText("Bot: " + response + "\n");
                storeConversation(username, userQuery, response);
                userInput.clear();
            }
        });
    }

    // Improved word matching for better responses.
    private String handleUserQuery(String query) {
        Map<String, String> faqMap = getFAQMap();
        for (String key : faqMap.keySet()) {
            if (query.contains(key.toLowerCase()) || query.matches(".*\\b" + key.toLowerCase() + "\\b.*")) {
                return faqMap.get(key);
            }
        }
        return "Sorry, I didn't understand that. Please try asking something else.";
    }

    // Return FAQs as a String with full questions.
    private String getFAQsFullQuestions() {
        StringBuilder faqText = new StringBuilder();
        getFullFAQMap().forEach((question, answer) -> faqText.append("Q: ").append(question).append("\nA: ").append(answer).append("\n\n"));
        return faqText.toString();
    }

    // Return menu as a String.
    private String getMenu() {
        return "Normal Menu:\n1. Chicken Salad\n2. Steak\n3. Grilled Fish\n\nVegan Menu:\n1. Tofu Salad\n2. Vegan Burger\n3. Grilled Vegetables\n\nKids Menu:\n1. Chicken Nuggets\n2. Mac and Cheese\n3. Mini Pizza";
    }

    // FAQ Map with predefined questions and answers (keywords).
    private Map<String, String> getFAQMap() {
        Map<String, String> faqMap = new HashMap<>();
        faqMap.put("opening hours", "We are open from 9 AM to 10 PM.");
        faqMap.put("vegan", "Yes, we offer a variety of vegan dishes. For more information visit the ''Menu'' tab");
        faqMap.put("reservation", "You can reserve a table through our chatbot, just click on the ''reservation tab'' and fill your details.");
        faqMap.put("wheelchair", "Yes, our restaurant is fully wheelchair accessible.");
        faqMap.put("pets", "Only service animals are allowed inside the restaurant.");
        faqMap.put("payment methods", "We accept cash, credit/debit cards, and contactless payments.");
        faqMap.put("kids", "Yes, we have a special menu for children.");
        faqMap.put("parking", "Yes, we have free parking for customers right outside the restaurant.");
        faqMap.put("wifi", "Yes, we provide free WiFi for all customers.");
        faqMap.put("discounts", "We offer discounts on weekdays from 2 PM to 4 PM.");
        faqMap.put("recommendation", "We offer 3 different menus, normal one, a vegan option and a kid's menu. For more information go in the menu tab.");
        return faqMap;
    }

    // Full FAQ Map with complete questions and answers.
    private Map<String, String> getFullFAQMap() {
        Map<String, String> fullFAQMap = new HashMap<>();
        fullFAQMap.put("What are your opening hours?", "We are open from 9 AM to 10 PM.");
        fullFAQMap.put("Do you offer vegan options?", "Yes, we offer a variety of vegan dishes.");
        fullFAQMap.put("How can I make a reservation?", "Yes, you can reserve a table through our chatbot or by calling the restaurant.");
        fullFAQMap.put("Is the restaurant wheelchair accessible?", "Yes, our restaurant is fully wheelchair accessible.");
        fullFAQMap.put("Are pets allowed?", "Only service animals are allowed inside the restaurant.");
        fullFAQMap.put("What payment methods do you accept?", "We accept cash, credit/debit cards, and contactless payments.");
        fullFAQMap.put("Do you have a kids' menu?", "Yes, we have a special menu for children.");
        fullFAQMap.put("Is parking available?", "Yes, we have free parking for customers right outside the restaurant.");
        fullFAQMap.put("Do you provide WiFi?", "Yes, we provide free WiFi for all customers.");
        fullFAQMap.put("Do you have any special discounts?", "We offer discounts on weekdays from 2 PM to 4 PM.");
        return fullFAQMap;
    }

    // Open window to make a reservation.
    private void openReservationWindow() {
        Stage reservationStage = new Stage();
        TextField dateField = new TextField();
        dateField.setPromptText("Date (YYYY-MM-DD)");
        TextField timeField = new TextField();
        timeField.setPromptText("Time (HH:MM)");
        TextField guestsField = new TextField();
        guestsField.setPromptText("Number of Guests");
        Button confirmButton = new Button("Confirm");
        confirmButton.setOnAction(e -> {
            String date = dateField.getText();
            String time = timeField.getText();
            String guests = guestsField.getText();
            if (!date.isEmpty() && !time.isEmpty() && !guests.isEmpty()) {
                try {
                    UserDAO userDAO = new UserDAO();
                    boolean success = userDAO.makeReservation(username, date, time, Integer.parseInt(guests));
                    showAlert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
                            success ? "Reservation Successful" : "Reservation Failed",
                            success ? "Your reservation has been confirmed!" : "Failed to make reservation. Try again.");
                    if (success) reservationStage.close();
                } catch (Exception ex) {
                    showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + ex.getMessage());
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please fill in all fields.");
            }
        });

        VBox layout = new VBox(10, dateField, timeField, guestsField, confirmButton);
        reservationStage.setScene(new Scene(layout, 400, 250));
        reservationStage.setTitle("Make Reservation");
        reservationStage.show();
    }

    // Open window to view reservations.
    private void openViewReservationsWindow() {
        Stage viewStage = new Stage();
        TextArea reservationsArea = new TextArea();
        reservationsArea.setEditable(false);

        UserDAO userDAO = new UserDAO();
        List<String> reservations = userDAO.getUserReservations(username);
        StringBuilder reservationText = new StringBuilder();
        for (String reservation : reservations) {
            reservationText.append(reservation).append("\n");
        }
        reservationsArea.setText(reservationText.toString().isEmpty() ? "No reservations found." : reservationText.toString());

        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> viewStage.close());

        VBox layout = new VBox(10, reservationsArea, returnButton);
        viewStage.setScene(new Scene(layout, 400, 300));
        viewStage.setTitle("View Reservations");
        viewStage.show();
    }

    // Open window to cancel a reservation.
    private void openCancelReservationWindow() {
        Stage cancelStage = new Stage();
        TextField reservationIdField = new TextField();
        reservationIdField.setPromptText("Reservation ID to cancel");
        Button cancelButton = new Button("Cancel Reservation");
        cancelButton.setOnAction(e -> {
            String reservationId = reservationIdField.getText();
            try {
                UserDAO userDAO = new UserDAO();
                boolean success = userDAO.cancelReservation(Integer.parseInt(reservationId));
                showAlert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
                        success ? "Cancellation Successful" : "Cancellation Failed",
                        success ? "Reservation has been canceled." : "Failed to cancel reservation. Check ID and try again.");
                if (success) cancelStage.close();
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Reservation ID must be a number.");
            }
        });

        VBox layout = new VBox(10, reservationIdField, cancelButton);
        cancelStage.setScene(new Scene(layout, 400, 200));
        cancelStage.setTitle("Cancel Reservation");
        cancelStage.show();
    }

    // Save user feedback.
    private void saveFeedback(Integer rating, String comment) {
        if (rating == null) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please select a rating.");
            return;
        }
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        boolean success = feedbackDAO.saveFeedback(username, rating, comment);
        showAlert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR,
                success ? "Thank You!" : "Submission Failed",
                success ? "Your feedback has been submitted." : "Failed to save your feedback. Please try again.");
    }

    // Retrieve conversation history.
    private String getConversationHistory() {
        List<Conversation> history = new ConversationDAO().getConversations(username);
        StringBuilder historyText = new StringBuilder();
        for (Conversation conversation : history) {
            historyText.append("Date/Time: ").append(conversation.getTimestamp())
                    .append("\nQuestion: ").append(conversation.getQuery())
                    .append("\nAnswer: ").append(conversation.getResponse())
                    .append("\n\n");
        }
        return historyText.toString().isEmpty() ? "No conversation history found." : historyText.toString();
    }

    // Store conversation history in database.
    private void storeConversation(String username, String query, String response) {
        ConversationDAO conversationDAO = new ConversationDAO();
        conversationDAO.saveConversation(username, query, response);
    }

    // Show alert dialog.
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}