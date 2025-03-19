package com.example.controllers;

import com.example.dtos.SocialMention;
import com.example.services.SocialMentionService;
import com.example.services.TweeterAnalyzerService;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;
import io.micronaut.http.HttpResponse;

@Controller("/social-mention")
public class SocialMentionRefactorController {

    private final SocialMentionService socialMentionService;

    @Inject
    public SocialMentionRefactorController(SocialMentionService socialMentionService) {
        this.socialMentionService = socialMentionService;
    }

    @Post("/analyze")
    @Produces(MediaType.TEXT_PLAIN)
    public HttpResponse<String> analyze(@Body SocialMention socialMention) {
        if (socialMention == null) {
            return HttpResponse.badRequest("Error: SocialMention data is missing.");
        }

        String riskLevel = socialMentionService.analyzeSocialMention(socialMention);

        return HttpResponse.ok(riskLevel);
    }
}
