
import java.io.*;
        import java.net.*;
        import java.util.Scanner;

public class SimpleTCPServer {
    public static void main(String[] args) {
        int port = 5000; // Server port number

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            // Accept a client connection
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            // Setup input and output streams
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Use Scanner for console input
            Scanner sc = new Scanner(System.in);
            String response;
            // Chat loop
            String message;
            while (true) {
                // Read message from the client
                message = reader.readLine();
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Client disconnected");
                    break;
                }
                System.out.println("\nClient: " + message);

                response="";
                System.out.print("You: ");
                response = sc.nextLine(); // Use Scanner for input
                writer.println(response);
                if (response.equalsIgnoreCase("exit")) {
                    System.out.println("Connection closed");
                    break;
                }
            }

            socket.close();
        } catch (IOException ex) {
            System.out.println("Server error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
