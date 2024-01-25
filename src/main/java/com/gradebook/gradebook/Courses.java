package com.gradebook.gradebook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.stream.Collectors;

public class Courses {
    private String courseTitle;
    private String semester;
    private int year;
    private double courseGrade;
    private ObservableList<Assignment> assignmentsList = FXCollections.observableArrayList();



    public Courses(String title, String Semester, int Year) {
        this.courseTitle = title;
        this.semester = Semester;
        this.year = Year;
        this.courseGrade = 0;
    }

    public void calculateCourseGrade() {
         calculateCourseGrade(this.assignmentsList);
    }
    public double calculateCourseGrade(ObservableList<Assignment> assignmentsList) {
        double totalWeightedGrade = 0.0;
        double totalWeight = 0.0;

        for (Assignment assignment : assignmentsList) {
            double weight = assignment.getWeight();
            double unweightedGrade = assignment.getReceivedPoints() / assignment.getPossiblePoints();
            double weightedGrade = unweightedGrade * weight;

            totalWeightedGrade += weightedGrade;
            totalWeight += weight;
        }

        // Check to prevent division by zero
        if (totalWeight > 0) {
            this.courseGrade =  (totalWeightedGrade / totalWeight) * 100;
        } else {
            this.courseGrade = 0;
        }

        return this.courseGrade;
    }
    public double calculateCourseGradeByType(String type) {
        return calculateCourseGrade(getAssignmentsOfType(type));
    }

    public void addAssignment(Assignment assignment) {
        assignmentsList.add(assignment);
        calculateCourseGrade(); // Recalculate the course grade
    }


    public ObservableList<Assignment> getAssignmentsOfType(String type) {
        return assignmentsList.stream()
                .filter(a -> a.getAssignmentType().equals(type))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    // Remove an assignment from the course
    public void removeAssignment(Assignment assignment) {
        assignmentsList.remove(assignment);
        calculateCourseGrade(); // Recalculate the course grade
    }

    //////////////////////////// Setting Methods
    public void setCourseTitle(String courseTitle) {this.courseTitle = courseTitle;}
    public void setSemester(String semester) {this.semester = semester;}
    public void setYear(int year) {this.year = year;}
    public void setCourseGrade(double courseGrade) {this.courseGrade = courseGrade;}
    public void setAssignmentsList(ObservableList<Assignment> assignments) {
        this.assignmentsList = assignments;
        calculateCourseGrade(); // Recalculate the course grade
    }
    //////////////////////////// End setting Methods

    //////////////////////////// Getting Methods
    public String getCourseTitle() {return courseTitle;}
    public String getSemester() {return semester;}
    public int getYear() {return year;}
    public double getCourseGrade() {return courseGrade;}
    public ObservableList<Assignment> getAssignmentsList() {return assignmentsList;}

    //////////////////////////// End getting Methods

    public String serialize() {
        // TODO Look into putting functionality into a separate class designed for saving.
        JSONObject json = new JSONObject();
        json.put("courseTitle", courseTitle);
        json.put("semester", semester);
        json.put("year", year);

        JSONArray assignmentsArray = new JSONArray();
        for (Assignment assignment : assignmentsList) {
            assignmentsArray.put(new JSONObject(assignment.serialize()));
        }
        json.put("assignments", assignmentsArray);

        return json.toString();
    }

}
