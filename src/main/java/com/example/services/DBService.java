package com.example.services;

import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

@Singleton
public class DBService {

    private final String host;
    private final int port;

     public DBService(@Value("${db.host}") String host, @Value("${db.port}") int port) {
        this.host = host;
        this.port = port;
    }

    public void insertFBPost(String tableName, Double score, String message, String facebookAccount) {
        System.out.println("Insertando en " + tableName + ": " +
                " Score=" + score +
                " Message=" + message +
                " FacebookAccount=" + facebookAccount);
    }

    public void insertTweet(String tableName, Double score, String message, String tweeterUrl, String tweeterAccount) {
        System.out.println("Insertando en " + tableName + ": " +
                " Score=" + score +
                " Message=" + message +
                " TweeterUrl=" + tweeterUrl +
                " TweeterAccount=" + tweeterAccount);
    }
}
