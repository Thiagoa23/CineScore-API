package com.example.CineScore.API.controllers;

import com.example.CineScore.API.models.Admin;
import com.example.CineScore.API.models.Movie;
import com.example.CineScore.API.models.Genre;
import com.example.CineScore.API.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody Admin admin) {
        Optional<Admin> savedAdmin = adminService.registerAdmin(admin);

        if (savedAdmin.isPresent()) {
            return ResponseEntity.ok(savedAdmin.get());
        } else {
            return ResponseEntity.badRequest().body("Username already exists");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        // Lógica de autenticação
        return ResponseEntity.ok("Admin logged in successfully");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutAdmin(@RequestParam String adminId) {
        adminService.logoutAdmin(adminId);
        return ResponseEntity.ok("Admin logged out successfully");
    }

   
}