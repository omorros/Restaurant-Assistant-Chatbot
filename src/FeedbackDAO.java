import java.sql.Connection;
import java.sql.PreparedStatement;
/*
The FeedbackDAO class handles database operations related to user feedback.
Allows saving user feedback into the database.
 */
public class FeedbackDAO {

    //Saves feedback provided by a user into the database.
    public boolean saveFeedback(String username, int rating, String comment) {

        // SQL query to insert feedback into the database.
        String sql = "INSERT INTO feedback (username, rating, comment) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); // Establish a database connection.
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepare the SQL statement.

            pstmt.setString(1, username);
            pstmt.setInt(2, rating);
            pstmt.setString(3, comment);

            // Execute the INSERT query and check if any rows were inserted.
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Return false if an error occurred.
        }
    }
}
