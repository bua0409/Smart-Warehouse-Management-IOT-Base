package datn.springboot.controller;

import datn.springboot.entity.Zone;
import datn.springboot.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/Zone")
public class ZoneController {
    private final ZoneService zoneService;

    @Autowired public ZoneController(ZoneService zoneService) { this.zoneService = zoneService;}

    @PostMapping
    public ResponseEntity<Zone> addZone(@RequestBody Zone zone) {
        return ResponseEntity.ok(zoneService.saveZone(zone));
    }

    @GetMapping
    public ResponseEntity<List<Zone>> getAllZones() {
        return ResponseEntity.ok(zoneService.findAllZones());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Zone>> getZoneById(@PathVariable String id) {
        return ResponseEntity.ok(zoneService.findZoneById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Zone> updateZone(@PathVariable String id,@RequestBody Zone zone) {
        return ResponseEntity.ok(zoneService.updateZone(id,zone));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZone(@PathVariable String id) {
        zoneService.deleteZone(id);
        return ResponseEntity.noContent().build();
    }


}