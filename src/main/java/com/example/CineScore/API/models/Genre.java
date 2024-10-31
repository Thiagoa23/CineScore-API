package com.example.CineScore.API.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import lombok.Data;

@Data
@Document(collection = "genres")
public class Genre {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

}