package com.example.CineScore.API.services;

import java.util.List;

import com.example.CineScore.API.models.Genre;
import com.example.CineScore.API.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public String createGenre(Genre genre) {
        // Verifica se já existe um gênero com o mesmo nome
        List<Genre> existingGenres = genreRepository.findByName(genre.getName());
        if (!existingGenres.isEmpty()) {
            // Retorna uma mensagem indicando que o gênero já existe
            return "Gênero já cadastrado: " + genre.getName();
        }
        genreRepository.save(genre);
        return "Gênero cadastrado com sucesso: " + genre.getName();
    }

    public Iterable<Genre> getAllGenres() {
        return genreRepository.findAll();
    }
}

