package com.gradebook.gradebook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.spreadsheet.SpreadsheetCellEditor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseViewListManager implements Initializable {

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
        CourseAddController childController = loader.getController();

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
        CourseEditController childController = setupEditController(loader, selectedCourse);

        Stage stage = createStage(root, "Edit Course", 588, 280);
        stage.showAndWait();

        updateCourseIfEdited(childController, selectedCourse, courseList);
    }

    protected void deleteSelectedCourse(ListView<Courses> courseList) {
        updateCourseListDisplay(courseList);
        int index = courseList.getSelectionModel().getSelectedIndex();
        courseList.getItems().remove(index);
        courseList.getSelectionModel().clearSelection();
        courseList.refresh();
    }

    protected void openAssignmentViewForSelectedCourse(ListView<Courses> courseList) throws Exception {

        Courses selectedCourse = courseList.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("assignmentView.fxml"));
        Parent assignmentViewRoot = loader.load();
        AssignmentViewController controller = loader.getController();
        controller.setCurrentCourse(selectedCourse);
        controller.refreshCourseGrade();

        Scene currentScene = courseList.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();

        // Store the original scene
        Scene courseViewScene = currentStage.getScene();

        // Set the new scene
        Scene assignmentScene = new Scene(assignmentViewRoot,737, 642);
        currentStage.setScene(assignmentScene);
        currentStage.setTitle("Assignments for " + selectedCourse.getCourseTitle());

        // TODO consider moving this to its own method in a different class
        currentStage.setOnCloseRequest(event -> {
            if (currentStage.getScene().equals(courseViewScene)) {
                String filePath = CourseViewController.logsDirPath + CourseViewController.fileName;
                try {
                    writeCourseListToFile(courseList,filePath);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
            // Set the original scene back when the window is closed
            // TODO: Setting here within CourseViewListManager removes original setting in CourseView leading to redundant serialization
            event.consume();
            currentStage.setScene(courseViewScene);
        });
    }
    private CourseEditController setupEditController(FXMLLoader loader, Courses course) {
        CourseEditController controller = loader.getController();
        controller.setYearComboBox(course.getYear());
        controller.setSemesterComboBox(course.getSemester());
        controller.setTitle(course.getCourseTitle());
        return controller;
    }
    private void updateCourseIfEdited(CourseEditController controller, Courses course, ListView<Courses> courseList) {
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
    private void processNewCourseData(CourseAddController controller, ListView<Courses> courseList) {
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

    protected void writeCourseListToFile(ListView<Courses> courseList, String filePath) throws IOException {
        String serializedData = serializeCourseList(courseList);
        System.out.println("Saving file to " + filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write(serializedData);
            System.out.println("Files saved");
            return;
        }
    }
    protected String readCourseListFromFile(String filePath) throws Exception {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                contentBuilder.append(currentLine).append("\n");
            }
        }return contentBuilder.toString();
    }
    protected String serializeCourseList(ListView<Courses> courseList) {
        // TODO Look into putting functionality into a separate class designed for saving.
        JSONArray courseListArray = new JSONArray();
        for (Courses courses : courseList.getItems()) {
            courseListArray.put(new JSONObject(courses.serialize()));
        }
        return courseListArray.toString(4);
    }

    protected static ObservableList<Courses> deserializeCourseList(String jsonString) {
        JSONArray courseListArray = new JSONArray(jsonString);
        ObservableList<Courses> coursesObservableList = FXCollections.observableArrayList();

        for (int i = 0; i < courseListArray.length(); i++) {
            JSONObject courseJson = courseListArray.getJSONObject(i);
            Courses course = Courses.deserialize(courseJson.toString());
            coursesObservableList.add(course);
        }return coursesObservableList;
    }
//    protected static ListView<Courses> deserializeCourseList(String jsonString) {
//        JSONArray courseListArray = new JSONArray(jsonString);
//        ListView<Courses> courseListView = new ListView<>();
//
//        for (int i = 0; i < courseListArray.length(); i++) {
//            JSONObject courseJson = courseListArray.getJSONObject(i);
//            Courses course = Courses.deserialize(courseJson.toString());
//            courseListView.getItems().add(course);
//        }
//        return courseListView;
//    }

    public static ListView<Courses> mergeListViews(ListView<Courses> listView1, ListView<Courses> listView2) {
        ObservableList<Courses> combinedList = FXCollections.observableArrayList(listView1.getItems());

        for (Courses courseFromList2 : listView2.getItems()) {
            if (!containsCourse(combinedList, courseFromList2)) {
                combinedList.add(courseFromList2);
            }
        }
        listView1.setItems(combinedList);
        return listView1;
    }
    private static boolean containsCourse(ObservableList<Courses> list, Courses courseToCheck) {
        for (Courses course : list) {
            if (course.equals(courseToCheck)) { // Define equals method in Courses class
                return true;
            }
        }
        return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    public void populateDisplayWithTest(ListView<Courses> courseList) {
        updateCourseListDisplay(courseList);
    }
}
