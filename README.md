# restaurant-assistant-chatbot
A JavaFX-based restaurant assistant chatbot with PostgreSQL integration.
The chatbot enables customers to register, log in, make and cancel reservations, view the menu, ask FAQs, provide feedback, and review conversation history. All data is stored in a PostgreSQL database for persistence.

# Project Overview

This project automates common restaurant interactions through a conversational chatbot with a graphical user interface.
It was designed to reduce staff workload on repetitive tasks while improving customer experience with instant responses.

**Main features include:**

* User registration and login

* Viewing the restaurant menu (regular, vegan, kids menu)

* Asking and answering FAQs

* Creating, canceling, and viewing reservations

* Viewing conversation history

* Providing feedback with ratings and comments

* Secure logout to end the session

# Technologies Used

* JavaFX – User interface

* Java (OOP) – Core application logic

* JDBC – Database connectivity

* PostgreSQL – Database for users, reservations, FAQs, feedback, and conversations

* IntelliJ IDEA – Development environment

# Repository Contents

* ChatbotInterface.java – Main chatbot interface (JavaFX)

* Conversation.java – Model for conversations

* ConversationDAO.java – Data access for conversation history

* DatabaseConnection.java – Database connectivity class

* FeedbackDAO.java – Data access for feedback

* InitialPage.java – Welcome screen (login/register options)

* LoginForm.java – Login interface

* RegistrationForm.java – Registration form

* UserDAO.java – Data access for user accounts

* RestaurantChatbot_Backup.sql – PostgreSQL database schema and sample data

# Setup Instructions
1. Database Setup

**Install PostgreSQL.**

**Restore the provided database backup:**

psql -U postgres -f RestaurantChatbot_Backup.sql


**Ensure a database named restaurant_chatbot is created with tables: users, reservations, faq, feedback, conversation_history.**

2. Configure Database Connection

**Update DatabaseConnection.java with your PostgreSQL credentials:**

private static final String URL = "jdbc:postgresql://localhost:5432/restaurant_chatbot";
private static final String USER = "postgres";
private static final String PASSWORD = "postgres";

3. Compile and Run
javac *.java
java InitialPage

# Example Interactions

**FAQ:**

Q: "Do you provide WiFi?"

A: "Yes, we provide free WiFi for all customers."

**Reservation:**

User books a table for 5 guests on 2024-12-20 at 18:30.

Saved in the reservations table.

**Feedback:**

# User rates 5 and comments "Lovely staff".

**Future Improvements**

* Encrypt user passwords for security

* Add an admin panel for managing FAQs and menu options

* Enhance chatbot with NLP for more flexible queries

* Implement email or SMS notifications for reservations