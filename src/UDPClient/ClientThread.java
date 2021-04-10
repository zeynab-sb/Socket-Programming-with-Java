package UDPClient;

import TCPClient.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;
import static UDPServer.UDPServer.TCPUDPPort;


public class ClientThread implements Runnable{

    DatagramSocket clientSocket;
    HashMap<String, Client> friends = new HashMap<>();
    String clientName;

    public ClientThread(String clientName, int port) throws SocketException {
        TCPUDPPort = port;
        clientSocket = new DatagramSocket(port);
        this.clientName = clientName;
    }

    @Override
    public void run() {
        try{
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String firstMsg = "udp name " + clientName;
            sendMsgToUDPServer(firstMsg);

        while (true) {
            System.out.println("> ");
            String line = consoleReader.readLine();
            if (line.startsWith("exit"))
                break;

            if(line.startsWith("UDP") || line.startsWith("udp")){
                String receivedMessage = sendMsgToUDPServer(line);
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

    public String sendMsgToUDPServer(String msg){
        try{
            DatagramPacket packet = new DatagramPacket(msg.getBytes(), msg.getBytes().length,
                    InetAddress.getByName("localhost"), 20001);
            clientSocket.send(packet);
            byte[] buffer = new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
            clientSocket.receive(receivedPacket);

            String receivedMessage = new String(receivedPacket.getData());
            System.out.println("UDP Server: " + receivedMessage);

            return receivedMessage;

        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
