# Note-Taking API

## Overview
A simple RESTful API to manage notes. It allows creating, retrieving, updating, and deleting notes.

## How to Run the Application
1. Clone the repository using: `git clone git@github.com:ramjay11/notes-app.git`
2. Navigate to the project folder.
3. Run `mvn spring-boot:run` to start the Spring Boot application.
4. The API will be running on `http://localhost:9090`.

## API Endpoints
- **POST /notes** - Create a new note.
- **GET /notes** - Get all notes.
- **GET /notes/{id}** - Get a note by ID.
- **PUT /notes/{id}** - Update a note by ID.
- **DELETE /notes/{id}** - Delete a note by ID.