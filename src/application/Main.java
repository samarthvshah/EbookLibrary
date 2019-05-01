package application;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

import application.controller.EditCodeController;
import application.controller.EditStudentController;
import application.controller.LoginSceneController;
import application.controller.ManagementSceneController;
import application.controller.NewStudentController;
import application.controller.StudentSceneController;
import application.controller.NewCodeController;
import application.controller.WeeklyBooksListViewController;
import application.model.Book;
import application.model.ClassBook;
import application.model.RedemptionCode;
import application.model.Student;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class Main extends Application {
	private Stage primaryStage;
	private ArrayList<Book> books = new ArrayList<Book>();
	private ArrayList<Student> students = new ArrayList<Student>();
	private ArrayList<RedemptionCode> codes = new ArrayList<RedemptionCode>();
	private ArrayList<ClassBook> classBooks = new ArrayList<ClassBook>();

	private JSONParser parser;
	private ManagementSceneController manageScene;
	private LoginSceneController loginController;
	private StudentSceneController ssc;


	/** What the program does when it is loaded up for the first time
	 * 
	 * Sets the window, initializes the JSONParser, screen controllers, and data. then shows the login window
	 * 
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			parser = new JSONParser();

			refreshData();

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/LoginScreen.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Scene scene = new Scene(page);
			
			loginController = loader.getController();
			loginController.setParameters(this, primaryStage);
			
			ssc = new StudentSceneController(this, null);
			manageScene = new ManagementSceneController(this, books, classBooks, students, codes);			
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Switches the screen to the Student window
	 * 
	 * @param s The student that logged in
	 */
	public void loggedInStudent(Student s) {
		ssc = new StudentSceneController(this, s);
		Scene scene = ssc.getScene();
		scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());
		primaryStage.setScene(scene);
	}
	
	/** Switches the screen back to the login window
	 * 
	 */
	public void loggedOut() {
		try {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/LoginScreen.fxml"));
		AnchorPane page = (AnchorPane) loader.load();
		
		loginController = loader.getController();
		loginController.setParameters(this, primaryStage);
		
		Scene scene = new Scene(page);
		
		primaryStage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Switches the screen to the Admin Window
	 * 
	 */
	public void loggedIn() {
		manageScene.switchToBooks();
		Scene scene = manageScene.getScene();
		scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());
		primaryStage.setScene(scene);
	}
	
	/** Opens a new window with the weekly report on it
	 * 
	 * @return true when the method has finished
	 */
	public boolean showReport() {
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Edit Code"); //Sets title to Edit Member
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(primaryStage);
		
		ObservableList<Book> booksList = FXCollections.observableArrayList();

        for (Book b: books) {
        	if (b.isDueInWeek()) 
        		booksList.add(b);
        }
        for (ClassBook b: classBooks) {
        	if (b.isDueInWeek()) 
        		booksList.add(b);
        }
        WeeklyBooksListViewController bvc = new WeeklyBooksListViewController(booksList); 
		
		Scene scene = new Scene(bvc.getTable(), 800, 600);
		scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());

		dialogStage.setScene(scene);
		dialogStage.showAndWait();
		return true;
	}
	
	/** Opens a new window with help on it
	 * 
	 * @param s The string with the help on it
	 * @return True if the window is shown successfully
	 */
	public boolean showHelpWindow(String s) {
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Help"); //Sets title to Edit Member
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(primaryStage);
		
		TextArea text = new TextArea(s);
		text.setWrapText(true);
		BorderPane pane = new BorderPane();
		pane.setTop(text);
		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());

		dialogStage.setScene(scene);
		dialogStage.showAndWait();
		
		return true;
	}

	/** Opens a new window where a redemption code can be edited
	 * 
	 * @param c The redemption code that will be edited
	 * @return if the window ran successfully
	 */
	public boolean showEditCodeWindow(RedemptionCode c) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/EditCodesScene.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Code"); //Sets title to Edit Member
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			EditCodeController controller = loader.getController();
			if (controller.setFields(this, c, dialogStage, parser)) {
				dialogStage.showAndWait();
			}

			manageScene.switchToCodes();

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/** Opens a new window where a new redemption code can be added
	 * 
	 * @return if the window ran successfully
	 */
	public boolean showNewCodeWindow() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/NewCodesScene.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("New Code"); //Sets title to Edit Member
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			NewCodeController controller = loader.getController();
			if (controller.setFields(this, dialogStage, parser)) {
				dialogStage.showAndWait();
			}

			manageScene.switchToCodes();

			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/** Opens a new window where a new student code can be added
	 * 
	 * @return if the window ran successfully
	 */
	public boolean showNewStudentWindow() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/NewStudentsScene.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("New Student"); //Sets title to Edit Member
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			NewStudentController controller = loader.getController();
			if (controller.setFields(this, dialogStage, parser)) {
				dialogStage.showAndWait();
			}

			manageScene.switchToStudents();

			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/** Opens a new window where a student can be edited
	 * 
	 * @param s The student that will be edited
	 * @return if the window ran successfully
	 */
	public boolean showEditStudentWindow(Student s) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/EditStudentsScene.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Student"); //Sets title to Edit Member
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			EditStudentController controller = loader.getController();
			
			if (controller.setFields(this, dialogStage, parser, s)) {
				dialogStage.showAndWait();
			}

			manageScene.switchToStudents();

			return true;
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/** Refreshes the data with the local data files, as well as returning books that are overdue, finally refreshing the windows of the admin page
	 * 
	 */
	public void refreshData() {

		try {
			books.clear();
			codes.clear();
			students.clear();
			classBooks.clear();

			JSONObject jsonObject = (JSONObject)parser.parse(new FileReader("res/books.json"));
			JSONObject jsonObject2 = (JSONObject)parser.parse(new FileReader("res/students.json"));
			JSONObject jsonObject3= (JSONObject)parser.parse(new FileReader("res/codes.json"));
			JSONObject jsonObject4= (JSONObject)parser.parse(new FileReader("res/classbooks.json"));

			JSONArray booksa = (JSONArray) jsonObject.get("books");
			JSONArray studentsa = (JSONArray) jsonObject2.get("students");
			JSONArray codesa = (JSONArray) jsonObject3.get("codes");
			JSONArray classbooksa = (JSONArray) jsonObject4.get("classbooks");

			this.intializeBooks(booksa);
			this.intializeStudents(studentsa);
			this.checkOutBooks(booksa);
			this.intializeClassBooks(classbooksa);
			this.initializeCodes(codesa);
			this.checkOutClassBooks(classbooksa);
			
			returnBooks();

			if (manageScene != null) {
				manageScene.refreshControllers(books, classBooks, students, codes);
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/** Returns all of the books that are overdue in the database, updates the json files
	 * 
	 */
	public void returnBooks() {
		
		try {
			JSONObject jsonObject = (JSONObject)parser.parse(new FileReader("res/books.json"));
			JSONObject jsonObject4= (JSONObject)parser.parse(new FileReader("res/classbooks.json"));			

			JSONArray booksa = (JSONArray) jsonObject.get("books");
			JSONArray classbooksa = (JSONArray) jsonObject4.get("classbooks");
			
			for (int i = 0; i < books.size(); i++) {
				if (books.get(i).isLate()) {
					books.get(i).getStudentCheckedOutObject().returnBook(books.get(i));
					JSONObject book = (JSONObject)booksa.get(i);
					book.put("c", false);
					book.put("s", null);
					book.put("d", null);	
					
				}
			}
			for (int i = 0; i < classBooks.size(); i++) {
				if (classBooks.get(i).isLate()) {
					classBooks.get(i).getStudentCheckedOutObject().returnBook(classBooks.get(i));
					JSONObject book = (JSONObject)classbooksa.get(i);
					book.put("c", false);
					book.put("s", null);
					book.put("d", null);	
				}
			}
			
	    	try (FileWriter file = new FileWriter("res/books.json")) {
				file.write(jsonObject.toJSONString());
			}
	    	try (FileWriter file = new FileWriter("res/classbooks.json")) {
				file.write(jsonObject4.toJSONString());
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/** Used to check out books that are already checked out in the database set up
	 * 
	 * @param books The array of books in the JSON file of normal books
	 */
	private void checkOutBooks(JSONArray books) {

		for (int i = 0; i < books.size(); i++) {
			JSONObject book = (JSONObject) books.get(i);

			if ((boolean)book.get("c")) {
				String id = (String)book.get("s");

				for (int j = 0; j < students.size(); j++) {
					Student s = students.get(j);

					if (s.getID().get().equals(id)) {
						JSONArray date = (JSONArray) book.get("d");

						LocalDate d = LocalDate.of((int)((long)date.get(0)), (int)((long)date.get(1)), (int)((long)date.get(2)) );

						s.checkOutBook(this.books.get(i), d);
					}
				}
			}
		}
	}

	/** Used to check out classbooks that are already checked out in the database set up
	 * 
	 * @param books The array of classbooks in the JSON file of normal books
	 */
	private void checkOutClassBooks(JSONArray classbooks) {

		for (int i = 0; i < classbooks.size(); i++) {
			JSONObject book = (JSONObject) classbooks.get(i);

			if ((boolean)book.get("c")) {
				String id = (String)book.get("s");
				ClassBook b = this.classBooks.get(i);

				for (int j = 0; j < students.size(); j++) {
					Student s = students.get(j);

					if (s.getID().get().equals(id)) {
						JSONArray date = (JSONArray) book.get("d");

						LocalDate d = LocalDate.of((int)((long)date.get(0)), (int)((long)date.get(1)), (int)((long)date.get(2)) );

						s.redeemCode(b.getCode().getCode().get(), codes,  d);
					}
				}
			}
		}
	}

	/** Initializes the book objects based on the books in the JSON files and adds them to the arraylist of books
	 * 
	 * @param books The array of books in the JSON file of normal books
	 */
	private void intializeBooks(JSONArray books) {

		for (int i = 0; i < books.size(); i++) {
			JSONObject book = (JSONObject) books.get(i);

			int id = (int)(long)book.get("id");
			String t = (String)book.get("t");
			String a = (String)book.get("a");
			int isbn = (int)(long)book.get("i");

			this.books.add(new Book(id, t , a, isbn, false, null, null));
		}
	}

	/** Initializes the classbook objects based on the classbooks in the JSON files and adds them to the arraylist of classbooks
	 * 
	 * @param books The array of classbooks in the JSON file of classbooks
	 */
	private void intializeClassBooks(JSONArray books) {

		for (int i = 0; i < books.size(); i++) {
			JSONObject book = (JSONObject) books.get(i);

			int id = (int)(long)book.get("id");
			String t = (String)book.get("t");
			String a = (String)book.get("a");
			int isbn = (int)(long)book.get("i");

			this.classBooks.add(new ClassBook(id, t , a, isbn, false, null, null));
		}
	}

	/** Initializes the redemption code objects based on the codes in the JSON files and adds them to the arraylist of redemption codes
	 * 
	 * @param codes The array of redemption code in the JSON file of codes
	 */
	private void initializeCodes(JSONArray codes) {

		for (int i = 0; i < codes.size(); i++) {
			JSONObject code = (JSONObject) codes.get(i);

			String c = (String)code.get("c");
			int b = (int)(long)code.get("b");

			for (int j = 0; j < classBooks.size(); j++) {
				ClassBook book = classBooks.get(j);
				if (book.getId().get() == b) {
					this.codes.add(new RedemptionCode(c, book));
				}
			}
		}
	}

	/** Initializes the student objects based on the students in the JSON files and adds them to the arraylist of students
	 * 
	 * @param students The array of students in the JSON file of students
	 */
	private void intializeStudents(JSONArray students) {

		for (int i = 0; i < students.size(); i++) {
			JSONObject student = (JSONObject) students.get(i);

			String fn = (String)student.get("fn");
			String ln = (String)student.get("ln");
			String id = (String)student.get("id");
			int g = (int)(long)student.get("g");

			this.students.add(new Student(fn, ln , id, g, null));
		}
	}

	/**
	 * 
	 * @return The arraylist of redemption codes in the program
	 */
	public ArrayList<RedemptionCode> getCodesList() {
		return codes;
	}

	/**
	 * 
	 * @return The arraylist of classbooks in the program
	 */
	public ArrayList<ClassBook> getClassBooksList() {
		return classBooks;
	}
	
	/**
	 * 
	 * @return The arraylist of students in the program
	 */
	public ArrayList<Student> getStudentsList() {
		return students;
	}
	
	/**
	 * 
	 * @return The arraylist of normal books in the program
	 */
	public ArrayList<Book> getBooksList() {
		return books;
	}

	/**
	 * 
	 * @return The JSONParser used to read the JSON files
	 */
	public JSONParser getParser() {
		return parser;
	}
	
	/**
	 * 
	 * @return The primary window of the program
	 */
	public Stage getStage() {
		return primaryStage;
	}
	
	/**
	 * 
	 * @return The controller of the manage window used to show the admin window
	 */
	public ManagementSceneController getController() {
		return manageScene;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
