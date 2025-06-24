package datn.springboot.service;

import datn.springboot.entity.Shelf;

import java.util.List;
import java.util.Optional;

public interface ShelfService {
    List<Shelf> findAllShelfs();
    Optional<Shelf> findShelfById(String id);
    Shelf saveShelf(Shelf shelf);
    void deleteShelf(String id);
    Shelf updateShelf(String id,Shelf shelf);
    List<Shelf> findShelfsByZoneId(String zoneId);
}
