package application.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {

	private final IntegerProperty id;
	private final StringProperty title;
	private final StringProperty author;
	private final IntegerProperty isbn;
	private BooleanProperty isCheckedOut;
	private LocalDate dateCheckedOut;
	private Student checkedOutBy;

	/** Creates the book object with the given values as the parameters
	 * 
	 * @param id The ID of the book
	 * @param t The title of the book
	 * @param a The author of the book
	 * @param i The ISBN number of the book
	 * @param c If the book is checked out
	 * @param d When the book is due
	 * @param s Who has checked out the book
	 */
	public Book(int id, String t, String a, int i, boolean c, LocalDate d, Student s) {
		this.id = new SimpleIntegerProperty(id);
		title =  new SimpleStringProperty(t);
		author =  new SimpleStringProperty(a);
		isbn = new SimpleIntegerProperty(i);

		if (c) {
			isCheckedOut = new SimpleBooleanProperty(true);
			dateCheckedOut = d;
			checkedOutBy = s;			
		} else {
			isCheckedOut = new SimpleBooleanProperty(false);
			dateCheckedOut = null;
			checkedOutBy = null;
		}
	} 

	/** Checks out the book with the parameter as the student checking out the book, with the due date in 3 weeks
	 * 
	 * @param s The student checking out the book
	 * @return True if the check out was successful
	 */
	public boolean checkOutBook(Student s) {
		if (!isCheckedOut.get()) {
			isCheckedOut.set(true);

			LocalDate today = LocalDate.now();
			dateCheckedOut = today.plus(3, ChronoUnit.WEEKS);		

			checkedOutBy = s;
			return true;
		} else {
			return false;
		}
	}

	/** Checks out the book with the parameters for the student checking out the book and the date due given
	 * mostly used to initialize books that are already checked out and need to be refreshed
	 * 
	 * @param s The student checking out the book
	 * @param d The return date to be set
	 * @return True if the check out was successful
	 */
	public boolean checkOutBook(Student s, LocalDate d) {
		if (!isCheckedOut.get()) {

			isCheckedOut.set(true);
			dateCheckedOut = d;	
			checkedOutBy = s;

			return true;
		} else {
			return false;
		}
	}

	/** 
	 * @return a string representation of the book with the title, author, if it is checked out, who, and when
	 */
	public String toString() {
		if (isCheckedOut.get()) {
			return title.get() + " by " + author.get() + " is checked out by " + checkedOutBy.getFirstName().get() + " and is due on " + dateCheckedOut.toString();
		} else {
			return title.get() + " by " + author.get() + " is not checked out";
		}
	}

	/** Returns the book by setting the fields related to false or null
	 * 
	 */
	public void returnBook() {
		isCheckedOut.set(false);
		dateCheckedOut = null;
		checkedOutBy = null;
	}

	/**
	 * 
	 * @return If the current date is after the date at which the book is due
	 */
	public boolean isLate() {
		if (isCheckedOut.get()) {
			LocalDate today = LocalDate.now();
			return today.isAfter(dateCheckedOut);
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @return If the due date is in the upcoming week 
	 */
	public boolean isDueInWeek() {
		if (isCheckedOut.get()) {
			LocalDate today = LocalDate.now();
			return today.isAfter(dateCheckedOut.minusWeeks(1));
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @return The ID Of the Book
	 */
	public IntegerProperty getId() {
		return id;
	}

	/**
	 * 
	 * @return The title of the Book
	 */
	public StringProperty getTitle() {
		return title;
	}

	/**
	 * 
	 * @return The author of the book
	 */
	public StringProperty getAuthor() {
		return author;
	}

	/**
	 * 
	 * @return The ISBN number of the book
	 */
	public IntegerProperty getISBN() {
		return isbn;
	}

	/**
	 * 
	 * @return If the book is checked out
	 */
	public BooleanProperty getCheckedOut() {
		return new SimpleBooleanProperty(isCheckedOut.not().get());
	}

	/**
	 * 
	 * @return When the book is due in a LocalDate object that can be used for math or comparisons
	 */
	public LocalDate getDate() {
		return dateCheckedOut;
	}

	/**
	 * 
	 * @return When the book is due in a string format
	 */
	public StringProperty getDateCheckedOut() {
		if (dateCheckedOut != null) {
			return new SimpleStringProperty(dateCheckedOut.toString());
		} else {
			return new SimpleStringProperty("n/a");
		}
	}

	/**
	 * 
	 * @return Who has checked out the book in a string format
	 */
	public StringProperty getStudentCheckedOut() {
		if (checkedOutBy != null) {
			return new SimpleStringProperty(checkedOutBy.getFirstName().get() + " " + checkedOutBy.getLastName().get());
		} else {
			return new SimpleStringProperty("n/a");
		}
	}

	/**
	 * 
	 * @return Who has checked out the book in a Student object that can be used to call methods or get values
	 */
	public Student getStudentCheckedOutObject() {
		return checkedOutBy;
	}


}
