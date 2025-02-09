package net.salesianos.server.threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
// import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ClientHandler extends Thread {

    private DataInputStream clientInputStream;
    private DataOutputStream clientOutputStream;
    private String username;
    private Map<String, Integer> playerNumbers;
    private ArrayList<ClientHandler> clients;
    private static Random random = new Random();
    private static final int MAX_NUMBER = 100;

    public ClientHandler(DataInputStream clientInputStream, DataOutputStream clientOutputStream, String username,
            Map<String, Integer> playerNumbers, ArrayList<ClientHandler> clients) {
        this.clientInputStream = clientInputStream;
        this.clientOutputStream = clientOutputStream;
        this.username = username;
        this.playerNumbers = playerNumbers;
        this.clients = clients;
    }

    @Override
    public void run() {
        try {
            clientOutputStream.writeUTF("Welcome to the Number higher Game, " + username + "!");
            clientOutputStream.flush();

            while (true) {
                String command = clientInputStream.readUTF().toLowerCase();

                switch (command) {
                    case "play":
                        playGame();
                        break;
                    case "get winner":
                        getWinner();
                        break;
                    case "exit":
                        disconnectClient();
                        return;
                    default:
                        clientOutputStream.writeUTF("Invalid command. Try 'play' or 'get winner'.");
                        clientOutputStream.flush();
                }
            }
        } catch (SocketException se) {
            System.out.println("Connection closed with client " + this.username + ".");
        } catch (IOException ioe) {
            System.err.println("IO Exception in ClientHandler for " + this.username + ": " + ioe.getMessage());
        } finally {
            // Cleanup if needed
        }
    }

    private void playGame() throws IOException {
        int number = random.nextInt(MAX_NUMBER) + 1;
        playerNumbers.put(username, number);
        clientOutputStream.writeUTF("Your number is: " + number);
        clientOutputStream.flush();
        broadcastMessage(username + " has played and received a number.");
    }

    private void getWinner() throws IOException {
        if (playerNumbers.size() < 2) {
            clientOutputStream.writeUTF("Not enough players to determine a winner. Need at least 2 players.");
            clientOutputStream.flush();
            return;
        }

        String winnerName = null;
        int maxNumber = -1;

        for (Map.Entry<String, Integer> entry : playerNumbers.entrySet()) {
            if (entry.getValue() > maxNumber) {
                maxNumber = entry.getValue();
                winnerName = entry.getKey();
            }
        }

        if (winnerName != null) {
            String winnerMessage = "The winner is " + winnerName + " with the number " + maxNumber + "!";
            broadcastMessage(winnerMessage);
            playerNumbers.clear(); // Reset the game for the next round
        } else {
            clientOutputStream.writeUTF("Error determining winner.");
            clientOutputStream.flush();
        }
    }

    private void broadcastMessage(String message) {
        for (ClientHandler client : clients) {
            try {
                client.clientOutputStream.writeUTF(message);
                client.clientOutputStream.flush();
            } catch (IOException e) {
                System.err.println("Error broadcasting message to " + client.username + ": " + e.getMessage());
                // Consider removing the client if broadcast fails consistently
            }
        }
    }

    private void disconnectClient() throws IOException {
        clients.remove(this); // Remove client from list
        broadcastMessage(username + " has left the game.");
        clientOutputStream.close();
        clientInputStream.close();
        System.out.println("Client " + username + " disconnected.");
    }
}