package com.xcdm.livein.service;

import com.xcdm.livein.entity.District;
import com.xcdm.livein.interfaces.DistrictService;
import com.xcdm.livein.repo.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {
    private final DistrictRepository districtRepository;


    @Override
    public Optional<District> findByRegionId(Integer regionId) {
        return districtRepository.findByRegionId(regionId);
    }

    @Override
    public Optional<District> findByRegionNameAndRegionId(String name, Integer regionId) {
        return districtRepository.findByRegionNameAndRegionId(name, regionId);
    }
}
