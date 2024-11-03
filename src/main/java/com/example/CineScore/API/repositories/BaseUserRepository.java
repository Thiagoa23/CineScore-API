package com.example.CineScore.API.repositories;

import com.example.CineScore.API.models.BaseUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseUserRepository extends MongoRepository<BaseUser, String> {
    //usar essa parte para métodos genéricos, se necessário, para todos os usuários.
}
