# 🤖 AI-Powered Session Recommender

An intelligent recommendation system that suggests relevant conference sessions based on user interests, role, and preferences using AI algorithms.

## 🌟 Features

### ✅ Completed Requirements

- **AI-powered recommendation engine** using multiple algorithms:
  - Role-based matching
  - Interest-based filtering using Jaccard similarity
  - Experience level compatibility
  - Track preference matching
  - Collaborative filtering
- **Personalized session suggestions** based on user profile
- **Detailed explanations** for each recommendation
- **Session saving** to user agenda
- **User feedback system** with ratings
- **Mobile-friendly web interface**
- **RESTful API** for integration

### 🎯 Acceptance Criteria Status

- ✅ Users can input their role and interests
- ✅ System provides relevant session recommendations
- ✅ Recommendations include explanations
- ✅ Users can save recommendations to their agenda
- ✅ System learns from user feedback
- ✅ Mobile-friendly recommendation interface

## 🏗️ Architecture

### Backend (Server Module)
- **Java 8** with Maven
- **Jackson** for JSON processing
- **Apache Commons Math** for ML algorithms
- **SLF4J** for logging

### Frontend (Webapp Module)
- **Responsive HTML5/CSS3/JavaScript**
- **Mobile-first design**
- **Real-time API integration**
- **Progressive enhancement**

### API Endpoints
- `GET /api/recommendations?userId={id}&maxRecommendations={num}` - Get recommendations
- `POST /api/recommendations?action=saveSession` - Save session to agenda
- `POST /api/recommendations?action=feedback` - Submit feedback
- `GET /api/user?userId={id}` - Get user profile
- `POST /api/user?action=create` - Create user profile
- `POST /api/user?action=updateInterests` - Update interests
- `POST /api/user?action=rateSession` - Rate a session

## 🧠 AI Recommendation Algorithm

The system uses a weighted scoring algorithm that combines multiple factors:

### Scoring Weights
- **Role Match**: 30% - Matches user role with session target roles
- **Interest Match**: 25% - Uses Jaccard similarity for interest overlap
- **Experience Level**: 20% - Considers skill level compatibility
- **Track Preference**: 15% - Matches preferred conference tracks
- **Collaborative Filtering**: 10% - Learns from similar users

### Algorithm Features
- **Minimum threshold filtering** (score > 0.1)
- **Excludes already saved/attended sessions**
- **Sorts by confidence score**
- **Limits results** to requested number
- **Provides explanations** for each recommendation

## 🚀 Getting Started

### Prerequisites
- Java 8 or higher
- Maven 3.0+
- Modern web browser

### Build and Run

```bash
# Clone the repository
cd multi-module

# Build the project
mvn clean compile

# Run tests
mvn test

# Package the application
mvn package

# Deploy to servlet container (e.g., Tomcat)
# Copy webapp/target/webapp.war to your servlet container
```

### Development Server

```bash
# Start Jetty development server
cd webapp
mvn jetty:run

# Access the application at http://localhost:8080
```

## 📱 User Interface

### Profile Setup
1. Enter user ID, name, and email
2. Select role from predefined options
3. Choose experience level
4. Add interests (comma-separated)
5. Submit to create profile and get recommendations

### Recommendations View
- **Session cards** with title, speaker, track, and level
- **Confidence score** displayed as percentage
- **Explanation text** describing why recommended
- **Matching factors** as tags
- **Save to agenda** button
- **Rate session** functionality

### Mobile Optimization
- Responsive grid layout
- Touch-friendly buttons
- Optimized typography
- Fast loading times

## 🔧 Configuration

### Sample Data
The system includes pre-configured sample data:

**Sessions**: 10 diverse sessions across tracks:
- AI/ML (Machine Learning, Deep Learning)
- Web Development (React, Microservices)
- Cloud (AWS, Serverless)
- Mobile (Flutter)
- Security (Best Practices)
- Management (Agile, Team Building)

**Users**: 5 sample user profiles with different roles and interests

### Customization
- Add new sessions in `SessionService.initializeSampleSessions()`
- Modify scoring weights in `RecommendationEngine`
- Customize UI styling in `index.jsp`
- Add new user roles in the frontend form

## 🧪 Testing

### Unit Tests
- `RecommendationEngineTest` - Tests core recommendation logic
- `TestGreeter` - Basic functionality test

### Test Coverage
- Valid user recommendations
- Non-existent user handling
- Saved session exclusion
- Role-based matching
- Result limiting

### Running Tests
```bash
mvn test
```

## 📊 API Examples

### Get Recommendations
```bash
curl "http://localhost:8080/api/recommendations?userId=dev-001&maxRecommendations=5"
```

### Create User Profile
```bash
curl -X POST "http://localhost:8080/api/user?action=create" \
  -d "userId=new-user&name=John Doe&email=john@example.com&role=Developer&experienceLevel=Intermediate&interests=javascript,react"
```

### Save Session
```bash
curl -X POST "http://localhost:8080/api/recommendations?action=saveSession" \
  -d "userId=dev-001&sessionId=web-001"
```

## 🔮 Future Enhancements

### Planned Features
- **Machine Learning Integration**: TensorFlow/PyTorch models
- **Real-time Learning**: Dynamic weight adjustment
- **Social Features**: User connections and social recommendations
- **Advanced Analytics**: Recommendation performance metrics
- **Content-based Filtering**: NLP analysis of session descriptions
- **Hybrid Recommendations**: Combining multiple algorithms

### Technical Improvements
- **Database Integration**: Replace in-memory storage
- **Caching Layer**: Redis for performance
- **Authentication**: User login and security
- **Microservices**: Split into smaller services
- **Container Deployment**: Docker and Kubernetes
- **API Documentation**: OpenAPI/Swagger

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Add tests for new functionality
4. Ensure all tests pass
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🙏 Acknowledgments

- Apache Commons Math for mathematical utilities
- Jackson for JSON processing
- Maven for build management
- SLF4J for logging framework

---

**Built with ❤️ for intelligent conference experiences**