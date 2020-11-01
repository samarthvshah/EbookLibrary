package application.controller;

import application.model.Book;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class WeeklyBooksListViewController {
	
	TableView<Book> table;
	TableColumn<Book, String> titleCol;;
	TableColumn<Book, String> authorCol;
	TableColumn<Book, Boolean> isCheckedOutCol;
	TableColumn<Book, String> dateDueCol;			
	TableColumn<Book, String> checkedOutByCol;
	TableColumn<Book, String> typeCol;	

	/** Creates a table with all of the books due in the next week
	 * 
	 * @param booksList The list of books to put into the table
	 */
	public WeeklyBooksListViewController(ObservableList<Book> booksList) {
		table = new TableView<Book>();
		titleCol = new TableColumn<Book, String>("Title");
		authorCol = new TableColumn<Book, String>("Author");
		isCheckedOutCol = new TableColumn<Book, Boolean>("Available");
		dateDueCol = new TableColumn<Book, String>("Date Due");			
		checkedOutByCol = new TableColumn<Book, String>("Checked Out By");
		typeCol = new TableColumn<Book, String>("Type");	
		
		isCheckedOutCol.setMinWidth(100);
//		isCheckedOutCol.setResizable(false);
		
		checkedOutByCol.setMinWidth(150);
//		isCheckedOutCol.setResizable(false);
		
		dateDueCol.setMinWidth(100);
//		dateDueCol.setResizable(false);

		titleCol.setCellValueFactory(cellData -> cellData.getValue().getTitle());
		authorCol.setCellValueFactory(cellData -> cellData.getValue().getAuthor());
		isCheckedOutCol.setCellValueFactory(cellData -> cellData.getValue().getCheckedOut());
		dateDueCol.setCellValueFactory(cellData -> cellData.getValue().getDateCheckedOut());
		checkedOutByCol.setCellValueFactory(cellData -> cellData.getValue().getStudentCheckedOut());
		typeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClass().toString().substring(24)));

		table.setItems(booksList);
        table.getColumns().addAll(typeCol, titleCol, authorCol, isCheckedOutCol, checkedOutByCol, dateDueCol);
        
	}
	
	/**
	 * 
	 * @return The table with the data values entered
	 */
	public TableView<Book> getTable() {
		return table;
	}


}
