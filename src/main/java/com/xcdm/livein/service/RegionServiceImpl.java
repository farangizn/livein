package com.xcdm.livein.service;

import com.xcdm.livein.entity.Region;
import com.xcdm.livein.interfaces.RegionService;
import lombok.RequiredArgsConstructor;
import com.xcdm.livein.repo.RegionRepository;
import org.springframework.stereotype.Service;

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
}
