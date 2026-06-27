package com.learner.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Auth Service is HEALTHY");
    }
}
