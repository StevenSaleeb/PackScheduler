package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * Represents a faculty member, extending the User class, with a maximum number
 * of courses they are allowed to teach.
 * @author Shreyash Jain
 * @author Steven Saleeb
 */
public class Faculty extends User {
	
	/** schedule of a Faculty that has courses the Faculty is teaching */
	private FacultySchedule schedule;

	/** Number of courses the faculty member may teach */
	private int maxCourses;

	/** Minimum number of courses a faculty member can teach */
	public static final int MIN_COURSES = 1;

	/** Maximum number of courses a faculty member can teach */
	public static final int MAX_COURSES = 3;

	/**
	 * Constructs a Faculty object with all required fields
	 * 
	 * @param firstName  faculty first name
	 * @param lastName   faculty last name
	 * @param id         faculty id
	 * @param email      faculty email
	 * @param hashPW     hashed password
	 * @param maxCourses number of courses
	 * @throws IllegalArgumentException if maxCourses is out of range
	 */
	public Faculty(String firstName, String lastName, String id, String email, String hashPW, int maxCourses) {

		super(firstName, lastName, id, email, hashPW);
		setMaxCourses(maxCourses);
		schedule = new FacultySchedule(id);
	}

	/**
	 * Sets the maximum number of courses the faculty member may teach
	 * @param maxCourses the max courses the faculty member can teach
	 * @throws IllegalArgumentException if value is invalid
	 */
	public void setMaxCourses(int maxCourses) {

		if (maxCourses < MIN_COURSES || maxCourses > MAX_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		} else {
			this.maxCourses = maxCourses;
		}
	}

	/**
	 * Returns the maximum number of courses the faculty may teach
	 * @return maxCourses
	 */
	public int getMaxCourses() {
		return maxCourses;

	}

	/**
	 * Returns a hash code value for the object including the maxCourses field
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}

	/**
	 * Compares this Faculty object to another object
	 * 
	 * @param obj object to compare
	 * @return true if equal and false if it is not
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Faculty)) {
			return false;
		}
		Faculty other = (Faculty) obj;

		return maxCourses == other.maxCourses;
	}

	/**
	 * Returns a formatted String with the correct informations
	 * @return formatted faculty data
	 */
	@Override
	public String toString() {
		return super.toString() + "," + maxCourses;
	}

	/**
	 * Returns the schedule of the Faculty
	 * @return schedule of the Faculty
	 */
	public FacultySchedule getSchedule() {
		return schedule;
	}

	/**
	 * Returns true if the number of scheduled courses is greater than the Faculty’s maxCourses
	 * @return true if number of scheduled courses is greater than max courses, false otherwise
	 */
	public boolean isOverloaded() {
		return schedule.getNumScheduledCourses() > getMaxCourses();
	}

}
