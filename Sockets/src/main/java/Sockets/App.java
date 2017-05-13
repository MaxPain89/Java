package Sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            ExecutorService service = Executors.newFixedThreadPool(2);
            int port = 5555;
            ServerSocket ss = new ServerSocket(port); // создаем сокет сервера и привязываем его к вышеуказанному порту
            System.out.println("Waiting for a client...");
            Integer count = 0;
            while(true) {
                Socket socket = ss.accept();
                service.submit(new Responder(socket,count));
                System.out.println("Created new tread");
                count++;
            }
        } catch(Exception x) { x.printStackTrace(); }
    }
}
