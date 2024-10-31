package com.example.CineScore.API.controllers;

import com.example.CineScore.API.models.Genre;
import com.example.CineScore.API.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/genres")
@CrossOrigin(origins = "http://localhost:3000")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @PostMapping
    public String addGenre(@RequestBody Genre genre) {
        return genreService.createGenre(genre);
    }


    @GetMapping
    public List<String> getGenres() {
        List<Genre> genres = (List<Genre>) genreService.getAllGenres();
        return genres.stream()
                    .map(Genre::getName)  
                    .collect(Collectors.toList());  
    }
}