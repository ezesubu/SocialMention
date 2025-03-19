package com.example.services;

import com.example.dtos.SocialMention;
import com.example.enums.SocialMediaTable;
import com.example.services.impl.PostgresDBService;
import jakarta.inject.Singleton;

@Singleton
public class SocialMentionService {

    private final DBService dbService;
    private final FacebookAnalyzerService facebookAnalyzer;
    private final TweeterAnalyzerService tweeterAnalyzer;

    public SocialMentionService(PostgresDBService dbService, FacebookAnalyzerService facebookAnalyzer, TweeterAnalyzerService tweeterAnalyzer) {
        this.dbService = dbService;
        this.facebookAnalyzer = facebookAnalyzer;
        this.tweeterAnalyzer = tweeterAnalyzer;
    }

    public String analyzeSocialMention(SocialMention socialMention) {
        boolean isFacebook = socialMention.getFacebookAccount() != null;
        boolean isTweeter = socialMention.getTweeterAccount() != null;

        if (!isFacebook && !isTweeter) {
            return "Error: Twitter or Facebook account must be present.";
        }

        double facebookScore = isFacebook ? analyzeFacebook(socialMention) : 0d;
        double tweeterScore = isTweeter ? analyzeTwitter(socialMention) : 0d;

        return evaluateRisk(facebookScore, tweeterScore, isFacebook, isTweeter);
    }

    private double analyzeFacebook(SocialMention socialMention) {
        double facebookScore = 0d;
        double commentsScore = 0d;

        socialMention.setMessage("facebookMessage: " + socialMention.getMessage());

        if (socialMention.hasComments()) {
            commentsScore = facebookAnalyzer.calculateFacebookCommentsScore(socialMention.extractComments());
            if (commentsScore < 50d) {
                facebookScore -= 100d;
            }
        }

        if (facebookScore > -100d) {
            facebookScore = facebookAnalyzer.analyzePost(socialMention.getMessage(), socialMention.getFacebookAccount());
            dbService.insertFBPost(SocialMediaTable.ANALYZED_FB_POSTS.getTableName(), facebookScore, socialMention.getMessage(), socialMention.getFacebookAccount());
        }

        return facebookScore;
    }

    private double analyzeTwitter(SocialMention socialMention) {
        socialMention.setMessage("tweeterMessage: " + socialMention.getMessage());

        double tweeterScore = tweeterAnalyzer.analyzeTweet(
                socialMention.getMessage(),
                socialMention.getTweeterUrl(),
                socialMention.getTweeterAccount()
        );

        dbService.insertTweet(
                SocialMediaTable.ANALYZED_TWEETS.getTableName(),
                tweeterScore,
                socialMention.getMessage(),
                socialMention.getTweeterUrl(),
                socialMention.getTweeterAccount()
        );

        return tweeterScore;
    }

    private String evaluateRisk(double facebookScore, double tweeterScore, boolean isFacebook, boolean isTweeter) {
        if (isFacebook) {
            if (facebookScore == -100d) return "HIGH_RISK";
            if (facebookScore > -100d && facebookScore < 50d) return "MEDIUM_RISK";
            if (facebookScore >= 50d) return "LOW_RISK";
        }
        if (isTweeter) {
            if (tweeterScore >= -1d && tweeterScore <= -0.5d) return "HIGH_RISK";
            if (tweeterScore > -0.5d && tweeterScore < 0.7d) return "MEDIUM_RISK";
            if (tweeterScore >= 0.7d) return "LOW_RISK";
        }
        return "UNKNOWN_RISK";
    }
}
