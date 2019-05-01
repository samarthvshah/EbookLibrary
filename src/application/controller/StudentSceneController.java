package application.controller;

import java.io.IOException;

import application.Main;
import application.model.Book;
import application.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class StudentSceneController {
	private Scene scene;
	private BorderPane pane;
	private Main main;
	private StudentAccessBooksViewController sabvc;
	private StudentManageBooksViewController smbvc;
	private Student student;
	
	/** Creates a new window where a student can perform actions like checking out and returning books
	 * 
	 * @param m The main of the program
	 * @param s The student that logged in
	 */
	public StudentSceneController(Main m, Student s) {
		main = m;
		student = s;
		
		pane = new BorderPane();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/StudentsMenuBar.fxml"));
		try {

		HBox menuBarBox = (HBox) loader.load();
		pane.setTop(menuBarBox);
		
		StudentMenuBarController controller = loader.getController();
		controller.setMain(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		switchToExplore();
		
		scene = new Scene(pane, 1280, 720);
	}
	
	/** Switches the page to show the redeem page
	 * 
	 */
	public void switchToRedeem() {
		main.refreshData();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/RedeemCode.fxml"));
		try {

		BorderPane codePane = (BorderPane) loader.load();
		pane.setCenter(codePane);
		
		RedeemCodeController controller = loader.getController();
		controller.setMain(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** Switches the page to show the manage page
	 * 
	 */
	public void switchToManage() {
		main.refreshData();
		
		ObservableList<Book> booksList = FXCollections.observableArrayList();
		for (Book b: student.getBooksArrayList()) {
			booksList.add(b);
		}
		
		smbvc = new StudentManageBooksViewController(this, booksList);
		
		pane.setCenter(smbvc.getPane());
	}
	
	/** Switches the page to show the explore page
	 * 
	 */
	public void switchToExplore() {
		main.refreshData();
		
		ObservableList<Book> booksList = FXCollections.observableArrayList();
		for (Book b: main.getBooksList()) {
			booksList.add(b);
		}
		
		sabvc = new StudentAccessBooksViewController(this, booksList);
		
		pane.setCenter(sabvc.getPane());
	}
	
	/**
	 * 
	 * @param s The string to show on the help window
	 */
	public void showHelp(String s) {
		main.showHelpWindow(s);
	}
	
	/**
	 * 
	 * @return The scene with the different pages in it
	 */
	public Scene getScene() {
		return scene;
	}
	
	/**
	 * 
	 * @return The main of the program
	 */
	public Main getMain() {
		return main;
	}
	
	/**
	 * 
	 * @return The student in control of the program
	 */
	public Student getStudent() {
		return student;
	}
}
