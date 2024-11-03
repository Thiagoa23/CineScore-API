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
    
    // Para buscar filmes por gênero (assumindo que 'genres' é uma lista de IDs de gêneros em Movie)
    List<Movie> findByGenresContaining(String genreId);
}
