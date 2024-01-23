package com.gradebook.gradebook;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CourseViewListManager {

    private static CourseViewListManager listManagerInstance;
    protected CourseViewListManager() {
    }
    protected static CourseViewListManager getInstance() {
        return listManagerInstance == null ? listManagerInstance =
                new CourseViewListManager() : listManagerInstance;
    }

    protected void openAddCourseWindow(ListView<Course> courseList) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addCourse.fxml"));
        Parent root = loader.load();
        CourseAddController childController = loader.getController();

        childController.setYearComboBox();
        childController.setSemesterComboBox();

        Stage stage = createStage(root, "Add Course", 588, 280);
        stage.showAndWait();

        processNewCourseData(childController, courseList);
    }

    protected void openEditCourseWindow(ListView<Course> courseList) throws Exception{
        Course selectedCourse = courseList.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("editCourse.fxml"));
        Parent root = loader.load();
        CourseEditController childController = setupEditController(loader, selectedCourse);

        Stage stage = createStage(root, "Edit Course", 588, 280);
        stage.showAndWait();

        updateCourseIfEdited(childController, selectedCourse, courseList);
    }

    protected void deleteSelectedCourse(ListView<Course> courseList) {
        updateCourseListDisplay(courseList);
        int index = courseList.getSelectionModel().getSelectedIndex();
        courseList.getItems().remove(index);
        courseList.getSelectionModel().clearSelection();
        courseList.refresh();
    }

    protected void openAssignmentViewForSelectedCourse(ListView<Course> courseList) throws Exception {

        Course selectedCourse = courseList.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("assignmentView.fxml"));
        Parent assignmentViewRoot = loader.load();
        AssignmentViewController controller = loader.getController();
        controller.setCurrentCourse(selectedCourse);

        Scene currentScene = courseList.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();

        // Store the original scene
        Scene courseViewScene = currentStage.getScene();

        // Set the new scene
        Scene assignmentScene = new Scene(assignmentViewRoot,737, 642);
        currentStage.setScene(assignmentScene);
        currentStage.setTitle("Assignments for " + selectedCourse.getCourseTitle());

        // Handle the window close request
        currentStage.setOnCloseRequest(event -> {
            if (currentStage.getScene().equals(courseViewScene)) {
                return;
            }
            // Set the original scene back when the window is closed
            event.consume();
            currentStage.setScene(courseViewScene);
        });
    }
    private CourseEditController setupEditController(FXMLLoader loader, Course course) {
        CourseEditController controller = loader.getController();
        controller.setYearComboBox(course.getYear());
        controller.setSemesterComboBox(course.getSemester());
        controller.setTitle(course.getCourseTitle());
        return controller;
    }
    private void updateCourseIfEdited(CourseEditController controller, Course course, ListView<Course> courseList) {
        if (controller.isEdited()) {
            course.setYear(controller.getYear());
            course.setSemester(controller.getSemester());
            course.setCourseTitle(controller.getTitle());
            updateCourseListDisplay(courseList);
        }
    }
    private Course createCourse(String title, String semester, int year)  {
        return new Course(title,semester,year);
    }
    @FXML
    private void addToList(Course course, ListView<Course> courseList) {
        courseList.getItems().add(course);
        updateCourseListDisplay(courseList);
    }
    private Stage createStage(Parent root, String title, double width, double height) {
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(new Scene(root, width, height));
        return stage;
    }
    private void processNewCourseData(CourseAddController controller, ListView<Course> courseList) {
        int year = controller.getYear();
        String semester = controller.getSemester();
        String title = controller.getTitle();

        if (year != 0 && semester != null && title != null && !title.trim().isEmpty()) {
            addToList(createCourse(title, semester, year), courseList);
        }
    }
    @FXML
    private void updateCourseListDisplay(ListView<Course> courseList){
        courseList.setCellFactory(lv -> new ListCell<Course>() {
            @Override
            public void updateItem(Course course, boolean empty) {
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

    private void saveCourseListToFile(ListView<Course> courseList) {

    }

}
