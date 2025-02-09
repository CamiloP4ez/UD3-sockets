package net.salesianos.client;

import net.salesianos.client.threads.ServerListener;
import net.salesianos.utils.Constants;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
// import java.util.InputMismatchException;
import java.util.Scanner;

public class ClientApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username:");
        String username = scanner.nextLine();

        try (Socket socket = new Socket("localhost", Constants.SERVER_PORT);
                DataOutputStream outputStream = new DataOutputStream(
                        new BufferedOutputStream(socket.getOutputStream()));
                DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()))) {

            outputStream.writeUTF(username);
            outputStream.flush();

            ServerListener serverListenerThread = new ServerListener(inputStream);
            serverListenerThread.start();

            System.out
                    .println("Connected to the server. Type 'play' to get a number or 'get winner' to see the winner.");

            while (true) {
                System.out.print("-> ");
                String command = scanner.nextLine().toLowerCase();

                outputStream.writeUTF(command);
                outputStream.flush();

                if (command.equals("exit")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Error connecting to the server: " + e.getMessage());
        } finally {
            System.out.println("Client application finished.");
            scanner.close();
        }
    }
}