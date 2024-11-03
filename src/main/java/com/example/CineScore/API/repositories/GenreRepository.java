package com.example.CineScore.API.repositories;

import com.example.CineScore.API.models.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface GenreRepository extends MongoRepository<Genre, String> {
    Optional<Genre> findByName(String name);
}
