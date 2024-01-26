package com.gradebook.gradebook;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;

public class CourseViewController extends  CourseViewListManager{
        @FXML private Button addButton;
        @FXML private Button editButton;
        @FXML private Button deleteButton;
        @FXML private ListView<Courses> courseList;
        public static final String logsDirPath = "logs";
        public static final String fileName = "coursesData.json";

        private CourseViewListManager courseViewListManager = CourseViewListManager.getInstance();

        @FXML
        public void handleAddButtonAction() throws Exception {
            courseViewListManager.openAddCourseWindow(courseList);
        }
        @FXML
        public void handleEditButtonAction() throws Exception{
            if (CourseIsSelected()) {
                courseViewListManager.openEditCourseWindow(courseList);
            }
        }
        @FXML
        public void handleDeleteButtonAction() {
            if (CourseIsSelected()) {
                courseViewListManager.deleteSelectedCourse(courseList);
            }
        }
        @FXML
        public void handleCourseDoubleClick(MouseEvent event) throws Exception {
            if (2 == event.getClickCount() && CourseIsSelected()) {
                courseViewListManager.openAssignmentViewForSelectedCourse(courseList);
            }
        }
        protected Courses getCurrentCourse() {
            return courseList.getSelectionModel().getSelectedItem();
        }
        private boolean CourseIsSelected() {
            return !courseList.getSelectionModel().isEmpty();
        }
        protected String handleSerializeCourseList() {
            return courseViewListManager.serializeCourseList(courseList);
        }
        public void initialize() throws Exception {
            ObservableList<Courses> combinedList = FXCollections.observableArrayList();
            File logsDir = new File(logsDirPath);
            if (!logsDir.exists()) {
                logsDir.mkdir();
            }
            handleLoadCourseListFromFile();
            combinedList.addAll(courseList.getItems());

            ListView<Courses> generatedCourseListView = CourseViewCoursePopulate.generateCourseListView();
            combinedList.addAll(generatedCourseListView.getItems());

            courseList = mergeListViews(courseList, generatedCourseListView);
            //courseList.setItems(generatedCourseListView.getItems());

            populateDisplayWithTest(courseList);
        }
        public void handleSaveCourseListToFile() throws Exception {
            // TODO Look into putting functionality into a separate class designed for saving.
            String filePath = logsDirPath + File.separator + fileName;
            writeCourseListToFile(courseList, filePath);
        }

        public void handleLoadCourseListFromFile() throws Exception {
            String filePath = logsDirPath + File.separator + fileName;
            File file = new File(filePath);

            if (file.exists()) {
                String jsonData = readCourseListFromFile(filePath);
                this.courseList.setItems(deserializeCourseList(jsonData));
            }
        }

}
