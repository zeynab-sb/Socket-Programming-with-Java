package UDPClient;

import TCPServer.TCPThread;

import java.io.IOException;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    static ExecutorService pool = Executors.newCachedThreadPool();
    public static void main(String args[]) {
        try {
            ClientThread clientThread = new ClientThread();
            pool.execute(clientThread);
            TCPThread tcpThread = new TCPThread();
            pool.execute(tcpThread);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
