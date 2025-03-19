package com.example.services;

import jakarta.inject.Singleton;

@Singleton
public class FacebookAnalyzerService {

    public double calculateFacebookCommentsScore(String comments) {
        return comments.length() > 50 ? 60d : 10d;
    }

    public double analyzePost(String message, String facebookAccount) {
        //ToDo: Simulación de análisis de post en Facebook
        if (message.contains("spam")) {
            return -50d;
        }
        if (message.contains("positive")) {
            return 80d;
        }
        return 20d;
    }
}
