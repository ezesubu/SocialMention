package com.example.enums;

public enum SocialMediaTable {
    ANALYZED_TWEETS("analyzed_tweets"),
    ANALYZED_FB_POSTS("analyzed_fb_posts");

    private final String tableName;

    SocialMediaTable(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }
}
