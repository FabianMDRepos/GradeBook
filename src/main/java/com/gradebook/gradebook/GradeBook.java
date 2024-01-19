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
        //Parent root = FXMLLoader.load(getClass().getResource("CourseView.fxml"));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseView.fxml"));
//        Parent root = loader.load();
//        Controller controller = loader.getController();
//
//        root.getChildrenUnmodifiable();
//        primaryStage.setTitle("Grade Book");
//        primaryStage.setResizable(false);
//
//        primaryStage.setScene(new Scene(root, 737, 642));
//        primaryStage.show();
    }
    public void delete(){

    }


    public static void main(String[] args) {
        launch(args);
    }

}
