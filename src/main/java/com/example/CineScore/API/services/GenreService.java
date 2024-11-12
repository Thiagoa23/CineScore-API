package com.example.CineScore.API.services;

import com.example.CineScore.API.models.Genre;
import com.example.CineScore.API.models.Movie;
import com.example.CineScore.API.repositories.GenreRepository;
import com.example.CineScore.API.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MovieRepository movieRepository;

    // Adicionar Gênero
    public Optional<Genre> addGenre(Genre genre) {
        Optional<Genre> existingGenre = genreRepository.findByName(genre.getName());
        if (existingGenre.isPresent()) {
            return Optional.empty(); // Gênero já existe
        } else {
            return Optional.of(genreRepository.save(genre)); // Salva e retorna o gênero
        }
    }

    // Atualizar Gênero
    public Optional<Genre> updateGenre(String genreId, Genre genre) {
        Optional<Genre> existingGenre = genreRepository.findById(genreId);
        if (existingGenre.isPresent()) {
            genre.setId(genreId); // Garante que o ID não seja alterado
            return Optional.of(genreRepository.save(genre)); // Atualiza e salva o gênero
        } else {
            return Optional.empty(); // Gênero não encontrado
        }
    }

    // Remover Gênero
    public boolean deleteGenre(String genreId) {
        if (genreRepository.existsById(genreId)) {
            genreRepository.deleteById(genreId);
            return true;
        }
        return false; // Gênero não encontrado
    }

    // Método para obter todos os gêneros
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    // Buscar Gênero por Nome
    public Optional<Genre> findGenreByName(String name) {
        return genreRepository.findByName(name);
    }

    // Listar Filmes de um Gênero
    public List<Movie> findMoviesByGenre(String genreId) {
        List<Movie> moviesByPrimaryGenre = movieRepository.findByPrimaryGenre(genreId);
        List<Movie> moviesByOtherGenres = movieRepository.findByOtherGenresContaining(genreId);
        
        List<Movie> allMovies = new ArrayList<>(moviesByPrimaryGenre);
        allMovies.addAll(moviesByOtherGenres);
        
        return allMovies;
    }
}
