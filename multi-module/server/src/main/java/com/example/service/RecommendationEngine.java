package com.example.service;

import com.example.model.Session;
import com.example.model.UserProfile;
import com.example.model.Recommendation;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * AI-powered recommendation engine that suggests sessions based on user preferences,
 * collaborative filtering, and content-based filtering.
 */
public class RecommendationEngine {
    
    private static final Logger logger = LoggerFactory.getLogger(RecommendationEngine.class);
    
    private final SessionService sessionService;
    private final UserService userService;
    
    // Weights for different recommendation factors
    private static final double ROLE_MATCH_WEIGHT = 0.3;
    private static final double INTEREST_MATCH_WEIGHT = 0.25;
    private static final double EXPERIENCE_LEVEL_WEIGHT = 0.2;
    private static final double TRACK_PREFERENCE_WEIGHT = 0.15;
    private static final double COLLABORATIVE_WEIGHT = 0.1;
    
    public RecommendationEngine(SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }
    
    /**
     * Generate personalized session recommendations for a user.
     */
    public List<Recommendation> generateRecommendations(String userId, int maxRecommendations) {
        logger.info("Generating recommendations for user: {}", userId);
        
        UserProfile user = userService.getUserProfile(userId);
        if (user == null) {
            logger.warn("User profile not found for userId: {}", userId);
            return Collections.emptyList();
        }
        
        List<Session> allSessions = sessionService.getAllSessions();
        List<Recommendation> recommendations = new ArrayList<>();
        
        for (Session session : allSessions) {
            // Skip sessions user has already saved or attended
            if (user.getSavedSessions().contains(session.getId()) || 
                user.getAttendedSessions().contains(session.getId())) {
                continue;
            }
            
            double score = calculateRecommendationScore(user, session);
            if (score > 0.1) { // Minimum threshold
                String explanation = generateExplanation(user, session, score);
                List<String> matchingFactors = getMatchingFactors(user, session);
                String recommendationType = determineRecommendationType(user, session);
                
                recommendations.add(new Recommendation(session, score, explanation, 
                                                      matchingFactors, recommendationType, userId));
            }
        }
        
        // Sort by score and return top recommendations
        return recommendations.stream()
                .sorted((r1, r2) -> Double.compare(r2.getScore(), r1.getScore()))
                .limit(maxRecommendations)
                .collect(Collectors.toList());
    }
    
    /**
     * Calculate recommendation score using multiple factors.
     */
    private double calculateRecommendationScore(UserProfile user, Session session) {
        double totalScore = 0.0;
        
        // Role-based matching
        double roleScore = calculateRoleMatch(user, session);
        totalScore += roleScore * ROLE_MATCH_WEIGHT;
        
        // Interest-based matching
        double interestScore = calculateInterestMatch(user, session);
        totalScore += interestScore * INTEREST_MATCH_WEIGHT;
        
        // Experience level matching
        double experienceScore = calculateExperienceMatch(user, session);
        totalScore += experienceScore * EXPERIENCE_LEVEL_WEIGHT;
        
        // Track preference matching
        double trackScore = calculateTrackMatch(user, session);
        totalScore += trackScore * TRACK_PREFERENCE_WEIGHT;
        
        // Collaborative filtering
        double collaborativeScore = calculateCollaborativeScore(user, session);
        totalScore += collaborativeScore * COLLABORATIVE_WEIGHT;
        
        return Math.min(1.0, totalScore); // Cap at 1.0
    }
    
    /**
     * Calculate role-based matching score.
     */
    private double calculateRoleMatch(UserProfile user, Session session) {
        if (user.getRole() == null || session.getTargetRoles() == null) {
            return 0.0;
        }
        
        return session.getTargetRoles().contains(user.getRole()) ? 1.0 : 0.0;
    }
    
    /**
     * Calculate interest-based matching using cosine similarity.
     */
    private double calculateInterestMatch(UserProfile user, Session session) {
        if (user.getInterests() == null || session.getTags() == null) {
            return 0.0;
        }
        
        Set<String> userInterests = new HashSet<>(user.getInterests());
        Set<String> sessionTags = new HashSet<>(session.getTags());
        
        // Calculate intersection
        Set<String> intersection = new HashSet<>(userInterests);
        intersection.retainAll(sessionTags);
        
        if (intersection.isEmpty()) {
            return 0.0;
        }
        
        // Calculate Jaccard similarity
        Set<String> union = new HashSet<>(userInterests);
        union.addAll(sessionTags);
        
        return (double) intersection.size() / union.size();
    }
    
    /**
     * Calculate experience level matching.
     */
    private double calculateExperienceMatch(UserProfile user, Session session) {
        if (user.getExperienceLevel() == null || session.getLevel() == null) {
            return 0.5; // Neutral score if level not specified
        }
        
        String userLevel = user.getExperienceLevel().toLowerCase();
        String sessionLevel = session.getLevel().toLowerCase();
        
        if (userLevel.equals(sessionLevel)) {
            return 1.0;
        }
        
        // Allow some flexibility - beginners can attend intermediate, etc.
        if (userLevel.equals("beginner") && sessionLevel.equals("intermediate")) {
            return 0.7;
        }
        if (userLevel.equals("intermediate") && 
            (sessionLevel.equals("beginner") || sessionLevel.equals("advanced"))) {
            return 0.8;
        }
        if (userLevel.equals("advanced") && sessionLevel.equals("intermediate")) {
            return 0.7;
        }
        
        return 0.3; // Some mismatch penalty
    }
    
    /**
     * Calculate track preference matching.
     */
    private double calculateTrackMatch(UserProfile user, Session session) {
        if (user.getPreferredTracks() == null || session.getTrack() == null) {
            return 0.5; // Neutral if no preference specified
        }
        
        return user.getPreferredTracks().contains(session.getTrack()) ? 1.0 : 0.0;
    }
    
    /**
     * Calculate collaborative filtering score based on similar users.
     */
    private double calculateCollaborativeScore(UserProfile user, Session session) {
        List<UserProfile> allUsers = userService.getAllUsers();
        List<UserProfile> similarUsers = findSimilarUsers(user, allUsers);
        
        if (similarUsers.isEmpty()) {
            return 0.0;
        }
        
        int positiveRatings = 0;
        int totalRatings = 0;
        
        for (UserProfile similarUser : similarUsers) {
            if (similarUser.getSessionRatings().containsKey(session.getId())) {
                int rating = similarUser.getSessionRatings().get(session.getId());
                if (rating >= 4) { // Consider 4-5 as positive
                    positiveRatings++;
                }
                totalRatings++;
            }
        }
        
        return totalRatings > 0 ? (double) positiveRatings / totalRatings : 0.0;
    }
    
    /**
     * Find users similar to the given user based on interests and role.
     */
    private List<UserProfile> findSimilarUsers(UserProfile user, List<UserProfile> allUsers) {
        return allUsers.stream()
                .filter(u -> !u.getUserId().equals(user.getUserId()))
                .filter(u -> u.getRole() != null && u.getRole().equals(user.getRole()))
                .filter(u -> hasCommonInterests(user, u))
                .limit(10) // Limit to top 10 similar users
                .collect(Collectors.toList());
    }
    
    /**
     * Check if two users have common interests.
     */
    private boolean hasCommonInterests(UserProfile user1, UserProfile user2) {
        if (user1.getInterests() == null || user2.getInterests() == null) {
            return false;
        }
        
        Set<String> interests1 = new HashSet<>(user1.getInterests());
        Set<String> interests2 = new HashSet<>(user2.getInterests());
        interests1.retainAll(interests2);
        
        return !interests1.isEmpty();
    }
    
    /**
     * Generate human-readable explanation for the recommendation.
     */
    private String generateExplanation(UserProfile user, Session session, double score) {
        StringBuilder explanation = new StringBuilder();
        
        if (session.getTargetRoles() != null && session.getTargetRoles().contains(user.getRole())) {
            explanation.append("This session is specifically designed for ").append(user.getRole()).append("s. ");
        }
        
        if (user.getInterests() != null && session.getTags() != null) {
            Set<String> commonInterests = new HashSet<>(user.getInterests());
            commonInterests.retainAll(session.getTags());
            if (!commonInterests.isEmpty()) {
                explanation.append("It matches your interests in ")
                          .append(String.join(", ", commonInterests)).append(". ");
            }
        }
        
        if (user.getExperienceLevel() != null && session.getLevel() != null && 
            user.getExperienceLevel().equalsIgnoreCase(session.getLevel())) {
            explanation.append("The session level (").append(session.getLevel())
                      .append(") matches your experience level. ");
        }
        
        if (session.getRating() > 4.0) {
            explanation.append("This session has received excellent ratings from other attendees. ");
        }
        
        if (explanation.length() == 0) {
            explanation.append("This session might be interesting based on your profile and preferences.");
        }
        
        return explanation.toString().trim();
    }
    
    /**
     * Get specific factors that led to this recommendation.
     */
    private List<String> getMatchingFactors(UserProfile user, Session session) {
        List<String> factors = new ArrayList<>();
        
        if (session.getTargetRoles() != null && session.getTargetRoles().contains(user.getRole())) {
            factors.add("Role match: " + user.getRole());
        }
        
        if (user.getInterests() != null && session.getTags() != null) {
            Set<String> commonInterests = new HashSet<>(user.getInterests());
            commonInterests.retainAll(session.getTags());
            if (!commonInterests.isEmpty()) {
                factors.add("Interest match: " + String.join(", ", commonInterests));
            }
        }
        
        if (user.getExperienceLevel() != null && session.getLevel() != null && 
            user.getExperienceLevel().equalsIgnoreCase(session.getLevel())) {
            factors.add("Experience level: " + session.getLevel());
        }
        
        if (user.getPreferredTracks() != null && user.getPreferredTracks().contains(session.getTrack())) {
            factors.add("Preferred track: " + session.getTrack());
        }
        
        return factors;
    }
    
    /**
     * Determine the primary type of recommendation.
     */
    private String determineRecommendationType(UserProfile user, Session session) {
        if (session.getTargetRoles() != null && session.getTargetRoles().contains(user.getRole())) {
            return "role-based";
        }
        
        if (user.getInterests() != null && session.getTags() != null) {
            Set<String> commonInterests = new HashSet<>(user.getInterests());
            commonInterests.retainAll(session.getTags());
            if (!commonInterests.isEmpty()) {
                return "interest-based";
            }
        }
        
        return "general";
    }
}