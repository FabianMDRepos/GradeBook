package com.gradebook.gradebook;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GradeBook extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        CourseView cv = new CourseView(primaryStage);
    }
    public void delete(){

    }


    public static void main(String[] args) {
        launch(args);
    }

}
