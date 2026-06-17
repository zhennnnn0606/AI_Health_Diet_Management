# AI Health Diet Management | AI健康飲食管理系統

## 📋 Project Overview | 專案概述

**AI Health Diet Management** is an intelligent system powered by Gemini 2.5 Flash Lite that analyzes meal descriptions and provides real-time nutritional insights, health scores, and personalized recommendations.

**AI健康飲食管理系統**是一個由 Gemini 2.5 Flash Lite 驅動的智慧系統，能分析餐食描述，提供即時營養洞察、健康評分和個性化建議。

### Core Features | 核心功能
- 🤖 AI-powered meal analysis using Gemini API
- 📊 Real-time nutrition analysis with health scores (1-10)
- 💡 Personalized dietary recommendations
- 🏥 Ingredient extraction and analysis
- 📱 Responsive web interface with history tracking
- ☁️ Cloud-ready deployment architecture

---

## 🏗️ Architecture & Data Flow | 專案架構與資料流向
### Request/Response Flow
1. **Frontend**: User inputs meal description → POST request to `/api/meals/analyze`
2. **Controller**: `MealController` receives request → validates input
3. **Service**: `MealService` delegates to `GeminiService` → saves to database
4. **AI Integration**: `GeminiService` constructs system prompt → calls Gemini API
5. **Database**: `MealRepository` persists `Meal` entity to SQLite
6. **Response**: JSON response returned to frontend → immediate UI update
7. **History**: Auto-load meal history from `/api/meals/history`

---

## 🛠️ Technology Stack | 技術棧

### Backend
- **Spring Boot**: 3.2.4
- **Java**: 17+
- **Spring Data JPA**: ORM framework
- **Hibernate Community Dialects**: SQLite support
- **SQLite JDBC**: Database driver

### Database
- **SQLite**: `data.db` (file-based, zero-configuration)
- **Dialect**: `org.hibernate.community.dialect.SQLiteDialect`

### Frontend
- **HTML5**: Semantic markup
- **CSS3**: Responsive design with gradients and animations
- **JavaScript (Vanilla)**: Fetch API, DOM manipulation

### AI & Integrations
- **Gemini API**: v1beta `generateContent`
- **Model**: `gemini-2.5-flash-lite`
- **GSON**: JSON serialization/deserialization

### Cloud & DevOps
- **Docker**: Multi-stage build optimization
- **Render**: PaaS deployment platform
- **Maven**: Build automation (pom.xml)

---

## 📊 JSON Schema | JSON 格式

### Request
```json
{
  "mealDescription": "早餐吃了一碗白粥配榨菜，喝了一杯黑咖啡"
}
```

### Response
```json
{
  "mealName": "簡易早餐組合",
  "nutritionAnalysis": "低熱量、高鈉、纖維不足...",
  "healthScore": 5,
  "recommendations": "建議增加蔬菜和蛋白質攝取...",
  "ingredients": ["白粥", "榨菜", "黑咖啡"]
}
```

---

## 🚀 Local Setup | 本地啟動

### Prerequisites | 系統需求
- Java 17 or higher
- Maven 3.8+
- Windows PowerShell or CMD

### Step 1: Navigate to Project
```powershell
cd $env:USERPROFILE\AI_Health_Diet_Management
```

### Step 2: Set Environment Variable (PowerShell)
```powershell
$env:GEMINI_API_KEY="your-actual-gemini-api-key-here"
```

### Step 3: Run with Maven
```bash
mvn clean install
mvn spring-boot:run
```

### Step 4: Access Application
- **URL**: http://localhost:8080
- **API Endpoint**: http://localhost:8080/api/meals

### Important Notes
- Replace `your-actual-gemini-api-key-here` with your actual Gemini API key
- The database file `data.db` will be created automatically in the project root
- Ensure the API key has permission for Gemini 2.5 Flash Lite model

---

## ☁️ Cloud Deployment on Render | 雲端部署指南

### Prerequisites
- Render account (https://render.com)
- GitHub repository with this code
- Gemini API key

### Deployment Steps

#### Option 1: Docker Deployment
1. **Create Web Service** on Render
2. **Connect GitHub repository**
3. **Set Runtime**: Docker
4. **Add Environment Variables**:
5. **Deploy**

#### Option 2: JAR Deployment
1. **Create Web Service** on Render
2. **Set Build Command**: `mvn clean install -DskipTests`
3. **Set Start Command**: `java -jar target/health-diet-management-1.0.0.jar`
4. **Add Environment Variables**:
5. **Deploy**

### Database Persistence on Render
- SQLite `data.db` is stored in the ephemeral filesystem
- For production, migrate to **PostgreSQL** or **MySQL**
- Modify `application.properties` datasource configuration

### Example Environment Variables
```properties
GEMINI_API_KEY=sk-abc123...
PORT=8080
SPRING_DATASOURCE_URL=jdbc:sqlite:data.db
```

---

## 📁 Project Structure | 專案結構
---

## 🔐 Security Best Practices | 安全性最佳實踐

1. **API Key Management**
   - ✅ Never hardcode API keys
   - ✅ Use environment variables: `${GEMINI_API_KEY:}`
   - ✅ Render: Set secrets in dashboard

2. **CORS Configuration**
   - ✅ Currently allows all origins (`*`)
   - ⚠️ For production, restrict to specific domains

3. **Input Validation**
   - ✅ Validate meal descriptions on backend
   - ✅ Sanitize HTML to prevent XSS

4. **Database Security**
   - ✅ SQLite suitable for development/testing
   - ⚠️ For production, migrate to PostgreSQL with encryption

---

## 🧪 API Endpoints | API 端點

### 1. Analyze Meal | 分析餐食
### 2. Get Meal History | 獲取歷史記錄
### 3. Delete Meal Record | 刪除記錄
4. **View Results**:
   - Health Score: 4/10
   - Recommendations: 增加蔬菜攝取，減少油脂
   - Ingredients: 炒飯, 炸雞

5. **Track History**: All meals saved automatically

---

## 📄 License | 授權

This project is provided as-is for educational and development purposes.

---

## 👨‍💻 Author | 作者

Created with ❤️ for health-conscious developers.

**Support**: For API issues, visit [Google Generative AI Documentation](https://ai.google.dev/docs)

---

## 🗺️ Roadmap | 發展路線圖

- [ ] Multi-language support (English, Traditional Chinese, Simplified Chinese)
- [ ] Mobile app (React Native)
- [ ] PostgreSQL migration
- [ ] Advanced analytics dashboard
- [ ] Meal plan generation
- [ ] Integration with fitness trackers
- [ ] User authentication & profiles

---

**Last Updated**: June 2024
**Status**: Active Development
