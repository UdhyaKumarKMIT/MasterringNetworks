package MultithreadSimple;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
    private static final int PORT = 12345;  // Port to listen on
    private static final int MAX_CLIENTS = 10; // Maximum number of threads (clients)

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            // Thread pool to manage client connections (limit to MAX_CLIENTS)
            ExecutorService threadPool = Executors.newFixedThreadPool(MAX_CLIENTS);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Handle the client connection in a new thread
                threadPool.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException ex) {
            System.out.println("Server error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)) {

            // Send the system time to the client
            String systemTime = "Current system time: " + System.currentTimeMillis();
            output.println(systemTime);

            // Close the connection after sending the time
            System.out.println("Sent time to client: " + systemTime);

        } catch (IOException ex) {
            System.out.println("Client communication error: " + ex.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException ex) {
                System.out.println("Error closing client socket: " + ex.getMessage());
            }
        }
    }
}
