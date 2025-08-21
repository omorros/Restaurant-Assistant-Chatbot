import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/*
UserDAO class provides methods to interact with the database.
Includes functionality for registration, login, reservation system and FAQs.
 */
public class UserDAO {

    // Method to register a new user.
    public boolean registerUser(String fullName, String username, String email, String password, String phoneNumber, String preferences) {
        String sql = "INSERT INTO users (full_name, username, email, password, phone_number, preferences) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the parameters.
            pstmt.setString(1, fullName);
            pstmt.setString(2, username);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            pstmt.setString(5, phoneNumber);
            pstmt.setString(6, preferences);

            // Execute the query and check if any rows were inserted.
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to validate login credentials.
    public boolean loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the parameters for the SELECT query.
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Return true if a matching record is found.

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to fetch reservations for a specific user.
    public List<String> getUserReservations(String username) {
        String sql = "SELECT reservation_id, reservation_date, reservation_time, guests FROM reservations WHERE username = ?";
        List<String> reservations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the parameter for the SELECT query.
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            // Process the result set to extract reservation details.
            while (rs.next()) {
                int id = rs.getInt("reservation_id");
                String date = rs.getString("reservation_date");
                String time = rs.getString("reservation_time");
                int guests = rs.getInt("guests");

                reservations.add("ID: " + id + " | Date: " + date + " | Time: " + time + " | Guests: " + guests);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return reservations;
    }

    // Method to cancel a reservation by ID.
    public boolean cancelReservation(int reservationId) {
        String sql = "DELETE FROM reservations WHERE reservation_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the parameter for the DELETE query.
            pstmt.setInt(1, reservationId);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to fetch FAQs from the database.
    public List<String> getFAQs() {
        String sql = "SELECT question, answer FROM faq";
        List<String> faqs = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // Process the result set to extract FAQ details.
            while (rs.next()) {
                String question = rs.getString("question");
                String answer = rs.getString("answer");
                faqs.add("Q: " + question + "\nA: " + answer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return faqs;
    }

    // Method to make a reservation.
    public boolean makeReservation(String username, String date, String time, int guests) {
        String sql = "INSERT INTO reservations (username, reservation_date, reservation_time, guests) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Convert the date string into java.sql.Date.
            Date reservationDate = Date.valueOf(date); // Converts YYYY-MM-DD to java.sql.Date

            // Convert the time string into java.sql.Time.
            java.sql.Time reservationTime = java.sql.Time.valueOf(time + ":00"); // Ensures HH:MM:SS format by appending seconds

            pstmt.setString(1, username);
            pstmt.setDate(2, reservationDate); // Set the properly formatted date.
            pstmt.setTime(3, reservationTime); // Set the properly formatted time.
            pstmt.setInt(4, guests); // Set the number of guests.

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
