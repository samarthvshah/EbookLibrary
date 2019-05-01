package application.controller;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import application.Main;
import application.model.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewStudentController {

	Main m;
	Stage s;
	@FXML private TextField firstNameField;
	@FXML private TextField lastNameField;
	@FXML private TextField idField;
	@FXML private TextField gradeField;
	JSONParser parser;

	/** Sets the fields of the window
	 * 
	 * @param m The main of the program
	 * @param s The window that the page is on
	 * @param p The JSONParsr used to read and edit the JSON files
	 * @return true when the fields are set
	 */
	public boolean setFields(Main m, Stage s, JSONParser p) {
		this.m = m;
		this.s = s;
		parser = p;

		return true;
	}

	/** Closes the window when the cancel button is pressed
	 * 
	 * @param event when the cancel button is pressed
	 */
	@FXML
	void handleCancel(ActionEvent event) {
		s.close();
	}

    /** Creates a new student with the fields entered
     * 
     * @param event when the ok button is pressed
     */
	@FXML
	void handleOk(ActionEvent event) {
		if (isInputValid()) {

			String fname = firstNameField.getText();
			String lname = lastNameField.getText();
			String id = idField.getText();
			int grade = Integer.parseInt(gradeField.getText());

			ArrayList<Student> students = m.getStudentsList();
			students.add(new Student(fname, lname, id, grade, null));

			try {
				JSONObject object = (JSONObject)parser.parse(new FileReader("res/students.json"));
				JSONArray studentsa = (JSONArray) object.get("students");
				JSONObject newStudents = new JSONObject();

				newStudents.put("ln", lname);
				newStudents.put("fn", fname);
				newStudents.put("g", grade);
				newStudents.put("id", id);

				studentsa.add(newStudents);

				try (FileWriter file = new FileWriter("res/students.json")) {
					file.write(object.toJSONString());
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			s.close();
		}
	}

    /**
     * 
     * @return true of the fields entered can be used to make a valid student
     */
	public boolean isInputValid() {
		String errorMessage = "";

		if(firstNameField.getText() == null || firstNameField.getText().length() == 0) {
			errorMessage += "No valid first name!\n";
		}

		if(lastNameField.getText() == null || lastNameField.getText().length() == 0) {
			errorMessage += "No valid last name!\n";
		}

		if(idField.getText() == null || idField.getText().length() == 0) {
			errorMessage += "No valid id!\n";
		}

		if(gradeField.getText() == null || gradeField.getText().length() == 0) {
			errorMessage += "No valid grade!\n";
		} else{
			try{
				Integer.parseInt(gradeField.getText());
			} catch (NumberFormatException e){
				errorMessage += "No valid grade!\n";
			}
		}

		//if there is no error message, returns true
		if (errorMessage.length() == 0) {
			return true;
		} else { //if there is an error message, create alert with error messages printed
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(s);
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);
			alert.showAndWait();

			return false;
		}
	}
}
