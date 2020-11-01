package application.controller;

import application.model.ClassBook;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ClassBooksListViewController {

	TableView<ClassBook> table;
	TableColumn<ClassBook, String> idCol;;
	TableColumn<ClassBook, String> titleCol;;
	TableColumn<ClassBook, String> authorCol;
	TableColumn<ClassBook, Boolean> isCheckedOutCol;
	TableColumn<ClassBook, String> dateDueCol;			
	TableColumn<ClassBook, String> checkedOutByCol;
	TableColumn<ClassBook, String> codeCol;	

	/** Creates a new table with the classbooks values inserted
	 * 
	 * @param booksList The list of classbooks to put into the table
	 */
	public ClassBooksListViewController(ObservableList<ClassBook> ClassBooksList) {
		table = new TableView<ClassBook>();
		idCol = new TableColumn<ClassBook, String>("ID");
		titleCol = new TableColumn<ClassBook, String>("Title");
		authorCol = new TableColumn<ClassBook, String>("Author");
		isCheckedOutCol = new TableColumn<ClassBook, Boolean>("Available");
		dateDueCol = new TableColumn<ClassBook, String>("Date Due");			
		checkedOutByCol = new TableColumn<ClassBook, String>("Checked Out By");	
		codeCol = new TableColumn<ClassBook, String>("Code");
		
		isCheckedOutCol.setMinWidth(100);
		checkedOutByCol.setMinWidth(150);
		dateDueCol.setMinWidth(100);

		idCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().get() + ""));
		titleCol.setCellValueFactory(cellData -> cellData.getValue().getTitle());
		authorCol.setCellValueFactory(cellData -> cellData.getValue().getAuthor());
		isCheckedOutCol.setCellValueFactory(cellData -> cellData.getValue().getCheckedOut());
		dateDueCol.setCellValueFactory(cellData -> cellData.getValue().getDateCheckedOut());
		checkedOutByCol.setCellValueFactory(cellData -> cellData.getValue().getStudentCheckedOut());
		codeCol.setCellValueFactory(cellData -> cellData.getValue().getCodeString());

		table.setItems(ClassBooksList);
		table.getColumns().addAll(idCol, titleCol, authorCol, isCheckedOutCol, checkedOutByCol, dateDueCol, codeCol);
	}

	/**
	 * 
	 * @return Returns the table with the data inside
	 */
	public TableView<ClassBook> getTable() {
		return table;
	}
}
