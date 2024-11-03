package com.example.CineScore.API.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "notifications")
public class Notification {

    @Id
    private String id;
    private String userId;
    private String message;
    private boolean read = false;

    public Notification(String userId, String message) {
        this.userId = userId;
        this.message = message;
    }
}
