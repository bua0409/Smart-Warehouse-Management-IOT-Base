package datn.springboot.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("esp_config")
public class EspConfig {
    private String id;
    private String ssid;
    private String password;
    private String mqttServer;
    private String espId;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getSsid() {
        return ssid;
    }
    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getMqttServer() {
        return mqttServer;
    }
    public void setMqttServer(String mqttServer) {
        this.mqttServer = mqttServer;
    }
    public String getEspId() {
        return espId;
    }
    public void setEspId(String espId) {
        this.espId = espId;
    }
}
