package datn.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product")
public class product {

    @Id
    private String rfid;
    private String beacon_name;
    private String zone;
    private String block;
    private String time_in;
    private String time_out;


    public product(){}
    public product(String rfid, String time_out, String time_in, String block, String zone, String beacon_name) {
        this.rfid = rfid;
        this.time_out = time_out;
        this.time_in = time_in;
        this.block = block;
        this.zone = zone;
        this.beacon_name = beacon_name;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getBeacon_name() {
        return beacon_name;
    }

    public void setBeacon_name(String beacon_name) {
        this.beacon_name = beacon_name;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getTime_in() {
        return time_in;
    }

    public void setTime_in(String time_in) {
        this.time_in = time_in;
    }

    public String getTime_out() {
        return time_out;
    }

    public void setTime_out(String time_out) {
        this.time_out = time_out;
    }
}
