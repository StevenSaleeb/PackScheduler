package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Class that represents a student. A student has a first and last name, a unique id, an email, password,
 * and max credits.
 * @author Shreyash Jain
 * @author Liam Yeager
 * @author Steven Saleeb
 */
public class Student extends User implements Comparable<Student> {
	
	/** Schedule of a Student */
	private Schedule schedule;
	
	/** Constant representing the maximum amount of credits a student can have */
	public static final int MAX_CREDITS = 18;
	
	
	/** Variable representing the maximum amount of credits this student can have */
	private int maxCredits;

	/**
	 * Constructor for student
	 * @param firstName the first name of a student
	 * @param lastName the last name of a student
	 * @param id the unique id of a student
	 * @param email the email of a student
	 * @param hashPW the password of a student
	 * @param maxCredits the maximum amount of credits this student can have
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW, int maxCredits) {
		super(firstName, lastName, id, email, hashPW);
		schedule = new Schedule();
		setMaxCredits(maxCredits);
		
	}
	

	/**
	 * Constructor without max credits, defaults it to the constant
	 * @param firstName the first name of a student
	 * @param lastName the last name of a student
	 * @param id the unique id of a student
	 * @param email the email of a student
	 * @param hashPW the password of a student
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW) {
		this(firstName, lastName, id, email, hashPW, MAX_CREDITS);
		}
	/**
	 * Gets the max credits of a student
	 * @return max credits of a student
	 */
	public int getMaxCredits() {
		return maxCredits;
	}

	/**
	 * Sets the max credits of a student
	 * @param maxCredits max credits of a student
	 * @throws IllegalArgumentException if max credits is invalid
	 */
	public void setMaxCredits(int maxCredits) {
		if (maxCredits < 3 || maxCredits > 18) {
			throw new IllegalArgumentException("Invalid max credits");
		}
		this.maxCredits = maxCredits;
	}

	/**
	 * Compares this student with another student. 
	 * Returns a negative integer, zero, or a positive integer if this 
	 * Student is less than, equal to, or greater than the other Student.
	 * Students are ordered by last name, then first name, then their unity id,
	 * and a student is less than when their last name, first name, or unity id
	 * is less than numerically or alphabetically (depending on which parameters are the
	 * same).
	 * @param s the Student being compared
	 * @return the integer representing if this Student is less than, equal to, or greater than the other Student
	 * @throws NullPointerException if the given Student is null
	 */
	@Override
	public int compareTo(Student s) {
		if (s == null) {
			throw new NullPointerException("Given student is null.");
		}
		
		if (getLastName().equals(s.getLastName())) {
			if (getFirstName().equals(s.getFirstName())) {
				if (getId().equals(s.getId())) {
					// Student objects have the same fields
					return 0;
				}
				else if (getId().compareTo(s.getId()) < 0){
					// Student id of this Student is lower alphabetically
					return -1;
				}
				else {
					// Student id of this Student is higher alphabetically
					return 1;
				}
			}
			else if (getFirstName().compareTo(s.getFirstName()) < 0) {
				// First name is lower alphabetically
				return -1;
			}
			else {
				// First name is higher alphabetically
				return 1;
			}
		}
		else if (getLastName().compareTo(s.getLastName()) < 0) {
			// Last name is lower alphabetically
			return -1;
		}
		else {
			// Last name is higher alphabetically
			return 1;
		}
	}

	/**
	 * Generates a unique hash code for this object
	 * @return the hash code
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCredits;
		return result;
	}


	/**
	 * Checks if another student is equal to the student
	 * @param obj object being compared to
	 * @return true if it is equal and false if it is not
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return !(maxCredits != other.maxCredits);
	}



	/**
	 * Turns this object into a string representation of itself
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName() + "," + getId() + "," + getEmail() + "," + getPassword() + "," + maxCredits;
	}


	/**
	 * Returns the student's schedule
	 * @return the student's schedule
	 */
	public Schedule getSchedule() {
		return schedule;
	}
	
	
	/**
	 * Returns true if the course can be added to the student's schedule.
	 * If the Course is null, if the Course is already in the schedule, 
	 * if there is a conflict, or if the Student has no more room in their 
	 * schedule for the course this method will return false.
	 * @param c the course to be checked.
	 * @return true if the course can be added to the student's schedule.
	 */
	public boolean canAdd(Course c) {
		if (!schedule.canAdd(c)) {
			return false;
		}

		return (schedule.getScheduleCredits() + c.getCredits()) <= getMaxCredits();
	}


}
