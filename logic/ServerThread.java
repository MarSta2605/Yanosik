import java.io.*;
import java.net.Socket;
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

            String text = "";

            while (!text.equals("exit")) {
                text = bufferedReader.readLine();
                System.out.println("SERVER got a msg: " + text);
                printWriter.println(prepareData(Long.parseLong(text)));
            }

            socket.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    //prepared text, which will be sent to the client
    public StringBuilder prepareData(long id) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();

        List<VehicleDto> vehicleDtoList = dbController.getVehicles(dbController.getUser(id).getLogin());
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

}
