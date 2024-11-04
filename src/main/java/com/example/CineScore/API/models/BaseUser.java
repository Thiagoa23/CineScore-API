package com.example.CineScore.API.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseUser {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String username;

    private String password;
    private String role;

    public BaseUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
