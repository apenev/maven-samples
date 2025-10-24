<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>AI-Powered Session Recommender</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
            line-height: 1.6;
            color: #333;
            background-color: #f5f5f5;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 2rem 0;
            text-align: center;
            margin-bottom: 2rem;
        }
        
        .header h1 {
            font-size: 2.5rem;
            margin-bottom: 0.5rem;
        }
        
        .header p {
            font-size: 1.2rem;
            opacity: 0.9;
        }
        
        .card {
            background: white;
            border-radius: 12px;
            padding: 1.5rem;
            margin-bottom: 1.5rem;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            transition: transform 0.2s ease;
        }
        
        .card:hover {
            transform: translateY(-2px);
        }
        
        .form-group {
            margin-bottom: 1rem;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 600;
            color: #555;
        }
        
        .form-group input,
        .form-group select,
        .form-group textarea {
            width: 100%;
            padding: 0.75rem;
            border: 2px solid #e1e5e9;
            border-radius: 8px;
            font-size: 1rem;
            transition: border-color 0.2s ease;
        }
        
        .form-group input:focus,
        .form-group select:focus,
        .form-group textarea:focus {
            outline: none;
            border-color: #667eea;
        }
        
        .btn {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            padding: 0.75rem 1.5rem;
            border-radius: 8px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: transform 0.2s ease;
        }
        
        .btn:hover {
            transform: translateY(-1px);
        }
        
        .btn:disabled {
            opacity: 0.6;
            cursor: not-allowed;
            transform: none;
        }
        
        .btn-secondary {
            background: #6c757d;
        }
        
        .btn-success {
            background: #28a745;
        }
        
        .recommendations {
            display: none;
        }
        
        .recommendation-item {
            border-left: 4px solid #667eea;
            padding: 1rem;
            margin-bottom: 1rem;
            background: #f8f9fa;
            border-radius: 0 8px 8px 0;
        }
        
        .recommendation-title {
            font-size: 1.25rem;
            font-weight: 600;
            color: #333;
            margin-bottom: 0.5rem;
        }
        
        .recommendation-meta {
            display: flex;
            flex-wrap: wrap;
            gap: 1rem;
            margin-bottom: 0.75rem;
            font-size: 0.9rem;
            color: #666;
        }
        
        .recommendation-explanation {
            margin-bottom: 1rem;
            color: #555;
        }
        
        .recommendation-factors {
            margin-bottom: 1rem;
        }
        
        .factor-tag {
            display: inline-block;
            background: #e9ecef;
            color: #495057;
            padding: 0.25rem 0.5rem;
            border-radius: 4px;
            font-size: 0.8rem;
            margin-right: 0.5rem;
            margin-bottom: 0.25rem;
        }
        
        .score-badge {
            background: #28a745;
            color: white;
            padding: 0.25rem 0.5rem;
            border-radius: 12px;
            font-size: 0.8rem;
            font-weight: 600;
        }
        
        .loading {
            text-align: center;
            padding: 2rem;
            color: #666;
        }
        
        .error {
            background: #f8d7da;
            color: #721c24;
            padding: 1rem;
            border-radius: 8px;
            margin-bottom: 1rem;
        }
        
        .success {
            background: #d4edda;
            color: #155724;
            padding: 1rem;
            border-radius: 8px;
            margin-bottom: 1rem;
        }
        
        .grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 1.5rem;
        }
        
        @media (max-width: 768px) {
            .container {
                padding: 10px;
            }
            
            .header h1 {
                font-size: 2rem;
            }
            
            .header p {
                font-size: 1rem;
            }
            
            .recommendation-meta {
                flex-direction: column;
                gap: 0.5rem;
            }
        }
    </style>
</head>
<body>
    <div class="header">
        <div class="container">
            <h1>🤖 AI Session Recommender</h1>
            <p>Get personalized conference session recommendations powered by AI</p>
        </div>
    </div>
    
    <div class="container">
        <!-- User Profile Setup -->
        <div class="card" id="profileSetup">
            <h2>👤 Set Up Your Profile</h2>
            <p>Tell us about yourself to get personalized recommendations</p>
            
            <form id="profileForm">
                <div class="grid">
                    <div>
                        <div class="form-group">
                            <label for="userId">User ID</label>
                            <input type="text" id="userId" name="userId" placeholder="Enter your user ID" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="name">Name</label>
                            <input type="text" id="name" name="name" placeholder="Your full name" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="email">Email</label>
                            <input type="email" id="email" name="email" placeholder="your.email@example.com" required>
                        </div>
                    </div>
                    
                    <div>
                        <div class="form-group">
                            <label for="role">Role</label>
                            <select id="role" name="role" required>
                                <option value="">Select your role</option>
                                <option value="Developer">Developer</option>
                                <option value="Data Scientist">Data Scientist</option>
                                <option value="Engineering Manager">Engineering Manager</option>
                                <option value="DevOps Engineer">DevOps Engineer</option>
                                <option value="Mobile Developer">Mobile Developer</option>
                                <option value="Frontend Developer">Frontend Developer</option>
                                <option value="Backend Developer">Backend Developer</option>
                                <option value="Full Stack Developer">Full Stack Developer</option>
                                <option value="Architect">Architect</option>
                                <option value="Team Lead">Team Lead</option>
                                <option value="Project Manager">Project Manager</option>
                                <option value="Security Engineer">Security Engineer</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label for="experienceLevel">Experience Level</label>
                            <select id="experienceLevel" name="experienceLevel" required>
                                <option value="">Select experience level</option>
                                <option value="Beginner">Beginner</option>
                                <option value="Intermediate">Intermediate</option>
                                <option value="Advanced">Advanced</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label for="interests">Interests (comma-separated)</label>
                            <textarea id="interests" name="interests" rows="3" 
                                placeholder="e.g., javascript, react, python, machine-learning, aws"></textarea>
                        </div>
                    </div>
                </div>
                
                <button type="submit" class="btn">Create Profile & Get Recommendations</button>
            </form>
        </div>
        
        <!-- Existing User -->
        <div class="card">
            <h2>🔄 Existing User?</h2>
            <p>Already have a profile? Get recommendations directly</p>
            
            <div class="form-group">
                <label for="existingUserId">User ID</label>
                <input type="text" id="existingUserId" placeholder="Enter your user ID">
            </div>
            
            <button type="button" class="btn btn-secondary" onclick="getRecommendations()">
                Get My Recommendations
            </button>
        </div>
        
        <!-- Loading State -->
        <div id="loading" class="loading" style="display: none;">
            <h3>🤖 AI is analyzing your preferences...</h3>
            <p>Generating personalized recommendations</p>
        </div>
        
        <!-- Error Messages -->
        <div id="errorMessage" class="error" style="display: none;"></div>
        
        <!-- Success Messages -->
        <div id="successMessage" class="success" style="display: none;"></div>
        
        <!-- Recommendations -->
        <div id="recommendations" class="recommendations">
            <div class="card">
                <h2>🎯 Your Personalized Recommendations</h2>
                <div id="recommendationsList"></div>
            </div>
        </div>
    </div>
    
    <script>
        // Global variables
        let currentUserId = null;
        
        // Form submission handler
        document.getElementById('profileForm').addEventListener('submit', async function(e) {
            e.preventDefault();
            
            const formData = new FormData(e.target);
            const profileData = {
                userId: formData.get('userId'),
                name: formData.get('name'),
                email: formData.get('email'),
                role: formData.get('role'),
                experienceLevel: formData.get('experienceLevel'),
                interests: formData.get('interests')
            };
            
            try {
                showLoading(true);
                hideMessages();
                
                // Create user profile
                const response = await fetch('/api/user?action=create', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: new URLSearchParams(profileData)
                });
                
                if (response.ok) {
                    currentUserId = profileData.userId;
                    showSuccess('Profile created successfully!');
                    
                    // Get recommendations
                    await getRecommendations(profileData.userId);
                } else {
                    const error = await response.json();
                    showError(error.error || 'Failed to create profile');
                }
            } catch (error) {
                showError('Network error: ' + error.message);
            } finally {
                showLoading(false);
            }
        });
        
        // Get recommendations function
        async function getRecommendations(userId = null) {
            const targetUserId = userId || document.getElementById('existingUserId').value || currentUserId;
            
            if (!targetUserId) {
                showError('Please enter a user ID');
                return;
            }
            
            try {
                showLoading(true);
                hideMessages();
                
                const response = await fetch(`/api/recommendations?userId=${encodeURIComponent(targetUserId)}&maxRecommendations=10`);
                
                if (response.ok) {
                    const recommendations = await response.json();
                    displayRecommendations(recommendations);
                    currentUserId = targetUserId;
                } else {
                    const error = await response.json();
                    showError(error.error || 'Failed to get recommendations');
                }
            } catch (error) {
                showError('Network error: ' + error.message);
            } finally {
                showLoading(false);
            }
        }
        
        // Display recommendations
        function displayRecommendations(recommendations) {
            const container = document.getElementById('recommendationsList');
            
            if (recommendations.length === 0) {
                container.innerHTML = '<p>No recommendations found. Try updating your profile or interests.</p>';
            } else {
                container.innerHTML = recommendations.map(rec => `
                    <div class="recommendation-item">
                        <div class="recommendation-title">${rec.session.title}</div>
                        <div class="recommendation-meta">
                            <span><strong>Speaker:</strong> ${rec.session.speaker}</span>
                            <span><strong>Track:</strong> ${rec.session.track}</span>
                            <span><strong>Level:</strong> ${rec.session.level}</span>
                            <span class="score-badge">Score: ${(rec.score * 100).toFixed(0)}%</span>
                        </div>
                        <div class="recommendation-explanation">${rec.explanation}</div>
                        <div class="recommendation-factors">
                            <strong>Why recommended:</strong><br>
                            ${rec.matchingFactors.map(factor => `<span class="factor-tag">${factor}</span>`).join('')}
                        </div>
                        <div style="margin-top: 1rem;">
                            <button class="btn btn-success" onclick="saveSession('${rec.session.id}')">
                                💾 Save to My Agenda
                            </button>
                            <button class="btn btn-secondary" onclick="rateSession('${rec.session.id}')">
                                ⭐ Rate Session
                            </button>
                        </div>
                    </div>
                `).join('');
            }
            
            document.getElementById('recommendations').style.display = 'block';
        }
        
        // Save session to agenda
        async function saveSession(sessionId) {
            if (!currentUserId) {
                showError('Please set up your profile first');
                return;
            }
            
            try {
                const response = await fetch('/api/recommendations?action=saveSession', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: new URLSearchParams({
                        userId: currentUserId,
                        sessionId: sessionId
                    })
                });
                
                if (response.ok) {
                    showSuccess('Session saved to your agenda!');
                } else {
                    const error = await response.json();
                    showError(error.error || 'Failed to save session');
                }
            } catch (error) {
                showError('Network error: ' + error.message);
            }
        }
        
        // Rate session
        async function rateSession(sessionId) {
            const rating = prompt('Rate this session (1-5 stars):');
            
            if (!rating || isNaN(rating) || rating < 1 || rating > 5) {
                showError('Please enter a valid rating between 1 and 5');
                return;
            }
            
            if (!currentUserId) {
                showError('Please set up your profile first');
                return;
            }
            
            try {
                const response = await fetch('/api/user?action=rateSession', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: new URLSearchParams({
                        userId: currentUserId,
                        sessionId: sessionId,
                        rating: rating
                    })
                });
                
                if (response.ok) {
                    showSuccess(`Session rated ${rating} stars! This helps improve future recommendations.`);
                } else {
                    const error = await response.json();
                    showError(error.error || 'Failed to rate session');
                }
            } catch (error) {
                showError('Network error: ' + error.message);
            }
        }
        
        // Utility functions
        function showLoading(show) {
            document.getElementById('loading').style.display = show ? 'block' : 'none';
        }
        
        function showError(message) {
            const errorDiv = document.getElementById('errorMessage');
            errorDiv.textContent = message;
            errorDiv.style.display = 'block';
        }
        
        function showSuccess(message) {
            const successDiv = document.getElementById('successMessage');
            successDiv.textContent = message;
            successDiv.style.display = 'block';
        }
        
        function hideMessages() {
            document.getElementById('errorMessage').style.display = 'none';
            document.getElementById('successMessage').style.display = 'none';
        }
        
        // Auto-hide messages after 5 seconds
        setInterval(() => {
            const errorDiv = document.getElementById('errorMessage');
            const successDiv = document.getElementById('successMessage');
            
            if (errorDiv.style.display === 'block') {
                setTimeout(() => errorDiv.style.display = 'none', 5000);
            }
            
            if (successDiv.style.display === 'block') {
                setTimeout(() => successDiv.style.display = 'none', 5000);
            }
        }, 100);
    </script>
</body>
</html>