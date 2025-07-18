package datn.springboot.entity.dto;

public class PackageDTO {
    private String rfid;
    private String poId;
    private String productId;
    private String beacon_name;
    private String zone;
    private String block;
    private String time_in;
    private String time_out;
    private String status; // giữ nguyên là String để tránh lỗi Enum parsing

    public String getRfid() { return rfid; }
    public void setRfid(String rfid) { this.rfid = rfid; }

    public String getPoId() { return poId; }
    public void setPoId(String poId) { this.poId = poId; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getBeacon_name() { return beacon_name; }
    public void setBeacon_name(String beacon_name) { this.beacon_name = beacon_name; }

    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }

    public String getBlock() { return block; }
    public void setBlock(String block) { this.block = block; }

    public String getTime_in() { return time_in; }
    public void setTime_in(String time_in) { this.time_in = time_in; }

    public String getTime_out() { return time_out; }
    public void setTime_out(String time_out) { this.time_out = time_out; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
