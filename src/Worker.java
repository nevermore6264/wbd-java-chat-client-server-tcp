import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Worker extends Thread {
    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public Worker(Socket socket) throws IOException {
        this.socket = socket;
        in = socket.getInputStream();
        out = socket.getOutputStream();
    }

    // nhan tin nhan
    public void send(String message) throws IOException {
        out.write(message.getBytes());
    }

    // gui tin nhan
    @Override
    public void run() {
        byte[] buff = new byte[2048];
        try {
            while (true) {
                int reveivedBytes = in.read(buff);
                if (reveivedBytes < 1)
                    break;
                String message = new String(buff, 0, reveivedBytes);
                System.out.println(message);
            }
        } catch (IOException e) {
            System.out.println("Can't read");
        }
    }
}