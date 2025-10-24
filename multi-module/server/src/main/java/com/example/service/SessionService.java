package com.example.service;

import com.example.model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Service for managing conference sessions.
 */
public class SessionService {
    
    private static final Logger logger = LoggerFactory.getLogger(SessionService.class);
    
    // In-memory storage for demo purposes
    private final Map<String, Session> sessions = new HashMap<>();
    
    public SessionService() {
        initializeSampleSessions();
    }
    
    /**
     * Get all available sessions.
     */
    public List<Session> getAllSessions() {
        return new ArrayList<>(sessions.values());
    }
    
    /**
     * Get a session by ID.
     */
    public Session getSessionById(String sessionId) {
        return sessions.get(sessionId);
    }
    
    /**
     * Add a new session.
     */
    public void addSession(Session session) {
        sessions.put(session.getId(), session);
        logger.info("Added session: {}", session.getTitle());
    }
    
    /**
     * Update an existing session.
     */
    public void updateSession(Session session) {
        if (sessions.containsKey(session.getId())) {
            sessions.put(session.getId(), session);
            logger.info("Updated session: {}", session.getTitle());
        }
    }
    
    /**
     * Delete a session.
     */
    public void deleteSession(String sessionId) {
        Session removed = sessions.remove(sessionId);
        if (removed != null) {
            logger.info("Deleted session: {}", removed.getTitle());
        }
    }
    
    /**
     * Search sessions by various criteria.
     */
    public List<Session> searchSessions(String query, String track, String level, List<String> tags) {
        return sessions.values().stream()
                .filter(session -> matchesQuery(session, query))
                .filter(session -> track == null || track.equals(session.getTrack()))
                .filter(session -> level == null || level.equals(session.getLevel()))
                .filter(session -> tags == null || hasMatchingTags(session, tags))
                .collect(ArrayList::new, (list, session) -> list.add(session), ArrayList::addAll);
    }
    
    private boolean matchesQuery(Session session, String query) {
        if (query == null || query.trim().isEmpty()) {
            return true;
        }
        
        String lowerQuery = query.toLowerCase();
        return session.getTitle().toLowerCase().contains(lowerQuery) ||
               session.getDescription().toLowerCase().contains(lowerQuery) ||
               session.getSpeaker().toLowerCase().contains(lowerQuery);
    }
    
    private boolean hasMatchingTags(Session session, List<String> tags) {
        if (session.getTags() == null) {
            return false;
        }
        
        return session.getTags().stream().anyMatch(tags::contains);
    }
    
    /**
     * Initialize sample sessions for demonstration.
     */
    private void initializeSampleSessions() {
        // AI/ML Track
        addSession(new Session("ai-001", 
                "Introduction to Machine Learning for Developers",
                "Learn the fundamentals of machine learning and how to integrate ML models into your applications.",
                "Dr. Sarah Chen",
                "AI/ML",
                "Beginner",
                Arrays.asList("machine-learning", "python", "tensorflow", "ai"),
                Arrays.asList("Developer", "Data Scientist")));
        
        addSession(new Session("ai-002",
                "Advanced Deep Learning Techniques",
                "Explore cutting-edge deep learning architectures and their practical applications.",
                "Prof. Michael Rodriguez",
                "AI/ML",
                "Advanced",
                Arrays.asList("deep-learning", "neural-networks", "pytorch", "computer-vision"),
                Arrays.asList("Data Scientist", "ML Engineer")));
        
        // Web Development Track
        addSession(new Session("web-001",
                "Modern Frontend Development with React",
                "Build responsive and interactive web applications using React and modern JavaScript.",
                "Jessica Park",
                "Web Development",
                "Intermediate",
                Arrays.asList("react", "javascript", "frontend", "web"),
                Arrays.asList("Frontend Developer", "Full Stack Developer")));
        
        addSession(new Session("web-002",
                "Microservices Architecture Patterns",
                "Design and implement scalable microservices using modern architectural patterns.",
                "David Kumar",
                "Web Development",
                "Advanced",
                Arrays.asList("microservices", "architecture", "docker", "kubernetes"),
                Arrays.asList("Backend Developer", "Architect", "DevOps Engineer")));
        
        // Cloud Track
        addSession(new Session("cloud-001",
                "Getting Started with AWS",
                "Introduction to Amazon Web Services and cloud computing fundamentals.",
                "Maria Gonzalez",
                "Cloud",
                "Beginner",
                Arrays.asList("aws", "cloud", "infrastructure", "devops"),
                Arrays.asList("Developer", "DevOps Engineer", "System Administrator")));
        
        addSession(new Session("cloud-002",
                "Serverless Computing with Lambda",
                "Build and deploy serverless applications using AWS Lambda and related services.",
                "Robert Kim",
                "Cloud",
                "Intermediate",
                Arrays.asList("serverless", "lambda", "aws", "functions"),
                Arrays.asList("Backend Developer", "Cloud Engineer")));
        
        // Mobile Development Track
        addSession(new Session("mobile-001",
                "Cross-Platform Mobile Development with Flutter",
                "Create beautiful mobile apps for iOS and Android using Flutter framework.",
                "Anna Petrov",
                "Mobile",
                "Intermediate",
                Arrays.asList("flutter", "dart", "mobile", "cross-platform"),
                Arrays.asList("Mobile Developer", "Frontend Developer")));
        
        // Security Track
        addSession(new Session("security-001",
                "Application Security Best Practices",
                "Learn essential security practices to protect your applications from common vulnerabilities.",
                "James Wilson",
                "Security",
                "Intermediate",
                Arrays.asList("security", "owasp", "authentication", "encryption"),
                Arrays.asList("Developer", "Security Engineer", "DevOps Engineer")));
        
        // Management Track
        addSession(new Session("mgmt-001",
                "Agile Project Management for Tech Teams",
                "Effective agile methodologies and tools for managing software development projects.",
                "Lisa Thompson",
                "Management",
                "Beginner",
                Arrays.asList("agile", "scrum", "project-management", "leadership"),
                Arrays.asList("Project Manager", "Team Lead", "Scrum Master")));
        
        addSession(new Session("mgmt-002",
                "Building High-Performance Engineering Teams",
                "Strategies for recruiting, developing, and retaining top engineering talent.",
                "Mark Anderson",
                "Management",
                "Advanced",
                Arrays.asList("leadership", "team-building", "hiring", "culture"),
                Arrays.asList("Engineering Manager", "CTO", "Team Lead")));
        
        // Set additional properties for some sessions
        Session aiSession = sessions.get("ai-001");
        aiSession.setDuration(60);
        aiSession.setRoom("Main Auditorium");
        aiSession.setTimeSlot("9:00 AM - 10:00 AM");
        aiSession.setRating(4.5);
        aiSession.setAttendeeCount(150);
        
        Session webSession = sessions.get("web-001");
        webSession.setDuration(90);
        webSession.setRoom("Room A");
        webSession.setTimeSlot("10:30 AM - 12:00 PM");
        webSession.setRating(4.7);
        webSession.setAttendeeCount(80);
        
        logger.info("Initialized {} sample sessions", sessions.size());
    }
}