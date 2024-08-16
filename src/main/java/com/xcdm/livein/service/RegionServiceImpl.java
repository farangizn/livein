package com.xcdm.livein.service;

import com.xcdm.livein.entity.Region;
import com.xcdm.livein.interfaces.RegionService;
import lombok.RequiredArgsConstructor;
import com.xcdm.livein.repo.RegionRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    @Override
    public Region findByName(String name) {

        Optional<Region> regionOpt = regionRepository.findByName(name);
        return regionOpt.orElse(null);

    }

    @Override
    public Optional<Region> findById(Integer regionId) {
        return regionRepository.findById(regionId);
    }

    @Override
    public HttpEntity<?> getRegion(String name) {
        Region region = findByName(name);
        if (region != null) {
            return ResponseEntity.ok(region);
        } else {
            return ResponseEntity.badRequest().body("Region under name " + name + " not found");
        }
    }

    @Override
    public List<Region> getRegions() {
        return regionRepository.findAll();
    }
}
