# 🎓 Testing System

A modern, full-stack educational web application designed for tutors and students. 
Tutors can seamlessly create, manage, and evaluate tests, while students can browse available tests, complete them, and view their scores instantly.

## 🌟 Key Features

* **Role-Based Access Control (RBAC):**
  * **🧑‍🎓 Student:** Browse available tests, take tests with real-time progress tracking, and submit answers.
  * **👨‍🏫 Tutor:** Create, edit, and delete tests. Add single-choice or multiple-choice questions with designated correct answers. View test submissions and grades.
* **Smart Test Editor:** An embedded, inline test editor that allows creating the test configuration and adding questions simultaneously without leaving the page.
* **Modern & Minimal UI:** A custom-built, responsive CSS design system featuring a clean aesthetic, subtle animations, and intuitive feedback (toast notifications, progress bars).
* **Instant Scoring:** Automatically calculates scores upon test submission, displaying correct answers and progress rings.

## 🛠️ Technology Stack

### Backend (Java)
* **Java 21/26**
* **Spring Boot 3.5.0** (Spring Web, Spring Data JPA)
* **H2 Database:** In-memory database (data resets on application restart, perfect for testing and development)
* **Maven:** Dependency and build management
* **CORS Configuration:** Fully configured to securely allow cross-origin requests from the frontend.

### Frontend (JavaScript)
* **Vue 3:** Composition API for reactive components
* **Vite:** Next-generation frontend tooling for lightning-fast HMR
* **Pinia:** State management for authentication, tests, and results
* **Axios:** HTTP client for REST API communication
* **Vanilla CSS:** Custom minimal design system (no bulky frameworks like Bootstrap or Tailwind)

---

## 🚀 Getting Started

To run this application locally on your machine, you need to start both the backend and frontend servers.

### 1. Start the Backend (Spring Boot)

1. Open a terminal and navigate to the backend directory:
   ```bash
   cd testing-system
   ```
2. Run the application using the Maven Wrapper:
   ```bash
   # Windows
   mvnw.cmd spring-boot:run
   
   # Mac/Linux
   ./mvnw spring-boot:run
   ```
3. The server will start on `http://localhost:8080`.
   * *Note: The database is pre-seeded with sample tests and two default accounts.*

### 2. Start the Frontend (Vue.js)

1. Open a second terminal and navigate to the frontend directory:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   npm run dev
   ```
4. The application will be available at `http://localhost:5173`.

---

## 🔑 Default Accounts

For quick testing, use the following pre-seeded accounts:

| Role | Username | Password |
| :--- | :--- | :--- |
| **Tutor** | `tutor` | `tutor123` |
| **Student** | `student` | `student123` |

You can also create a new account by clicking **Sign In / Register** on the home page.

## 📂 Project Structure

```text
Individual-work/
├── frontend/                 # Vue 3 Frontend application
│   ├── src/
│   │   ├── api/              # Axios API service calls
│   │   ├── assets/           # Custom CSS design system (main.css)
│   │   ├── components/       # Reusable Vue components (Forms, Alerts, etc.)
│   │   ├── router/           # Vue Router configuration & Auth Guards
│   │   ├── stores/           # Pinia State Management (Auth, Tests, Results)
│   │   └── views/            # Main application pages
│   └── package.json          
│
├── testing-system/           # Spring Boot Backend application
│   ├── src/main/java/.../
│   │   ├── config/           # CORS, Data Initialization
│   │   ├── controller/       # REST API Endpoints
│   │   ├── dto/              # Data Transfer Objects
│   │   ├── model/            # JPA Entities (User, Test, Question, etc.)
│   │   ├── repository/       # Spring Data Repositories
│   │   └── service/          # Business Logic
│   └── pom.xml               
│
└── .gitignore                # Global Git Ignore rules
```
