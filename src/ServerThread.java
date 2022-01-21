import java.io.*;
import java.net.Socket;

public class ServerThread extends Thread{

    private final Socket socket;

    public ServerThread (Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        try{
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, true);

            String text = "";

            while(!text.equals("exit")){
                text = bufferedReader.readLine();
                System.out.println("SERVER: " + text);
                printWriter.println("Server sent: " + text);
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
