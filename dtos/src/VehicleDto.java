import java.util.Date;

public class VehicleDto {

    private long id;
    private String login;
    private String brand;
    private String model;
    private Date insertTime;

    public VehicleDto(long id, String login, String brand, String model, Date insertTime) {
        this.id = id;
        this.login = login;
        this.brand = brand;
        this.model = model;
        this.insertTime = insertTime;
    }

    public long getID() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public Date getInsertTime() {
        return insertTime;
    }
}

