package edu.ncsu.csc216.pack_scheduler.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * A helper class for reading Faculty records from files and writing records to files.
 * Can read Faculty records and make a list of them from a file, and can take a list of Faculty
 * records and write them to a file.
 * @author Steven Saleeb
 * @author Shreyash Jain
 */
public class FacultyRecordIO {
	
	/**
	 * Reads a Faculty directory from given filename 
	 * @param filename the path of the file that the directory will be taken from
	 * @return a LinkedList of Faculty objects
	 * @throws FileNotFoundException if file does not exist on system
	 */
	public static LinkedList<Faculty> readFacultyRecords(String filename) throws FileNotFoundException {
		Scanner scan = new Scanner(new FileInputStream(filename));
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		
		while (scan.hasNextLine()) {
			try {
				faculty.add(processFaculty(scan.nextLine()));
			}
			catch (IllegalArgumentException e) {
				continue;
			}
		}
		scan.close();
		return faculty;
	}
	
	/**
	 * Creates a Faculty object from the line of a csv file
	 * @param line the line from the csv file
	 * @return a Faculty object read from the line
	 * @throws IllegalArgumentException if there is a line with invalid format
	 */
	private static Faculty processFaculty(String line) {
		Scanner scan = new Scanner(line);
		scan.useDelimiter(",");
		String firstName;
		String lastName;
		String id;
		String email;
		String hashedPassword;
		int maxCourses;
		
		if (scan.hasNext()) {
			firstName = scan.next();
		}
		else {
			scan.close();
			throw new IllegalArgumentException("Invalid line format");
		}
		if (scan.hasNext()) {
			lastName = scan.next();
		}
		else {
			scan.close();
			throw new IllegalArgumentException("Invalid line format");
		}
		if (scan.hasNext()) {
			id = scan.next();
		}
		else {
			scan.close();
			throw new IllegalArgumentException("Invalid line format");
		}
		if (scan.hasNext()) {
			email = scan.next();
		}
		else {
			scan.close();
			throw new IllegalArgumentException("Invalid line format");
		}
		if (scan.hasNext()) {
			hashedPassword = scan.next();
		}
		else {
			scan.close();
			throw new IllegalArgumentException("Invalid line format");
		}
		if (scan.hasNextInt()) {
			maxCourses = scan.nextInt();
		}
		else {
			scan.close();
			throw new IllegalArgumentException("Invalid line format");
		}
		
		Faculty faculty = new Faculty(firstName, lastName, id, email, hashedPassword, maxCourses);

		scan.close();
		return faculty;
	}
	
	/**
	 * Writes a csv file containing a Faculty directory
	 * @param fileName the name of the destination file
	 * @param facultyDirectory the directory of Faculty to be converted to csv
	 * @throws IOException if unable to write to file
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws IOException {
		PrintStream writer = new PrintStream(new File(fileName));
			
		for (int i = 0; i < facultyDirectory.size(); i++) {
			Faculty faculty = facultyDirectory.get(i);
			writer.println(faculty.toString());
		}

		writer.close();
		
		
	}
	
}
