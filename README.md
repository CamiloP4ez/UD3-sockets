# Number Guessing Game - Client/Server Application in Java

## Description

This project implements a simple number guessing game using Java, sockets, and threads, as a client/server application.  The game allows multiple players to connect to a server, "play" to receive a random number, and then request to see who the "winner" is based on who drew the highest number.

This project is designed to fulfill the requirements of the **UD3 - Práctica 1 – Aplicación cliente/servidor** exercise, demonstrating understanding of network communication using sockets and multithreading in Java.

is part of the development process).

## Functionality

The application works as follows:

1.  **Client Connection:** Multiple clients can connect to the server. Each client is prompted to enter a username upon connection.
2.  **Play Command:**  A client can type `play` and send this command to the server. The server responds by generating a random number for that client and sending it back. This number is only revealed to the client who requested it.
3.  **Get Winner Command:** After at least two clients have played, any client can type `get winner` and send this command. The server will then determine the player who received the highest number among all players who have played and announce the winner to all connected clients. The game then resets, ready for a new round.
4.  **Error Handling:** The server handles cases where there are not enough players to determine a winner and provides feedback to the clients.
5.  **Exit Command:** Clients can type `exit` to gracefully disconnect from the server.
6.  **Concurrent Connections:** The server uses threads to manage multiple client connections simultaneously, ensuring that multiple players can interact with the game concurrently.

## Project Structure

The project is structured into the following packages:

*   **`net.salesianos.utils`**:
    *   `Constants.java`: Contains constant values used throughout the application, such as the server port number (`SERVER_PORT`).
*   **`net.salesianos.client`**:
    *   `ClientApp.java`:  The main class for the client application. Handles user input, communication with the server, and displaying server messages.
    *   **`net.salesianos.client.threads`**:
        *   `ServerListener.java`: A thread in the client application responsible for continuously listening for messages from the server and printing them to the client's console.
*   **`net.salesianos.server`**:
    *   `ServerApp.java`: The main class for the server application.  Sets up the server socket, accepts client connections, and manages client handlers.
    *   **`net.salesianos.server.threads`**:
        *   `ClientHandler.java`:  A thread class responsible for handling communication with a single connected client. Manages game logic for each client, including processing commands like `play` and `get winner`.

## How to Run

1.  **Compilation:**
    *   Navigate to the root directory of the project in your terminal.
    *   Compile the Java files using `javac net/salesianos/utils/Constants.java net/salesianos/client/threads/ServerListener.java net/salesianos/client/ClientApp.java net/salesianos/server/threads/ClientHandler.java net/salesianos/server/ServerApp.java`
    *   Alternatively, if you are using an IDE like IntelliJ IDEA or Eclipse, you can simply build the project.

2.  **Run the Server:**
    *   Open a terminal and navigate to the directory where you compiled the `.class` files (typically the root of the project, or a `bin` directory if your IDE uses one).
    *   Run the server application using: `java net.salesianos.server.ServerApp`
    *   You should see the message: `Server started on port 5000` and `Waiting for client connection...`

3.  **Run Clients:**
    *   Open **multiple** new terminals for each client you want to run.
    *   In each terminal, navigate to the same directory where you ran the server.
    *   Run the client application using: `java net.salesianos.client.ClientApp`
    *   Each client will prompt you to `Enter your username:`. Enter a unique username for each client.
    *   You should see a welcome message from the server and be ready to play.

4.  **Play the Game:**
    *   In each client terminal, type `play` and press Enter to receive your number.
    *   Once at least two clients have played, in any client terminal, type `get winner` and press Enter to see the winner announced.
    *   To disconnect a client, type `exit` and press Enter in that client's terminal.

## Technologies Used

*   **Java:** Programming language used for the entire application.
*   **Java Sockets (`java.net.Socket`, `java.net.ServerSocket`):**  For network communication between the client and server applications.
*   **Java Input/Output Streams (`java.io.DataInputStream`, `java.io.DataOutputStream`, `java.io.BufferedInputStream`, `java.io.BufferedOutputStream`):** For efficient data transmission over sockets.
*   **Java Threads (`java.lang.Thread`):** For handling concurrent client connections on the server and for asynchronous message listening on the client.

## Git Repository

[**[REPOSITORY](https://github.com/CamiloP4ez/UD3-sockets)**] -  Link to Git repository containing the project code.

## Video Explanation

Remember to also create a video explanation as per the exercise instructions, demonstrating the application, explaining the code, and justifying your design choices.

---
This `README.md` provides a comprehensive overview of the Number Guessing Game application.