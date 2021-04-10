import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;

public class UDPServer {

    DatagramSocket UDPServerSocket;
    public UDPServer(int port) throws SocketException {
        UDPServerSocket = new DatagramSocket(port);
    }

    public void run() throws IOException {
        HashMap<String,String[]> clientInfos = new HashMap<>();

        /**
         * I'm {name} -- I'm Jack
         * Welcome {name} -- Welcome Jack OR You are already online
         */
        /**
         * Chat {name} -- Chat Sara
         * {Sara's info} ip: , port:  OR The client doesn't exist
         */
        while (true) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            System.out.println("Server is listening");
            UDPServerSocket.receive(packet);

            String receivedMessage = new String(packet.getData());
            String response = null;
            System.out.println("Packet Source IP: " + packet.getAddress().getHostAddress());
            System.out.println("Packet Source Port: " + packet.getPort());


            if (receivedMessage.startsWith("I'm")) {
                String[] tokens = receivedMessage.split(" ");
                if (clientInfos.containsKey(tokens[1]))
                    response = "You are already online";
                else {
                    String[] clientInfo = new String[2];
                    clientInfo[0] = packet.getAddress().getHostAddress();
                    clientInfo[1] = String.valueOf(packet.getPort());
                    clientInfos.put(tokens[1].trim(),clientInfo);
                    System.out.println(clientInfos.isEmpty());
                    response = "Welcome " + tokens[1];
                }

            } else if (receivedMessage.startsWith("Chat")) {
                String[] tokens = receivedMessage.split(" ");
                if (clientInfos.containsKey((tokens[1]).trim())){
                    response = "Info " + clientInfos.get(tokens[1].trim())[0] + " " + clientInfos.get(tokens[1].trim())[1];
                }else response = "The client doesn't exist";

            } else response = "Wrong keyword";
            DatagramPacket sendPacket = new DatagramPacket(response.getBytes(), response.getBytes().length,
                    packet.getAddress(), packet.getPort());
            UDPServerSocket.send(sendPacket);
        }
    }

    public static void main(String args[]){
        try {
            UDPServer udpServer = new UDPServer(20001);
            udpServer.run();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
