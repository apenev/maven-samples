package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

/**
 * Represents a conference session that can be recommended to users.
 */
public class Session {
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("description")
    private String description;
    
    @JsonProperty("speaker")
    private String speaker;
    
    @JsonProperty("track")
    private String track;
    
    @JsonProperty("level")
    private String level; // Beginner, Intermediate, Advanced
    
    @JsonProperty("tags")
    private List<String> tags;
    
    @JsonProperty("duration")
    private int duration; // in minutes
    
    @JsonProperty("room")
    private String room;
    
    @JsonProperty("timeSlot")
    private String timeSlot;
    
    @JsonProperty("targetRoles")
    private List<String> targetRoles;
    
    @JsonProperty("rating")
    private double rating;
    
    @JsonProperty("attendeeCount")
    private int attendeeCount;

    // Default constructor
    public Session() {}

    // Constructor with essential fields
    public Session(String id, String title, String description, String speaker, 
                   String track, String level, List<String> tags, List<String> targetRoles) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.speaker = speaker;
        this.track = track;
        this.level = level;
        this.tags = tags;
        this.targetRoles = targetRoles;
        this.rating = 0.0;
        this.attendeeCount = 0;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSpeaker() { return speaker; }
    public void setSpeaker(String speaker) { this.speaker = speaker; }

    public String getTrack() { return track; }
    public void setTrack(String track) { this.track = track; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public String getTimeSlot() { return timeSlot; }
    public void setTimeSlot(String timeSlot) { this.timeSlot = timeSlot; }

    public List<String> getTargetRoles() { return targetRoles; }
    public void setTargetRoles(List<String> targetRoles) { this.targetRoles = targetRoles; }

    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    public int getAttendeeCount() { return attendeeCount; }
    public void setAttendeeCount(int attendeeCount) { this.attendeeCount = attendeeCount; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(id, session.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Session{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", speaker='" + speaker + '\'' +
                ", track='" + track + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}