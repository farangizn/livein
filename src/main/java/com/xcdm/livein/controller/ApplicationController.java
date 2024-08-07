package com.xcdm.livein.controller;

import lombok.RequiredArgsConstructor;
import com.xcdm.livein.entity.Application;
import com.xcdm.livein.interfaces.ApplicationService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public HttpEntity<Application> save(@RequestBody Application application) {
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.save(application));
    }
}
