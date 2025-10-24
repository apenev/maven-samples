package com.example.servlet;

import com.example.model.UserProfile;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Servlet for handling user profile operations.
 */
public class UserProfileServlet extends HttpServlet {
    
    private UserService userService;
    private ObjectMapper objectMapper;
    
    @Override
    public void init() throws ServletException {
        super.init();
        userService = new UserService();
        objectMapper = new ObjectMapper();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String userId = request.getParameter("userId");
        
        if (userId == null || userId.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"userId parameter is required\"}");
            return;
        }
        
        try {
            UserProfile userProfile = userService.getUserProfile(userId);
            if (userProfile == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"User profile not found\"}");
                return;
            }
            
            String jsonResponse = objectMapper.writeValueAsString(userProfile);
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Failed to retrieve user profile: " + e.getMessage() + "\"}");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        
        if ("create".equals(action)) {
            handleCreateProfile(request, response);
        } else if ("updateInterests".equals(action)) {
            handleUpdateInterests(request, response);
        } else if ("rateSession".equals(action)) {
            handleRateSession(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid action\"}");
        }
    }
    
    private void handleCreateProfile(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        String userId = request.getParameter("userId");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String role = request.getParameter("role");
        String experienceLevel = request.getParameter("experienceLevel");
        String interestsParam = request.getParameter("interests");
        String tracksParam = request.getParameter("preferredTracks");
        
        if (userId == null || name == null || email == null || role == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"userId, name, email, and role are required\"}");
            return;
        }
        
        try {
            List<String> interests = interestsParam != null ? 
                Arrays.asList(interestsParam.split(",")) : null;
            List<String> tracks = tracksParam != null ? 
                Arrays.asList(tracksParam.split(",")) : null;
            
            UserProfile userProfile = new UserProfile(userId, name, email, role, experienceLevel, interests);
            userProfile.setPreferredTracks(tracks);
            
            userService.saveUserProfile(userProfile);
            
            String jsonResponse = objectMapper.writeValueAsString(userProfile);
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Failed to create user profile: " + e.getMessage() + "\"}");
        }
    }
    
    private void handleUpdateInterests(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        String userId = request.getParameter("userId");
        String interestsParam = request.getParameter("interests");
        
        if (userId == null || interestsParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"userId and interests parameters are required\"}");
            return;
        }
        
        try {
            List<String> interests = Arrays.asList(interestsParam.split(","));
            userService.updateUserInterests(userId, interests);
            response.getWriter().write("{\"success\": true, \"message\": \"Interests updated\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Failed to update interests: " + e.getMessage() + "\"}");
        }
    }
    
    private void handleRateSession(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        String userId = request.getParameter("userId");
        String sessionId = request.getParameter("sessionId");
        String ratingParam = request.getParameter("rating");
        
        if (userId == null || sessionId == null || ratingParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"userId, sessionId, and rating parameters are required\"}");
            return;
        }
        
        try {
            int rating = Integer.parseInt(ratingParam);
            if (rating < 1 || rating > 5) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Rating must be between 1 and 5\"}");
                return;
            }
            
            userService.rateSession(userId, sessionId, rating);
            response.getWriter().write("{\"success\": true, \"message\": \"Session rated\"}");
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Invalid rating format\"}");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Failed to rate session: " + e.getMessage() + "\"}");
        }
    }
}