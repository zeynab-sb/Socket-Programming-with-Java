package TCPClient;

import java.io.*;
import java.net.Socket;

public class Client {
    Socket socket;
    BufferedWriter writer;

    public Client(String ip, int port) throws IOException {
        socket = new Socket(ip, port);
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void run(String line) {
            try {
                    writer.write(line + "\n");
                    writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        try {
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

