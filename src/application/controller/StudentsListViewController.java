package application.controller;

import java.io.IOException;

import application.Main;
import application.model.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class StudentsListViewController {

	BorderPane pane;
	TableView<Student> table;
	TableColumn<Student, String> fNameCol;
	TableColumn<Student, String> lNameCol;
	TableColumn<Student, String> gradeCol;
	TableColumn<Student, String> idCol;			
	TableColumn<Student, String> bookOut1Col;	
	TableColumn<Student, String> bookOut2Col;	
	ChoiceBox<String> filterBox;
	TextField search;
	HBox searchBox;
	Main main;
	HBox editBox;

	/** Creates a new page where admins can manage students with table, action menu bar, and a search bar
	 * 
	 * @param m
	 * @param StudentsList
	 */
	public StudentsListViewController(Main m, ObservableList<Student> StudentsList) {
		pane = new BorderPane();
		main = m;

		FilteredList<Student> filStudents = new FilteredList<Student>(StudentsList, p -> true);

		filterBox = new ChoiceBox<String>();
		filterBox.getItems().addAll("First Name", "Last Name", "Grade", "ID");
		filterBox.setValue("First Name");

		search = new TextField();
		search.setPromptText("Filter Here");
		search.setOnKeyReleased(keyEvent -> {
			if (filterBox.getValue().equals("First Name")) {
				filStudents.setPredicate(p -> p.getFirstName().get().contains(search.getText().trim()));
			} else if (filterBox.getValue().equals("Last Name")) {
				filStudents.setPredicate(p -> p.getLastName().get().contains(search.getText().trim()));
			} else if (filterBox.getValue().equals("Grade")) {
				filStudents.setPredicate(p -> p.getGrade().toString().contains(search.getText().trim()));
			} else if (filterBox.getValue().equals("ID")) {
				filStudents.setPredicate(p -> p.getID().get().contains(search.getText().trim()));
			}
		});

		filterBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal != null) {
				search.setText("");
				filStudents.setPredicate(null);
			}
		});

		searchBox = new HBox(search, filterBox);
		searchBox.setAlignment(Pos.CENTER);

		table = new TableView<Student>();
		fNameCol = new TableColumn<Student, String>("First Name");
		lNameCol = new TableColumn<Student, String>("Last Name");
		gradeCol = new TableColumn<Student, String>("Grade");
		idCol = new TableColumn<Student, String>("ID");			
		bookOut1Col = new TableColumn<Student, String>("Book 1 Checked Out");
		bookOut2Col = new TableColumn<Student, String>("Book 2 Checked Out");
		
		fNameCol.setMinWidth(125);
		lNameCol.setMinWidth(125);
		gradeCol.setMinWidth(100);
		bookOut1Col.setMinWidth(175);
		bookOut2Col.setMinWidth(175);


		fNameCol.setCellValueFactory(cellData -> cellData.getValue().getFirstName());
		lNameCol.setCellValueFactory(cellData -> cellData.getValue().getLastName());
		gradeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGrade().get() + ""));
		idCol.setCellValueFactory(cellData -> cellData.getValue().getID());
		bookOut1Col.setCellValueFactory(cellData -> cellData.getValue().getBook1());
		bookOut2Col.setCellValueFactory(cellData -> cellData.getValue().getBook2());
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/ChangeBarStudents.fxml"));
		try {

			editBox = (HBox) loader.load();
			ChangeBarStudentsController controller = loader.getController();
			controller.setParents(main, this);

			} catch (IOException e) {
				e.printStackTrace();
			}

		table.setItems(filStudents);
		table.getColumns().addAll(idCol, fNameCol, lNameCol, gradeCol, bookOut1Col, bookOut2Col);
		
		pane.setTop(searchBox);
		pane.setCenter(table);
		pane.setBottom(editBox);
	}

	/**
	 * 
	 * @return The student selected in the table
	 */
	public Student getSelectedStudent() {
		return table.getSelectionModel().getSelectedItem();
	}
	
	/**
	 * 
	 * @return The page with the table, search abr and menu bar
	 */
	public BorderPane getPane() {
		return pane;
	}


}
