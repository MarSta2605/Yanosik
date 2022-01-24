import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client2 {

    private static final int PORT = 2605;
    private static final String HOST = "127.0.0.1";
    private int id;

    Client2() {
        this.id = -1;
    }

    public void start() {
        try {
            Socket socket = new Socket(HOST, PORT);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            Scanner scanner = new Scanner(System.in);

            //simple client verification
            while (id < 0) {
                System.out.println("Input Your login");
                String log = scanner.nextLine();
                printWriter.println(log);
                System.out.println("Input Your password");
//TODO password should be hidden, e.g. console.readPassword();
                String pass = scanner.nextLine();
                printWriter.println(pass);
                id = Integer.parseInt(bufferedReader.readLine());
            }

            String text = "";
            System.out.println("If You want to get offers, write something. If You want to disconnect, write \"exit\"");

            while (true) {
                text = scanner.nextLine();
                if (text.equals("exit")) break;
                printWriter.println(id);

                System.out.println("MSG from the server:");
                Arrays.stream(bufferedReader.readLine().split(";"))
                        .toList()
                        .forEach(System.out::println);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client2 c = new Client2();
        c.start();
    }

}
