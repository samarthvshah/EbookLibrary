package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StudentReturnBookBarController {

	StudentManageBooksViewController smbvc;
	
	/** Sets the page of the controller
	 * 
	 * @param m The page of the program where student can return books
	 */
	public void setMain(StudentManageBooksViewController m) {
		smbvc = m;
	}
 	
	/** Handles what to do when the return button is pressed
	 * 
	 * @param event the event when the return button is pressed
	 */
    @FXML
    void handleReturn(ActionEvent event) {
    	smbvc.handleReturn();
    }
}

