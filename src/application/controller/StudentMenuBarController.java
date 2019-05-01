package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class StudentMenuBarController {
	
	StudentSceneController main;
	
	/**
	 * 
	 * @param m The student window to add the menu bar to
	 */
	public void setMain(StudentSceneController m) {
		main = m;
	}

	/** handles the change to the explore page when that button is pressed
	 * 
	 * @param event the event where the explore button is pressed
	 */
    @FXML
    void handleExplore(ActionEvent event) {
    	main.switchToExplore();
    }

	/** handles the change to the manage page when that button is pressed
	 * 
	 * @param event the event where the manage button is pressed
	 */
    @FXML
    void handleManage(ActionEvent event) {
    	main.switchToManage();
    }

	/** handles the change to the redeem page when that button is pressed
	 * 
	 * @param event the event where the redeem button is pressed
	 */
    @FXML
    void handleRedeemCode(ActionEvent event) {
    	main.switchToRedeem();
    }
    
    /** Shows the help page when the help button is pressed
     * 
	 * @param event the event where the help button is pressed
     */
    @FXML
    void handleHelp(ActionEvent event) {
    	String s = "Student:\n" + 
    			"\n" + 
    			"Once logged in as a student, you have the ability to check out and return books, as well as redeem redemption codes for class books. Note that a student can only have up to 2 books checked out concurrently, including class books. \n" + 
    			"\n" + 
    			"To check out a books, click on the book you want to check out in the table, then click the check out button on the bottom. Then you will get a message either confirming checkout or saying that the checkout is invalid. The checkout could be invalid for a number of reasons, including that the book is already checked out or the student already has 2 books checked out.\n" + 
    			"\n" + 
    			" A student can also return books they have checked out in the same manner: selecting the book to return and clicking the return button at the bottom the window. If the book is due, it will automatically be returned. \n" + 
    			"\n" + 
    			"To redeem a redemption code for a book that is related to a classroom, get the code for your teacher. Then enter that code (case-sensitive) into the field and click redeem. The same confirmation will appear as for checking out books normally.\n" + 
    			"";
    	main.showHelp(s);
    }
    
	/** handles the log out when that button is pressed
	 * 
	 * @param event the event where the log out button is pressed
	 */
    @FXML
    void handleLogOut(ActionEvent event) {
    	main.getMain().loggedOut();
    }
    

}

