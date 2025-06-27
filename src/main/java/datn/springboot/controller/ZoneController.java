package datn.springboot.controller;

import datn.springboot.entity.Block;
import datn.springboot.entity.Shelf;
import datn.springboot.entity.Zone;
import datn.springboot.service.BlockService;
import datn.springboot.service.ShelfService;
import datn.springboot.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/Zone")
public class ZoneController {
    private final ZoneService zoneService;
    private final ShelfService shelfService;
    private final BlockService blockService;

    @Autowired public ZoneController(ZoneService zoneService, ShelfService shelfService, BlockService blockService) {
        this.zoneService = zoneService;
        this.shelfService = shelfService;
        this.blockService = blockService;
    }

    @PostMapping
    public ResponseEntity<Zone> addZone(@RequestBody Zone zone) {
        // 1. Lưu Zone trước để sinh zoneId nếu chưa có
        Zone savedZone = zoneService.saveZone(zone);

        List<String> shelfIds = savedZone.getShelfIdList();
        if (shelfIds == null) shelfIds = new ArrayList<>();

        for (String shelfId : shelfIds) {
            // Tạo Shelf
            Shelf shelf = new Shelf();
            shelf.setId(shelfId);
            shelf.setZoneId(savedZone.getId());

            List<String> blockIds = new ArrayList<>();
            String[][] blockPatterns = {
                    {"1", "1"}, {"1", "2"},
                    {"2", "1"}, {"2", "2"},
                    {"3", "1"}, {"3", "2"}
            };

            // Tạo các Block trong shelf
            for (String[] pattern : blockPatterns) {
                String blockId = shelfId + "." + pattern[0] + "." + pattern[1];

                Block block = new Block();
                block.setId(blockId);
                block.setShelfId(shelfId);
                block.setZoneId(savedZone.getId());
                block.setHasPackage(false);
                block.setRfid(null);
                block.setBeaconName(null);

                blockService.saveBlock(block);
                blockIds.add(blockId);
            }

            shelf.setBlockIdList(blockIds);
            shelfService.saveShelf(shelf);
        }

        // 3. Cập nhật lại danh sách shelfId (đề phòng zone chưa có)
        savedZone.setShelfIdList(shelfIds);
        Zone updatedZone = zoneService.updateZone(savedZone.getId(), savedZone);

        return ResponseEntity.ok(updatedZone);
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
        zone.getShelfIdList().forEach(shelfId -> {
            if (shelfService.findShelfById(shelfId).isEmpty()){
                Shelf shelf = new Shelf();
                shelf.setId(shelfId);
                shelf.setZoneId(zone.getId());
                List<String> blockIds = new ArrayList<>();
                String[][] blockPatterns = {
                        {"1", "1"}, {"1", "2"},
                        {"2", "1"}, {"2", "2"},
                        {"3", "1"}, {"3", "2"}
                };

                for (String[] pattern : blockPatterns) {
                    String blockId = shelfId + "." + pattern[0] + "." + pattern[1];

                    Block block = new Block();
                    block.setId(blockId);                 // Gán blockId cụ thể
                    block.setShelfId(shelfId);
                    block.setZoneId(zone.getId());
                    block.setHasPackage(false);
                    block.setRfid(null);
                    block.setBeaconName(null);

                    blockService.saveBlock(block);
                    blockIds.add(blockId);
                }

                // Cập nhật danh sách block ID cho Shelf
                shelf.setBlockIdList(blockIds);
//            shelfService.updateShelf(shelfId, shelf);
                shelfService.saveShelf(shelf);
            };
        });
        return ResponseEntity.ok(zoneService.updateZone(id,zone));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZone(@PathVariable String id) {
        zoneService.deleteZone(id);
        return ResponseEntity.noContent().build();
    }


}