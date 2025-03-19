package com.example.dtos;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.annotation.Serdeable;

import java.util.Arrays;
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


    public SocialMention(String message, String facebookAccount, String tweeterAccount, String tweeterUrl, String[] facebookComments) {
        this.message = message;
        this.facebookAccount = facebookAccount;
        this.tweeterAccount = tweeterAccount;
        this.tweeterUrl = tweeterUrl;
        this.facebookComments = Arrays.asList(facebookComments);
    }

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

//ToDo: mejorar con micronaut getter and setter ademas de agregar validation en el backend
//package com.example.dtos;
//
//import io.micronaut.serde.annotation.Serdeable;
//import io.micronaut.core.annotation.Introspected;
//import io.micronaut.core.annotation.NonNull;
//import io.micronaut.core.annotation.Nullable;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import io.micronaut.core.annotation.AccessorsStyle;
//import io.micronaut.core.annotation.Creator;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//@Serdeable // Permite serialización en Micronaut
//@Introspected // Habilita introspección en tiempo de compilación
//@AccessorsStyle(fluent = true) // Permite setters/getters sin "get" ni "set"
//public class SocialMention {
//
//    @NonNull
//    private final String message;
//
//    private final Optional<String> facebookAccount;
//    private final Optional<String> tweeterAccount;
//    private final Optional<String> creationDate;
//    private final Optional<String> tweeterUrl;
//    private final List<String> facebookComments;
//
//    @Creator
//    public SocialMention(
//            @NonNull @JsonProperty("message") String message,
//            @Nullable @JsonProperty("facebookAccount") String facebookAccount,
//            @Nullable @JsonProperty("tweeterAccount") String tweeterAccount,
//            @Nullable @JsonProperty("creationDate") String creationDate,
//            @Nullable @JsonProperty("tweeterUrl") String tweeterUrl,
//            @Nullable @JsonProperty("facebookComments") List<String> facebookComments) {
//        this.message = message;
//        this.facebookAccount = Optional.ofNullable(facebookAccount);
//        this.tweeterAccount = Optional.ofNullable(tweeterAccount);
//        this.creationDate = Optional.ofNullable(creationDate);
//        this.tweeterUrl = Optional.ofNullable(tweeterUrl);
//        this.facebookComments = facebookComments != null ? List.copyOf(facebookComments) : Collections.emptyList();
//    }
//
//    public boolean hasComments() {
//        return !facebookComments.isEmpty();
//    }
//
//    public List<String> extractComments() {
//        return facebookComments;
//    }
//
//    /** Determina si el mensaje proviene de Facebook */
//    public boolean isFacebookPost() {
//        return facebookAccount.isPresent();
//    }
//
//    /** Determina si el mensaje proviene de Twitter */
//    public boolean isTweet() {
//        return tweeterAccount.isPresent();
//    }
//
//    // Getters con estilo fluido
//    public String message() { return message; }
//
//    public Optional<String> facebookAccount() { return facebookAccount; }
//
//    public Optional<String> tweeterAccount() { return tweeterAccount; }
//
//    public Optional<String> creationDate() { return creationDate; }
//
//    public Optional<String> tweeterUrl() { return tweeterUrl; }
//
//    public List<String> facebookComments() { return facebookComments; }
//}
