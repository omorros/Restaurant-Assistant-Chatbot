import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 The ConversationDAO class handles database operations related to the conversation history.
 It allows saving conversations to the database and retrieving conversation history.
 */
public class ConversationDAO {
    // Database connection details.
    private static final String URL = "jdbc:postgresql://localhost:5432/restaurant_chatbot";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    //Saves a conversation between the user and the chatbot to the database.
    public void saveConversation(String username, String query, String response) {
        String sql = "INSERT INTO conversation_history (username, query, response, timestamp) VALUES (?, ?, ?, NOW())";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, query);
            pstmt.setString(3, response);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Retrieves the conversation history for a specific user from the database.
    public List<Conversation> getConversations(String username) {
        List<Conversation> conversations = new ArrayList<>();
        String sql = "SELECT query, response, timestamp FROM conversation_history WHERE username = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String query = rs.getString("query");
                String response = rs.getString("response");
                String timestamp = rs.getString("timestamp");
                conversations.add(new Conversation(query, response, timestamp));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conversations;
    }
}
