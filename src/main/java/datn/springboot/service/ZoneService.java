package datn.springboot.service;

import datn.springboot.entity.Zone;

import java.util.List;
import java.util.Optional;

public interface ZoneService {
    Zone saveZone(Zone zone);
    List<Zone> findAllZones();
    Optional<Zone> findZoneById(String id);
    void deleteZone(String id);
    Zone updateZone(String id, Zone zone);
}
