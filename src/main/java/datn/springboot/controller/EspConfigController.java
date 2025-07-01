package datn.springboot.controller;

import datn.springboot.entity.EspConfig;
import datn.springboot.repo.EspConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/esp/config")
public class EspConfigController {

    private final EspConfigRepository configRepo;

    public EspConfigController(EspConfigRepository configRepo) {
        this.configRepo = configRepo;
    }


    // GET cấu hình theo ESP ID
    @GetMapping("/{espId}")
    public ResponseEntity<?> getByEspId(@PathVariable String espId) {
        return configRepo.findByEspId(espId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET all
    @GetMapping
    public List<EspConfig> getAll() {
        return configRepo.findAll();
    }

    // POST: Thêm mới
    @PostMapping
    public EspConfig create(@RequestBody EspConfig config) {
        return configRepo.save(config);
    }

    // PUT: Cập nhật theo ID
    @PutMapping("/{id}")
    public ResponseEntity<EspConfig> update(@PathVariable String id, @RequestBody EspConfig newConfig) {
        return configRepo.findById(id)
                .map(existing -> {
                    existing.setSsid(newConfig.getSsid());
                    existing.setPassword(newConfig.getPassword());
                    existing.setMqttServer(newConfig.getMqttServer());
                    existing.setEspId(newConfig.getEspId());
                    return ResponseEntity.ok(configRepo.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Xoá theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        if (configRepo.existsById(id)) {
            configRepo.deleteById(id);
            return ResponseEntity.ok("Deleted");
        }
        return ResponseEntity.notFound().build();
    }
}
