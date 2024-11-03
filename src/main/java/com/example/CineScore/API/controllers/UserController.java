package com.example.CineScore.API.controllers;

import com.example.CineScore.API.models.User;
import com.example.CineScore.API.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        Optional<User> savedUser = userService.registerUser(user);

        // Verifica se o usuário foi salvo e retorna a resposta apropriada
        if (savedUser.isPresent()) {
            return ResponseEntity.ok(savedUser.get());
        } else {
            return ResponseEntity.badRequest().body("Username already exists");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        // Lógica de autenticação (não implementada)
        return ResponseEntity.ok("User logged in successfully");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestParam String userId) {
        userService.logoutUser(userId);
        return ResponseEntity.ok("User logged out successfully");
    }

    @PostMapping("/movies/{movieId}/rate")
    public ResponseEntity<?> rateMovie(@PathVariable String movieId, @RequestParam int rating, @RequestParam String userId) {
        userService.rateMovie(movieId, rating, userId);
        return ResponseEntity.ok("Movie rated successfully");
    }

    @PostMapping("/movies/{movieId}/comment")
    public ResponseEntity<?> addComment(@PathVariable String movieId, @RequestParam String comment, @RequestParam int rating, @RequestParam String userId) {
        userService.addComment(movieId, comment, rating, userId);
        return ResponseEntity.ok("Comment added successfully");
    }

    @GetMapping("/{userId}/notifications")
    public ResponseEntity<?> getNotifications(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getNotifications(userId));
    }

    @PostMapping("/report")
    public ResponseEntity<?> reportUser(@RequestParam String reportedUserId, @RequestParam String reportingUserId, @RequestParam String reason) {
        userService.reportUser(reportedUserId, reportingUserId, reason);
        return ResponseEntity.ok("Report submitted successfully");
    }
}
