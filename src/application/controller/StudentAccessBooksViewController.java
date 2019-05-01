package application.controller;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import application.Main;
import application.model.Book;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class StudentAccessBooksViewController {
	StudentSceneController ssc;
	BorderPane pane;
	BooksListViewController bvc;
	ObservableList<Book> booksList;
	ChoiceBox<String> filterBox;
	TextField search;
	CheckBox available;
	HBox searchBox;
	HBox editBox;

	/** Creates a new page where student can see what books are available to check out 
	 * 
	 * @param m The controller of the student window
	 * @param bl The list of books currently in the database
	 */
	public StudentAccessBooksViewController(StudentSceneController m, ObservableList<Book> bl) {
		ssc = m;
		booksList = bl;
		FilteredList<Book> filBooks = new FilteredList<Book>(booksList, p -> true);

		filterBox = new ChoiceBox<String>();
		filterBox.getItems().addAll("Title", "Author");
		filterBox.setValue("Title");

		search = new TextField();
		search.setPromptText("Filter Here");
		search.setOnKeyReleased(keyEvent -> {
			if (filterBox.getValue().equals("Title")) {
				filBooks.setPredicate(p -> p.getTitle().get().contains(search.getText().trim()));
			} else if (filterBox.getValue().equals("Author")) {
				filBooks.setPredicate(p -> p.getAuthor().get().contains(search.getText().trim()));
			}

		});

		available = new CheckBox("Only Available Books");

		available.selectedProperty().addListener(new ChangeListener<Boolean> () {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (newValue) {
					if (filBooks.getPredicate() != null) {
						filBooks.setPredicate(filBooks.getPredicate().and(p -> ((Book) p).getCheckedOut().get()));
					} else {
						filBooks.setPredicate(p -> p.getCheckedOut().get());
					}
				} else {
					filBooks.setPredicate(null);

				}
			}
		});

		filterBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal != null) {
				search.setText("");
				filBooks.setPredicate(null);
			}
		});

		searchBox = new HBox(search, filterBox, available);
		searchBox.setAlignment(Pos.CENTER);
		pane = new BorderPane();
		bvc = new BooksListViewController(filBooks);

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/StudentCheckOutBookBar.fxml"));
		try {

			editBox = loader.load();
			pane.setBottom(editBox);

			StudentCheckOutBookBarController controller = loader.getController();
			controller.setMain(this);

		} catch (IOException e) {
			e.printStackTrace();
		}

		pane.setTop(searchBox);
		pane.setCenter(bvc.getTable());
	}

	/** Handles the check out when the button is pressed
	 * 
	 */
	@FXML
	void handleCheckOut() {
		Book b = bvc.getTable().getSelectionModel().getSelectedItem();
		if (b == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(ssc.getMain().getStage());
			alert.setTitle("Check Out Book");
			alert.setHeaderText("Please choose a Book");
			alert.setContentText("No Book Chosen");
			alert.showAndWait();
		} else {
			if (ssc.getStudent().checkOutBook(b)) {

				try {
					JSONObject jsonObject = (JSONObject)ssc.getMain().getParser().parse(new FileReader("res/books.json"));
					JSONArray booksa = (JSONArray) jsonObject.get("books");


					for (int i = 0; i < ssc.getMain().getBooksList().size(); i++) {
						if (ssc.getMain().getBooksList().get(i).getId().get() == b.getId().get()) {
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

					try (FileWriter file = new FileWriter("res/books.json")) {
						file.write(jsonObject.toJSONString());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.initOwner(ssc.getMain().getStage());
				alert.setTitle("Check Out Book");
				alert.setHeaderText("Book Checked Out");
				alert.setContentText("The Book " + b.getTitle().get() + " was checked out");
				alert.showAndWait();

				ssc.switchToExplore();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(ssc.getMain().getStage());
				alert.setTitle("Check Out Book");
				alert.setHeaderText("Book Not Checked Out");
				alert.setContentText("The Book " + b.getTitle().get() + " was not checked out");
				alert.showAndWait();
			}
		}
	}

	/**
	 * 
	 * @return The page with the table and search bar and button on it
	 */
	public BorderPane getPane() {
		return pane;
	}

}
