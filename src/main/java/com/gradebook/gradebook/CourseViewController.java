package com.gradebook.gradebook;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class CourseViewController extends  CourseViewListManager{
        @FXML private Button addButton;
        @FXML private Button editButton;
        @FXML private Button deleteButton;
        @FXML private ListView<Course> courseList;

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
        protected Course getCurrentCourse() {
            return courseList.getSelectionModel().getSelectedItem();
        }
        private boolean CourseIsSelected() {
            return !courseList.getSelectionModel().isEmpty();
        }
}
