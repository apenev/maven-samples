package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

/**
 * Represents a session recommendation with explanation and confidence score.
 */
public class Recommendation {
    
    @JsonProperty("session")
    private Session session;
    
    @JsonProperty("score")
    private double score; // Confidence score (0.0 to 1.0)
    
    @JsonProperty("explanation")
    private String explanation; // Why this session is recommended
    
    @JsonProperty("matchingFactors")
    private List<String> matchingFactors; // Specific factors that led to recommendation
    
    @JsonProperty("recommendationType")
    private String recommendationType; // "role-based", "interest-based", "collaborative", etc.
    
    @JsonProperty("userId")
    private String userId; // User this recommendation is for

    // Default constructor
    public Recommendation() {}

    // Constructor
    public Recommendation(Session session, double score, String explanation, 
                         List<String> matchingFactors, String recommendationType, String userId) {
        this.session = session;
        this.score = score;
        this.explanation = explanation;
        this.matchingFactors = matchingFactors;
        this.recommendationType = recommendationType;
        this.userId = userId;
    }

    // Getters and Setters
    public Session getSession() { return session; }
    public void setSession(Session session) { this.session = session; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public List<String> getMatchingFactors() { return matchingFactors; }
    public void setMatchingFactors(List<String> matchingFactors) { this.matchingFactors = matchingFactors; }

    public String getRecommendationType() { return recommendationType; }
    public void setRecommendationType(String recommendationType) { this.recommendationType = recommendationType; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return Objects.equals(session, that.session) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, userId);
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "session=" + session.getTitle() +
                ", score=" + score +
                ", recommendationType='" + recommendationType + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}