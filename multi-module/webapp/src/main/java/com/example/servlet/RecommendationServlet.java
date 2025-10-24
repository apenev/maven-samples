package com.example.servlet;

import com.example.model.Recommendation;
import com.example.model.UserProfile;
import com.example.service.RecommendationEngine;
import com.example.service.SessionService;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet for handling recommendation requests.
 */
public class RecommendationServlet extends HttpServlet {
    
    private RecommendationEngine recommendationEngine;
    private UserService userService;
    private ObjectMapper objectMapper;
    
    @Override
    public void init() throws ServletException {
        super.init();
        SessionService sessionService = new SessionService();
        userService = new UserService();
        recommendationEngine = new RecommendationEngine(sessionService, userService);
        objectMapper = new ObjectMapper();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String userId = request.getParameter("userId");
        String maxRecommendationsParam = request.getParameter("maxRecommendations");
        
        if (userId == null || userId.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"userId parameter is required\"}");
            return;
        }
        
        int maxRecommendations = 10; // default
        if (maxRecommendationsParam != null) {
            try {
                maxRecommendations = Integer.parseInt(maxRecommendationsParam);
            } catch (NumberFormatException e) {
                // Use default value
            }
        }
        
        try {
            List<Recommendation> recommendations = recommendationEngine.generateRecommendations(userId, maxRecommendations);
            String jsonResponse = objectMapper.writeValueAsString(recommendations);
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Failed to generate recommendations: " + e.getMessage() + "\"}");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        
        if ("feedback".equals(action)) {
            handleFeedback(request, response);
        } else if ("saveSession".equals(action)) {
            handleSaveSession(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid action\"}");
        }
    }
    
    private void handleFeedback(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        String userId = request.getParameter("userId");
        String feedback = request.getParameter("feedback");
        
        if (userId == null || feedback == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"userId and feedback parameters are required\"}");
            return;
        }
        
        try {
            userService.addRecommendationFeedback(userId, feedback);
            response.getWriter().write("{\"success\": true, \"message\": \"Feedback recorded\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Failed to record feedback: " + e.getMessage() + "\"}");
        }
    }
    
    private void handleSaveSession(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        String userId = request.getParameter("userId");
        String sessionId = request.getParameter("sessionId");
        
        if (userId == null || sessionId == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"userId and sessionId parameters are required\"}");
            return;
        }
        
        try {
            userService.saveSessionToAgenda(userId, sessionId);
            response.getWriter().write("{\"success\": true, \"message\": \"Session saved to agenda\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Failed to save session: " + e.getMessage() + "\"}");
        }
    }
}