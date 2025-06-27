package datn.springboot.entity;

import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "order")
public class Order {
    public String id;
    public List<String> productIdList;
    public List<String> blockIdList;

    public Order(){};
    public Order(String id, List<String> productIdList, List<String> blockIdList){
        this.id = id;
        this.productIdList = productIdList;
        this.blockIdList = blockIdList;
    }


    public String getId(){
        return id;
    }

    public List<String> getProductIdList() {
        return productIdList;
    }

    public List<String> getBlockIdList() {
        return blockIdList;
    }
    public void setId(String id) {
        this.id = id;
    }

    public void setBlockIdList(List<String> blockIdList) {
        this.blockIdList = blockIdList;
    }

    public void setProductIdList(List<String> productIdList) {
        this.productIdList = productIdList;
    }
}
