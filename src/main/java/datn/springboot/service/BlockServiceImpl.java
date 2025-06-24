package datn.springboot.service;

import datn.springboot.entity.Block;
import datn.springboot.repo.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlockServiceImpl implements BlockService {
    private BlockRepository blockRepository;

    @Autowired
    public BlockServiceImpl(BlockRepository blockRepository) {
        this.blockRepository = blockRepository;
    }
    @Override
    public List<Block> getBlocks() {
        return blockRepository.findAll();
    }
    @Override
    public Optional<Block> getBlockById(String id) {
        return blockRepository.findById(id);
    }

    @Override
    public Block saveBlock(Block block) {
        return blockRepository.save(block);
    }

    @Override
    public Block updateBlock(Block block) {
        return blockRepository.save(block);
    }

    @Override
    public void deleteBlock(String id) {
        blockRepository.deleteById(id);
    }

    @Override
    public List<Block> getBlocksByZoneId(String zoneId) {
        List<Block> blocks = new ArrayList<>();
        blockRepository.findAll().forEach(block -> {
            if (block.getZoneId().equals(zoneId)) {
                blocks.add(block);
            }
        });
        return blocks;
    }

    @Override
    public List<Block> getBlocksByZoneIdAndStatus(String zoneId, Boolean status) {
        List<Block> blocks = new ArrayList<>();
        blockRepository.findAll().forEach(block -> {
            if (block.getZoneId().equals(zoneId)) {
                if (block.getHasPackage() == status) {
                    blocks.add(block);
                }
            }
        });
        return blocks;
    }

    @Override
    public List<Block> getBlocksByStatus(Boolean status) {
        List<Block> blocks = new ArrayList<>();
        blockRepository.findAll().forEach(block -> {
            if (block.getHasPackage() == status){
                blocks.add(block);
            }
        });
        return blocks;
    }

    @Override
    public List<Block> getBlocksByShelfIdAndStatus(String shelfId, Boolean status) {
        List<Block> blocks = new ArrayList<>();
        blockRepository.findAll().forEach(block -> {
            if (block.getShelfId().equals(shelfId)) {
                if (block.getHasPackage() == status) {
                    blocks.add(block);
                }
            }
        });
        return blocks;
    }
}
