package com.xcdm.livein.controller;

import com.xcdm.livein.dto.DistrictDTO;
import com.xcdm.livein.interfaces.DistrictService;
import com.xcdm.livein.mappers.DistrictMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/districts")
public class DistrictController {

    private final DistrictService districtService;

    @GetMapping("/{region_id}")
    public HttpEntity<List<DistrictDTO>> getDistrict(@PathVariable Integer region_id, @RequestParam(required = false) String name) {
       return districtService.getDistrict(region_id, name);
    }
}
