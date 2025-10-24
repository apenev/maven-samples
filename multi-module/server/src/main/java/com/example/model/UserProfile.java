package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a user profile with preferences and interests for session recommendations.
 */
public class UserProfile {
    
    @JsonProperty("userId")
    private String userId;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("role")
    private String role; // Developer, Manager, Architect, etc.
    
    @JsonProperty("experienceLevel")
    private String experienceLevel; // Beginner, Intermediate, Advanced
    
    @JsonProperty("interests")
    private List<String> interests; // Technology interests/tags
    
    @JsonProperty("preferredTracks")
    private List<String> preferredTracks;
    
    @JsonProperty("savedSessions")
    private Set<String> savedSessions; // Session IDs
    
    @JsonProperty("attendedSessions")
    private Set<String> attendedSessions; // Session IDs
    
    @JsonProperty("sessionRatings")
    private java.util.Map<String, Integer> sessionRatings; // Session ID -> Rating (1-5)
    
    @JsonProperty("feedbackHistory")
    private List<String> feedbackHistory; // Feedback on recommendations

    // Default constructor
    public UserProfile() {
        this.savedSessions = new HashSet<>();
        this.attendedSessions = new HashSet<>();
        this.sessionRatings = new java.util.HashMap<>();
        this.feedbackHistory = new java.util.ArrayList<>();
    }

    // Constructor with essential fields
    public UserProfile(String userId, String name, String email, String role, 
                      String experienceLevel, List<String> interests) {
        this();
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.experienceLevel = experienceLevel;
        this.interests = interests;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getExperienceLevel() { return experienceLevel; }
    public void setExperienceLevel(String experienceLevel) { this.experienceLevel = experienceLevel; }

    public List<String> getInterests() { return interests; }
    public void setInterests(List<String> interests) { this.interests = interests; }

    public List<String> getPreferredTracks() { return preferredTracks; }
    public void setPreferredTracks(List<String> preferredTracks) { this.preferredTracks = preferredTracks; }

    public Set<String> getSavedSessions() { return savedSessions; }
    public void setSavedSessions(Set<String> savedSessions) { this.savedSessions = savedSessions; }

    public Set<String> getAttendedSessions() { return attendedSessions; }
    public void setAttendedSessions(Set<String> attendedSessions) { this.attendedSessions = attendedSessions; }

    public java.util.Map<String, Integer> getSessionRatings() { return sessionRatings; }
    public void setSessionRatings(java.util.Map<String, Integer> sessionRatings) { this.sessionRatings = sessionRatings; }

    public List<String> getFeedbackHistory() { return feedbackHistory; }
    public void setFeedbackHistory(List<String> feedbackHistory) { this.feedbackHistory = feedbackHistory; }

    // Utility methods
    public void saveSession(String sessionId) {
        this.savedSessions.add(sessionId);
    }

    public void unsaveSession(String sessionId) {
        this.savedSessions.remove(sessionId);
    }

    public void rateSession(String sessionId, int rating) {
        if (rating >= 1 && rating <= 5) {
            this.sessionRatings.put(sessionId, rating);
        }
    }

    public void addFeedback(String feedback) {
        this.feedbackHistory.add(feedback);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", experienceLevel='" + experienceLevel + '\'' +
                ", interests=" + interests +
                '}';
    }
}