package com.xcdm.livein.service;

import com.xcdm.livein.dto.DistrictDTO;
import com.xcdm.livein.entity.District;
import com.xcdm.livein.interfaces.DistrictService;
import com.xcdm.livein.mappers.DistrictMapper;
import com.xcdm.livein.repo.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {
    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;


    @Override
    public List<District> findByRegionId(Integer regionId) {
        return districtRepository.findAllByRegionId(regionId);
    }

    @Override
    public List<District> findByRegionNameAndRegionId(String name, Integer regionId) {
        return districtRepository.findAllByRegionNameAndRegionId(name, regionId);
    }

    @Override
    public HttpEntity<List<DistrictDTO>> getDistrict(Integer regionId, String name) {
        List<District> districts;

        if (name != null) {
            districts = findByRegionNameAndRegionId(name, regionId);
        } else {
            districts = findByRegionId(regionId);
        }

        List<DistrictDTO> dto = districts.stream()
                .map(district -> {
                    DistrictDTO districtMapperDto = districtMapper.toDto(district);
                    districtMapperDto.setRegionId(district.getRegion().getId());
                    return districtMapperDto;
                })
                .toList();
        return ResponseEntity.ok(dto);

    }
}
