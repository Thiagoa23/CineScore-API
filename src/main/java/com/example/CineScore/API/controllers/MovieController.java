package com.example.CineScore.API.controllers;

import com.example.CineScore.API.models.Movie;
import com.example.CineScore.API.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "http://localhost:3000")
public class MovieController {

    @Autowired
    private MovieService movieService;

    // Endpoint para adicionar um novo filme
    @PostMapping
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Optional<Movie> addedMovie = movieService.addMovie(movie);
        return addedMovie.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    // Endpoint para atualizar um filme existente
    @PutMapping("/{movieId}")
    public ResponseEntity<Movie> updateMovie(@PathVariable String movieId, @RequestBody Movie movie) {
        Optional<Movie> updatedMovie = movieService.updateMovie(movieId, movie);
        return updatedMovie.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para remover um filme
    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable String movieId) {
        boolean deleted = movieService.deleteMovie(movieId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Endpoint para listar todos os filmes
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    // Endpoint para buscar um único filme por nome
    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam String name) {
        List<Movie> movies = movieService.findMoviesByName(name);
        if (movies.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(movies);
        }
    }

    // Endpoint para listar filmes por gênero
    @GetMapping("/genre/{genreId}")
    public ResponseEntity<List<Movie>> getMoviesByGenre(@PathVariable String genreId) {
        List<Movie> movies = movieService.findMoviesByGenre(genreId);
        return ResponseEntity.ok(movies);
    }

    // Endpoint para retornar o Top 10 filmes
    @GetMapping("/top10")
    public List<Movie> getTop10Movies() {
        return movieService.getTop10Movies();
    }

    // Endpoint para retornar os últimos filmes lançados (limite de 5)
    @GetMapping("/latest")
    public ResponseEntity<List<Movie>> getLatestMovies() {
        List<Movie> latestMovies = movieService.getLatestMovies();
        return ResponseEntity.ok(latestMovies);
    }

    // Endpoint para adicionar uma avaliação a um filme
    @PostMapping("/{movieId}/rate")
    public ResponseEntity<Void> addRating(@PathVariable String movieId, @RequestParam int rating) {
        if (rating < 1 || rating > 5) {
            return ResponseEntity.badRequest().build(); // Avaliação deve ser entre 1 e 5
        }
        movieService.addRating(movieId, rating);
        return ResponseEntity.ok().build();
    }
}
