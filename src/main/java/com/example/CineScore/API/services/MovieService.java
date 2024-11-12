package com.example.CineScore.API.services;

import com.example.CineScore.API.models.Movie;
import com.example.CineScore.API.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    // Adicionar Filme
    public Optional<Movie> addMovie(Movie movie) {
        Optional<Movie> existingMovie = movieRepository.findByNameIgnoreCase(movie.getName());
        if (existingMovie.isPresent()) {
            return Optional.empty(); // Filme já existe
        } else {
            return Optional.of(movieRepository.save(movie)); // Salva e retorna o filme
        }
    }

    // Atualizar Filme
    public Optional<Movie> updateMovie(String movieId, Movie movie) {
        Optional<Movie> existingMovie = movieRepository.findById(movieId);
        if (existingMovie.isPresent()) {
            movie.setId(movieId); // Garante que o ID não seja alterado
            return Optional.of(movieRepository.save(movie)); // Atualiza e salva o filme
        } else {
            return Optional.empty(); // Filme não encontrado
        }
    }

    // Remover Filme
    public boolean deleteMovie(String movieId) {
        if (movieRepository.existsById(movieId)) {
            movieRepository.deleteById(movieId);
            return true;
        }
        return false; // Filme não encontrado
    }

    // Método para obter todos os filmes
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    // Buscar Filme por Nome
    public List<Movie> findMoviesByName(String name) {
        return movieRepository.findByNameContainingIgnoreCase(name);
    }

    // Buscar Filmes por Gênero
    public List<Movie> findMoviesByGenre(String genreId) {
        List<Movie> moviesByPrimaryGenre = movieRepository.findByPrimaryGenre(genreId);
        List<Movie> moviesByOtherGenres = movieRepository.findByOtherGenresContaining(genreId);

        List<Movie> allMovies = new ArrayList<>(moviesByPrimaryGenre);
        allMovies.addAll(moviesByOtherGenres);

        return allMovies;
    }

    // Método para buscar o Top 10 filmes com maior classificação
    public List<Movie> getTop10Movies() {
        return movieRepository.findTop10ByOrderByRatingDesc();
    }

    public List<Movie> getLatestMovies() {
        return movieRepository.findTop5ByOrderByReleaseDateDesc(); // Ajuste o número conforme necessário
    }

    // Adicionar Avaliação ao Filme
    public void addRating(String movieId, int rating) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        movie.ifPresent(m -> {
            m.addRating(rating); // Adiciona avaliação e atualiza a média
            movieRepository.save(m);
        });
    }
}
