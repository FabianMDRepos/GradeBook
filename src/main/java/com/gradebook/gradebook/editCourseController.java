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

public class editCourseController extends addCourseController {

    @FXML private ComboBox<String> semesterComboBox;
    @FXML private ComboBox<String> yearComboBox;
    @FXML private ObservableList<String> yearValues;
    @FXML private TextField courseTitleField;
    private boolean edited = false;

    @FXML private Button Save;



    public void setYearComboBox(int Year) {
        String currentYear = Integer.toString(Year);
        yearValues = FXCollections.observableArrayList(generateYearList());
        yearComboBox.getItems().addAll(generateYearList());
        for (String year : yearComboBox.getItems()) {
            if (currentYear.equals(year)) {
                yearComboBox.getSelectionModel().select(year);
                break;
            }
        }
    }

    public void setSemesterComboBox(String currentSemester) {
        semesterComboBox.getItems().addAll("Spring","Summer","Fall");
        for (String semester : semesterComboBox.getItems()) {
            if (currentSemester.equals(semester)) {
                semesterComboBox.getSelectionModel().select(semester);
                break;
            }
        }

    }

    public void setTitle(String currentTitle){
        CourseTitle = currentTitle;
        courseTitleField.setText(currentTitle);
    }
    public boolean isEdited(){
        if (edited) {
            edited = false;
            return true;
        }else {
            return false;
        }
    }
    //Probably default to the create button from addCourse class
    @FXML private void saveButton(){
        String courseTitle = getCourseTitle();
        if (!courseTitle.equals("empty")) {
            CourseTitle = getCourseTitle();
        }
        CourseSemester = getCourseSemester();
        CourseYear = getCourseYear();
        edited = true;

        Stage stage = (Stage) Save.getScene().getWindow();
        stage.close();
    }


}
