package UDPClient;

import TCPClient.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;

public class ClientThread implements Runnable{

    static public int port = 20002;
    DatagramSocket clientSocket;
    HashMap<String, Client> friends = new HashMap<>();
    String clientName;

    public ClientThread(String clientName) throws SocketException {
        clientSocket = new DatagramSocket(port++);
        this.clientName = clientName;
    }

    @Override
    public void run() {
        try{
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("Please enter your request: ");
            String line = consoleReader.readLine();
            DatagramPacket packet = new DatagramPacket(line.getBytes(), line.getBytes().length,
                    InetAddress.getByName("localhost"), 20001);
            clientSocket.send(packet);

            if (line.startsWith("exit"))
                break;

            if(line.startsWith("UDP") || line.startsWith("udp")){
                byte[] buffer = new byte[1024];
                DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
                clientSocket.receive(receivedPacket);

                String receivedMessage = new String(receivedPacket.getData());
                System.out.println("UDP Server: " + receivedMessage);

                if (receivedMessage.startsWith("Info")){
                   String[] infos = receivedMessage.split(" ");
                   Client client = new Client(infos[2].trim(), Integer.valueOf(infos[3].trim()));
                   friends.put(infos[1].trim(),client);
                }
            }else {
                String[] tokens = line.split(" ");
                if(friends.containsKey(tokens[0].trim())){
                    friends.get(tokens[0].trim()).run(clientName + ": " +tokens[1].trim());
                }else {
                    System.out.println("The user doesn't exist. First connect.");
                }

            }
        }
        clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
