package TCPServer;

import java.io.*;
import java.net.Socket;

public class ConnectionThread implements Runnable {
    Socket connectionSocket;
    BufferedReader reader;

    public ConnectionThread(Socket connectionSocket) throws IOException {
        this.connectionSocket = connectionSocket;
        reader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
    }

    @Override
    public void run() {
        while (true) {
            try {
                String line = reader.readLine();
                if(line!= null)
                    System.out.println(line);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}