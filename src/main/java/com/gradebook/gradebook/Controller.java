package com.gradebook.gradebook;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class Controller implements Initializable {
    @FXML private Button Add;
    @FXML private Button Edit;
    @FXML public Button Delete;
    @FXML private ListView<Course> CourseList;
    @FXML private Set<String> stringSet;

    protected Course selectedCourse;

    @FXML public void addToList(Course course) {
        CourseList.getItems().add(course);
        updateText(course,false);
    }


    @FXML public void deleteButton(ActionEvent event){
        if (!CourseList.getSelectionModel().isEmpty() ) {
            Course course = CourseList.getItems().get(CourseList.getSelectionModel().getSelectedIndex());
            updateText(course, true);
            int index = CourseList.getSelectionModel().getSelectedIndex();
            CourseList.getItems().remove(index);
            CourseList.getSelectionModel().clearSelection();
            CourseList.refresh();
        }
    }
    @FXML public void addButton(ActionEvent event){

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addCourse.fxml"));
            Parent root = loader.load();
            CourseAddController childController = loader.getController();

            childController.setYearComboBox();
            childController.setSemesterComboBox();

            root.getChildrenUnmodifiable();
            Stage stage = new Stage();
            stage.setTitle("Add Course");
            stage.setResizable(false);

            stage.setScene(new Scene(root, 588, 280));
            stage.showAndWait();

            int year = childController.getYear();
            String semester = childController.getSemester();
            String title = childController.getTitle();

            if (year != 0) {
                createCourse(title,semester,year);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML public void editButton(ActionEvent event){
        if (!CourseList.getSelectionModel().isEmpty() ) {

            Course course = CourseList.getItems().get(CourseList.getSelectionModel().getSelectedIndex());

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("editCourse.fxml"));
                Parent root = loader.load();
                CourseEditController childController = loader.getController();

                String currentSemester = course.getSemester();
                int currentYear = course.getYear();
                String currentTitle = course.getCourseTitle();

                childController.setYearComboBox(currentYear);
                childController.setSemesterComboBox(currentSemester);
                childController.setTitle(currentTitle);

                root.getChildrenUnmodifiable();
                Stage stage = new Stage();
                stage.setTitle("Edit Course");
                stage.setResizable(false);

                stage.setScene(new Scene(root, 588, 280));
                stage.showAndWait();

                int Year = childController.getYear();
                String Semester = childController.getSemester();
                String Title = childController.getTitle();

                if (childController.isEdited()) {
                    course.setYear(Year);
                    course.setSemester(Semester);
                    course.setCourseTitle(Title);

                    updateText(course, false);
                }



            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void createCourse(String title, String semester, int year)  {
        Course course = new Course(title,semester,year);
        addToList(course);
    }

    @FXML public void updateText(Course course, boolean bool){
        CourseList.setCellFactory(lv -> new ListCell<Course>() {
            @Override
            public void updateItem(Course course, boolean empty) {
                super.updateItem(course, empty);
                if (empty) {
                    setText(null);
                } else {
                    int yearInt = course.getYear();
                    String yearString = Integer.toString(yearInt);
                    String text = course.getCourseTitle() + "            " + course.getSemester() + "              " + yearString;
                    setText(text);
                }
            }
        });
    }

    public void onDoubleClick(MouseEvent click) {

        CourseList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent Event) {
                if (Event.getClickCount() == 2 && !CourseList.getSelectionModel().isEmpty()){
                    selectedCourse = CourseList.getItems().get(CourseList.getSelectionModel().getSelectedIndex());
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("assignmentView.fxml"));
                        Parent root = loader.load();
                        assignmentViewController childController = loader.getController();

                        childController.setCurrentCourse(selectedCourse);
                        childController.initialize();
                        ObservableList<Assignment> courseAssignments = selectedCourse.getAssignmentObservableList();
                        childController.addExistingAssignments(courseAssignments);
                        childController.addAssignmentToTable();

                        root.getChildrenUnmodifiable();
                        //childController.generateTable();
                        Stage stage = new Stage();
                        stage.setTitle("Assignments");
                        stage.setResizable(false);

                        stage.setScene(new Scene(root, 516, 400));
                        stage.showAndWait();


                        if (!childController.data.isEmpty()) {
                            //ObservableList<Assignment> = "get observable list data"
                            //ObservableList<Assignment> newAssignments = childController.getNewData();
                            ObservableList<Assignment> selectedCourseAssignments = selectedCourse.getAssignmentObservableList();


                            //Add new assignments to the courses assignment observable list
                            //selectedCourseAssignments.addAll(newAssignments);
                            //selectedCourse.setAssignmentObservableList(selectedCourseAssignments);

                            ObservableList<Assignment> newAssignments = childController.getData();
                            selectedCourse.setAssignmentObservableList(newAssignments);
                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                CourseList.setCellFactory(lv -> {
                            ListCell<Course> cell = new ListCell<Course>() {
                            protected void updateItem (Course course, boolean empty){
                                super.updateItem(course, empty);
                                if (empty) {
                                    setText(null);
                                } else {
                                    int yearInt = course.getYear();
                                    String yearString = Integer.toString(yearInt);
                                    String text = course.getCourseTitle() + "            " + course.getSemester() + "              " + yearString;
                                    setText(text);
                                }}
                        };
                    cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                        if (cell.isEmpty() ) {
                            CourseList.getSelectionModel().clearSelection();
                            return;
                        }
                    });
                    return cell;
            });
            }
        });
    }

    public Course getSelectedCourse() { return selectedCourse;}

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
