package net.salesianos.client.threads;

import java.io.DataInputStream;
import java.io.IOException;

public class ServerListener extends Thread {

    private DataInputStream inputStream;

    public ServerListener(DataInputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String serverMessage = this.inputStream.readUTF();
                System.out.println(serverMessage);
            } catch (IOException e) {
                System.out.println("Disconnected from server.");
                break;
            }
        }
    }
}
