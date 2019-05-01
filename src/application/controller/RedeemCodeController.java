package application.controller;

import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import application.Main;
import application.model.ClassBook;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class RedeemCodeController {
	
	private Main main;
	private StudentSceneController ssc;
	@FXML private TextField codeField;
	
	/** Sets the fields of the redeem page
	 * 
	 * @param s the student scene controller that page is controlled by
	 */
	public void setMain(StudentSceneController s) {
		ssc = s;
		main = ssc.getMain();
		codeField.setText("");
	}
	
	/** Handles what to do when the redeem button is pressed
	 * 
	 */
	@FXML
	void handleRedeem() {
		if (isInputValid()) {
			if (ssc.getStudent().redeemCode(codeField.getText(), main.getCodesList())) {
				ClassBook b = null;
				
				try {
					JSONObject jsonObject = (JSONObject)ssc.getMain().getParser().parse(new FileReader("res/classbooks.json"));
					JSONArray booksa = (JSONArray) jsonObject.get("classbooks");

					
					for (int i = 0; i < ssc.getMain().getClassBooksList().size(); i++) {
						if (main.getClassBooksList().get(i).getCode().getCode().get().equals(codeField.getText())) {
							b = main.getClassBooksList().get(i);
							JSONObject book = (JSONObject)booksa.get(i);
							book.put("c", true);
							book.put("s", ssc.getStudent().getID().get());
							JSONArray date = new JSONArray();
							date.add(b.getDate().getYear());
							date.add(b.getDate().getMonthValue());
							date.add(b.getDate().getDayOfMonth());
							book.put("d", date);	
						}
					}
					
					try (FileWriter file = new FileWriter("res/classbooks.json")) {
						file.write(jsonObject.toJSONString());
					}
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.initOwner(ssc.getMain().getStage());
					alert.setTitle("Redeem Code");
					alert.setHeaderText("Book Checked Out");
					alert.setContentText("The Book " + b.getTitle().get() + " was checked out");
					alert.showAndWait();
					
					ssc.switchToRedeem();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(ssc.getMain().getStage());
				alert.setTitle("Redeem Code");
				alert.setHeaderText("Book Not Checked Out");
				alert.setContentText("Code not found");
				alert.showAndWait();
			}
		}
	}
	
	/**
	 * 
	 * @return Checks to make sure that the code entered is valid
	 */
	public boolean isInputValid() {
		String errorMessage = "";
		
		if(codeField.getText() == null || codeField.getText().length() == 0)
			errorMessage += "No Code Entered!\n";
	
		//if there is no error message, returns true
		if (errorMessage.length() == 0) {
			return true;
		} else { //if there is an error message, create alert with error messages printed
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(main.getStage());
			alert.setTitle("Invalid Fields");
			alert.setHeaderText("Please correct invalid fields");
			alert.setContentText(errorMessage);
			alert.showAndWait();

			return false;
		}
    }

}
