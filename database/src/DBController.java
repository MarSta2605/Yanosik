import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBController {

    private Connection connection;
    ResultSet resultSet = null;

    public DBController() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/Yanosik", "postgres", "Marcin12#");
    }

    public ResultSet request(String query) throws SQLException {
        Statement statement = connection.createStatement();
        resultSet = statement.executeQuery(query);
        return resultSet;
    }

    public UserDto getUser(long id) throws SQLException {
        ResultSet user = request("select * from users where id = " + id);
        if (user.next()) {
            return new UserDto(
                    user.getLong("id"),
                    user.getString("login"),
                    user.getString("email"),
                    user.getString("password"),
                    user.getDate("insert_time")
            );
        }
        return null;
    }

    public List<VehicleDto> getVehicles(String login) throws SQLException {
        ResultSet vehicles = request("select * from vehicles where login = '" + login + "'");
        List<VehicleDto> vehicleDtoList = new ArrayList<>();
        while (vehicles.next()) {
            VehicleDto vehicleDto = new VehicleDto(
                    vehicles.getLong("id"),
                    vehicles.getString("login"),
                    vehicles.getString("brand"),
                    vehicles.getString("model"),
                    vehicles.getDate("insert_time")
            );
            vehicleDtoList.add(vehicleDto);
        }
        if (vehicleDtoList.size() > 0) return vehicleDtoList;
        return new ArrayList<>();
    }

    public List<InsuranceOfferDto> getInsurances(long vehicleID) throws SQLException {
        List<InsuranceOfferDto> insuranceOfferDtoList = new ArrayList<>();
        ResultSet insurances = request("select * from insurance_offers where vehicle_id = " + vehicleID);
        while (insurances.next()) {
            InsuranceOfferDto insuranceOfferDto = new InsuranceOfferDto(
                    insurances.getLong("id"),
                    insurances.getLong("vehicle_id"),
                    insurances.getString("insurer"),
                    insurances.getFloat("price"),
                    insurances.getDate("insert_time")
            );
            insuranceOfferDtoList.add(insuranceOfferDto);
        }

        if (insuranceOfferDtoList.size() > 0) return insuranceOfferDtoList;
        return new ArrayList<>();
    }

}
