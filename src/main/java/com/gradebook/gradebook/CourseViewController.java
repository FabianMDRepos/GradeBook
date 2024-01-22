package com.gradebook.gradebook;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class CourseViewController extends  CourseViewListManager{
        @FXML private Button addButton;
        @FXML private Button editButton;
        @FXML private Button deleteButton;
        @FXML private ListView<Courses> courseList;

        private CourseViewListManager courseViewListManager = CourseViewListManager.getInstance();

        @FXML
        public void handleAddButtonAction() throws Exception {
            courseViewListManager.openAddCourseWindow(courseList);
        }
        @FXML
        public void handleEditButtonAction() throws Exception{
            if (isCourseSelected()) {
                courseViewListManager.openEditCourseWindow(courseList);
            }
        }
        @FXML
        public void handleDeleteButtonAction() {
            if (isCourseSelected()) {
                courseViewListManager.deleteSelectedCourse(courseList);
            }
        }
        @FXML
        public void handleCourseDoubleClick(MouseEvent event) throws Exception {
            if (event.getClickCount() == 2) {
                courseViewListManager.openAssignmentViewForSelectedCourse(courseList);
            }
        }

        private boolean isCourseSelected() {
            return !courseList.getSelectionModel().isEmpty();
        }
}
