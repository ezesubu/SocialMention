package com.example.services;

import com.example.dtos.SocialMention;
import jakarta.inject.Singleton;

@Singleton
public class SocialMentionService {
    private static final String ANALYZED_TWEETS_TABLE = "analyzed_tweets";
    private static final String ANALYZED_FB_TABLE = "analyzed_fb_posts";

    private final DBService dbService;
    private final FacebookAnalyzerService facebookAnalyzer;
    private final TweeterAnalyzerService tweeterAnalyzer;

    public SocialMentionService(DBService dbService, FacebookAnalyzerService facebookAnalyzer, TweeterAnalyzerService tweeterAnalyzer) {
        this.dbService = dbService;
        this.facebookAnalyzer = facebookAnalyzer;
        this.tweeterAnalyzer = tweeterAnalyzer;
    }

    public String analyzeSocialMention(SocialMention socialMention) {
        boolean isFacebook = socialMention.getFacebookAccount() != null;
        boolean isTweeter = socialMention.getTweeterAccount() != null;

        if (!isFacebook && !isTweeter) {
            return "Error: Tweeter or Facebook account must be present.";
        }

        double facebookScore = 0d;
        double tweeterScore = 0d;

        if (isFacebook) {
            double commentsScore = 0;
            socialMention.setMessage("facebookMessage: " + socialMention.getMessage());
            if (socialMention.hasComments()) {
                System.out.println(facebookAnalyzer.calculateFacebookCommentsScore(socialMention.extractComments()) );
                commentsScore += facebookAnalyzer.calculateFacebookCommentsScore(socialMention.extractComments());
                if (commentsScore < 50d) {
                    facebookScore -= 100d;
                }
            }
            if (facebookScore > -100) {
                facebookScore = facebookAnalyzer.analyzePost(socialMention.getMessage(), socialMention.getFacebookAccount());
                dbService.insertFBPost(ANALYZED_FB_TABLE, facebookScore, socialMention.getMessage(), socialMention.getMessage());
            }
        }

        if (isTweeter) {
            socialMention.setMessage("tweeterMessage: " + socialMention.getMessage());
            tweeterScore = tweeterAnalyzer.analyzeTweet(socialMention.getMessage(), socialMention.getTweeterUrl(), socialMention.getTweeterAccount());
            dbService.insertTweet(ANALYZED_TWEETS_TABLE, tweeterScore, socialMention.getMessage(), socialMention.getTweeterUrl(), socialMention.getTweeterAccount());
        }

        return evaluateRisk(facebookScore, tweeterScore, isFacebook, isTweeter);
    }

    private String evaluateRisk(double facebookScore, double tweeterScore, boolean isFacebook, boolean isTweeter) {
        if (isFacebook) {
            if (facebookScore == -100d) return "HIGH_RISK";
            if (facebookScore > -100d && facebookScore < 50d) return "MEDIUM_RISK";
            if (facebookScore >= 50d) return "LOW_RISK";
        }
        if (isTweeter) {
            if (tweeterScore >= -1 && tweeterScore <= -0.5d) return "HIGH_RISK";
            if (tweeterScore > -0.5d && tweeterScore < 0.7d) return "MEDIUM_RISK";
            if (tweeterScore >= 0.7d) return "LOW_RISK";
        }
        return "UNKNOWN_RISK";
    }
}
