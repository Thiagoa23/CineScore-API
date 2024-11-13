package com.example.CineScore.API.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "movies")
public class Movie {

    @Id
    private String id;

    @Indexed(unique = true) // Garante unicidade do nome do filme
    private String name;

    private String synopsis;
    private LocalDate releaseDate;
    private String director;
    private List<String> actors;
    private Double rating = 0.0;
    private Map<Integer, Integer> ratings = new HashMap<>(); // Mapa para contagem de votos por estrela (1-5)
    private String imageUrl;
    private String primaryGenre; // ID do gênero principal
    private String primaryGenreName; // Nome do gênero principal expandido
    private List<String> otherGenres; // Lista de IDs de gênero associados ao filme
    private List<String> otherGenreNames = new ArrayList<>(); // Lista dos nomes dos gêneros secundários
    private List<Comment> comments = new ArrayList<>();

    public Movie(String name, String synopsis, LocalDate releaseDate, String director, List<String> actors, String imageUrl, String primaryGenre, List<String> otherGenres) {
        this.name = name;
        this.synopsis = synopsis;
        this.releaseDate = releaseDate;
        this.director = director;
        this.actors = actors;
        this.imageUrl = imageUrl;
        this.primaryGenre = primaryGenre;
        this.otherGenres = otherGenres;

        // Inicializa o mapa de ratings com contagens zeradas
        for (int i = 1; i <= 5; i++) {
            ratings.put(i, 0);
        }
    }

    public void addRating(int rating) {
        if (rating < 1 || rating > 5) return; // Valida o intervalo de classificação

        // Incrementa o contador de votos para a classificação especificada
        ratings.put(rating, ratings.getOrDefault(rating, 0) + 1);
        calculateAverageRating();
    }

    public void calculateAverageRating() {
        int totalVotes = 0;
        int totalScore = 0;

        for (Map.Entry<Integer, Integer> entry : ratings.entrySet()) {
            int stars = entry.getKey();
            int count = entry.getValue();
            totalVotes += count;
            totalScore += stars * count;
        }

        this.rating = totalVotes > 0 ? (double) totalScore / totalVotes : 0.0;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    // Método para definir os nomes dos gêneros secundários
    public void setOtherGenreNames(List<String> otherGenreNames) {
        this.otherGenreNames = otherGenreNames;
    }
}
