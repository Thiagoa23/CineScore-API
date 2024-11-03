package com.example.CineScore.API.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "reports")
public class Report {

    @Id
    private String id;
    private String reportedUserId;
    private String reportingUserId;
    private String reason;
    private boolean resolved = false;

    public Report(String reportedUserId, String reportingUserId, String reason) {
        this.reportedUserId = reportedUserId;
        this.reportingUserId = reportingUserId;
        this.reason = reason;
    }
}
