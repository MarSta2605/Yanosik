import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ServerThread extends Thread {

    private final Socket socket;
    private final DBController dbController;

    public ServerThread(Socket socket) throws SQLException {
        this.socket = socket;
        this.dbController = new DBController();
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream, true);

            //simple client verification
            {
                int clientID = -1;
                String log = bufferedReader.readLine();
                String pass = bufferedReader.readLine();
                clientID = getSpecificUserID(log, pass);
                while (clientID < 0) {
                    printWriter.println(-1);
                    log = bufferedReader.readLine();
                    pass = bufferedReader.readLine();
                    clientID = getSpecificUserID(log, pass);
                }
                printWriter.println(clientID);
            }

            String text = "";

            while (true) {
                try{
                    text = bufferedReader.readLine();
                    printWriter.println(prepareData(Long.parseLong(text)));
                }catch(Exception e){
                    System.out.println("Client disconnected");
                    break;
                }
            }
            socket.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private int getSpecificUserID(String login, String password) throws SQLException {
        ResultSet userResultSet = dbController.request("select * from users where login = '" + login + "'");
        if (userResultSet.next()) {
            if (checkPass(password, userResultSet.getString("password"))) {
                return userResultSet.getInt("id");
            }
        }
        return -1;
    }

    //prepared text, which will be sent to the client
    private StringBuilder prepareData(long id) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();

        List<VehicleDto> vehicleDtoList = dbController.getVehicles(dbController.getUser(id).getLogin());
        if (vehicleDtoList.size() == 0)
            return stringBuilder.append("That Client doesn't have a vehicle.");
        for (VehicleDto vehicleDto : vehicleDtoList) {
            List<InsuranceOfferDto> insuranceOfferDtoList = dbController.getInsurances(vehicleDto.getID());
            if (insuranceOfferDtoList.size() > 0) {
                stringBuilder.append("Available offers:;")
                        .append("->Vehicle: ")
                        .append(vehicleDto.getBrand())
                        .append(", ")
                        .append(vehicleDto.getModel())
                        .append(";")
                        .append("-->Offers:;");
                int counter = 1;
                for (InsuranceOfferDto insuranceOfferDto : insuranceOfferDtoList) {
                    stringBuilder.append("--->")
                            .append(counter)
                            .append(". Insurer: ")
                            .append(insuranceOfferDto.getInsurer())
                            .append(", price: ")
                            .append(insuranceOfferDto.getPrice())
                            .append(";");
                    counter++;
                }
            } else {
                stringBuilder.append("->No available offers for: ")
                        .append(vehicleDto.getBrand())
                        .append(", ")
                        .append(vehicleDto.getModel());
            }
        }
        return stringBuilder;
    }

    private Boolean checkPass(String pass, String storedHash) {
        //generate hash with const salt
        return generateHash(pass).equals(storedHash);
    }

    private String generateHash(String pass) {
        byte[] salt = getSalt();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hashedPassword = md.digest(pass.getBytes(StandardCharsets.UTF_8));
            for (byte b : hashedPassword)
                stringBuilder.append(String.format("%02x", b));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private byte[] getSalt() {
        //salt can be stored somewhere, e.g. in DB
        return "[B@2cdf8d8a".getBytes();
    }

}
