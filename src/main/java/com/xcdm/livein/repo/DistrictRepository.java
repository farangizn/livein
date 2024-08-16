package com.xcdm.livein.repo;

import com.xcdm.livein.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Integer> {
    List<District> findAllByRegionId(Integer regionId);

    List<District> findAllByRegionNameAndRegionId(String regionName, Integer regionId);

}