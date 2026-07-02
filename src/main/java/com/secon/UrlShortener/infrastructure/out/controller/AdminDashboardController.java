package com.secon.UrlShortener.infrastructure.out.controller;

import com.secon.UrlShortener.domain.usecase.AdminDashboardUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AdminDashboardController {

    private final AdminDashboardUseCase adminDashboard;

    public AdminDashboardController(AdminDashboardUseCase adminDashboard) {
        this.adminDashboard = adminDashboard;
    }

    @GetMapping("/api/admin/dashboard")
    public ResponseEntity<?> getDataFromOriginalUrl(@RequestParam String originalUrl){
        return ResponseEntity.status(200).body(adminDashboard.getDataByOriginalUrl(originalUrl));
    }

    @GetMapping("/api/admin/dashboard/all")
    public ResponseEntity<?> getAllData(){
        return ResponseEntity.status(200).body(adminDashboard.getAllData());
    }
}
