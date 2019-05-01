package application.controller;

import java.util.ArrayList;

import application.Main;
import application.model.Student;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class LoginSceneController {

	Scene scene;
	@FXML private TextField usernameField;
	@FXML private PasswordField passwordField;
	Stage s;
	Main m;

	/**
	 * 
	 * @param main The main of the program
	 * @param stage The window that the window should be on
	 */
	public void setParameters(Main main, Stage stage) {
		m = main;
		s = stage;
	}

	/** Handles the login by checking for username and passwords against students and and the admin login
	 * 
	 * @return True if the login occurs successfully
	 */
	@FXML
	boolean handleLogin() {
		if (isInputValid()) {

			String username = usernameField.getText();
			String password = passwordField.getText();

			if (username.equals("librarian") && password.equals("FBLA")) {
				m.loggedIn();
				return true;
			}

			ArrayList<Student> students = m.getStudentsList();

			for (Student s: students) {
				if (s.getFirstName().get().equals(username) && s.getID().get().equals(password)) {
					m.loggedInStudent(s);
					return true;
				}
			}

			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(s);
			alert.setTitle("Incorrect Fields");
			alert.setHeaderText("Username or Password are Invalid");
			alert.showAndWait();

			return false;
		}
		return false;
	}

    /**
     * 
     * @return True if the input has valid fields to login with
     */
	public boolean isInputValid() {
		String errorMessage = "";

		if(usernameField.getText() == null || usernameField.getText().length() == 0) {
			errorMessage += "No valid username!\n";
		}

		if(passwordField.getText() == null || passwordField.getText().length() == 0) {
			errorMessage += "No valid password!\n";
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
