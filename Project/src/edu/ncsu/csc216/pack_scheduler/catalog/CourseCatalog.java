package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.Activity;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * A class used to hold a catalog of courses at NC State. Users can load and save courses to a file,
 * add and remove courses. as well as get a string of course information from the catalog.
 * @author Steven Saleeb
 * @author Shreyash Jain
 * @author Liam Yeager
 */
public class CourseCatalog {
	
	/** column size of the 2d array of catalog */
	private static final int COLUMN_SIZE = 5;

	/** a catalog has a SortedList of Courses that make up the catalog */
	private SortedList<Course> catalog;
	
	/**
	 * Constructor to make a new CourseCatalog object. Adds a sorted list onto the catalog.
	 */
	public CourseCatalog() {
		catalog = new SortedList<Course>();
	}
	
	/**
	 * Resets the catalog to be an empty sorted list.
	 */
	public void newCourseCatalog() {
		catalog = new SortedList<Course>();
	}
	
	/**
	 * Loads course records into the catalog from a file.
	 * @param fileName the name of the file being loaded.
	 * @throws IllegalArgumentException if the file cannot be found.
	 */
	public void loadCoursesFromFile(String fileName) {
		
		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		}
		catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}
		
	/**
	 * Adds a Course with the following fields to the catalog and returns true if the Course is added and 
	 * false if the Course already exists in the catalog. If there is an error constructing the Course, 
	 * the IllegalArgumentException is allowed to propagate to the client.
	 * @param name the name of the course
	 * @param title the title of the course
	 * @param section the section of the course
	 * @param credits the number of credits the course is worth
	 * @param instructorId the id of the instructor teaching the course
	 * @param meetingDays the days of the week the course meets
	 * @param enrollmentCap the enrollment capacity of the course
	 * @param startTime the starting time of the course
	 * @param endTime the ending time of the week
	 * @return true if the Course is added and false if the Course already exists in the catalog
	 * @throws IllegalArgumentException if any of the fields in the given course is invalid
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays, int startTime, int endTime) {
		Course course = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime);
		if (catalog.size() == 0) {
			catalog.add(course);
			return true;
		}
		for (int i = 0; i < catalog.size(); i++) {
			if (catalog.get(i).compareTo(course) == 0) {
				return false;
			}
		}
		catalog.add(course);
		return true;
		
	}
	
	/**
	 * Removes a course given its name and section from the catalog.
	 * Returns true if the Course is removed from the catalog and false if the Course is not in the catalog.
	 * @param name the name of the course to be removed.
	 * @param section the section of the course to be removed.
	 * @return true if the Course is removed from the catalog and false if the Course is not in the catalog
	 */
	public boolean removeCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				catalog.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the Course from the catalog with the given name and section. Returns null if the Course isn’t in the catalog.
	 * @param name the name of the course
	 * @param section the section of the course
	 * @return the Course from the catalog with the given name and section.
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				return catalog.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Returns the name, section, title, and meeting information for Courses in the catalog.
	 * @return returns the name, section, title, and meeting information for Courses in the catalog in a String array.
	 */
	public String[][] getCourseCatalog() {
		if (catalog.size() == 0) {
			return new String[0][0];
		}
		String[][] array = new String[catalog.size()][COLUMN_SIZE];
		for (int i = 0; i < catalog.size(); i++) {
			for (int j = 0; j < array[i].length; j++) {
				Activity activity = catalog.get(i);
				array[i] = activity.getShortDisplayArray();
			}
		}
		return array;
	}
	
	/**
	 * Saves the catalog course records to the given file. 
	 * Any IOExceptions are caught and an IllegalArgumentException is thrown to the client.
	 * @param fileName the name of the file to be saved to.
	 * @throws IllegalArgumentException if the file is unable to be written to
	 */
	public void saveCourseCatalog(String fileName) {
		try {
			CourseRecordIO.writeCourseRecords(fileName, catalog);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}

	/**
	 * Generates a unique hashcode for this object.
	 * @return the integer hashcode for this object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((catalog == null) ? 0 : catalog.hashCode());
		return result;
	}

	/**
	 * Checks if this object is equal to another object.
	 * @return returns true if this object is equal, false if not
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CourseCatalog other = (CourseCatalog) obj;
		if (catalog == null) {
			if (other.catalog != null)
				return false;
		} else if (!catalog.equals(other.catalog))
			return false;
		return true;
	}
}
		
	

