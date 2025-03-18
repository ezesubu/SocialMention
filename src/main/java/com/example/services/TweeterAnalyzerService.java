package com.example.services;

import jakarta.inject.Singleton;

@Singleton
public class TweeterAnalyzerService {

    public double analyzeTweet(String message, String tweeterUrl, String tweeterAccount) {
        //ToDo: Simulación de análisis de post en Twitter
        if (message.contains("alert")) {
            return -0.8d;
        }
        if (message.contains("happy")) {
            return 0.9d;
        }
        return 0.5d;
    }
}
