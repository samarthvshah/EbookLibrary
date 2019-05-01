package application.controller;

import java.io.FileReader;
import java.io.FileWriter;
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

public class EditStudentController {

	Main m;
	Stage s;
	Student student;
	@FXML private TextField firstNameField;
	@FXML private TextField lastNameField;
	@FXML private TextField idField;
	@FXML private TextField gradeField;
	JSONParser parser;

	/**
	 * 
	 * @param m The main of the program
	 * @param s The new window that the edit window will be on
	 * @param p The parser used to read and change the json file
	 * @param stu The student that will be edited
	 * @return
	 */
	public boolean setFields(Main m, Stage s, JSONParser p, Student stu) {
		this.m = m;
		this.s = s;
		parser = p;
		student = stu;

		if (stu == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(s);
			alert.setTitle("No Student Chosen");
			alert.setHeaderText("Please choose a Student");
			alert.setContentText("No Student Chosen");

			alert.showAndWait();
			s.close();
			return false;
		}

		firstNameField.setText(student.getFirstName().get());
		lastNameField.setText(student.getLastName().get());
		idField.setText(student.getID().get());
		gradeField.setText(student.getGrade().get() + "");

		return true;
	}

	/** Closed the window
	 * 
	 * @param event the event where the window is closed
	 */
	@FXML
	void handleCancel(ActionEvent event) {
		s.close();

	}

    /** Updates the fields of the student when the ok button is pressed
     * 
     * @param event the event where the ok button is pressed
     */
	@FXML
	void handleOk(ActionEvent event) {
		if (isInputValid()) {

			String fname = firstNameField.getText();
			String lname = lastNameField.getText();
			String id = idField.getText();
			int grade = Integer.parseInt(gradeField.getText());


			student.setFirstName(fname);
			student.setLastName(lname);
			student.setGrade(grade);
			student.setID(id);

			try {

				int index = -1;

				for (int i = 0; i < m.getStudentsList().size(); i++) {
					if (student.getID().get().equals(m.getStudentsList().get(i).getID().get())) {
						index = i;
					}
				}

				JSONObject object = (JSONObject)parser.parse(new FileReader("res/students.json"));
				JSONArray codesa = (JSONArray) object.get("students");
				JSONObject student = (JSONObject)codesa.get(index);

				student.remove("fn");
				student.remove("ln");
				student.remove("g");
				student.remove("id");

				student.put("fn", this.student.getFirstName().get());
				student.put("ln", this.student.getLastName().get());
				student.put("g", this.student.getGrade().get());
				student.put("id", this.student.getID().get());



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
     * @return True if the input has valid fields to change the student to
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
