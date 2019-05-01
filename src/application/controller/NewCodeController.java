package application.controller;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import application.Main;
import application.model.ClassBook;
import application.model.RedemptionCode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewCodeController {
	
	Main m;
	Stage s;
	@FXML private TextField codeField;
	@FXML private TextField bookField;
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

    /** Creates a new redemption code with the fields entered
     * 
     * @param event when the ok button is pressed
     */
    @FXML
    void handleOk(ActionEvent event) {
    	if (isInputValid()) {
    		int id = Integer.parseInt(bookField.getText());
    		ArrayList<ClassBook> books = m.getClassBooksList();

    		for (ClassBook book: books) {    			
    			if (book.getId().get() == id) {
    	    		ArrayList<RedemptionCode> codes = m.getCodesList();
    				
    	    		codes.add(new RedemptionCode(codeField.getText(), book));
    	    		    	    		
    	    		try {
    	    		JSONObject object = (JSONObject)parser.parse(new FileReader("res/codes.json"));
        	    	JSONArray codesa = (JSONArray) object.get("codes");
        	    	JSONObject newCode = new JSONObject();
        	    	newCode.put("b", id);
        	    	newCode.put("c", codeField.getText());
        	    	
        	    	codesa.add(newCode);
        	    	
        	    	try (FileWriter file = new FileWriter("res/codes.json")) {
        				file.write(object.toJSONString());
        			}
        	    	
    	    		} catch(Exception e) {
        				e.printStackTrace();

        			}
    			}
    		}	
    	}
    	s.close();	   	
    }
	
    /**
     * 
     * @return true of the fields entered can be used to make a valid redemption code
     */
    public boolean isInputValid() {
		String errorMessage = "";

		if(codeField.getText() == null || codeField.getText().length() == 0)
			errorMessage += "No valid code!\n";
		
		if(bookField.getText() == null || bookField.getText().length() == 0) {
			errorMessage += "No valid book id!\n";
		} else{
			try{
				Integer.parseInt(bookField.getText());
			} catch (NumberFormatException e){
				errorMessage += "No valid book id!\n";
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
