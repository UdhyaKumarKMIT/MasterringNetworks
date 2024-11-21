import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPChatServer {
    public static void main(String[] args) {
        int port = 8000; // Server port
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Server is listening on port " + port);

            byte[] buffer = new byte[1024];
            Scanner sc = new Scanner(System.in);

            DatagramPacket reciiniti=new DatagramPacket(buffer,buffer.length);
            InetAddress clientAddress=reciiniti.getAddress();
            int clientPort=reciiniti.getPort();
            String temp = "You are connected. Current server time: " + System.currentTimeMillis();
            buffer=temp.getBytes();
            DatagramPacket initial=new DatagramPacket(buffer,buffer.length,clientAddress,clientPort);
            socket.send(initial);
            while (true) {
                // Receive a packet from the client
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String recv = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Client: " + recv);

                // Check if the client wants to disconnect
                if (recv.equalsIgnoreCase("exit")) {
                    System.out.println("Client disconnected.");
                    break;
                }

                // Send a response to the client
                System.out.print("You: ");
                if (sc.hasNextLine()) sc.nextLine();
                String response = sc.nextLine();
                byte[] responseBytes = response.getBytes();

                DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, clientAddress, clientPort);
                socket.send(responsePacket);

                // Check if the server wants to disconnect
                if (response.equalsIgnoreCase("exit")) {
                    System.out.println("Connection closed.");
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("Server error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
