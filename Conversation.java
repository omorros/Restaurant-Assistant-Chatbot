
/*
The Conversation class represents a single interaction between the user and the chatbot.
It stores the user's query, the chatbot's response, and the timestamp.
 */
public class Conversation {
    private String query;
    private String response;
    private String timestamp;

    //Constructor to initialize a Conversation object.
    public Conversation(String query, String response, String timestamp) {
        this.query = query;
        this.response = response;
        this.timestamp = timestamp;
    }

    public String getQuery() {
        return query;
    }

    public String getResponse() {
        return response; //Get response.
    }

    public String getTimestamp() {
        return timestamp; //Get timestamp.
    }
}
