package com.example.CineScore.API.controllers;

import com.example.CineScore.API.models.User;
import com.example.CineScore.API.services.UserService;
import com.example.CineScore.API.models.Admin;
import com.example.CineScore.API.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> registerData) {
        String username = registerData.get("username");
        String password = registerData.get("password");
        System.out.println("Tentando registrar usuário: " + username);

        if (userService.findByUsername(username).isPresent()) {
            System.out.println("Nome de usuário já existe: " + username);
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "O nome de usuário já está em uso."));
        }

        User newUser = new User(username, password);
        userService.registerUser(newUser);
        System.out.println("Usuário registrado com sucesso: " + username);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Registro concluído com sucesso!",
                "username", username,
                "role", "USER"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        // Verifica se é um admin
        Optional<Admin> adminOpt = adminService.findByUsername(username);
        if (adminOpt.isPresent() && adminOpt.get().getPassword().equals(password)) {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Login de administrador bem-sucedido!",
                    "username", username,
                    "role", "ADMIN"));
        }

        // Verifica se é um usuário normal
        Optional<User> userOpt = userService.findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Login bem-sucedido!",
                    "username", username,
                    "role", "USER"));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                "success", false,
                "message", "Usuário ou senha inválidos."));
    }

    @PostMapping("/movies/{movieId}/rate")
    public ResponseEntity<?> rateMovie(@PathVariable String movieId, @RequestParam int rating,
            @RequestParam String userId) {
        userService.rateMovie(movieId, rating, userId);
        return ResponseEntity.ok("Movie rated successfully");
    }

    @PostMapping("/movies/{movieId}/comment")
    public ResponseEntity<?> addComment(@PathVariable String movieId, @RequestParam String comment,
            @RequestParam int rating, @RequestParam String userId) {
        userService.addComment(movieId, comment, rating, userId);
        return ResponseEntity.ok("Comment added successfully");
    }

    @GetMapping("/{userId}/notifications")
    public ResponseEntity<?> getNotifications(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getNotifications(userId));
    }

    @PostMapping("/report")
    public ResponseEntity<?> reportUser(@RequestParam String reportedUserId, @RequestParam String reportingUserId,
            @RequestParam String reason) {
        userService.reportUser(reportedUserId, reportingUserId, reason);
        return ResponseEntity.ok("Report submitted successfully");
    }
}
