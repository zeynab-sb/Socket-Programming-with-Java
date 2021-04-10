package TCPClient;

import java.io.*;
import java.net.Socket;

public class Client {
    Socket socket;
//    BufferedReader reader;
    BufferedWriter writer;

    public Client(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
//        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void run(String line) {
            try {
                    writer.write(line + "\n");
                    writer.flush();
//                    line = reader.readLine();
//                    System.out.println("Server Response: " + line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        try {
//            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

