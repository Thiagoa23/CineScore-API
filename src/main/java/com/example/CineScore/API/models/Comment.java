package com.example.CineScore.API.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Comment {
    private String userId;
    private String username;
    private String text;
    private int rating;
    private LocalDateTime timestamp;
}
