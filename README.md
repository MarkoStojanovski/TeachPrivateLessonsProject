# TeachPrivateLessonsProject

## Overview
### The idea is to connect professors and students for private lessons. Professors can offer lessons to students, and students can also share their knowledge by giving lessons to their colleagues within various institutions. The project emphasizes flexibility and accessibility, creating a collaborative learning environment.

## Full Code is on master branch.
- This is back-end part. The front-end is on TeachPrivateLessonsFrontEnd repo.

## Models
### User – Manages user data and roles.
### Professors - Who offer private lessons
### Students - Who book private lessons, view available courses and offer their own lessons to colleagues.
### Subjects - That students need help for. 
### Sessions - Details on who will give the private lesson and who will receive it, along with the price, date etc.
### Institutions - Where the sessions will be held.
### Rooms - In what rooms will the sessions be held.

## Enumerations
### Role – Defines user roles (e.g., Professor, Student Tutor, Student, admin).
### Room Type - This helps with the capacity in the room (CLASSROOM, CONFERENCE_ROOM, LECTURE_HALL)
### Session Status - Status of session (PENDING, COMPLETED, CANCELLED)

## Repositories
### JpaRepository with custom queries for database operations.

## REST Controllers
### Provides RESTful APIs for front-end integration with a React application.

## Technologies Used
### Backend: Java Spring Boot
### Database: PostgreSQL
### Security: Spring Security for authentication and role-based authorization
