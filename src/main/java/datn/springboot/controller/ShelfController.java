package datn.springboot.controller;

import datn.springboot.entity.Shelf;
import datn.springboot.entity.Zone;
import datn.springboot.service.ShelfService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/Shelf")
public class ShelfController {
    private final ShelfService shelfService;
    public ShelfController(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    @GetMapping
    public ResponseEntity<List<Shelf>> getAllShelf() {
        return ResponseEntity.ok(shelfService.findAllShelfs());
    }
    @GetMapping({"/id"})
    public ResponseEntity<Optional<Shelf>> getShelfById(@PathVariable String id) {
        return ResponseEntity.ok(shelfService.findShelfById(id));
    }
    @PutMapping({"/id"})
    public ResponseEntity<Shelf> updateShelfById(@PathVariable String id, @RequestBody Shelf shelf) {
        return ResponseEntity.ok(shelfService.updateShelf(id, shelf));
    }
    @GetMapping({"zoneId"})
    public ResponseEntity<List<Shelf>> getShelfByZoneId(@PathVariable String zoneId) {
        return ResponseEntity.ok(shelfService.findShelfsByZoneId(zoneId));
    }
}
