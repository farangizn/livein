package com.xcdm.livein.interfaces;

import com.xcdm.livein.entity.District;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface DistrictService {

    Optional<District> findByRegionId(Integer regionId);

    Optional<District> findByRegionNameAndRegionId(String name, Integer regionId);
}
