package com.example.services.impl;

import com.example.services.DBService;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

@Singleton
public class PostgresDBService implements DBService {

    private final String host;
    private final int port;

    public PostgresDBService(@Value("${db.host}") String host, @Value("${db.port}") int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void insertFBPost(String tableName, Double score, String message, String facebookAccount) {
        System.out.println("Insertando en " + tableName + ": " +
                " Score=" + score +
                " Message=" + message +
                " FacebookAccount=" + facebookAccount);
    }

    @Override
    public void insertTweet(String tableName, Double score, String message, String tweeterUrl, String tweeterAccount) {
        System.out.println("Insertando en " + tableName + ": " +
                " Score=" + score +
                " Message=" + message +
                " TweeterUrl=" + tweeterUrl +
                " TweeterAccount=" + tweeterAccount);
    }
}
