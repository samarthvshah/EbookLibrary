package application.controller;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import application.Main;
import application.model.Book;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class StudentManageBooksViewController {
	StudentSceneController ssc;
	BorderPane pane;
	BooksListViewController bvc;
	ObservableList<Book> booksList;
	HBox editBox;
	
	/** Creates a new window with the parameters for fields, with a table and a return button
	 * 
	 * @param m The main of the progam
	 * @param bl The list of books checked out to put into the tabke
	 */
	public StudentManageBooksViewController(StudentSceneController m, ObservableList<Book> bl) {
		ssc = m;
		booksList = bl;		
		
		pane = new BorderPane();
		bvc = new BooksListViewController(bl);
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/StudentReturnBookBar.fxml"));
		try {

			editBox = loader.load();
			pane.setBottom(editBox);
			
			StudentReturnBookBarController controller = loader.getController();
			controller.setMain(this);

			} catch (IOException e) {
				e.printStackTrace();
			}
	
		pane.setCenter(bvc.getTable());
	}
	
	/** Handles when the return button is pressed by returning the selected book
	 * 
	 */
	@FXML
	void handleReturn() {
		Book b = bvc.getTable().getSelectionModel().getSelectedItem();
		
		if (b == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(ssc.getMain().getStage());
			alert.setTitle("Return Book");
			alert.setHeaderText("Please choose a Book");
			alert.setContentText("No Book Chosen");
			alert.showAndWait();
		} else {
			if (ssc.getStudent().returnBook(b)) {
				
				try {
					JSONObject jsonObject = (JSONObject)ssc.getMain().getParser().parse(new FileReader("res/books.json"));
					JSONArray booksa = (JSONArray) jsonObject.get("books");
					
					for (int i = 0; i < ssc.getMain().getBooksList().size(); i++) {
						if (ssc.getMain().getBooksList().get(i).getId().get() == b.getId().get()) {
							JSONObject book = (JSONObject)booksa.get(i);
							book.put("c", false);
							book.put("s", null);
							book.put("d", null);	
						}
					}
					
					try (FileWriter file = new FileWriter("res/books.json")) {
						file.write(jsonObject.toJSONString());
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(ssc.getMain().getStage());
				alert.setTitle("Return Book");
				alert.setHeaderText("Book Returned");
				alert.setContentText("The Book " + b.getTitle().get() + " was returned");
				alert.showAndWait();
				
				ssc.switchToManage();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(ssc.getMain().getStage());
				alert.setTitle("Check Out Book");
				alert.setHeaderText("Book Not Checked Out");
				alert.setContentText("The Book " + b.getTitle().get() + " was not returned");
				alert.showAndWait();
			}
		}
	}
	
	/**
	 * 
	 * @return the pane with the page on it
	 */
	public BorderPane getPane() {
		return pane;
	}

}
