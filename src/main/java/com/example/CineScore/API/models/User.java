package com.example.CineScore.API.models;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "user")
public class User extends BaseUser {
    private String status;
    private int warnings;
    private String banLevel;
    private LocalDateTime banEndTime;
    private String banMessage;

    private static final int LOW_WARNING_LIMIT = 3;
    private static final int MEDIUM_WARNING_LIMIT = 2;

    public User(String username, String password) {
        super(username, password, "USER");
        this.status = "ACTIVE";
        this.warnings = 0;
        this.banLevel = "LOW";
        this.banMessage = "Você foi banido devido a violações das regras.";
    }

    public void incrementWarnings() {
        this.warnings++;
        if (shouldBan()) {
            applyBan();
        } else if (this.warnings > 0) {
            this.status = "WARNED";
        }
    }

    private boolean shouldBan() {
        if ("HIGH".equals(this.banLevel)) {
            return this.warnings >= 1;
        } else if ("MEDIUM".equals(this.banLevel)) {
            return this.warnings >= MEDIUM_WARNING_LIMIT;
        } else {
            return this.warnings >= LOW_WARNING_LIMIT;
        }
    }

    public void applyBan() {
        if ("LOW".equals(this.banLevel)) {
            this.status = "BANNED";
            this.banEndTime = LocalDateTime.now().plusDays(7);
        } else if ("MEDIUM".equals(this.banLevel)) {
            this.status = "BANNED";
            this.banEndTime = LocalDateTime.now().plusDays(30);
        } else {
            this.status = "BANNED";
            this.banEndTime = null;
        }
    }

    public void resetWarnings() {
        if (this.banEndTime != null && LocalDateTime.now().isAfter(this.banEndTime)) {
            this.warnings = 0;
            this.status = "ACTIVE";
            this.banLevel = "LOW";
            this.banEndTime = null;
        }
    }

    public void setBanMessage(String message) {
        this.banMessage = message;
    }
}

