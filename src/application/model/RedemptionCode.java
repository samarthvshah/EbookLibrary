package application.model;

import java.time.LocalDate;
import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RedemptionCode {

	private StringProperty code;
	private BooleanProperty isInUse;
	private Student student;
	private ClassBook book;
	
	/** Creates a new redemption code with a code and a classbook to mount it on
	 * 
	 * @param code The string value that is the code
	 * @param b The classbook that this code will be used to redeem the book
	 */
	public RedemptionCode(String code, ClassBook b) {
		this.code = new SimpleStringProperty(code);
		student = null;
		isInUse = new SimpleBooleanProperty(false);
		book = b;
		b.mountCode(this);
	}

	/** Creates a new redemption code with a random code and a classbook to use it on
	 * 
	 * @param codesUsed The current list of codes to ensure the new code does not match an old one
	 * @param b The classbook that this code will be used to redeem the book
	 */
	public RedemptionCode(ArrayList<RedemptionCode> codesUsed, ClassBook b) {
		ArrayList<String> codeStrings = new ArrayList<String>();
		
		for (RedemptionCode c: codesUsed) {
			codeStrings.add(c.getCode().get());
		}
		
		String str = "";

		do {
			str = "";
			for (int i = 0; i < 8; i++) {
				str += (int)(Math.random()*9);
			} 
		} while (!this.checkForUnique(str, codeStrings));

		code = new SimpleStringProperty(str);
		student = null;
		isInUse = new SimpleBooleanProperty(false);
		book = b;
		b.mountCode(this);	
	}
	
	/**
	 *  @return A string representation of the redemption code with the code and the book it is related to
	 */
	public String toString() {
		String str = "code: " + code.get() + " for book " + book.getTitle();
		if (isInUse.get()) {
			str += "is checked out to" + student.getFirstName();
		} else {
			str += "is not checked out";

		}
		return str;
	}

	/** Checks if a string is already used or contained in a list of Strings
	 * 
	 * @param str The string to be checked
	 * @param codesUsed The lsit of Strings that have already been sued
	 * @return A boolean if a string is already used or contained in a list of Strings
	 */ 
	private boolean checkForUnique(String str, ArrayList<String> codesUsed) {
		if (codesUsed == null || codesUsed.isEmpty()) {
			return true;
		} else {
			for (int i = 0; i < codesUsed.size()-1; i++) {
				if (str.equals(codesUsed.get(i))) {
					return false;
				}
			}
			return true;
		}
	}
	
	/**
	 * 
	 * @param r The redemption code to compare to
	 * @return If the codes of the 2 codes are equal
	 */
	public boolean equals(RedemptionCode r) {
		return r.getCode().equals(this.getCode());
	}
	
	/** Redeems the code and checks out the classbook
	 * 
	 * @param s The student who entered the redemption code
	 * @return True if the redemption is redeemed succesfully
	 */
	public boolean redeemCode(Student s) {
		if (!isInUse.get()) {
			isInUse.set(true);
			student = s;
			book.checkOutBook(s);
			return true;
		} else {
			return false;
		}
	}
	
	/** Redeems the code and checks out the classbook wwith the data given
	 *  
	 * @param s The student who entered the redemption code
	 * @param d The date to set for the due date
	 * @return True if the redemption is redeemed succesfully
	 */
	public boolean redeemCode(Student s, LocalDate d) {
		if (!isInUse.get()) {
			isInUse.set(true);
			student = s;
			book.checkOutBook(s, d);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @return A string with the code value
	 */
	public StringProperty getCode() {
		return code;
	}
	
	/**
	 * 
	 * @return A student object with the student who has redeemed the redemption code
	 */
	public Student getStudent() {
		return student;
	}
	
	/**
	 * 
	 * @return A string with the values of the student who has redeemed the redemption code
	 */
	public StringProperty getStudentString() {
		if (student != null) {
		return new SimpleStringProperty(student.getFirstName().get() + " " + student.getLastName().get());
		} else {
			return new SimpleStringProperty("n/a");
		}
	}
	
	/**
	 *  
	 * @return A boolean wit hif the code has been redeemed by a student
	 */
	public BooleanProperty getInUse() {
		return isInUse;
	}
	
	/**
	 * 
	 * @return The classbook that the redemption code is mounted on
	 */
	public ClassBook getBook() {
		return book;
	}
	
	/** Changes the code value of the redemption code into the new string
	 * 
	 * @param str The string to change the code to
	 * @param codesUsed The current list of codes to make sure the same code is not used twice
	 * @return True if the code is changed successfully
	 */
	public boolean changeCode(String str, ArrayList<RedemptionCode> codesUsed) {
		
		ArrayList<String> codeStrings = new ArrayList<String>();
		
		for (RedemptionCode c: codesUsed) {
			codeStrings.add(c.getCode().get());
		}
		
		if (checkForUnique(str, codeStrings)) {
			if (str.length() == 8) {
				for (int i = 0; i < str.length(); i++) {
					if (!Character.isDigit(str.charAt(i))) {
						return false;
					}
				}
				code = new SimpleStringProperty(str);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
