package edu.ncsu.csc216.pack_scheduler.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileInputStream;
import java.util.Scanner;
import java.io.File;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * A helper class for reading student records from files and writing records to files.
 * Can read student records and make a list of them from a file, and can take a list of student 
 * records and write them to a file.
 * @author Shreyash Jain
 * @author Liam Yeager
 * @author Steven Saleeb
 */
public class StudentRecordIO {
	/**
     * Reads a student directory from a filename
     * @param fileName the path of the file that the directory will be taken from
     * @return a Sorted List of student objects
     * @throws FileNotFoundException if file does not exist on system
     */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		Scanner fileScnr = new Scanner(new FileInputStream(fileName)); // creates a file scanner to read the file
		SortedList<Student>  students = new SortedList<Student>(); // creates a SortedList of student objects
		
		while (fileScnr.hasNextLine()) {
			try {
				Student student = processLine(fileScnr.nextLine()); // try to create a student object with the line
				students.add(student);
			}
			catch (IllegalArgumentException e) {
				// Skip any line that throws an exception
			}
		}
		
		fileScnr.close();
		return students;
	}
	
	/** 
	 * Creates a student object from the line of a csv file
	 * @param line the line from the csv file
	 * @return a student object read from the line
	 * @throws IllegalArgumentException if there is a line with invalid format
	 */
	private static Student processLine(String line) {
		Scanner lineScnr = new Scanner(line);
		lineScnr.useDelimiter(","); // scanner will use commas as a delimiter
		
		String firstName;
		String lastName;
		String id;
		String email;
		String hashedPassword;
		int maxCredits;
		
		// line has format: firstName,lastName,id,email,hashedPassword,maxCredits
		if (lineScnr.hasNext()) {
			firstName = lineScnr.next();
		}
		else {
			lineScnr.close();
			throw new IllegalArgumentException("Invalid line format");
		}
		if (lineScnr.hasNext()) {
			lastName = lineScnr.next();
		}
		else {
			lineScnr.close();
			throw new IllegalArgumentException("Invalid line format");
		}
		if (lineScnr.hasNext()) {
			id = lineScnr.next();
		}
		else {
			lineScnr.close();
			throw new IllegalArgumentException("Invalid line format");
		}
		if (lineScnr.hasNext()) {
			email = lineScnr.next();
		}
		else {
			lineScnr.close();
			throw new IllegalArgumentException("Invalid line format");
		}
		if (lineScnr.hasNext()) {
			hashedPassword = lineScnr.next();
		}
		else {
			lineScnr.close();
			throw new IllegalArgumentException("Invalid line format");
		}
		if (lineScnr.hasNextInt()) {
			maxCredits = lineScnr.nextInt();
		}
		else {
			lineScnr.close();
			throw new IllegalArgumentException("Invalid line format");
		}
		
		Student student = new Student(firstName, lastName, id, email, hashedPassword, maxCredits);

		lineScnr.close();
		return student;
	}
	
	/**
	 * Writes a csv file containing a student directory
	 * @param fileName the name of the destination file
	 * @param studentDirectory the directory of students to be converted to csv
	 * @throws IOException if unable to write to file
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		

		PrintStream fileWriter = new PrintStream(new File(fileName));
			
		for (int i = 0; i < studentDirectory.size(); i++) {
		    fileWriter.println(studentDirectory.get(i).toString());
		}

		fileWriter.close();
		
		
	}

}
