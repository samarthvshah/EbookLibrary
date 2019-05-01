package application.controller;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import application.Main;
import application.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

public class ChangeBarStudentsController {

	Main main;
	StudentsListViewController svc;

	/** Sets the main and the controller of the students list
	 * 
	 * @param m
	 * @param c
	 */
	public void setParents(Main m, StudentsListViewController c) {
		main = m;
		svc = c;
	}

	/**
	 * 
	 * @param event The event that results in the code handling that the student should be edited
	 */
	@FXML
	void handleEditStudent(ActionEvent event) {
		Student r = svc.getSelectedStudent();
		main.showEditStudentWindow(r);
	}

	/**
	 * 
	 * @param event The event that results in the code handling that a new student should be created
	 */
	@FXML
	void handleNewStudent(ActionEvent event) {
		main.showNewStudentWindow();
	}

	/**
	 * 
	 * @param event The event that results in the code handling that a student should be removed
	 */
	@FXML
	void handleRemoveStudent(ActionEvent event) {
		Student r = svc.getSelectedStudent();

		if (r == null) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(main.getStage());
			alert.setTitle("No Code Chosen");
			alert.setHeaderText("Please choose a code");
			alert.setContentText("No Code Chosen");
			alert.showAndWait();

		} else if (r.getBooks() == null) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(main.getStage());
			alert.setTitle("Student has books");
			alert.setHeaderText("This student currently has books checked out");
			alert.showAndWait();
		} else {

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Remove Student");
			alert.setHeaderText("Are you sure you want to remove this Student?");
			ButtonType buttonYes = new ButtonType("Yes");
			ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			alert.getButtonTypes().setAll(buttonYes, buttonTypeCancel);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonYes){

				try {

					JSONParser parser = main.getParser();
					JSONObject object = (JSONObject)parser.parse(new FileReader("res/students.json"));
					JSONArray studentsa = (JSONArray) object.get("students");

					for (int i = 0; i < main.getStudentsList().size(); i++) {
						if (main.getStudentsList().get(i).getID().equals(r.getID())) {
							studentsa.remove(i);
							
							try (FileWriter file = new FileWriter("res/students.json")) {
								file.write(object.toJSONString());
							}
						}
					}
					
					main.getController().switchToStudents();

				} catch(Exception e) {
					e.printStackTrace();

				}
			}
		}
	}
}

