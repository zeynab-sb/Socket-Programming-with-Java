package UDPClient;

import TCPServer.TCPThread;

import java.io.IOException;
import java.net.SocketException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    static ExecutorService pool = Executors.newCachedThreadPool();
    public static void main(String args[]) {
        try {
            Random r = new Random();
            int low = 20002;
            int high = 30002;
            int port = r.nextInt(high-low) + low;
            System.out.println("Enter your name: ");
            Scanner scanner = new Scanner(System.in);
            ClientThread clientThread = new ClientThread(scanner.next(), port);
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
