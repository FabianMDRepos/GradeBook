package com.gradebook.gradebook;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Objects;

public class Assignment{

    private SimpleStringProperty assignmentTitle;
    private SimpleStringProperty assignmentType;
    private SimpleIntegerProperty assignmentNumber;
    private SimpleDoubleProperty possiblePoints;
    private SimpleDoubleProperty receivedPoints;
    private SimpleDoubleProperty weight;
    private double unweightedGrade;
    private double weightedGrade;

    ///////////////////// Class Constructors 
    // Should check to see if an assignment has already been made with that title or number.
    public Assignment(String title, String type, int assignmentNUmber, double pointsPossible, double pointsReceived, double assignmentWeight) {
        this.assignmentTitle = new SimpleStringProperty(title);
        this.assignmentType = new SimpleStringProperty(type);
        this.assignmentNumber = new SimpleIntegerProperty(assignmentNUmber);
        this.possiblePoints = new SimpleDoubleProperty(pointsPossible);
        this.receivedPoints = new SimpleDoubleProperty(pointsReceived);
        this.weight = new SimpleDoubleProperty(assignmentWeight);
    }
    public Assignment(String title, String type, int assignmentNUmber, double pointsPossible, double assignmentWeight) {
        this.assignmentTitle = new SimpleStringProperty(title);
        this.assignmentType = new SimpleStringProperty(type);
        this.assignmentNumber = new SimpleIntegerProperty(assignmentNUmber);
        this.possiblePoints = new SimpleDoubleProperty(pointsPossible);
        this.weight = new SimpleDoubleProperty(assignmentWeight);
    }
    /////////////////////////////////////////////////////////////////////

    /////////////////// Grade Calculating Functions
    public void weightedGrade() {weightedGrade = (this.receivedPoints.get() /this.possiblePoints.get())*this.weight.get();}
    public void unWeightedGrade() {unweightedGrade = (this.receivedPoints.get() /this.possiblePoints.get());}
    //////////////////////////////////////////////////////////////////////

    /////////////////// Getting Functions
    public String getAssignmentTitle() {return assignmentType.get() + " " + assignmentNumber.get();}
    public String getAssignmentType() {return assignmentType.get();}
    public int getAssignmentNumber() {return assignmentNumber.get();}
    public double getPossiblePoints() {return possiblePoints.get();}
    public double getReceivedPoints() {return receivedPoints.get();}
    public double getWeight() {return weight.get();}
    public double getUnweightedGrade(){
        unWeightedGrade();
        return unweightedGrade;
    }
    public double getWeightedGrade(){
        weightedGrade();
        return weightedGrade;
    }
    ///////////////////////////////////////////////////////////////////////

    /////////////////////// Setting Functions
    public void setAssignmentType(String type) { this.assignmentType.set(type);}
    public void setAssignmentNumber(int number) {
        this.assignmentNumber.set(number);
    }
    public void setPossiblePoints(double pointsPossible) { this.possiblePoints.set(pointsPossible); }
    public void setReceivedPoints(double pointsReceived) { this.receivedPoints.set(pointsReceived); }
    public void setWeight(double assignmentWeight) { this.weight.set(assignmentWeight); }
    //////////////////////////////////////////////////////////////

    /////////////////////// Printing Function 
    public void printAssignment() {
        System.out.println("Type: " + assignmentType);
        System.out.println("Number: " + assignmentNumber);
        System.out.println("Possible points: " + possiblePoints );
        System.out.println("Points received: " + receivedPoints );
        System.out.println("Weight: " + weight ); 
        System.out.println("Weighted grade: " + getWeightedGrade());
        System.out.println("Unweighted grade: " + getUnweightedGrade() );
        System.out.println(); 
    }
    ///////////////////////////////////////////////////////////////

    //////////////////////// Utility Methods
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Assignment that = (Assignment) obj;
        return assignmentNumber.get() == that.assignmentNumber.get() &&
                Objects.equals(assignmentType.get(), that.assignmentType.get()) &&
                Objects.equals(assignmentTitle.get(), that.assignmentTitle.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignmentType.get(), assignmentTitle.get(), assignmentNumber.get());
    }

    public String serialize() {
        // TODO Look into putting functionality into a separate class designed for saving.
        JSONObject json = new JSONObject();
        json.put("assignmentTitle", assignmentTitle.get());
        json.put("assignmentType", assignmentType.get());
        json.put("assignmentNumber", assignmentNumber.get());
        json.put("possiblePoints", possiblePoints.get());
        json.put("receivedPoints", receivedPoints.get());
        json.put("weight", weight.get());
        return json.toString();
    }
    //////////////////////////// End Utility Methods
}