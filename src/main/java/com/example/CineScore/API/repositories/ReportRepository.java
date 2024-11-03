package com.example.CineScore.API.repositories;

import com.example.CineScore.API.models.Report;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<Report, String> {
    // Podemos adicionar métodos adicionais para buscar relatórios específicos, se necessário
}
