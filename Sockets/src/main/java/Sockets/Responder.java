package Sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * Created by Max on 13.05.2017.
 */
public class Responder implements Callable<Boolean> {
    private Socket socket;
    private Integer number;

    public Responder(Socket socket, Integer number) {
        this.socket = socket;
        this.number = number;
    }

    public Boolean call() throws Exception {
        System.out.println("Got a client :) ... Finally, someone saw me through all the cover!");
        System.out.println();

        // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиенту.
        InputStream sin = socket.getInputStream();
        OutputStream sout = socket.getOutputStream();

        // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
        DataInputStream in = new DataInputStream(sin);
        DataOutputStream out = new DataOutputStream(sout);

        String line = null;
        while(true) {
            line = in.readUTF(); // ожидаем пока клиент пришлет строку текста.
            if (line.equals("Bue")) {
                break;
            }
            System.out.println("The dumb client just sent me this line : " + line + "thread number " + String.valueOf(this.number));
            out.writeUTF(line); // отсылаем клиенту обратно ту самую строку текста.
            out.flush(); // заставляем поток закончить передачу данных.
        }
        this.socket.close();
        return true;
    }
}
