package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MenuBar {
	
	ManagementSceneController main;
	
	/** Sets the main of the program
	 * 
	 * @param m The main to set
	 */
	public void setMain(ManagementSceneController m) {
		main = m;
	}

	/** Handles when the books button is pressed
	 * 
	 * @param event the event where the books button is pressed
	 */
    @FXML
    void handleBooks(ActionEvent event) {
    	main.switchToBooks();
    }

	/** Handles when the codes button is pressed
	 * 
	 * @param event the event where the codes button is pressed
	 */
    @FXML
    void handleCodes(ActionEvent event) {
    	main.switchToCodes();
    }
    
    /** Handles the opening of the help page when the help button is pressed
     * 
	 * @param event the event where the help button is pressed
     */
    @FXML
    void handleHelp(ActionEvent event) {
    	String s = "Once logged in as an administrator, you have the ability to see all the books and their status, all the students and what books they currently have checked out, and all the redemption codes and whether they are in use.\n" + 
    			"\n" + 
    			"To edit a student or redemption code, click on the thing you want to edit in the table, then click on the edit button. This will show a new window where the current values are shown and you can edit them, then click ok to set them.\n" + 
    			"\n" + 
    			"To create a new student or redemption code, click on the new button at the bottom of the respective page. This will prompt a new window where the required fields are shown that need to be filled. For new students, first name, last name, grade, and id need to be filled. For redemption codes the code and the class book that is related to it need to be filled.\n" + 
    			"\n" + 
    			"To see what books are due in the upcoming week, click the Weekly Report button. This will show a list of all the books that are going to be due in the next 7 days.";
    	main.showHelp(s);
    }

	/** Handles when the students button is pressed
	 * 
	 * @param event the event where the students button is pressed
	 */
    @FXML
    void handleStudents(ActionEvent event) {
    	main.switchToStudents();
    }
    
	/** Handles when the weekly report button is pressed
	 * 
	 * @param event the event where the weekly report button is pressed
	 */
    @FXML
    void handleReports(ActionEvent event) {
    	main.showReport();
    }
    
	/** Handles when the log out button is pressed
	 * 
	 * @param event the event where the log out button is pressed
	 */
    @FXML
    void handleLogOut(ActionEvent event) {
    	main.getMain().loggedOut();
    }
    

}

