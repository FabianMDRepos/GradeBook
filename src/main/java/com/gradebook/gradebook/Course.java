//package com.gradebook.gradebook;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class Course {
//
//    private String course_title;
//    private String semester;
//    private int year;
//
//    HashMap<String, Double> gradesHashMap = new HashMap<String, Double>();
//
//    private double course_grade;
//
//    // change to observable list
//    ObservableList<Assignment> assignmentObservableList = FXCollections.observableArrayList();
//    HashMap<String, HashMap<Integer, Assignment>> assignmentHashMap = new HashMap<String, HashMap<Integer,Assignment>>();
//
//    // probably remove these
//    HashMap<Integer, Assignment> Quizzes = new HashMap<Integer, Assignment>();
//    HashMap<Integer, Assignment> Homeworks = new HashMap<Integer, Assignment>();
//    HashMap<Integer, Assignment> Exams = new HashMap<Integer, Assignment>();
//    HashMap<Integer, Assignment> Essays = new HashMap<Integer, Assignment>();
//    HashMap<Integer, Assignment> Attendance = new HashMap<Integer, Assignment>();
//    HashMap<Integer, Assignment> Lab = new HashMap<Integer, Assignment>();
//
//    // /////////////////////Class Constructor
//    public Course(String title, String Semester, int Year) {
//        course_title = title;
//        semester = Semester;
//        year = Year;
//
//        gradesHashMap.put("Quiz",0.0);
//        gradesHashMap.put("Homeworks",0.0);
//        gradesHashMap.put("Exam",0.0);
//        gradesHashMap.put("Essay",0.0);
//        gradesHashMap.put("Attendance",0.0);
//        gradesHashMap.put("Lab",0.0);
//
//        course_grade = 0;
//
//        assignmentHashMap.put("Quiz",Quizzes);
//        assignmentHashMap.put("Homework",Homeworks);
//        assignmentHashMap.put("Exam",Exams);
//        assignmentHashMap.put("Essay",Essays);
//        assignmentHashMap.put("Attendance",Attendance);
//        assignmentHashMap.put("Lab",Lab);
//    }
//    ///////////////////////////////////////////////////////////
//
//    ///////////////// Grade Calculation
//    public void courseGrade() {
//        int numberOfAssignments = assignmentObservableList.size();
//        double possiblePoints = 0;
//        double receivedPoints = 0;
//        double sum = 0;
//        for (Assignment assignments : assignmentObservableList ) {
//            possiblePoints += (assignments.getPossiblePoints() * assignments.getWeight());
//            receivedPoints += (assignments.getReceivedPoints() * assignments.getWeight());
//            sum = (receivedPoints/possiblePoints) * 100;
//            //sum += assignments.getGrade();
//        }
//        course_grade = sum;
//    }
//
//    //Error handling if thing doesn't exist
//    ///////////////////////// Getting Functions
//    public double getQuizGrade() {return gradesHashMap.get("Quiz");}
//    public double getHwGrade() {return gradesHashMap.get("Homework");}
//    public double getExamGrade() {return gradesHashMap.get("Exam");}
//    public double getEssayGrade() {return gradesHashMap.get("Essay");}
//    public double getAttendanceGrade() {return gradesHashMap.get("Attendance");}
//    public double getLabGrade() {return gradesHashMap.get("Lab");}
//
//    public ObservableList<Assignment> getAssignmentObservableList() { return assignmentObservableList; }
//
//    public double getCourseGrade() {
//        courseGrade();
//        return course_grade;
//    }
//
//
//    public String getCourseTitle() {return course_title;}
//    public String getSemester() {return semester;}
//    public int getYear() {return year;}
//    public HashMap getAssignmentHashMap() {return assignmentHashMap;}
//    //public Assignment getAssignmentsOfType() {}
//    ////////////////////////////////////////////////////////////////
//
//    // error handeling if input is wrong type or other perameter
//    //////////////////////////// Setting Functions
//    public void setCourseTitle(String courseTitle) {
//        course_title = courseTitle;
//    }
//    public void setSemester(String Semester) {
//        semester = Semester;
//    }
//    public void setYear(int Year) {
//        year = Year;
//    }
//    public void setAssignmentRecievedPoints(String AssignmentType, int AssignmentNumber, double pointsReceived) {
//        assignmentHashMap.get(AssignmentType).get(AssignmentNumber).setReceivedPoints(pointsReceived);
//        totalAssignmentTypeGrade(AssignmentType, pointsReceived);
//    }
//
//    public void setAssignmentObservableList(ObservableList<Assignment> assignments) {assignmentObservableList = assignments;}
//    //////////////////////////////////////////////
//
//    //////////////////////////// Add Assignment
//    public void addAssignment(String title, String type, int assignmentNUmber, double pointsPossible, double assignmentWeight) {
//        // This error handling may be done in a seperate outside function
//        // putIfAbsent may be a better thought process for this
//        Assignment assignmentObj = new Assignment(title, type, assignmentNUmber, pointsPossible, assignmentWeight);
//        if ( assignmentHashMap.containsKey(assignmentObj.getAssignmentType()) ) {
//            assignmentHashMap.get(assignmentObj.getAssignmentType()).put(assignmentObj.getAssignmentNumber(),assignmentObj);
//        }
//    }
//    public void addAssignment(String title, String type, int assignmentNUmber, double pointsPossible, double pointsRecieved, double assignmentWeight) {
//        // This error handling may be done in a seperate outside function
//        // putIfAbsent may be a better thought process for this
//        Assignment assignmentObj = new Assignment(title, type, assignmentNUmber, pointsPossible, pointsRecieved, assignmentWeight);
//        if ( assignmentHashMap.containsKey(assignmentObj.getAssignmentType()) ) {
//            assignmentHashMap.get(assignmentObj.getAssignmentType()).put(assignmentObj.getAssignmentNumber(),assignmentObj);
//        }
//        totalAssignmentTypeGrade(assignmentObj.getAssignmentType(),assignmentObj.getReceivedPoints());
//    }
//    //////////////////////////////////////////////////////////////
//
//    //////////////////////////// Remove Assignment
//    public void removeAssignment(String AssignmentType, int AssignmentNumber) {
//        Assignment assignmentObj = assignmentHashMap.get(AssignmentType).get(AssignmentNumber);
//        totalAssignmentTypeGrade(assignmentObj.getAssignmentType(),-1*(assignmentObj.getReceivedPoints()));
//        // This error handling may be done in a seperate outside function
//        if ( assignmentHashMap.containsKey(assignmentObj.getAssignmentType()) ) {
//            assignmentHashMap.get(AssignmentType).remove(AssignmentNumber);
//        }
//
//    }
//    //////////////////////////////////////////////////////////////
//
//    //////////////////////////// Access Assignment
//    public Assignment getAssignment(String AssignmentType, int AssignmentNumber) {
//        return assignmentHashMap.get(AssignmentType).get(AssignmentNumber);
//    }
//    public HashMap getAssignmentsOfType(String AssignmentType) {
//        return assignmentHashMap.get(AssignmentType);
//    }
//    ///////////////////////////////////////////////////////////////
//
//    ////////////////////////// Print assignments
//    public void printAllAssignmentsOfType(String AssignmentType) {
//        HashMap<Integer, Assignment> assignTypHashMap = getAssignmentsOfType(AssignmentType);
//
//        if (assignTypHashMap.isEmpty()) {
//            System.out.println("No " + AssignmentType + " to display.");
//        }
//        else {
//            for(Map.Entry<Integer,Assignment> Assign : assignTypHashMap.entrySet()) {
//                Assign.getValue().printAssignment();
//            }
//        }
//        // System.out.println(assignmentHashMap.get(AssignmentType).toString());
//    }
//
//    public void printAllAssignments() {
//        if (isNestedMapEmpty()) {
//            System.out.println("No assignments to display.");
//        }else{
//            for(Map.Entry<String, HashMap<Integer,Assignment>> typeMap : assignmentHashMap.entrySet()) {
//                for(Map.Entry<Integer,Assignment> Assign : typeMap.getValue().entrySet()) {
//                    Assign.getValue().printAssignment();
//                }
//            }
//        }
//        //System.out.println(assignmentHashMap.toString());
//    }
//    ///////////////////////////////////////////////////////////////
//
//    //////////////////////// MISC
//    public Boolean isNestedMapEmpty() {
//        int assignHashMapSize = assignmentHashMap.size();
//        int numEmptyInnerMaps = 0;
//        for(Map.Entry<String, HashMap<Integer,Assignment>> typeMap : assignmentHashMap.entrySet()) {
//            if  ( typeMap.getValue().isEmpty() ) {
//                numEmptyInnerMaps += 1;
//            }
//        }
//        if (assignHashMapSize == numEmptyInnerMaps) {
//            return true;
//        }else {
//            return false;
//        }
//    }
//
//    public void totalAssignmentTypeGrade(String AssignmentType,double pointsRecieved) {
//        gradesHashMap.put(AssignmentType, gradesHashMap.get(AssignmentType) + pointsRecieved);
//        courseGrade();
//    }
//    ///////////////////////////////////////////////////////////////
//
//}