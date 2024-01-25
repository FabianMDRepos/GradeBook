package com.gradebook.gradebook;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ListView;

import java.util.*;

public class CourseViewCoursePopulate extends CourseViewListManager {

    private static final Random random = new Random();
    private static final List<String> assignmentTypes = Arrays.asList("Quiz", "Homework", "Exam", "Essay", "Project");
    private static final double MAX_POINTS = 100.0;

    public static ListView<Courses> generateCourseListView() {
        ListView<Courses> courseListView = new ListView<>();
        List<Courses> generatedCourses = generateCourses();
        courseListView.getItems().addAll(generatedCourses);
        return courseListView;
    }
    private static Assignment generateRandomAssignment(int number) {
        String type = assignmentTypes.get(random.nextInt(assignmentTypes.size()));
        double pointsPossible = 50.0 + (random.nextDouble() * (MAX_POINTS - 50.0)); // Points between 50 and 100
        double pointsReceived = random.nextDouble() * pointsPossible; // Points received less than or equal to possible
        double weight = 0.1 + (random.nextDouble() * (1.0 - 0.1)); // Weight between 0.1 and 1.0

        return new Assignment(
                "Assignment " + number,
                type,
                number,
                pointsPossible,
                pointsReceived,
                weight
        );
    }

    public static List<Courses> generateCourses() {
        List<Courses> coursesList = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Courses course = new Courses("Course " + i, "Fall", 2021 + i);
            for (int j = 1; j <= 5; j++) {
                course.addAssignment(generateRandomAssignment(j));
            }
            coursesList.add(course);
        }
        return coursesList;
    }

//    public static void main(String[] args) {
//        List<Courses> generatedCourses = generateCourses();
//        // Output the generated courses and assignments for demonstration
//        for (Courses course : generatedCourses) {
//            System.out.println("Course: " + course.getCourseTitle() + ", " + course.getSemester() + " " + course.getYear());
//            for (Assignment assignment : course.getAssignmentsList()) {
//                System.out.println("  Assignment: " + assignment.getAssignmentType() + " #" + assignment.getAssignmentNumber() + " - " + assignment.getReceivedPoints() + "/" + assignment.getPossiblePoints() + " (Weight: " + assignment.getWeight() + ")");
//            }
//        }
//    }
}
