package application.controller;

import application.model.Book;
import application.model.ClassBook;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class BooksViewController {
	BorderPane pane;
	BooksListViewController bvc;
	ClassBooksListViewController cbvc;
	ObservableList<Book> booksList;
	ObservableList<ClassBook> classBooksList;
	ChoiceBox<String> filterBox;
	TextField search;
	CheckBox available;
	HBox searchBox;

	/** Creates a pane with two tables, normal books and classbook and a search bar
	 * 
	 * @param bl The list of books to put into the pane
	 * @param cbl The list of classbooks to put into the pane
	 */
	public BooksViewController(ObservableList<Book> bl, ObservableList<ClassBook> cbl) {
		booksList = bl;
		classBooksList = cbl;;
		FilteredList<Book> filBooks = new FilteredList<Book>(booksList, p -> true);
		FilteredList<ClassBook> filClassBooks = new FilteredList<ClassBook>(classBooksList, p -> true);

		filterBox = new ChoiceBox<String>();
		filterBox.getItems().addAll("Title", "Author");
		filterBox.setValue("Title");

		search = new TextField();
		search.setPromptText("Filter Here");
		search.setOnKeyReleased(keyEvent -> {
			if (filterBox.getValue().equals("Title")) {
				filBooks.setPredicate(p -> p.getTitle().get().contains(search.getText().trim()));
				filClassBooks.setPredicate(p -> p.getTitle().get().contains(search.getText().trim()));
			} else if (filterBox.getValue().equals("Author")) {
				filBooks.setPredicate(p -> p.getAuthor().get().contains(search.getText().trim()));
				filClassBooks.setPredicate(p -> p.getAuthor().get().contains(search.getText().trim()));
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
						filClassBooks.setPredicate(filClassBooks.getPredicate().and(p -> ((Book) p).getCheckedOut().get()));
					} else {
						filBooks.setPredicate(p -> p.getCheckedOut().get());
						filClassBooks.setPredicate(p -> p.getCheckedOut().get());
					}
				} else {
					filBooks.setPredicate(null);
					filClassBooks.setPredicate(null);

				}
			}

		});

		filterBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal != null) {
				search.setText("");
				filBooks.setPredicate(null);
				filClassBooks.setPredicate(null);
			}
		});

		searchBox = new HBox(search, filterBox, available);
		searchBox.setAlignment(Pos.CENTER);
		pane = new BorderPane();
		bvc = new BooksListViewController(filBooks);
		cbvc = new ClassBooksListViewController(filClassBooks);

		pane.setTop(searchBox);
		pane.setCenter(bvc.getTable());
		pane.setBottom(cbvc.getTable());
	}

	/**
	 * 
	 * @return The pane with the 2 tables and the search bar
	 */
	public BorderPane getPane() {
		return pane;
	}

}
