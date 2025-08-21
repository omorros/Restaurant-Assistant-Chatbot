import java.sql.Connection;
import java.sql.DriverManager;


 //The DatabaseConnection class is used to establish a connection to the PostgreSQL database used by the restaurant chatbot application.
public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/restaurant_chatbot";
    private static final String USER = "postgres"; // Username for the database.
    private static final String PASSWORD = "postgres"; // Password for the database.

     //Establishes and returns a connection to the database.
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
