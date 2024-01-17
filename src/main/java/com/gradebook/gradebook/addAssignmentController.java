package com.gradebook.gradebook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class addAssignmentController {

    @FXML private ComboBox<String> typeComboBox;
    @FXML private TextField numberField;
    @FXML private TextField weightField;
    @FXML private TextField receivedPointsField;
    @FXML private TextField possiblePointsField;

    @FXML private Button Create;
    @FXML private Button Cancel;

    protected String assignment_title;
    protected String assignment_type;
    protected int assignment_number;
    protected double possible_points;
    protected double received_points;
    protected double weight;

    private boolean addAssignmentCancelled;


    public void setTypeComboBox(){
        typeComboBox.getItems().addAll("Quiz","Home Work","Exam","Essay","Attendance","Lab");
        typeComboBox.getSelectionModel().select(1);
    }

    @FXML public void createButton(ActionEvent event){

        String Number = getAssignmentNumber();
        String Weight = getAssignmentWeight();
        String Received = getAssignmentReceivedPoints();
        String Possible = getAssignmentPossiblePoints();

        if (Number.equals("empty") || Weight.equals("empty") || Possible.equals("empty")) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("All fields must have valid input.");
            errorAlert.showAndWait();
            return;
        }

        assignment_type = getAssignmentType();
        assignment_title = assignment_type + Number;
        assignment_number = Integer.parseInt(Number);
        weight = Double.parseDouble(Weight);

        if (Received.equals("empty") ) {
            received_points = 0.0;
        }else {
            received_points = Double.parseDouble(Received);
        }
        possible_points = Double.parseDouble(Possible);


        Stage stage = (Stage) Create.getScene().getWindow();
        stage.close();
    }

    @FXML protected void closeButton(){
        // get a handle to the stage
        Stage stage = (Stage) Cancel.getScene().getWindow();
        setAddAssignmentCancelled(true);
        // do what you have to do
        stage.close();
    }

    protected String getAssignmentNumber() {
        if (numberField.getText() == null || numberField.getText().trim().isEmpty()) {
            String Input = "empty";
            return Input;
        }else {
            String Input = numberField.getText();
            return Input;
        }
    }

    protected String getAssignmentWeight() {
        if (weightField.getText() == null || weightField.getText().trim().isEmpty()) {
            String Input = "empty";
            return Input;
        }else {
            String Input = weightField.getText();
            return Input;
        }
    }

    protected String getAssignmentReceivedPoints() {
        if (receivedPointsField.getText() == null || receivedPointsField.getText().trim().isEmpty()) {
            String Input = "empty";
            return Input;
        }else {
            String Input = receivedPointsField.getText();
            return Input;
        }
    }

    protected String getAssignmentPossiblePoints() {
        if (possiblePointsField.getText() == null || possiblePointsField.getText().trim().isEmpty()) {
            String Input = "empty";
            return Input;
        }else {
            String Input = possiblePointsField.getText();
            return Input;
        }
    }

    protected String getAssignmentType(){
        String Input = typeComboBox.getValue();
        return Input;
    }

    public int getNumber(){return assignment_number;}
    public String getTitle(){return assignment_title;};
    public String getType(){return assignment_type;};
    public double getPossiblePoints(){return possible_points;};
    public double getReceivedPoints(){return received_points;};
    public double getWeight(){return weight;};

    public boolean isAddAssignmentCancelled() { return addAssignmentCancelled; }
    public void setAddAssignmentCancelled(boolean value) {addAssignmentCancelled = value;}


}
