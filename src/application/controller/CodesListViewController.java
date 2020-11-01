package application.controller;

import java.io.IOException;

import application.Main;
import application.model.RedemptionCode;
import application.model.Student;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class CodesListViewController {

	BorderPane pane;
	TableView<RedemptionCode> table;
	TableColumn<RedemptionCode, String> codeCol;;
	TableColumn<RedemptionCode, String> bookCol;
	TableColumn<RedemptionCode, Boolean> inUseCol;
	TableColumn<RedemptionCode, String> studentCol;
	ChoiceBox<String> filterBox;
	TextField search;
	HBox searchBox;
	HBox editBox;
	Main main;

	/** Creates a new pane with a table of redemption codes and a search bar
	 * 
	 * @param m The main to set for a field
	 * @param RedemptionCodesList The list of redemption codes to put into the pane
	 */
	public CodesListViewController(Main m, ObservableList<RedemptionCode> RedemptionCodesList) {

		main = m;
		pane = new BorderPane();

		FilteredList<RedemptionCode> filCodes = new FilteredList<RedemptionCode>(RedemptionCodesList, p -> true);

		filterBox = new ChoiceBox<String>();
		filterBox.getItems().addAll("Code", "Book Title");
		filterBox.setValue("Code");

		search = new TextField();
		search.setPromptText("Filter Here");
		search.setOnKeyReleased(keyEvent -> {
			if (filterBox.getValue().equals("Code")) {
				filCodes.setPredicate(p -> p.getCode().get().contains(search.getText().trim()));
			} else if (filterBox.getValue().equals("Book Title")) {
				filCodes.setPredicate(p -> p.getBook().getTitle().get().contains(search.getText().trim()));
			}

		});

		filterBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal != null) {
				search.setText("");
				filCodes.setPredicate(null);
			}
		});

		searchBox = new HBox(search, filterBox);
		searchBox.setAlignment(Pos.CENTER);

		table = new TableView<RedemptionCode>();
		codeCol = new TableColumn<RedemptionCode, String>("Code");
		bookCol = new TableColumn<RedemptionCode, String>("Book");
		inUseCol = new TableColumn<RedemptionCode, Boolean>("Is In Use");
		studentCol = new TableColumn<RedemptionCode, String>("Student");			

		codeCol.setCellValueFactory(cellData -> cellData.getValue().getCode());
		bookCol.setCellValueFactory(cellData -> cellData.getValue().getBook().getTitle());
		inUseCol.setCellValueFactory(cellData -> cellData.getValue().getInUse());
		studentCol.setCellValueFactory(cellData -> cellData.getValue().getStudentString());
		
		inUseCol.setMinWidth(100);
//		inUseCol.setResizable(false);

		table.setItems(filCodes);
		table.getColumns().addAll(codeCol, bookCol, inUseCol, studentCol);

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/ChangeBarCodes.fxml"));
		try {
			editBox = (HBox) loader.load();
			ChangeBarCodesController controller = loader.getController();
			controller.setParents(main, this);

		} catch (IOException e) {
			e.printStackTrace();
		}

		pane.setTop(searchBox);
		pane.setCenter(table);
		pane.setBottom(editBox);
	}

	/**
	 * 
	 * @return The pane of the codes page
	 */
	public BorderPane getPane() {
		return pane;
	}

	/**
	 * 
	 * @return Returns the code that is selected on the table
	 */
	public RedemptionCode getSelectedCode() {
		return table.getSelectionModel().getSelectedItem();
	}


}
