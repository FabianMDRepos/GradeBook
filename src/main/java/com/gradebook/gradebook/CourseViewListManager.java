package com.gradebook.gradebook;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CourseViewListManager {

    public void openAddCourseWindow(ListView<Courses> courseList) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addCourse.fxml"));
        Parent root = loader.load(); // Ensure the path is correct
        addCourseController childController = loader.getController();

        // Initialize combo boxes or other UI elements as needed
        childController.setYearComboBox();
        childController.setSemesterComboBox();

        Stage stage = new Stage();
        stage.setTitle("Add Course");
        stage.setResizable(false);
        stage.setScene(new Scene(root, 588, 280)); // Set window size as needed
        stage.showAndWait();

        // Retrieve user input from the controller after the window is closed
        int year = childController.getYear();
        String semester = childController.getSemester();
        String title = childController.getTitle();

        // Validate and use the data as needed
        if (year != 0 && semester != null && title != null && !title.trim().isEmpty()) {
            addToList(createCourse(title, semester, year),courseList);
        }
    }

    public void openEditCourseWindow(ListView<Courses> courseList) {

    }

    public void deleteSelectedCourse(ListView<Courses> courseList) {

    }
    private Courses createCourse(String title, String semester, int year)  {
        return new Courses(title,semester,year);
    }
    @FXML
    public void addToList(Courses course, ListView<Courses> courseList) {
        courseList.getItems().add(course);
        configureCourseListCellFactory(courseList);
    }
    @FXML
    public void configureCourseListCellFactory(ListView<Courses> courseList){
        courseList.setCellFactory(lv -> new ListCell<Courses>() {
            @Override
            public void updateItem(Courses course, boolean empty) {
                super.updateItem(course, empty);
                if (empty || course == null) {
                    setText(null);
                } else {
                    String formattedText = String.format("%s - %s %d",
                            course.getCourseTitle(),
                            course.getSemester(),
                            course.getYear());
                    setText(formattedText);
                }
            }
        });
    }
    public void openAssignmentViewForSelectedCourse(ListView<Courses> courseList) throws Exception{

        if (courseList.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        Courses selectedCourse = courseList.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("assignmentView.fxml"));
        Parent assignmentViewRoot = loader.load();

        assignmentViewController controller = loader.getController();
        controller.setCurrentCourse(selectedCourse);

        Scene currentScene = courseList.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();

        Scene assignmentScene = new Scene(assignmentViewRoot);
        currentStage.setScene(assignmentScene);

        currentStage.setTitle("Assignments for " + selectedCourse.getCourseTitle());


    }
}
