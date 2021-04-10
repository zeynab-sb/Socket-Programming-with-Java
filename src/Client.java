import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client {

    DatagramSocket clientSocket;

    public Client() throws SocketException {
        clientSocket = new DatagramSocket();
    }

    public void run() throws IOException {

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
    }

    public static void main(String args[]) {
        try {
            Client client = new Client();
            client.run();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
