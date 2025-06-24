package datn.springboot.service;

import datn.springboot.entity.Block;

import java.util.List;
import java.util.Optional;

public interface BlockService {
    List<Block> getBlocks();
    Optional<Block> getBlockById(String id);
    Block saveBlock(Block block);
    Block updateBlock(Block block);
    void deleteBlock(String id);
    List<Block> getBlocksByZoneId(String zoneId);
    List<Block> getBlocksByZoneIdAndStatus(String zoneId, Boolean status);
    List<Block> getBlocksByStatus(Boolean status);
    List<Block> getBlocksByShelfIdAndStatus(String shelfId, Boolean status);
}
