package application.controller;

import application.model.Book;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.ResizeFeatures;
import javafx.util.Callback;

public class BooksListViewController {

	TableView<Book> table;
	TableColumn<Book, String> idCol;;
	TableColumn<Book, String> titleCol;;
	TableColumn<Book, String> authorCol;
	TableColumn<Book, Boolean> isCheckedOutCol;
	TableColumn<Book, String> dateDueCol;			
	TableColumn<Book, String> checkedOutByCol;	

	/** Creates a new table with the books values inserted
	 * 
	 * @param booksList The list of books to put into the table
	 */
	public BooksListViewController(ObservableList<Book> booksList) {
		table = new TableView<Book>();
		
		table.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>() {
			  @Override
			  public Boolean call(ResizeFeatures p) {
			     return true;
			  }
			});
		
		idCol = new TableColumn<Book, String>("ID");
		titleCol = new TableColumn<Book, String>("Title");
		authorCol = new TableColumn<Book, String>("Author");
		isCheckedOutCol = new TableColumn<Book, Boolean>("Available");
		dateDueCol = new TableColumn<Book, String>("Date Due");			
		checkedOutByCol = new TableColumn<Book, String>("Checked Out By");	

		idCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().get() + ""));
		titleCol.setCellValueFactory(cellData -> cellData.getValue().getTitle());
		authorCol.setCellValueFactory(cellData -> cellData.getValue().getAuthor());
		isCheckedOutCol.setCellValueFactory(cellData -> cellData.getValue().getCheckedOut());
		dateDueCol.setCellValueFactory(cellData -> cellData.getValue().getDateCheckedOut());
		checkedOutByCol.setCellValueFactory(cellData -> cellData.getValue().getStudentCheckedOut());

		table.setItems(booksList);
		table.getColumns().addAll(idCol, titleCol, authorCol, isCheckedOutCol, checkedOutByCol, dateDueCol);

	}

	/**
	 * 
	 * @return Returns the table with the data inside
	 */
	public TableView<Book> getTable() {
		return table;
	}


}
