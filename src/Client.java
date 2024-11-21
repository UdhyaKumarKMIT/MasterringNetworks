import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String hostname = "localhost"; // Server address
        int port = 1234;              // Port number

        try (Socket socket = new Socket(hostname, port)) {
            System.out.println("Connected to the server");

            // Get input and output streams
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            // Send a message to the server
            writer.println("Hello, Server!");
            System.out.println("Server replied: " + reader.readLine());

            // Close the connection
        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}
