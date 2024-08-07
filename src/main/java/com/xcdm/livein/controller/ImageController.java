package com.xcdm.livein.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image-create")
@RequiredArgsConstructor
public class ImageController {

    @PostMapping
    public HttpEntity<?> createImage(@RequestParam String text) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(text);
    }
}
