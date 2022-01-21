import java.net.*;
import java.io.*;

public class Server extends Thread {

    private static final int PORT = 2605;

    public void startConnection() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                new ServerThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Server server = new Server();
        server.startConnection();
    }

}
