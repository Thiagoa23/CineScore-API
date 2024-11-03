package com.example.CineScore.API.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "genres")
public class Genre {

    @Id
    private String id;

    @Indexed(unique = true) // Garante unicidade do nome do gênero
    private String name;

    private Boolean primaryGenre;
    private List<String> associatedMovies; // Lista de IDs de filmes associados a esse gênero

    public Genre(String name, Boolean primaryGenre) {
        this.name = name;
        this.primaryGenre = primaryGenre;
    }

    public Genre(String name, Boolean primaryGenre, List<String> associatedMovies) {
        this.name = name;
        this.primaryGenre = primaryGenre;
        this.associatedMovies = associatedMovies;
    }
}
