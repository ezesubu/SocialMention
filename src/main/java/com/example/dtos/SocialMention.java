package com.example.dtos;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;

@Introspected
@Serdeable
public class SocialMention {
    private String message;
    private String facebookAccount;
    private String tweeterAccount;
    private String creationDate;
    private String tweeterUrl;
    private List<String> facebookComments;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFacebookAccount() {
        return facebookAccount;
    }

    public void setFacebookAccount(String facebookAccount) {
        this.facebookAccount = facebookAccount;
    }

    public String getTweeterAccount() {
        return tweeterAccount;
    }

    public void setTweeterAccount(String tweeterAccount) {
        this.tweeterAccount = tweeterAccount;
    }

    public List<String> getFacebookComments() {
        return facebookComments;
    }

    public void setFacebookComments(List<String> facebookComments) {
        this.facebookComments = facebookComments;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getTweeterUrl() {
        return tweeterUrl;
    }

    public void setTweeterUrl(String tweeterUrl) {
        this.tweeterUrl = tweeterUrl;
    }

    public boolean hasComments() {
        return this.facebookComments != null && !this.facebookComments.isEmpty();
    }

    public String extractComments() {
        if (hasComments()) {
            return String.join(" | ", this.facebookComments);
        }
        return "";
    }
}
