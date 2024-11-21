package MultiThreadTCPChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {

    private static final int port = 1234;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New Client Connected " + clientSocket.getInetAddress());

                threadPool.submit(new ClientHandler(clientSocket));
            }

        } catch (IOException ex) {
            System.out.println("Server Error " + ex.getMessage());
        }
    }

    static class ClientHandler implements Runnable {

        private Socket clientSocket;
        private BufferedReader input;
        private PrintWriter output;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            try {
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                output = new PrintWriter(clientSocket.getOutputStream(), true);
            } catch (IOException ex) {
                System.out.println("Error setting up streams: " + ex.getMessage());
            }
        }


        @Override
        public void run() {
            try {
                String message;
                // Communicate with the client
                while ((message = input.readLine()) != null) {
                    System.out.println("Client: " + message);

                    // Send the message back to the client (echo)
                    output.println("Server: " + message);

                    if ("exit".equalsIgnoreCase(message)) {
                        break;
                    }
                }
            } catch (IOException ex) {
                System.out.println("Error communicating with client: " + ex.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException ex) {
                    System.out.println("Error closing client socket: " + ex.getMessage());
                }
            }
        }

    }
}