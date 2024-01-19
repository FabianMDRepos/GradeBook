package com.gradebook.gradebook;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CourseView {
    public CourseView(Stage courseViewStage) throws Exception{
        FXMLLoader courseViewLoader = new FXMLLoader(getClass().getResource("CourseView.fxml"));
        Parent root = courseViewLoader.load();
        Controller controller = courseViewLoader.getController();

        courseViewStage.setTitle("Grade Book");
        courseViewStage.setResizable(false);
        courseViewStage.setScene(new Scene(root, 737, 642)); // Set the size as per your requirement
        courseViewStage.show();
    }
}
