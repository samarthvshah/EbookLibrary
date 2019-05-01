package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StudentCheckOutBookBarController {

	StudentAccessBooksViewController sabvc;
	
	/** Sets the main of the controller
	 * 
	 * @param m The main of the program
	 */
	public void setMain(StudentAccessBooksViewController m) {
		sabvc = m;
	}
 	
	/** Checks out the book when the button is pressed
	 * 
	 * @param event when the check out button is pressed
	 */
    @FXML
    void handleCheckOut(ActionEvent event) {
    	sabvc.handleCheckOut();
    }
}

