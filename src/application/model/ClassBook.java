package application.model;

import java.time.LocalDate;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClassBook extends Book{

	private RedemptionCode code;

	/** Creates the classbook object with the given values as the parameters and setting the redemption code to null
	 * 
	 * @param id The ID of the book
	 * @param t The title of the book
	 * @param a The author of the book
	 * @param i The ISBN number of the book
	 * @param c If the book is checked out
	 * @param d When the book is due
	 * @param s Who has checked out the book
	 */
	public ClassBook(int id, String t, String a, int i, boolean c, LocalDate d, Student s) {
		super(id ,t, a, i, c, d, s);
		code = null;
	}

	/**
	 *  @return A string representation of the book with an addition of a redemption code
	 */
	public String toString() {
		String str = super.toString();
		if (code == null) {
			str += " and has no redemption code";
		} else {
			str += " and has the code " + code.getCode().get();
		}
		return str;
	}

	/** Mounts a redemption code onto this classbook
	 * 
	 * @param r The redemeption code to mount
	 * @return True if the code is mounted successfully
	 */
	public boolean mountCode(RedemptionCode r) {
		if (code == null) {
			code = r;
			return true;
		} else {
			return false;
		}
	}

	/** Checks if the book's redemption code matches the string given
	 * 
	 * @param s The string to check
	 * @return True if the string matches the code of the classbook
	 */
	public boolean matchingCode(String s) {
		if (code == null) {
			return false; 
		} else {
			return code.getCode().get().equals(s);
		}
	}

	/**
	 * 
	 * @return The redemption code mounted on the classbook
	 */
	public RedemptionCode getCode() {
		return code;
	}

	/**
	 * 
	 * @return A string with the code of the redemption code mounted on the classbook
	 */
	public StringProperty getCodeString() {
		if (code != null) {
			return code.getCode();
		} else {
			return new SimpleStringProperty("n/a");
		}
	}

	/** Unmounts the redemption code on the classbook
	 * 
	 */
	public void unmountCode() {
		code = null;
	}
}
