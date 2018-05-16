import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {
    private InputStream in;
    private OutputStream out;
    private Socket socket;

    public Client(String serverAndress, int serverPort) throws IOException {
        // tao mot socket tai dia chi serverAndress va serverPort
        socket = new Socket(serverAndress, serverPort);
        // tao 2 stream mot la luong di hai la luong vao
        in = socket.getInputStream();
        out = socket.getOutputStream();
    }

    // gui tin nhan
    private void send(String message) {
        try {
            out.write(message.getBytes());
        } catch (IOException e) {
            System.out.println("Can't send");
        }
    }

    // nhan tin nhan
    @Override
    public void run() {
        byte[] buff = new byte[2048];
        while (true) {
            try {
                // doc tin nhan tu luong vao tra ve so luong byte da doc
                int receivedBytes = in.read(buff);
                if (receivedBytes < 1)
                    break;
                // convert mang buff sang kieu string
                String message = new String(buff, 0, receivedBytes);
                System.out.println(message);

            } catch (IOException e) {
                System.out.println("Can't received");
            }
        }
    }

    public static void main(String[] args) {
        Client client;
        try {
            client = new Client("localhost", 1997);
            Scanner scan = new Scanner(System.in);
            // tao ra 2 thread, 1 thread la de nhan tin nhan thead con lai la
            // thread main dung de gui tin nhan
            client.start();
            while (true) {
                String message = scan.nextLine();
                client.send(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}