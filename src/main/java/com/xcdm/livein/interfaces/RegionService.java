package com.xcdm.livein.interfaces;

import com.xcdm.livein.entity.Region;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface RegionService {

    Region findByName(String name);

    Optional<Region> findById(Integer regionId);

    HttpEntity<?> getRegion(String name);

    List<Region> getRegions();
}
