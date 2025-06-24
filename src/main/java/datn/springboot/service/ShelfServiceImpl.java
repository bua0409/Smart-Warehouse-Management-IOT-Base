package datn.springboot.service;

import datn.springboot.entity.Shelf;
import datn.springboot.repo.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShelfServiceImpl implements ShelfService {

    private ShelfRepository shelfRepository;
    @Autowired
    public ShelfServiceImpl(ShelfRepository shelfRepository) {
        this.shelfRepository = shelfRepository;
    }

    @Override
    public List<Shelf> findAllShelfs() {
        return shelfRepository.findAll();
    }

    @Override
    public Optional<Shelf> findShelfById(String id) {
        return shelfRepository.findById(id);
    }

    @Override
    public Shelf saveShelf(Shelf shelf) {
        return shelfRepository.save(shelf);
    }

    @Override
    public void deleteShelf(String id) {
        shelfRepository.deleteById(id);
    }

    @Override
    public Shelf updateShelf(String id,Shelf shelf) {
        return shelfRepository.save(shelf);
    }

    @Override
    public List<Shelf> findShelfsByZoneId(String zoneId) {
        return (List<Shelf>) shelfRepository.findAll().stream().filter(shelf -> shelf.getZoneId().equals(zoneId));
    }
}
