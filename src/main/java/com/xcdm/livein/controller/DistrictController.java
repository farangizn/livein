package com.xcdm.livein.controller;

import com.xcdm.livein.dto.DistrictDTO;
import com.xcdm.livein.entity.District;
import com.xcdm.livein.interfaces.DistrictService;
import com.xcdm.livein.interfaces.RegionService;
import com.xcdm.livein.mappers.DistrictMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/districts")
public class DistrictController {

    private final DistrictService districtService;
    private final DistrictMapper districtMapper;

    @GetMapping("/{region_id}")
    public HttpEntity<DistrictDTO> getDistrict(@PathVariable(required = true) Integer region_id, @RequestParam String name) {

        Optional<District> districtOptional;

        if (name != null) {
            districtOptional = districtService.findByRegionNameAndRegionId(name, region_id);
        } else {
            districtOptional = districtService.findByRegionId(region_id);
        }

        return districtOptional.map(district -> ResponseEntity.ok(districtMapper.toDto(district))).orElseGet(() -> ResponseEntity.notFound().build());

    }
}
