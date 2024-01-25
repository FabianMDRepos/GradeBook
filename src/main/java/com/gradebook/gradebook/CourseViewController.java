package com.gradebook.gradebook;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;

public class CourseViewController extends  CourseViewListManager{
        @FXML private Button addButton;
        @FXML private Button editButton;
        @FXML private Button deleteButton;
        @FXML private ListView<Courses> courseList;
        private String saveFilePath;

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
        public void initialize() {
            ListView<Courses> generatedCourseListView = CourseViewCoursePopulate.generateCourseListView();
            courseList.setItems(generatedCourseListView.getItems());
            populateDisplayWithTest(courseList);
        }
//        public void handleSaveCourseListToFile() throws Exception{
//            // TODO Look into putting functionality into a separate class designed for saving.
//            Scene courseViewScene = courseList.getScene();
//            Stage courseViewSage = (Stage) courseViewScene.getWindow();
//            courseViewSage.setOnCloseRequest(event -> {
//                        if (courseViewSage.getScene().equals(courseViewScene)) {
//                            String serializedData = courseViewListManager.serializeCourseList(courseList);
//                            System.out.println(serializedData);
//                        }
//                    });
////            String userHome = System.getProperty("user.home");
////            String directoryPath = userHome + File.separator + "YourApplicationName";
////            String filePath = directoryPath + File.separator + "grades.json";
////
////            // Create directory if it doesn't exist
////            File directory = new File(directoryPath);
////            if (!directory.exists()) {
////                directory.mkdirs();
////            }
//        }
}
