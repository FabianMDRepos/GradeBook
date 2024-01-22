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

    protected void openAddCourseWindow(ListView<Courses> courseList) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addCourse.fxml"));
        Parent root = loader.load();
        addCourseController childController = loader.getController();

        childController.setYearComboBox();
        childController.setSemesterComboBox();

        Stage stage = createStage(root, "Add Course", 588, 280);
        stage.showAndWait();

        processNewCourseData(childController, courseList);
    }

    protected void openEditCourseWindow(ListView<Courses> courseList) throws Exception{
        Courses selectedCourse = courseList.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("editCourse.fxml"));
        Parent root = loader.load();
        editCourseController childController = setupEditController(loader, selectedCourse);

        Stage stage = createStage(root, "Edit Course", 588, 280);
        stage.showAndWait();

        updateCourseIfEdited(childController, selectedCourse, courseList);
    }

    protected void deleteSelectedCourse(ListView<Courses> courseList) {
        Courses course = courseList.getItems().get(courseList.getSelectionModel().getSelectedIndex());
        updateCourseListDisplay(courseList);
        int index = courseList.getSelectionModel().getSelectedIndex();
        courseList.getItems().remove(index);
        courseList.getSelectionModel().clearSelection();
        courseList.refresh();
    }

    protected void openAssignmentViewForSelectedCourse(ListView<Courses> courseList) throws Exception{

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
    private editCourseController setupEditController(FXMLLoader loader, Courses course) {
        editCourseController controller = loader.getController();
        controller.setYearComboBox(course.getYear());
        controller.setSemesterComboBox(course.getSemester());
        controller.setTitle(course.getCourseTitle());
        return controller;
    }
    private void updateCourseIfEdited(editCourseController controller, Courses course, ListView<Courses> courseList) {
        if (controller.isEdited()) {
            course.setYear(controller.getYear());
            course.setSemester(controller.getSemester());
            course.setCourseTitle(controller.getTitle());
            updateCourseListDisplay(courseList);
        }
    }
    private Courses createCourse(String title, String semester, int year)  {
        return new Courses(title,semester,year);
    }
    @FXML
    private void addToList(Courses course, ListView<Courses> courseList) {
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
    private void processNewCourseData(addCourseController controller, ListView<Courses> courseList) {
        int year = controller.getYear();
        String semester = controller.getSemester();
        String title = controller.getTitle();

        if (year != 0 && semester != null && title != null && !title.trim().isEmpty()) {
            addToList(createCourse(title, semester, year), courseList);
        }
    }
    @FXML
    private void updateCourseListDisplay(ListView<Courses> courseList){
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

}
