package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {

	private final StringProperty fname;
	private final StringProperty lname;
	private final StringProperty id;
	private IntegerProperty grade;
	private ArrayList<Book> books;

	/** Creates a new Student with the parameters for the fields
	 * 
	 * @param f The first name of the student
	 * @param l The last name of the student
	 * @param ids The id of the student
	 * @param g The grade of the student
	 * @param books The list of books the student should have checked out
	 */
	public Student(String f, String l, String ids, int g, Book[] books) {
		fname = new SimpleStringProperty(f);
		lname = new SimpleStringProperty(l);
		id = new SimpleStringProperty(ids);
		grade = new SimpleIntegerProperty(g);
		this.books = new ArrayList<Book>();

		if (books != null) {
			for (int i = 0; i < books.length; i++) {
				this.books.add(books[i]);
			}
		}
	}

	/** Redeems a code with the entering of a string s with the data entered
	 * 
	 * @param s The string to search for
	 * @param codes The list of codes to search through
	 * @param d The date to set the due date to
	 * @return True if a code is redeemed successfully
	 */
	public boolean redeemCode(String s, ArrayList<RedemptionCode> codes, LocalDate d) {
		for (int i = 0; i < codes.size(); i++) {
			RedemptionCode r = codes.get(i);

			if (s.equals(r.getCode().get())) {

				if (books.isEmpty()) {
					if (r.redeemCode(this, d)) {
						books.add(r.getBook());
						return true;
					} else {
						return false;
					}
				} else if (books.size() == 1) {
					if (r.redeemCode(this, d)) {
						books.add(r.getBook());
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		return false;
	}

	/** Redeems a code with the entering of a string s
	 * 
	 * @param s The string to search for
	 * @param codes The list of codes to search through
	 * @return True if a code is redeemed successfully
	 */
	public boolean redeemCode(String s, ArrayList<RedemptionCode> codes) {

		for (int i = 0; i < codes.size(); i++) {
			RedemptionCode r = codes.get(i);

			if (s.equals(r.getCode().get())) {

				if (books.isEmpty()) {
					if (r.redeemCode(this)) {
						books.add(r.getBook());
						return true;
					} else {
						return false;
					}
				} else if (books.size() == 1) {
					if (r.redeemCode(this)) {
						books.add(r.getBook());
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		return false;
	}

	/** Check outs a book by the student
	 * 
	 * @param b The book to check out
	 * @return True if the book is checked out successfully
	 */
	public boolean checkOutBook(Book b) {		
		if (books.isEmpty()) {
			if (b.checkOutBook(this)) {
				books.add(b);
				return true;
			} else {
				return false;
			}
		} else if (books.size() < 2) {
			if (b.checkOutBook(this)) {
				books.add(b);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/** Check outs a book by the student with the date due entered
	 * 
	 * @param b The book to check out
	 * @param d The date that the book should be due
	 * @return True if the book is checked out successfully
	 */
	public boolean checkOutBook(Book b, LocalDate d) {		
		if (books.isEmpty()) {
			if (b.checkOutBook(this, d)) {
				books.add(b);
				return true;
			} else {
				return false;
			}
		} else if (books.size() == 1) {
			if (b.checkOutBook(this, d)) {
				books.add(b);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/** Returns a book and removes it from the students list of checked out books
	 * 
	 * @param b The book to return
	 * @return True if the book is returned successfully
	 */
	public boolean returnBook(Book b) {
		if (!books.isEmpty()) {
			if (books.get(0).getTitle().equals(b.getTitle())) {
				books.remove(0);
				b.returnBook();
				return true;
			} else if (books.get(1).getTitle().equals(b.getTitle())){
				books.remove(1);
				b.returnBook();

				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @return the first name of the student
	 */
	public StringProperty getFirstName() {
		return fname;
	}

	/**
	 * 
	 * @return the last name of the student
	 */
	public StringProperty getLastName() {
		return lname;
	}

	/**
	 * 
	 * @return the ID of the student
	 */
	public StringProperty getID() {
		return id;
	}

	/**
	 * 
	 * @return the Grade of the student
	 */
	public IntegerProperty getGrade() {
		return grade;
	}

	/**
	 * 
	 * @return A list of all the books checked out by that student in the form of an array
	 */
	public Book[] getBooks() {
		Book[] books = new Book[2];
		if (this.books.isEmpty()) {
			books[0] = null;
			books[1] = null;
		} else if (this.books.size() == 1) {
			books[0] = this.books.get(0);
			books[1] = null;
		} else {
			books[0] = this.books.get(0);
			books[1] = this.books.get(1);
		}

		return books;
	}

	/**
	 * 
	 * @return A list of all the books checked out by that student in the form of an arraylist
	 */
	public ArrayList<Book> getBooksArrayList() {
		return books;
	}

	/**
	 * 
	 * @return The first book checked out if it exists
	 */
	public StringProperty getBook1() {
		if (books.size() > 0) {
			return books.get(0).getTitle();
		} else {
			return new SimpleStringProperty("n/a");
		}
	}

	/**
	 * 
	 * @return The second book checked out if it exists
	 */
	public StringProperty getBook2() {
		if (books.size() > 1) {
			return books.get(1).getTitle();
		} else {
			return new SimpleStringProperty("n/a");
		}
	}

	/**
	 * 
	 * @param s The string to set to the first name
	 */
	public void setFirstName(String s) {
		fname.set(s);
	}

	/**
	 * 
	 * @param s The string to set to the last name
	 */
	public void setLastName(String s) {
		lname.set(s);
	}

	/**
	 * 
	 * @param s The string to set to the ID
	 */
	public void setID(String s) {
		id.set(s);
	}

	/**
	 * 
	 * @param s The string to set to the grade
	 */
	public void setGrade(int s) {
		grade.set(s);
	}

}
