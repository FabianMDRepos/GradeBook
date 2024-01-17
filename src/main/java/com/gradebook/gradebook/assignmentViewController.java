package com.gradebook.gradebook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class assignmentViewController extends Controller{

    @FXML private TableView<Assignment> tableView;
    @FXML private TableColumn<Assignment,String> Type;
    @FXML private TableColumn<Assignment,Integer> Number;
    @FXML private TableColumn<Assignment,Double> Received;
    @FXML private TableColumn<Assignment,Double> Possible;
    @FXML private TableColumn<Assignment,Double> Weight;
    @FXML private TextField Grade;

    @FXML private Button add;
    @FXML private Button edit;
    @FXML private Button delete;

    Courses currentCourse;
    ObservableList<Assignment> newData = FXCollections.observableArrayList();
    ObservableList<Assignment> data = FXCollections.observableArrayList();

    // Add function that checks for existing observable list in the course and
    // populates if one already exists
    public void addExistingAssignments(ObservableList<Assignment> courseAssignments) {
        if ( !courseAssignments.isEmpty() ) {
            data.addAll(courseAssignments);
            addAssignmentToTable();
        }
    }
    public void addAssignmentListToTable(ObservableList<Assignment> assignments) {
        tableView.setItems(assignments);
        tableView.refresh();
    }
    public void addAssignmentToTable(){//(Assignment assignment){

        Type.setCellValueFactory(new PropertyValueFactory<Assignment, String>("assignmentType"));
        Number.setCellValueFactory(new PropertyValueFactory<Assignment, Integer>("assignmentNumber"));
        Received.setCellValueFactory(new PropertyValueFactory<Assignment, Double>("receivedPoints"));
        Possible.setCellValueFactory(new PropertyValueFactory<Assignment, Double>("possiblePoints"));
        Weight.setCellValueFactory(new PropertyValueFactory<Assignment, Double>("weight"));

        tableView.setItems(data);
        tableView.refresh();
    }

    
    @FXML public void addAssignment(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addAssignment.fxml"));
            Parent root = loader.load();
            addAssignmentController childController = loader.getController();

            childController.setTypeComboBox();
            root.getChildrenUnmodifiable();
            Stage stage = new Stage();
            stage.setTitle("Add Assignment");
            stage.setResizable(false);

            stage.setOnCloseRequest(e -> {
                childController.setAddAssignmentCancelled(true);
            });

            stage.setScene(new Scene(root, 326, 312));
            stage.showAndWait();


            if (!childController.isAddAssignmentCancelled()) {
                String title = childController.getTitle();
                String type = childController.getType();
                int number = childController.getNumber();
                double possible = childController.getPossiblePoints();
                double received = childController.getReceivedPoints();
                double weight = childController.getWeight();

                createAssignment(title, type, number, possible, received, weight);
            }
            childController.setAddAssignmentCancelled(false);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void deleteAssignment(){
        if (!tableView.getSelectionModel().isEmpty()) {
            Assignment assignment = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex());
            int index = tableView.getSelectionModel().getSelectedIndex();

            data.remove(index);
            //tableView.getItems().remove(index);
            addAssignmentToTable();
            refreshCourseGrade();
            tableView.refresh();
        }
    }

    @FXML public void editAssignment(ActionEvent event){
        if (!tableView.getSelectionModel().isEmpty()) {
            int index = tableView.getSelectionModel().getSelectedIndex();
            Assignment assignment = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex());

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("editAssignment.fxml"));
                Parent root = loader.load();
                editAssignmentController childController = loader.getController();

                childController.setTypeComboBox();
                root.getChildrenUnmodifiable();

                childController.setTypeComboBox(assignment.getAssignmentType());
                childController.setAssignmentNumber(assignment.getAssignmentNumber());
                childController.setAssignmentWeight(assignment.getWeight());
                childController.setPointsReceived(assignment.getReceivedPoints());
                childController.setPointsPossible(assignment.getPossiblePoints());

                Stage stage = new Stage();
                stage.setTitle("Edit Assignment");
                stage.setResizable(false);

                stage.setScene(new Scene(root, 326, 312));
                stage.showAndWait();

                if (childController.isEdited()) {
                    // this step may be entirely useless
                    assignment.setAssignmentType(childController.getAssignmentType());
                    assignment.setAssignmentNumber(childController.getNumber());
                    assignment.setWeight(childController.getWeight());
                    assignment.setReceivedPoints(childController.getReceivedPoints());
                    assignment.setPossiblePoints(childController.getPossiblePoints());

                    String type = childController.getAssignmentType();
                    int number = Integer.parseInt(childController.getAssignmentNumber());
                    double weight = Double.parseDouble(childController.getAssignmentWeight());
                    double received = Double.parseDouble(childController.getAssignmentReceivedPoints());
                    double possible = Double.parseDouble(childController.getAssignmentPossiblePoints());

                    //update the data for the assignment at the specific index
                    tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex()).setAssignmentType(type);
                    tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex()).setAssignmentNumber(number);
                    tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex()).setWeight(weight);
                    tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex()).setReceivedPoints(received);
                    tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex()).setPossiblePoints(possible);

                    // Update data in observable list
                    data.set(index, assignment);
                    refreshCourseGrade();
                    tableView.refresh();
                }

            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void createAssignment(String title, String type, int number, double possible, double received, double weight){
        Assignment assignment;

        if (received == 0.0) {
            assignment = new Assignment(title,type,number,possible,weight);
        }else {
            assignment = new Assignment(title,type,number,possible,received,weight);
        }
       //assignment.printAssignment();
        data.add(assignment);
        newData.add(assignment);
        addAssignmentToTable();
        currentCourse.setAssignmentObservableList(data);
        refreshCourseGrade();
    }

    public ObservableList<Assignment> getNewData() { return newData; }
    public ObservableList<Assignment> getData() { return data; }
    public void setNewData(ObservableList<Assignment> inputData) {newData = inputData;}
    public void refreshCourseGrade(){
        double grade = currentCourse.getCourseGrade();
        grade = Math.round(grade * 100.0) / 100.0;
        String stringGrade = String.valueOf(grade);
        Grade.setText(stringGrade + "%");

    }
    public void setCurrentCourse(Courses selectedCourse) {currentCourse = selectedCourse; }
    public void initialize(){
        Grade.setEditable(false);
        Grade.setMouseTransparent(true);
        Grade.setFocusTraversable(false);
        refreshCourseGrade();
        //currentCourse = getSelectedCourse();

    }
}
