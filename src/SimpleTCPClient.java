import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SimpleTCPClient {
    public static void main(String[] args) throws IOException
    {
        int port=1234;
        String host="localhost";
        try(Socket socket=new Socket(host,port)) {
            System.out.println("Connected to server");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            Scanner sc = new Scanner(System.in);
            String message;
            while (true) {
                System.out.println("You : ");
                message = sc.nextLine();
                writer.println(message);
                if (message.contentEquals("exit")) {
                    System.out.println("Connection Closed ");
                    break;
                }

                String response = reader.readLine();
                if (response.equalsIgnoreCase("exit ")) {
                    System.out.println("Server Disconnected ");
                    break;
                }
                System.out.println("Server " + response);

            }
        }catch(UnknownHostException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
}


