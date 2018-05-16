import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;
    private InputStream in;
    private OutputStream out;

    public Server(int port) throws UnknownHostException, IOException {
        // tao mot server socket
        serverSocket = new ServerSocket(port);
    }

    private void waitForConnection() {
        Scanner scan = new Scanner(System.in);
        // lang nghe ket noi, trong nay no se chay 2 thread, mot thread chinh
        // dung de gui tin nhan, thread con lai nhan tin nhan
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                // moi lan co ket noi tu phia client toi thi tao mot thang
                // worker
                Worker worker = new Worker(socket);
                worker.start();
                String message = scan.nextLine();
                worker.send(message);
            } catch (IOException e) {
                System.out.println("Can't connect");
            }
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server(1997);
            server.waitForConnection();
            Scanner scan = new Scanner(System.in);
        } catch (IOException e) {
            System.out.println("Can't create server socket");
        }
    }
}