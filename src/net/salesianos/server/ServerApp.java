package net.salesianos.server;

import net.salesianos.server.threads.ClientHandler;
import net.salesianos.utils.Constants;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerApp {

    private static Map<String, Integer> playerNumbers = new HashMap<>();
    private static ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT)) {
            System.out.println("Server started on port " + serverSocket.getLocalPort());

            while (true) {
                System.out.println("Waiting for client connection...");
                Socket clientSocket = serverSocket.accept();

                DataInputStream clientInputStream = new DataInputStream(
                        new BufferedInputStream(clientSocket.getInputStream()));
                DataOutputStream clientOutputStream = new DataOutputStream(
                        new BufferedOutputStream(clientSocket.getOutputStream()));

                String username = clientInputStream.readUTF();
                System.out.println("Client connected: " + username);

                ClientHandler clientHandler = new ClientHandler(clientInputStream, clientOutputStream, username,
                        playerNumbers, clients);
                clients.add(clientHandler);
                clientHandler.start();
            }

        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Server application finished.");
        }
    }
}