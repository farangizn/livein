package com.xcdm.livein.repo;

import com.xcdm.livein.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Integer> {
    Optional<District> findByRegionId(Integer regionId);

    Optional<District> findByRegionNameAndRegionId(String regionName, Integer regionId);

}