package MultiThreadTCPChat;
import java.io.*;
import java.net.*;

public class TCPClient {
    private static final String SERVER_ADDRESS = "localhost"; // Server address
    private static final int SERVER_PORT = 12345; // Server port

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to the server");

            // Create a thread for receiving messages from the server
            Thread receiveThread = new Thread(() -> {
                try {
                    String response;
                    while ((response = input.readLine()) != null) {
                        System.out.println(response);
                    }
                } catch (IOException ex) {
                    System.out.println("Error receiving message: " + ex.getMessage());
                }
            });
            receiveThread.start();

            // Main thread for sending messages
            String message;
            while (true) {
                message = userInput.readLine();
                output.println(message); // Send message to server

                if ("exit".equalsIgnoreCase(message)) {
                    break;
                }
            }

        } catch (IOException ex) {
            System.out.println("Client error: " + ex.getMessage());
        }
    }
}
