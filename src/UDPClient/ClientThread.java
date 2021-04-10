package UDPClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ClientThread implements Runnable{

    static public int port = 20002;
    DatagramSocket clientSocket;

    public ClientThread() throws SocketException {
        clientSocket = new DatagramSocket(port++);
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

            byte[] buffer = new byte[1024];
            DatagramPacket receivedPacket = new DatagramPacket(buffer, buffer.length);
            clientSocket.receive(receivedPacket);

            String receivedMessage = new String(receivedPacket.getData());
            System.out.println("Received message: " + receivedMessage);

            if (receivedMessage.startsWith("Info")){

            }
        }
        clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
