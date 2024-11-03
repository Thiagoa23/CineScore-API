package com.example.CineScore.API.models;

import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseUser {
    @Id
    private String id;
    private String username;
    private String password;
    private String role;

    public BaseUser(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
