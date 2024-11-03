package com.example.CineScore.API.models;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "admins")
public class Admin extends BaseUser {

    private boolean isFounder = false;

    public Admin(String username, String password, boolean isFounder) {
        super(username, password, "ADMIN");
        this.isFounder = isFounder;
    }
}

