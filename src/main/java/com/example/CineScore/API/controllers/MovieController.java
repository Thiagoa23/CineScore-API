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

    // Endpoint para listar todos os filmes com gêneros expandidos
    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    // Endpoint atualizado: Buscar filmes por termo no nome com gêneros expandidos
    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam("query") String query) {
        if (query == null || query.isEmpty()) {
            return ResponseEntity.badRequest().build(); // Retorna erro se o termo estiver vazio
        }

        List<Movie> movies = movieService.searchMoviesByName(query);
        if (movies.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retorna 204 se não encontrar resultados
        } else {
            return ResponseEntity.ok(movies);
        }
    }

    // Endpoint para listar filmes por gênero com gêneros expandidos
    @GetMapping("/genre/{genreId}")
    public ResponseEntity<List<Movie>> getMoviesByGenre(@PathVariable String genreId) {
        List<Movie> movies = movieService.findMoviesByGenre(genreId);
        return ResponseEntity.ok(movies);
    }

    // Endpoint para retornar o Top 10 filmes com gêneros expandidos
    @GetMapping("/top10")
    public ResponseEntity<List<Movie>> getTop10Movies() {
        List<Movie> top10Movies = movieService.getTop10Movies();
        return ResponseEntity.ok(top10Movies);
    }

    // Endpoint para retornar os últimos filmes lançados com gêneros expandidos
    @GetMapping("/latest")
    public ResponseEntity<List<Movie>> getLatestMovies() {
        List<Movie> latestMovies = movieService.getLatestMovies();
        return ResponseEntity.ok(latestMovies);
    }

    // Endpoint para obter detalhes completos de um filme, incluindo `comments`
    @GetMapping("/{movieId}")
    public ResponseEntity<Movie> getMovieById(@PathVariable String movieId) {
        Optional<Movie> movie = movieService.getMovieById(movieId);
        return movie.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
