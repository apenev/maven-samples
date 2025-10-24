package com.example.service;

import com.example.model.UserProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Service for managing user profiles and preferences.
 */
public class UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    // In-memory storage for demo purposes
    private final Map<String, UserProfile> users = new HashMap<>();
    
    public UserService() {
        initializeSampleUsers();
    }
    
    /**
     * Get all users.
     */
    public List<UserProfile> getAllUsers() {
        return new ArrayList<>(users.values());
    }
    
    /**
     * Get a user profile by ID.
     */
    public UserProfile getUserProfile(String userId) {
        return users.get(userId);
    }
    
    /**
     * Create or update a user profile.
     */
    public void saveUserProfile(UserProfile userProfile) {
        users.put(userProfile.getUserId(), userProfile);
        logger.info("Saved user profile: {}", userProfile.getName());
    }
    
    /**
     * Delete a user profile.
     */
    public void deleteUserProfile(String userId) {
        UserProfile removed = users.remove(userId);
        if (removed != null) {
            logger.info("Deleted user profile: {}", removed.getName());
        }
    }
    
    /**
     * Update user interests.
     */
    public void updateUserInterests(String userId, List<String> interests) {
        UserProfile user = users.get(userId);
        if (user != null) {
            user.setInterests(interests);
            logger.info("Updated interests for user: {}", user.getName());
        }
    }
    
    /**
     * Save a session to user's agenda.
     */
    public void saveSessionToAgenda(String userId, String sessionId) {
        UserProfile user = users.get(userId);
        if (user != null) {
            user.saveSession(sessionId);
            logger.info("User {} saved session {}", user.getName(), sessionId);
        }
    }
    
    /**
     * Remove a session from user's agenda.
     */
    public void removeSessionFromAgenda(String userId, String sessionId) {
        UserProfile user = users.get(userId);
        if (user != null) {
            user.unsaveSession(sessionId);
            logger.info("User {} removed session {}", user.getName(), sessionId);
        }
    }
    
    /**
     * Rate a session.
     */
    public void rateSession(String userId, String sessionId, int rating) {
        UserProfile user = users.get(userId);
        if (user != null) {
            user.rateSession(sessionId, rating);
            logger.info("User {} rated session {} with {}", user.getName(), sessionId, rating);
        }
    }
    
    /**
     * Add feedback for recommendations.
     */
    public void addRecommendationFeedback(String userId, String feedback) {
        UserProfile user = users.get(userId);
        if (user != null) {
            user.addFeedback(feedback);
            logger.info("Added feedback for user: {}", user.getName());
        }
    }
    
    /**
     * Get users by role.
     */
    public List<UserProfile> getUsersByRole(String role) {
        return users.values().stream()
                .filter(user -> role.equals(user.getRole()))
                .collect(ArrayList::new, (list, user) -> list.add(user), ArrayList::addAll);
    }
    
    /**
     * Initialize sample users for demonstration.
     */
    private void initializeSampleUsers() {
        // Sample Developer
        UserProfile developer = new UserProfile("dev-001", "John Smith", "john.smith@example.com", 
                "Developer", "Intermediate", 
                Arrays.asList("javascript", "react", "python", "machine-learning"));
        developer.setPreferredTracks(Arrays.asList("Web Development", "AI/ML"));
        saveUserProfile(developer);
        
        // Sample Data Scientist
        UserProfile dataScientist = new UserProfile("ds-001", "Emily Chen", "emily.chen@example.com",
                "Data Scientist", "Advanced",
                Arrays.asList("machine-learning", "python", "tensorflow", "deep-learning", "statistics"));
        dataScientist.setPreferredTracks(Arrays.asList("AI/ML"));
        saveUserProfile(dataScientist);
        
        // Sample Manager
        UserProfile manager = new UserProfile("mgr-001", "Michael Johnson", "michael.johnson@example.com",
                "Engineering Manager", "Advanced",
                Arrays.asList("leadership", "agile", "team-building", "project-management"));
        manager.setPreferredTracks(Arrays.asList("Management"));
        saveUserProfile(manager);
        
        // Sample DevOps Engineer
        UserProfile devops = new UserProfile("devops-001", "Sarah Wilson", "sarah.wilson@example.com",
                "DevOps Engineer", "Intermediate",
                Arrays.asList("aws", "docker", "kubernetes", "microservices", "security"));
        devops.setPreferredTracks(Arrays.asList("Cloud", "Security"));
        saveUserProfile(devops);
        
        // Sample Mobile Developer
        UserProfile mobileDev = new UserProfile("mobile-001", "Alex Rodriguez", "alex.rodriguez@example.com",
                "Mobile Developer", "Intermediate",
                Arrays.asList("flutter", "dart", "mobile", "react-native", "ios", "android"));
        mobileDev.setPreferredTracks(Arrays.asList("Mobile"));
        saveUserProfile(mobileDev);
        
        // Add some sample ratings and saved sessions
        developer.rateSession("web-001", 5);
        developer.rateSession("ai-001", 4);
        developer.saveSession("web-002");
        
        dataScientist.rateSession("ai-001", 5);
        dataScientist.rateSession("ai-002", 4);
        dataScientist.saveSession("ai-002");
        
        manager.rateSession("mgmt-001", 5);
        manager.saveSession("mgmt-002");
        
        logger.info("Initialized {} sample users", users.size());
    }
}