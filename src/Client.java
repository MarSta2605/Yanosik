import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final int PORT = 2605;
    private static final String HOST = "127.0.0.1";
    //private static int ID;

    public Client(int id) {
    //    ID = id;
    }

    public void startConnection() {

        try {
            Socket socket = new Socket(HOST, PORT);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, true);

            Scanner scanner = new Scanner(System.in);
            String text = "";

            while (!text.equals("exit")) {
                text = scanner.nextLine();
                printWriter.println(text);
                InputStream inputStream = socket.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String receivedText = bufferedReader.readLine();
                System.out.println("CLIENT:" + receivedText);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Client c1 = new Client(1);
        c1.startConnection();
    }

}
