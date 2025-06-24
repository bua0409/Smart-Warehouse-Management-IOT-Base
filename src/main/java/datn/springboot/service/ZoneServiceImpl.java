package datn.springboot.service;

import datn.springboot.entity.Zone;
import datn.springboot.repo.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZoneServiceImpl implements ZoneService {
    private final ZoneRepository zoneRepository;

    @Autowired
    public ZoneServiceImpl(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    @Override
    public Zone saveZone(Zone zone) {
        return zoneRepository.save(zone);
    }

    @Override
    public List<Zone> findAllZones() {
        return zoneRepository.findAll();
    }

    @Override
    public Optional<Zone> findZoneById(String id) {
        return zoneRepository.findById(id);
    }

    @Override
    public void deleteZone(String id) {
        zoneRepository.deleteById(id);
    }

    @Override
    public Zone updateZone(String id, Zone zone) {
        return zoneRepository.save(zone);
    }
}
