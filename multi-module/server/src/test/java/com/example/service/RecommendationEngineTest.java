package com.example.service;

import com.example.model.Recommendation;
import com.example.model.Session;
import com.example.model.UserProfile;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for the RecommendationEngine.
 */
public class RecommendationEngineTest {
    
    private SessionService sessionService;
    private UserService userService;
    private RecommendationEngine recommendationEngine;
    
    @Before
    public void setUp() {
        sessionService = new SessionService();
        userService = new UserService();
        recommendationEngine = new RecommendationEngine(sessionService, userService);
    }
    
    @Test
    public void testGenerateRecommendations_ValidUser() {
        // Arrange
        String userId = "test-user";
        UserProfile user = new UserProfile(userId, "Test User", "test@example.com", 
                "Developer", "Intermediate", Arrays.asList("javascript", "react"));
        userService.saveUserProfile(user);
        
        // Act
        List<Recommendation> recommendations = recommendationEngine.generateRecommendations(userId, 5);
        
        // Assert
        assertNotNull(recommendations);
        assertFalse(recommendations.isEmpty());
        
        // Should have recommendations
        assertTrue(recommendations.size() > 0);
        
        // Check that recommendations have required fields
        Recommendation firstRec = recommendations.get(0);
        assertNotNull(firstRec.getSession());
        assertTrue(firstRec.getScore() > 0.0);
        assertNotNull(firstRec.getExplanation());
        assertNotNull(firstRec.getMatchingFactors());
    }
    
    @Test
    public void testGenerateRecommendations_UserNotFound() {
        // Arrange
        String userId = "non-existent-user";
        
        // Act
        List<Recommendation> recommendations = recommendationEngine.generateRecommendations(userId, 5);
        
        // Assert
        assertNotNull(recommendations);
        assertTrue(recommendations.isEmpty());
    }
    
    @Test
    public void testGenerateRecommendations_ExcludesSavedSessions() {
        // Arrange
        String userId = "test-user";
        UserProfile user = new UserProfile(userId, "Test User", "test@example.com", 
                "Developer", "Intermediate", Arrays.asList("javascript", "react"));
        user.saveSession("web-001"); // User already saved this session
        userService.saveUserProfile(user);
        
        // Act
        List<Recommendation> recommendations = recommendationEngine.generateRecommendations(userId, 5);
        
        // Assert
        assertNotNull(recommendations);
        // Should not recommend sessions that are already saved
        assertTrue(recommendations.stream().noneMatch(rec -> "web-001".equals(rec.getSession().getId())));
    }
    
    @Test
    public void testGenerateRecommendations_RoleBasedMatching() {
        // Arrange
        String userId = "test-user";
        UserProfile user = new UserProfile(userId, "Test User", "test@example.com", 
                "Data Scientist", "Advanced", Arrays.asList("machine-learning", "python"));
        userService.saveUserProfile(user);
        
        // Act
        List<Recommendation> recommendations = recommendationEngine.generateRecommendations(userId, 5);
        
        // Assert
        assertNotNull(recommendations);
        assertFalse(recommendations.isEmpty());
        
        // Should find AI/ML sessions for Data Scientist
        boolean hasAIMLSession = recommendations.stream()
                .anyMatch(rec -> "AI/ML".equals(rec.getSession().getTrack()));
        assertTrue("Should recommend AI/ML sessions for Data Scientist", hasAIMLSession);
    }
    
    @Test
    public void testGenerateRecommendations_LimitsResults() {
        // Arrange
        String userId = "test-user";
        UserProfile user = new UserProfile(userId, "Test User", "test@example.com", 
                "Developer", "Intermediate", Arrays.asList("javascript"));
        userService.saveUserProfile(user);
        
        // Act
        List<Recommendation> recommendations = recommendationEngine.generateRecommendations(userId, 3);
        
        // Assert
        assertNotNull(recommendations);
        assertTrue(recommendations.size() <= 3); // Should limit to 3 results or fewer
    }
}