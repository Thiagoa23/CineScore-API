package com.example.CineScore.API.services;

import com.example.CineScore.API.models.Admin;
import com.example.CineScore.API.models.User;
import com.example.CineScore.API.models.Genre;
import com.example.CineScore.API.models.Movie;
import com.example.CineScore.API.repositories.AdminRepository;
import com.example.CineScore.API.repositories.UserRepository;
import com.example.CineScore.API.repositories.MovieRepository;
import com.example.CineScore.API.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    public Admin registerAdmin(Admin newAdmin, String requestingAdminId) {
        if (adminRepository.count() == 0) {
            // Não há administradores; registra o primeiro como fundador
            newAdmin.setFounder(true);
            newAdmin.setRole("FOUNDER");
            return adminRepository.save(newAdmin);
        }
    
        Optional<Admin> requestingAdmin = adminRepository.findById(requestingAdminId);
        if (requestingAdmin.isEmpty() || !requestingAdmin.get().isFounder()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Permission denied");
        }
    
        if (adminRepository.findByUsername(newAdmin.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Conflict");
        }
        
        newAdmin.setRole("ADMIN");
        return adminRepository.save(newAdmin);
    }
    
    public boolean deleteAdmin(String adminId, String requestingAdminId) {
        // Verifica se o administrador solicitante é o fundador
        Optional<Admin> requestingAdmin = adminRepository.findById(requestingAdminId);
        if (requestingAdmin.isEmpty() || !requestingAdmin.get().isFounder()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the founder can delete admins");
        }
        
        // Verifica se o adminId existe antes de tentar excluir
        Optional<Admin> adminToDelete = adminRepository.findById(adminId);
        if (adminToDelete.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }
        
        // Realiza a exclusão do administrador
        adminRepository.deleteById(adminId);
        return true;
    }
    

    public void banUser(String userId, String level) {
        Optional<User> userOpt = userRepository.findById(userId);
        userOpt.ifPresent(user -> {
            user.setBanLevel(level);
            user.applyBan(); // Aplica a lógica de banimento
            userRepository.save(user);
        });
    }

    public void manageMovies(Movie movie, String action) {
        switch (action) {
            case "add":
                movieRepository.save(movie);
                break;
            case "edit":
                movieRepository.save(movie);
                break;
            case "delete":
                movieRepository.delete(movie);
                break;
        }
    }

    public void manageGenres(Genre genre, String action) {
        switch (action) {
            case "add":
                genreRepository.save(genre);
                break;
            case "edit":
                genreRepository.save(genre);
                break;
            case "delete":
                genreRepository.delete(genre);
                break;
        }
    }

    public void receiveReport(String reportId) {
        // Lógica para visualizar e gerenciar denúncias recebidas de usuários
    }

    public void sendWarning(String userId, String message) {
        Optional<User> userOpt = userRepository.findById(userId);
        userOpt.ifPresent(user -> {
            // Envia um alerta para o usuário por comportamento inadequado
        });
    }

    public void logoutAdmin(String adminId) {
        // Lógica para logout do administrador
    }
}
