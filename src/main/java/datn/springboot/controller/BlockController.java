package datn.springboot.controller;

import datn.springboot.entity.Block;
import datn.springboot.service.BlockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/Blocks")
public class BlockController {
    private final BlockService blockService;
    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    @GetMapping
    public ResponseEntity<List<Block>> getAllBlock() {
        return ResponseEntity.ok(blockService.getBlocks());
    }
    @GetMapping({"/{id}"})
    public ResponseEntity<Optional<Block>> getBlockById(@PathVariable String id) {
        return ResponseEntity.ok(blockService.getBlockById(id));
    }
    @GetMapping({"/hasPackage"})
    public ResponseEntity<List<Block>> getHasPackageBlock() {
        return ResponseEntity.ok(blockService.getBlocksByStatus(true));
    }
    @GetMapping({"/hasNoPackage"})
    public ResponseEntity<List<Block>> getHasNoPackageBlock() {
        return ResponseEntity.ok(blockService.getBlocksByStatus(false));
    }
//    @GetMapping({"/{zoneId}/hasPackage"})
//    public ResponseEntity<List<Block>> getHasPackageBlockByZoneId(@PathVariable String zoneId) {
//        return ResponseEntity.ok(blockService.getBlocksByZoneId(zoneId));
//    }

}
