import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPChatClient {
    public static void main(String[] args) throws IOException {
        int port = 8000;
        String host="localhost";
        try (DatagramSocket socket = new DatagramSocket()) {

            InetAddress serverAddress=InetAddress.getByName(host);

            byte[] buffer = new byte[1024];
            Scanner sc = new Scanner(System.in);
            while (true) {
                // Send a message to the server
                System.out.print("You: ");
                if (sc.hasNextLine()) sc.nextLine();
                String message = sc.nextLine();
                byte[] messageBytes = message.getBytes();
                DatagramPacket packet = new DatagramPacket(messageBytes, messageBytes.length, serverAddress, port);
                socket.send(packet);

                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Connection closed");
                    break;}

                    // Receive response from the server
                    DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
                    socket.receive(responsePacket);
                    String response = new String(responsePacket.getData(), 0, responsePacket.getLength());
                    System.out.println("Server: " + response);

                    if (response.equalsIgnoreCase("exit")) {
                        System.out.println("Server disconnected");
                        break;
                    }
                }

        } catch (Exception ex) {

            System.out.println("Server Error " + ex.getMessage());
            ex.printStackTrace();
        }

    }

}

