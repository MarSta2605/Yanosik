import java.util.Date;

public class InsuranceOfferDto {

    private long ID;
    private long vehicleID;
    private String insurer;
    private float price;
    private Date insertTime;

    public InsuranceOfferDto(long id, long vehicleID, String insurer, float price, Date insertTime) {
        this.ID = id;
        this.vehicleID = vehicleID;
        this.insurer = insurer;
        this.price = price;
        this.insertTime = insertTime;
    }

    public long getID() {
        return ID;
    }

    public long getVehicleID() {
        return vehicleID;
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