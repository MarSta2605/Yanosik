import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client {

    private static final int PORT = 2605;
    private static final String HOST = "127.0.0.1";
    private static int ID;

    public Client(int id) {
        ID = id;
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
                printWriter.println(ID);
                InputStream inputStream = socket.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                System.out.println("CLIENT " + ID + " got a msg:");
                Arrays.stream(bufferedReader.readLine().split(";"))
                        .toList()
                        .forEach(System.out::println);
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
