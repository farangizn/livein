package com.xcdm.livein.interfaces;

import com.xcdm.livein.dto.DistrictDTO;
import com.xcdm.livein.entity.District;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DistrictService {

    List<District> findByRegionId(Integer regionId);

    List<District> findByRegionNameAndRegionId(String name, Integer regionId);

    HttpEntity<List<DistrictDTO>> getDistrict(Integer regionId, String name);

}
