import java.util.Date;

public class InsuranceOfferDto {

    private long id;
    private long vehicleId;
    private String insurer;
    private float price;
    private Date insertTime;

    public InsuranceOfferDto(long id, long vehicleId, String insurer, float price, Date insertTime) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.insurer = insurer;
        this.price = price;
        this.insertTime = insertTime;
    }

    public long getID() {
        return id;
    }

    public long getVehicleID() {
        return vehicleId;
    }

    public String getInsurer() {
        return insurer;
    }

    public float getPrice() {
        return price;
    }

    public Date getInsertTime() {
        return insertTime;
    }
}