package datn.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "package")
public class Package {
    @Id
    private String id;

    private String poId;
    private String productId;

    @Indexed(unique = true)
    private String rfid;

    private String beacon_name;
    private String zone;
    private String block;
    private String time_in;
    private String time_out;
    private PackageStatus status = PackageStatus.IMPORTED; // Mặc định là 'Nhập Kho'


    public Package(){}
    public Package(String poId,String productId,String rfid, String time_out, String time_in, String block, String zone, String beacon_name) {
        this.poId = poId;
        this.productId = productId;
        this.rfid = rfid;
        this.time_out = time_out;
        this.time_in = time_in;
        this.block = block;
        this.zone = zone;
        this.beacon_name = beacon_name;
    }
    public String getId() {
        return id;
    }
    public String getPoId() {
        return poId;
    }
    public void setPoId(String poId) {
        this.poId = poId;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
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

    public PackageStatus getStatus() {
        return status;
    }

    public void setStatus(PackageStatus status) {
        this.status = status;
    }
}
