package application.controller;

import java.io.IOException;
import java.util.ArrayList;

import application.Main;
import application.model.Book;
import application.model.ClassBook;
import application.model.RedemptionCode;
import application.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ManagementSceneController {
	private Scene scene;
	private BorderPane pane;
	private Main main;
	private BooksViewController bvc;
	private StudentsListViewController svc;
	private CodesListViewController cvc;

	/** Creates a new window with the different pages already created
	 * 
	 * @param m The main of the program
	 * @param books The list of normal books in the database
	 * @param classBooks The list of classbooks in the database
	 * @param students The list of students in the database
	 * @param codes The list of redemption codes in the database
	 */
	public ManagementSceneController(Main m, ArrayList<Book> books, ArrayList<ClassBook> classBooks, ArrayList<Student> students, ArrayList<RedemptionCode> codes) {
		main = m;
		pane = new BorderPane();

		refreshControllers(books, classBooks, students, codes);

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/MenuBar.fxml"));
		try {

			HBox menuBarBox = (HBox) loader.load();
			pane.setTop(menuBarBox);

		} catch (IOException e) {
			e.printStackTrace();
		}

		MenuBar menuBar = loader.getController();
		menuBar.setMain(this);

		switchToBooks();

		scene = new Scene(pane, 1280, 720);
	}

	/** Refreshes the controller with new data
	 * 
	 * @param books The list of normal books in the database
	 * @param classBooks The list of classbooks in the database
	 * @param students The list of students in the database
	 * @param codes The list of redemption codes in the database
	 */
	public void refreshControllers(ArrayList<Book> books, ArrayList<ClassBook> classBooks, ArrayList<Student> students, ArrayList<RedemptionCode> codes) {
		ObservableList<Book> booksList = FXCollections.observableArrayList();
		ObservableList<Student> studentsList = FXCollections.observableArrayList();
		ObservableList<RedemptionCode> codesList = FXCollections.observableArrayList();
		ObservableList<ClassBook> classBooksList = FXCollections.observableArrayList();   

		for (Book b: books) {
			booksList.add(b);
		}
		for (Student s: students) {
			studentsList.add(s);
		}
		for (RedemptionCode c: codes) {
			codesList.add(c);
		}
		for (ClassBook b: classBooks) {
			classBooksList.add(b);
		}

		bvc = new BooksViewController(booksList,classBooksList); 
		cvc = new CodesListViewController(main, codesList); 
		svc = new StudentsListViewController(main, studentsList); 
	}

	/**
	 * 
	 * @return The window of the management scene
	 */
	public Scene getScene() {
		return scene;
	}

	/** Tells the main to open the weekly report
	 *  
	 */
	public void showReport() {
		main.showReport();
	}
	
	/** Tells the main to show the help page
	 * 
	 * @param s The string that has the help
	 */
	public void showHelp(String s) {
		main.showHelpWindow(s);
	}

	/** Switches the shown page to the students page
	 * 
	 */
	public void switchToStudents() {
		main.refreshData();

		pane.setCenter(null);
		pane.setBottom(null);
		pane.setCenter(svc.getPane());
	}

	/** Switches the shown page to the books page
	 * 
	 */
	public void switchToBooks() {
		main.refreshData();

		pane.setCenter(null);
		pane.setBottom(null);
		pane.setCenter(bvc.getPane());
	}

	/** Switches the shown page to the codes page
	 * 
	 */
	public void switchToCodes() {
		main.refreshData();

		pane.setCenter(null);
		pane.setBottom(null);
		pane.setCenter(cvc.getPane());
	}

	/**
	 * 
	 * @return The main of the program
	 */
	public Main getMain() {
		return main;
	}

}
