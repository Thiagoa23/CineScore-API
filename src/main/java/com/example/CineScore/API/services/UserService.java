package com.example.CineScore.API.services;

import com.example.CineScore.API.models.User;
import com.example.CineScore.API.models.Movie;
import com.example.CineScore.API.models.Comment;
import com.example.CineScore.API.models.Notification;
import com.example.CineScore.API.models.Report;
import com.example.CineScore.API.repositories.UserRepository;
import com.example.CineScore.API.repositories.AdminRepository;
import com.example.CineScore.API.repositories.MovieRepository;
import com.example.CineScore.API.repositories.NotificationRepository;
import com.example.CineScore.API.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ReportRepository reportRepository;

    public Optional<User> registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(userRepository.save(user));
    }

    public void rateMovie(String movieId, int rating, String userId) {
        Optional<Movie> movieOpt = movieRepository.findById(movieId);
        if (movieOpt.isPresent() && rating >= 1 && rating <= 5) {
            Movie movie = movieOpt.get();
            movie.addRating(rating);
            movieRepository.save(movie);
        }
    }

    public void addComment(String movieId, String commentText, int rating, String userId) {
        Optional<Movie> movieOpt = movieRepository.findById(movieId);
        Optional<User> userOpt = userRepository.findById(userId);
        if (movieOpt.isPresent() && userOpt.isPresent()) {
            Movie movie = movieOpt.get();
            User user = userOpt.get();

            Comment comment = new Comment();
            comment.setUserId(userId);
            comment.setUsername(user.getUsername());
            comment.setText(commentText);
            comment.setRating(rating);
            comment.setTimestamp(LocalDateTime.now());

            movie.addComment(comment);
            movieRepository.save(movie);
        }
    }

    public List<Notification> getNotifications(String userId) {
        return notificationRepository.findByUserId(userId);
    }

    public void logoutUser(String userId) {
        // Lógica para logout de um usuário padrão (pode limpar sessões ou tokens)
    }

    public void reportUser(String reportedUserId, String reportingUserId, String reason) {
        Report report = new Report(reportedUserId, reportingUserId, reason);
        reportRepository.save(report);
    }
}
