package com.example.CineScore.API.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.CineScore.API.models.Movie;
import java.util.Optional;
import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {
    // Método para buscar filme por nome exato, ignorando maiúsculas e minúsculas
    Optional<Movie> findByNameIgnoreCase(String name);

    // Para buscar filmes contendo o termo (ignorando maiúsculas/minúsculas)
    List<Movie> findByNameContainingIgnoreCase(String name);

    // Para buscar filmes pelo gênero principal
    List<Movie> findByPrimaryGenre(String genreId);

    // Para buscar filmes por outros gêneros associados
    List<Movie> findByOtherGenresContaining(String genreId);

    // Para buscar os Top 10 filmes por classificação
    List<Movie> findTop10ByOrderByRatingDesc();

    // Para buscar os 5 ultimos filmes lançados
    List<Movie> findTop5ByOrderByReleaseDateDesc();
}
