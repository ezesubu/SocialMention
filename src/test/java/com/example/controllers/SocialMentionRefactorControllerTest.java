package com.example.controllers;

import com.example.dtos.SocialMention;
import com.example.services.SocialMentionService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static io.micronaut.http.HttpStatus.*;

@MicronautTest
class SocialMentionRefactorControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    SocialMentionService socialMentionService;

    @Test
    void testAnalyze_HighRisk() {
        SocialMention socialMention = new SocialMention(
                "Test post spam", "user123", "user_twitter", "https://twitter.com/post/1",
                new String[]{"Nice con un"}
        );

        HttpRequest<SocialMention> request = HttpRequest.POST("/social-mention/analyze", socialMention);
        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);

        Assertions.assertEquals(OK, response.getStatus());
        Assertions.assertEquals("HIGH_RISK", response.body());
    }

    @Test
    void testAnalyze_MediumRisk() {
        SocialMention socialMention = new SocialMention(
                "Neutral post", "user123", "user_twitter", "https://twitter.com/post/2",
                new String[]{"I have mixed feelings and add a lot of words to test"}
        );

        HttpRequest<SocialMention> request = HttpRequest.POST("/social-mention/analyze", socialMention);
        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);

        Assertions.assertEquals(OK, response.getStatus());
        Assertions.assertEquals("MEDIUM_RISK", response.body());
    }

    @Test
    void testAnalyze_LowRisk() {
        SocialMention socialMention = new SocialMention(
                "post positive", "user123", "user_twitter", "https://twitter.com/post/3",
                new String[]{"I have mixed feelings and add a lot of words to test"}
        );

        HttpRequest<SocialMention> request = HttpRequest.POST("/social-mention/analyze", socialMention);
        HttpResponse<String> response = client.toBlocking().exchange(request, String.class);

        Assertions.assertEquals(OK, response.getStatus());
        Assertions.assertEquals("LOW_RISK", response.body());
    }
}
