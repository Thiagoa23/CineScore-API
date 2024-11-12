package com.example.CineScore.API.controllers;

import com.example.CineScore.API.models.Admin;
import com.example.CineScore.API.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(@RequestBody Admin newAdmin,
            @RequestParam(required = false) String requestingAdminId) {
        try {
            Admin savedAdmin = adminService.registerAdmin(newAdmin, requestingAdminId);
            return ResponseEntity.ok(savedAdmin);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        }
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<?> deleteAdmin(@PathVariable String adminId, @RequestParam String requestingAdminId) {
        boolean deleted = adminService.deleteAdmin(adminId, requestingAdminId);
        return deleted ? ResponseEntity.ok("Admin deleted successfully")
                : ResponseEntity.status(HttpStatus.FORBIDDEN).body("Permission denied");
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