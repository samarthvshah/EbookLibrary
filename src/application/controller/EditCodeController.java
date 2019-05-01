package application.controller;

import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import application.Main;
import application.model.RedemptionCode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditCodeController {
	
	Main m;
	RedemptionCode r;
	Stage s;
	@FXML private TextField codeField;
	JSONParser parser;
	
	/**
	 * 
	 * @param m The main of the program
	 * @param r The redemption code that will be edited
	 * @param s The new window that the edit window will be on
	 * @param p The parser used to read and change the json file
	 * @return
	 */
	public boolean setFields(Main m, RedemptionCode r, Stage s, JSONParser p) {
		this.m = m;
		this.r = r;
		this.s = s;
		parser = p;
		
		if (r == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(s);
			alert.setTitle("No Code Chosen");
			alert.setHeaderText("Please choose a code");
			alert.setContentText("No Code Chosen");

			alert.showAndWait();
			s.close();
			return false;
		}
		
		codeField.setText(r.getCode().get());

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

    /** Updates the fields of the code when the ok button is pressed
     * 
     * @param event the event where the ok button is pressed
     */
    @FXML
    void handleOk(ActionEvent event) {
    	if (isInputValid()) {
    		if (r.changeCode(codeField.getText(), m.getCodesList())) {
    			
    			try {
    				
    			int index = -1;
    			
    			for (int i = 0; i < m.getCodesList().size(); i++) {
    				if (r.getCode() == m.getCodesList().get(i).getCode()) {
    					index = i;
    				}
    			}
    			
    	        JSONObject object = (JSONObject)parser.parse(new FileReader("res/codes.json"));
    	    	JSONArray codesa = (JSONArray) object.get("codes");

    	    	
    	    	JSONObject code = (JSONObject)codesa.get(index);
    	    	code.remove("c");
    	    	code.put("c", r.getCode().get());
    	    	
    	    	try (FileWriter file = new FileWriter("res/codes.json")) {
    				file.write(object.toJSONString());
    			}
    	    	

    	    	
    			} catch(Exception e) {
    				e.printStackTrace();

    			}
    			
    			
    			s.close();
    		} else {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.initOwner(s);
    			alert.setTitle("Invalid Fields");
    			alert.setHeaderText("Please correct invalid fields");
    			alert.setContentText("Invalid Code Entered");

    			alert.showAndWait();
    		}
    	}
    		
    	
    }

    /**
     * 
     * @return True if the input has a valid field to change the code to
     */
    public boolean isInputValid() {
		String errorMessage = "";


		if(codeField.getText() == null || codeField.getText().length() == 0)
			errorMessage += "Code invalid!\n";
	

		
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
