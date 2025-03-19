package com.example.services;

import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

@Singleton
public interface DBService {
    void insertFBPost(String tableName, Double score, String message, String facebookAccount);
    void insertTweet(String tableName, Double score, String message, String tweeterUrl, String tweeterAccount);
}
