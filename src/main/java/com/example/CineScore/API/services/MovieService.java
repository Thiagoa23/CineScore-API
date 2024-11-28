package com.example.CineScore.API.services;

import com.example.CineScore.API.models.Movie;
import com.example.CineScore.API.models.Genre;
import com.example.CineScore.API.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreService genreService;

    // Método auxiliar para normalizar strings (remover acentos e tornar minúsculas)
    private String normalizeString(String input) {
        if (input == null) {
            return "";
        }
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD); // Decompor acentos
        return Pattern.compile("\\p{InCombiningDiacriticalMarks}+").matcher(normalized).replaceAll("") // Remove marcas
                .toLowerCase(); // Torna minúscula
    }

    // Método auxiliar para expandir o nome do gênero primário e os gêneros
    // secundários
    private Movie expandGenres(Movie movie) {
        if (movie.getPrimaryGenre() != null) {
            Genre primaryGenre = genreService.findById(movie.getPrimaryGenre());
            if (primaryGenre != null) {
                movie.setPrimaryGenreName(primaryGenre.getName());
            }
        }

        if (movie.getOtherGenres() != null && !movie.getOtherGenres().isEmpty()) {
            List<String> genreNames = movie.getOtherGenres().stream()
                    .map(genreId -> {
                        Genre genre = genreService.findById(genreId);
                        return genre != null ? genre.getName() : null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            movie.setOtherGenreNames(genreNames);
        }
        return movie;
    }

    // Obter todos os filmes com gêneros expandidos
    public List<Movie> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(this::expandGenres)
                .collect(Collectors.toList());
    }

    // Novo método: Buscar filmes pelo termo no nome com normalização
    public List<Movie> searchMoviesByName(String name) {
        String normalizedQuery = normalizeString(name);

        return movieRepository.findAll().stream()
                .filter(movie -> normalizeString(movie.getName()).contains(normalizedQuery))
                .map(this::expandGenres) // Expande os gêneros para retornar nomes no lugar de IDs
                .collect(Collectors.toList());
    }

    // Buscar filmes por gênero (primário ou secundário) com gêneros expandidos
    public List<Movie> findMoviesByGenre(String genreId) {
        List<Movie> moviesByPrimaryGenre = movieRepository.findByPrimaryGenre(genreId);
        List<Movie> moviesByOtherGenres = movieRepository.findByOtherGenresContaining(genreId);

        List<Movie> allMovies = new ArrayList<>(moviesByPrimaryGenre);
        allMovies.addAll(moviesByOtherGenres);

        return allMovies.stream()
                .map(this::expandGenres)
                .collect(Collectors.toList());
    }

    // Obter os Top 10 filmes com gêneros expandidos
    public List<Movie> getTop10Movies() {
        return movieRepository.findTop10ByOrderByRatingDesc().stream()
                .map(this::expandGenres)
                .collect(Collectors.toList());
    }

    // Obter os últimos 5 filmes lançados com gêneros expandidos
    public List<Movie> getLatestMovies() {
        return movieRepository.findTop5ByOrderByReleaseDateDesc().stream()
                .map(this::expandGenres)
                .collect(Collectors.toList());
    }

    // Obter os detalhes de um filme específico com todos os atributos
    public Optional<Movie> getMovieById(String movieId) {
        System.out.println("Buscando filme com ID: " + movieId);
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isPresent()) {
            System.out.println("Filme encontrado: " + movie.get().getName());
            expandGenres(movie.get());
        } else {
            System.out.println("Filme não encontrado para ID: " + movieId);
        }
        return movie;
    }

    // Adicionar um novo filme
    public Optional<Movie> addMovie(Movie movie) {
        System.out.println("Tentando adicionar filme: " + movie);
        Optional<Movie> existingMovie = movieRepository.findByNameIgnoreCase(movie.getName());
        if (existingMovie.isPresent()) {
            System.out.println("Filme já existe no banco de dados: " + existingMovie.get().getName());
            return Optional.empty();
        } else {
            Movie savedMovie = movieRepository.save(movie);
            System.out.println("Filme salvo com sucesso no banco de dados: " + savedMovie);
            return Optional.of(savedMovie);
        }
    }

    // Atualizar um filme existente
    public Optional<Movie> updateMovie(String movieId, Movie movie) {
        Optional<Movie> existingMovie = movieRepository.findById(movieId);
        if (existingMovie.isPresent()) {
            movie.setId(movieId);
            return Optional.of(movieRepository.save(movie));
        } else {
            return Optional.empty();
        }
    }

    // Remover um filme
    public boolean deleteMovie(String movieId) {
        if (movieRepository.existsById(movieId)) {
            movieRepository.deleteById(movieId);
            return true;
        }
        return false;
    }

    // Adicionar uma avaliação ao filme
    public void addRating(String movieId, int rating) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        movie.ifPresent(m -> {
            m.addRating(rating);
            movieRepository.save(m);
        });
    }
}
