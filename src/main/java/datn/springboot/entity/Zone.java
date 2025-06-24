package datn.springboot.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document(collection = "Zone")

public class Zone {
    private String id;
    private String name;
    private List<String> shelfIdList;
    public Zone(){}
    public Zone(String id, String name, List<String> shelfIdList) {
        this.id = id;
        this.name = name;
        this.shelfIdList = shelfIdList;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getShelfIdList() {
        return shelfIdList;
    }
    public void setShelfIdList(List<String> shelfIdList) {
        this.shelfIdList = shelfIdList;
    }
}
