# EbookLibrary
FBLA CAP Ebook Library

Ebook Library Database Application 



About
———
Hello, my name is Samarth Shah. I’m a sophomore at Homestead High
School, and here is my submission for Coding and Programming.
________________________________________________________________________________


Requirements
———————
Must have the java runtime environment installed (note that most computers already have it installed)

If the JavaFx version is before 8.0.181 the program may have errors due to an outdated api
________________________________________________________________________________


Documentation
———————
Event: 		Coding and Programming

Language: 		JavaFx 8, Java SE 8 1.8.0_201

IDE:			Eclipse Photon

Resources:    	stackoverflow.com
			tutorials.jenkov.com/javafx/index.html
			Official Java and JavaFx documentation
		
			<All resources free for non-commercial use>
________________________________________________________________________________


Running the Program
——————————

See the Statement of Assurance for instructions
________________________________________________________________________________


Functionality
——————

The goal of the program is to accurately simulate a library, just in an online medium. This medium gives the program some benefits, such as having quick access to all the books that are checked out, and being able to force return books as soon as they are due.

The program tracks library books, class books, and students that can access the library. The program is split into 2 major parts based on how the user logs in: management and student.

The management interface shows the administrator all the information, such as what books are checked out and when they are due. They can also see a list of students and what books they currently have checked out, a list of redemption codes that are available and whether they are in use at the moment, and finally be able to open a windows with a list of all the books due in the upcoming week.

The student interface shows the student a list of all the books they can check out (just library books). They cannot see the class books and must check those out with a redemption code. They can also manage what books they have checked out at the moment and return them.
________________________________________________________________________________


Help
———

Logging in:

if you are student, log in with your first name as the username and your ID as the password. For admins, log in with the username “librarian” and the password “FBLA”, case sensitive

Student:

Once logged in as a student, you have the ability to check out and return books, as well as redeem redemption codes for class books. Note that a student can only have up to 2 books checked out concurrently, including class books. 

To check out a books, click on the book you want to check out in the table, then click the check out button on the bottom. Then you will get a message either confirming checkout or saying that the checkout is invalid. The checkout could be invalid for a number of reasons, including that the book is already checked out or the student already has 2 books checked out.

 A student can also return books they have checked out in the same manner: selecting the book to return and clicking the return button at the bottom the window. If the book is due, it will automatically be returned. 

To redeem a redemption code for a book that is related to a classroom, get the code for your teacher. Then enter that code (case-sensitive) into the field and click redeem. The same confirmation will appear as for checking out books normally.

Administration:

Once logged in as an administrator, you have the ability to see all the books and their status, all the students and what books they currently have checked out, and all the redemption codes and whether they are in use.

To edit a student or redemption code, click on the thing you want to edit in the table, then click on the edit button. This will show a new window where the current values are shown and you can edit them, then click ok to set them.

To create a new student or redemption code, click on the new button at the bottom of the respective page. This will prompt a new window where the required fields are shown that need to be filled. For new students, first name, last name, grade, and id need to be filled. For redemption codes the code and the class book that is related to it need to be filled.

To see what books are due in the upcoming week, click the Weekly Report button. This will show a list of all the books that are going to be due in the next 7 days.
________________________________________________________________________________

