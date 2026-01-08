package edu.ncsu.csc216.pack_scheduler.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.File;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.FileInputStream;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * A helper class for Course file writing and reading. 
 * Reads Course records from text files. Writes a set of CourseRecords to a
 * file.
 * @author Steven Saleeb
 * @author Sarah Heckman
 * @author Liam Yeager
 */
public class CourseRecordIO {

	/**
	 * Reads course records from a file and generates a list of valid Courses. Any
	 * invalid Courses are ignored. If the file to read cannot be found or the
	 * permissions are incorrect a File NotFoundException is thrown.
	 * 
	 * @param fileName file to read Course records from
	 * @return a list of valid Courses
	 * @throws FileNotFoundException if the file cannot be found or read
	 */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName)); // Create a file scanner to read the file
		SortedList<Course> courses = new SortedList<Course>(); // Create an empty array of Course objects
		while (fileReader.hasNextLine()) { // While we have more lines in the file
			try { // Attempt to do the following
					// Read the line, process it in readCourse, and get the object
					// If trying to construct a Course in readCourse() results in an exception, flow
					// of control will transfer to the catch block, below
				Course course = readCourse(fileReader.nextLine());

				// Create a flag to see if the newly created Course is a duplicate of something
				// already in the list
				boolean duplicate = false;
				// Look at all the courses in our list
				for (int i = 0; i < courses.size(); i++) {
					// Get the course at index i
					Course current = courses.get(i);
					// Check if the name and section are the same
					if (course.getName().equals(current.getName())
							&& course.getSection().equals(current.getSection())) {
						// It's a duplicate!
						duplicate = true;
						break; // We can break out of the loop, no need to continue searching
					}
				}
				// If the course is NOT a duplicate
				if (!duplicate) {
					courses.add(course); // Add to the ArrayList!
				} // Otherwise ignore
			} catch (IllegalArgumentException e) {
				// The line is invalid b/c we couldn't create a course, skip it!
			}
		}
		// Close the Scanner b/c we're responsible with our file handles
		fileReader.close();
		// Return the ArrayList with all the courses we read!
		return courses; 
	}

	/**
	 * Reads a line from a file and creates a Course object from it.
	 * 
	 * @param nextLine a line from a CSV file containing info about a course
	 * @return a course object with the information from a line in a CSV file
	 * @throws IllegalArgumentException if line has an invalid format
	 */
	private static Course readCourse(String nextLine) {
		Scanner lineScnr = new Scanner(nextLine);
		lineScnr.useDelimiter(",");

		try {
			try {
				Course c;
				String name = lineScnr.next();
				String title = lineScnr.next();
				String section = lineScnr.next();
				int credits = lineScnr.nextInt();
				String instructorId = lineScnr.next();
				int enrollmentCap = lineScnr.nextInt();
				String meetingDays = lineScnr.next();

				if ("A".equals(meetingDays)) {
					if (lineScnr.hasNext()) {
						lineScnr.close();
						throw new IllegalArgumentException("Invalid line format.");
					}
					c = new Course(name, title, section, credits, null, enrollmentCap, meetingDays);
				}
				else {
					int startTime = lineScnr.nextInt();
					int endTime = lineScnr.nextInt();
	
					if (lineScnr.hasNext()) {
						lineScnr.close();
						throw new IllegalArgumentException("Invalid line format.");
					}
					c = new Course(name, title, section, credits, null, enrollmentCap, meetingDays, startTime, endTime);
				}
				// Find if there's an instructor with the id
				// Add the course to the instructor's schedule if the instructor exists
				Faculty f = RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId);
				if (f != null) {
					f.getSchedule().addCourseToSchedule(c);
				}
				
				lineScnr.close();
				return c;

			} catch (InputMismatchException e) {
				lineScnr.close();
				throw new IllegalArgumentException("Invalid line format.");
			}
		} catch (NoSuchElementException e) {
			lineScnr.close();
			throw new IllegalArgumentException("Invalid line format.");
		}
	}

	/**
	 * Writes the given list of Courses to
	 * 
	 * @param fileName file to write schedule of Courses to
	 * @param catalog  list of Courses to write
	 * @throws IOException if cannot write to file
	 */
	public static void writeCourseRecords(String fileName, SortedList<Course> catalog) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < catalog.size(); i++) {
			fileWriter.println(catalog.get(i).toString());
		}

		fileWriter.close();
	}

}