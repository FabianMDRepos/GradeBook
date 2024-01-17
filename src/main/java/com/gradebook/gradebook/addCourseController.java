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
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Set;

public class addCourseController {

    @FXML private ComboBox<String> semesterComboBox;
    @FXML private ComboBox<String> yearComboBox;
    @FXML private ObservableList<String> yearValues;
    @FXML private TextField courseTitleField;

    @FXML private Button Create;
    @FXML private Button Cancel;

    protected int CourseYear;
    protected String CourseSemester;
    protected String CourseTitle;



    public void setYearComboBox() {
        yearValues = FXCollections.observableArrayList(generateYearList());
        yearComboBox.getItems().addAll(generateYearList());
        int currentYearIndex = 10; // middle index in the year list. This is the  current year by design.
        yearComboBox.getSelectionModel().select(currentYearIndex);
    }

    public void setSemesterComboBox() {
        semesterComboBox.getItems().addAll("Spring","Summer","Fall");
        int month = Calendar.getInstance().get(Calendar.MONTH);
        if (month <= 4) {
            semesterComboBox.getSelectionModel().select(0);
        }else if ( (month > 4) && (month < 8) ) {
            semesterComboBox.getSelectionModel().select(1);
        }else if (month >= 8) {
            semesterComboBox.getSelectionModel().select(2);
        }
    }

    protected String[] generateYearList(){
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int size = 21;
        int yearToEnter = year-10;

        String[] result = new String[size];

        for (int i = 0; i < result.length; i++) {
            String yearString = Integer.toString(yearToEnter);
            result[i] = yearString;
            yearToEnter++;
        }
        return result;
    }

    @FXML public void createButton(ActionEvent event){
        String courseTitle = getCourseTitle();
        if (courseTitle.equals("empty")) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Input not valid");
            errorAlert.setContentText("Please enter in a course title.");
            errorAlert.showAndWait();
            return;
        }
        CourseTitle = courseTitle;
        CourseSemester = getCourseSemester();
        CourseYear = getCourseYear();

        Stage stage = (Stage) Create.getScene().getWindow();
        stage.close();
    }
    @FXML protected void closeButton(){
        // get a handle to the stage
        Stage stage = (Stage) Cancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    protected int getCourseYear() {
        String yearInput = yearComboBox.getValue();
        int courseYear = Integer.parseInt(yearInput);
        return courseYear;
    }

    protected String getCourseTitle() {
       if (courseTitleField.getText() == null || courseTitleField.getText().trim().isEmpty()) {
           String titleInput = "empty";
           return titleInput;
       }else {
           String titleInput = courseTitleField.getText();
           return titleInput;
       }
    }

    protected String getCourseSemester() {
        String semesterInput = semesterComboBox.getValue();
        return semesterInput;
    }

    public String getTitle(){return CourseTitle;}
    public String getSemester(){return CourseSemester;}
    public int getYear(){return CourseYear;}


}
