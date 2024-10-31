package com.example.CineScore.API.repositories;

import java.util.List;

import com.example.CineScore.API.models.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenreRepository extends MongoRepository<Genre, String> {
    public List<Genre> findByName(String name);
}
