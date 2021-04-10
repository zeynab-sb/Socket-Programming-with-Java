package TCPServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static UDPClient.ClientThread.port;

public class TCPThread implements Runnable{

    ServerSocket serverSocket;
    static ExecutorService pool = Executors.newCachedThreadPool();

    public TCPThread() throws IOException {
        serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("TCP Server is listening");
            Socket connectionSocket = null;
            try {
                connectionSocket = serverSocket.accept();
                System.out.println("Server Socket find a new connection");
                ConnectionThread serverThread = new ConnectionThread(connectionSocket);
                pool.execute(serverThread);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
