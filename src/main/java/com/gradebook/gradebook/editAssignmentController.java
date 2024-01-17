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

public class editAssignmentController extends addAssignmentController {

    @FXML private ComboBox<String> typeComboBox;
    @FXML private TextField numberField;
    @FXML private TextField weightField;
    @FXML private TextField receivedPointsField;
    @FXML private TextField possiblePointsField;

    @FXML private Button Save;
    private boolean edited = false;

    public void setTypeComboBox(String currentType) {
        typeComboBox.getSelectionModel().select(currentType);
    }

    public void setAssignmentNumber(int currentNumber) {
        numberField.setText( String.valueOf( currentNumber ) );
    }

    public void setAssignmentWeight(double currentWeight) {
        weightField.setText( String.valueOf( currentWeight ) );
    }

    public void setPointsReceived(double currentReceived) {
        receivedPointsField.setText( String.valueOf(currentReceived));
    }

    public void setPointsPossible(double currentPossible) {
        possiblePointsField.setText(String.valueOf(currentPossible));
    }

    @FXML public void saveButton(ActionEvent event){
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
        edited = true;

        Stage stage = (Stage) Save.getScene().getWindow();
        stage.close();
    }

    public boolean isEdited() {
        return edited;
    }

}
