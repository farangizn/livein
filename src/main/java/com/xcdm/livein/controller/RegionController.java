package com.xcdm.livein.controller;

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
    public HttpEntity<?> getRegion(@RequestParam(required = false) String name) {
        if (name == null) {
            return ResponseEntity.ok(regionService.getRegions());
        } else {
            return regionService.getRegion(name);
        }
    }
}
