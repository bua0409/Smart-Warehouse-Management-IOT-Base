package datn.springboot.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "block")
public class Block {
    private String id;
    private Boolean hasPackage;
    private String zoneId;
    private String shelfId;
    private String rfid;
    private String beaconName;

    public Block(String id, Boolean hasPackage, String zoneId, String shelfId, String rfid, String beaconName) {
        this.id = id;
        this.hasPackage = hasPackage;
        this.zoneId = zoneId;
        this.shelfId = shelfId;
        this.rfid = rfid;
        this.beaconName = beaconName;
    }
    public Block() {}
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Boolean getHasPackage() {
        return hasPackage;
    }
    public void setHasPackage(Boolean hasPackage) {
        this.hasPackage = hasPackage;
    }
    public String getZoneId() {
        return zoneId;
    }
    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
    public String getShelfId() {
        return shelfId;
    }
    public void setShelfId(String shelfId) {
        this.shelfId = shelfId;
    }
    public String getRfid() {
        return rfid;
    }
    public void setRfid(String rfid) {
        this.rfid = rfid;
    }
    public String getBeaconName() {
        return beaconName;
    }
    public void setBeaconName(String beaconName) {
        this.beaconName = beaconName;
    }
}
