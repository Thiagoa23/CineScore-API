package com.example.CineScore.API.models;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "admin")
public class Admin extends BaseUser {

    public Admin(String username, String password) {
        super(username, password, "ADMIN");
    }

}
