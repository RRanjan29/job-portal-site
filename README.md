# Job Portal System

A full-stack job portal application built with Spring Boot (backend) and HTML/CSS/JavaScript (frontend).

## Features

- User registration and login with JWT authentication
- Role-based access: Candidate and Recruiter
- Recruiters can post, update, delete jobs
- Candidates can view jobs and apply with resume upload
- Application status management

## Backend Setup

1. Ensure Java 17 and Maven are installed.
2. Set up MySQL database and update `application.properties` with your credentials.
3. Run `mvn spring-boot:run` to start the backend server on port 8080.

## Frontend Setup

1. Open `frontend/index.html` in a browser to access the application.
2. For a better experience, serve the frontend files using a local server.

## API Endpoints

- POST /api/auth/register - Register a new user
- POST /api/auth/login - Login
- GET /api/jobs - Get paginated jobs
- POST /api/jobs - Post a job (Recruiter)
- PUT /api/jobs/{id} - Update job (Recruiter)
- DELETE /api/jobs/{id} - Delete job (Recruiter)
- GET /api/jobs/my - Get recruiter's jobs
- POST /api/applications/apply - Apply for job (Candidate)
- GET /api/applications/my - Get candidate's applications
- GET /api/applications/job/{jobId} - Get applications for job (Recruiter)
- PUT /api/applications/{id}/status - Update application status (Recruiter)

## Database Schema

- users: id, name, email, password, role
- jobs: id, title, description, company, location, salary, recruiter_id
- applications: id, user_id, job_id, status, resume_path