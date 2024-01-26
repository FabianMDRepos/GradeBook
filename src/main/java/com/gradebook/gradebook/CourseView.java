package com.gradebook.gradebook;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CourseView {
    public CourseView(Stage courseViewStage) throws Exception{
        FXMLLoader courseViewLoader = new FXMLLoader(getClass().getResource("CourseViewTest.fxml"));
        Parent root = courseViewLoader.load();
        CourseViewController courseViewController = courseViewLoader.getController();
        courseViewController.initialize();


        courseViewStage.setTitle("Grade Book");
        courseViewStage.setResizable(false);
        courseViewStage.setScene(new Scene(root, 737, 642)); // Set the size as per your requirement
        courseViewStage.show();

        courseViewStage.setOnCloseRequest(event -> {
// TODO: Setting within CourseViewListManager removes original setting here in CourseView leading to redundant serialization
            try {
                courseViewController.handleSaveCourseListToFile();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}

