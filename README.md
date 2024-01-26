# GradeBook Application
### *NOTE this is a preliminary README and subject to change*
## Overview

GradeBook is a JavaFX application designed to manage and track academic courses and their associated assignments. It provides a user-friendly interface for adding, editing, and viewing courses and assignments, making it a handy tool for students, teachers, or anyone needing to organize academic information efficiently.

## Features

- **Manage Courses**: Add, edit, and delete courses with details such as course title, semester, and year.
- **Handle Assignments**: For each course, manage various assignments including quizzes, homework, exams, and projects.
- **Grade Calculation**: Calculate and display grades for each assignment and the overall course grade.
- **Data Persistence**: Save course and assignment data to a JSON file and load it back, ensuring data is retained between sessions.
- **User Interface**: A clean and intuitive interface using JavaFX, providing a smooth user experience.

## How It Works

### Course Management

The application allows users to create and maintain a list of courses. Each course has a title, a semester, and a year. Users can add new courses to the list, edit existing courses, or remove them.

### Assignment Handling

For each course, users can add assignments of various types (e.g., quizzes, homework, exams). Each assignment includes details such as title, type, number, points possible, points received, and weight. The application supports adding, editing, and deleting assignments for each course.

### Grade Calculation

The application calculates grades for each assignment based on the points received, points possible, and weight. It also computes an overall course grade, taking into account the weight of each assignment.

### Data Storage

Courses and assignments data are stored in a JSON file. This file is read at application startup to load previously saved data, and it is written to when data is updated, ensuring persistence across sessions.

### User Interface

GradeBook features a simple yet effective JavaFX-based GUI, making it easy to interact with the application. The interface includes tables for displaying courses and assignments, forms for adding/editing data, and buttons for various actions.

## Running the Application

To run GradeBook, you need to have Java and JavaFX set up on your machine. After cloning the repository, you can run the application using your favorite IDE or from the command line.

## Contributing

Contributions to GradeBook are welcome! Feel free to fork the repository, make changes, and submit pull requests. If you find any issues or have suggestions, please open an issue on GitHub.

---
