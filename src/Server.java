import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 5000; // Port number

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);
               //serverSocket and socket are variable names
            // Wait for a client connection
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            // Get input and output streams
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            // Communicate with the client
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Client says: " + message);
                writer.println("Server received: " + message);
            }

            // Close the connection
            socket.close();
        } catch (IOException ex) {
            System.out.println("Server error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
