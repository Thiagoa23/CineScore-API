package com.example.CineScore.API.controllers;

import com.example.CineScore.API.models.Genre;
import com.example.CineScore.API.models.Movie;
import com.example.CineScore.API.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/genres")
@CrossOrigin(origins = "http://localhost:3000")
public class GenreController {

    @Autowired
    private GenreService genreService;

    // Endpoint para adicionar um novo gênero
    @PostMapping
    public ResponseEntity<Genre> addGenre(@RequestBody Genre genre) {
        Optional<Genre> addedGenre = genreService.addGenre(genre);
        return addedGenre.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    // Endpoint para atualizar um gênero existente
    @PutMapping("/{genreId}")
    public ResponseEntity<Genre> updateGenre(@PathVariable String genreId, @RequestBody Genre genre) {
        Optional<Genre> updatedGenre = genreService.updateGenre(genreId, genre);
        return updatedGenre.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para remover um gênero
    @DeleteMapping("/{genreId}")
    public ResponseEntity<Void> deleteGenre(@PathVariable String genreId) {
        boolean deleted = genreService.deleteGenre(genreId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Endpoint para listar todos os gêneros
    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres() {
        List<Genre> genres = genreService.getAllGenres();
        return ResponseEntity.ok(genres);
    }

    // Endpoint para listar filmes de um gênero específico
    @GetMapping("/{genreId}/movies")
    public ResponseEntity<List<Movie>> getMoviesByGenre(@PathVariable String genreId) {
        List<Movie> movies = genreService.findMoviesByGenre(genreId);
        return ResponseEntity.ok(movies);
    }
}
