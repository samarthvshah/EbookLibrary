package application.controller;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Optional;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import application.Main;
import application.model.RedemptionCode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

public class ChangeBarCodesController {

	Main main;
	CodesListViewController cvc;

	/** Sets the main and the controller of the codes list
	 * 
	 * @param m
	 * @param c
	 */
	public void setParents(Main m, CodesListViewController c) {
		main = m;
		cvc = c;
	}

	/**
	 * 
	 * @param event The event that results in the code handling that the code should be edited
	 */
	@FXML
	void handleEditCode(ActionEvent event) {
		RedemptionCode r = cvc.getSelectedCode();
		main.showEditCodeWindow(r);
	}

	/**
	 * 
	 * @param event The event that results in the code handling that a new code should be created
	 */
	@FXML
	void handleNewCode(ActionEvent event) {
		main.showNewCodeWindow();
	}

	/**
	 * 
	 * @param event The event that results in the code handling that a code should be removed
	 */
	@FXML
	void handleRemoveCode(ActionEvent event) {
		RedemptionCode r = cvc.getSelectedCode();

		if (r == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(main.getStage());
			alert.setTitle("No Code Chosen");
			alert.setHeaderText("Please choose a code");
			alert.setContentText("No Code Chosen");
			alert.showAndWait();

		} else if (r.getInUse().get()) {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(main.getStage());
			alert.setTitle("Code is In Use");
			alert.setHeaderText("The code is currently in use");
			alert.showAndWait();
		} else {
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Remove Redemption Code");
			alert.setHeaderText("Are you sure you want to remove this code?");
			ButtonType buttonYes = new ButtonType("Yes");
			ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
			alert.getButtonTypes().setAll(buttonYes, buttonTypeCancel);
			Optional<ButtonType> result = alert.showAndWait();
			
			if (result.get() == buttonYes){
				
				try {
					JSONParser parser = main.getParser();
					JSONObject object = (JSONObject)parser.parse(new FileReader("res/codes.json"));
					JSONArray codesa = (JSONArray) object.get("codes");

					for (int i = 0; i < main.getCodesList().size(); i++) {
						if (main.getCodesList().get(i).getCode().equals(r.getCode())) {
							codesa.remove(i);

							try (FileWriter file = new FileWriter("res/codes.json")) {
								file.write(object.toJSONString());
							}
						}
					}
					main.getController().switchToCodes();

				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}

