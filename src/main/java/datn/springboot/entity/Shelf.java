package datn.springboot.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Document(collection = "Shelf")
public class Shelf {
    private String id;
    private List<String> blockIdList;
    private String zoneId;
    public Shelf() {};
    public Shelf(String id, List<String> blockIdList, String zoneId) {
        this.id = id;
        this.blockIdList = blockIdList;
        this.zoneId = zoneId;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<String> getBlockIdList() {
        return blockIdList;
    }
    public void setBlockIdList(List<String> blockIdList) {
        this.blockIdList = blockIdList;
    }
    public String getZoneId() {
        return zoneId;
    }
    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}
