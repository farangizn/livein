package com.xcdm.livein.controller;

import com.xcdm.livein.entity.Region;
import com.xcdm.livein.interfaces.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping
    public HttpEntity<Region> getRegion(@RequestParam String name) {

        Region region = regionService.findByName(name);

        if (region != null) {
            return ResponseEntity.ok(regionService.findByName(name));
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
