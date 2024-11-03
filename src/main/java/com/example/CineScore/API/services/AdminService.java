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
import org.springframework.stereotype.Service;

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

    public Optional<Admin> registerAdmin(Admin newAdmin, String requestingAdminId) {
        Optional<Admin> requestingAdmin = adminRepository.findById(requestingAdminId);
        if (requestingAdmin.isPresent() && requestingAdmin.get().isFounder()) {
            return Optional.of(adminRepository.save(newAdmin)); // Apenas fundador pode adicionar admins
        }
        return Optional.empty(); // Negar acesso se não for o fundador
    }
    
    public boolean deleteAdmin(String adminIdToDelete, String requestingAdminId) {
        Optional<Admin> requestingAdmin = adminRepository.findById(requestingAdminId);
        Optional<Admin> adminToDelete = adminRepository.findById(adminIdToDelete);
    
        if (requestingAdmin.isPresent() && requestingAdmin.get().isFounder() && adminToDelete.isPresent()) {
            if (!adminToDelete.get().isFounder()) {
                adminRepository.deleteById(adminIdToDelete); // Fundador pode deletar outros admins
                return true;
            }
        }
        return false; // Negar acesso se não for o fundador ou tentando excluir o fundador
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
