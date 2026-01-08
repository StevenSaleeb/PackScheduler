package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;
import edu.ncsu.csc216.pack_scheduler.util.LinkedQueue;

/**
 * The CourseRoll class has a list of Students enrolled in a Course and has an enrollment capacity for the Course.
 * It has behavior for enrolling and dropping a Student to and from a Course.
 * @author Shreyash Jain
 */
public class CourseRoll {
	
	/** The course associated with the course roll */
	private Course course;
	/** list of Students in Course */
	private LinkedAbstractList<Student> roll;
	/** the roll’s enrollment capacity */
	private int enrollmentCap;
	/** A wait list for extra students enrolling in a course */
	private LinkedQueue<Student> waitlist;
	/** smallest class size is 10 */
	private static final int MIN_ENROLLMENT = 10;
	/** largest class size is 250 */
	private static final int MAX_ENROLLMENT = 250;
	/** Constant representing the wait list capacity */
	private static final int WAITLIST_CAPACITY = 10;
	
	/**
	 * Constructs a new CourseRoll object with an enrollment capacity
	 * @param course the course associated with the course roll
	 * @param enrollmentCap enrollment capacity
	 * @throws IllegalArgumentException if enrollmentCap is less than MIN_ENROLLMENT or greater than MAX_ENROLLMENT
	 * or if the course is null
	 */
	public CourseRoll(Course course, int enrollmentCap) {
		setEnrollmentCap(enrollmentCap);
		setCourse(course);
		roll = new LinkedAbstractList<Student>(getEnrollmentCap());
		waitlist = new LinkedQueue<Student>(WAITLIST_CAPACITY);
	}
	
	/**
	 * Sets the course of the course roll
	 * @param course the course associated with this course roll
	 * @throws IllegalArgumentException if the course is null
	 */
	private void setCourse(Course course) {
		if (course == null) {
			throw new IllegalArgumentException();
		}
		this.course = course;
	}
	
	/**
	 * Returns the enrollment capacity of Course
	 * @return enrollment capacity
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}
	
	/**
	 * Sets the enrollment capacity of Course
	 * @param enrollmentCap enrollment capacity
	 * @throws IllegalArgumentException if enrollmentCap is less than MIN_ENROLLMENT or greater than MAX_ENROLLMENT
	 */
	public void setEnrollmentCap(int enrollmentCap) {
		if (enrollmentCap < MIN_ENROLLMENT || enrollmentCap > MAX_ENROLLMENT) {
			throw new IllegalArgumentException("Invalid enrollment cap");
		}
		 
		if (roll != null) {
			roll.setCapacity(enrollmentCap);
		}
		
		this.enrollmentCap = enrollmentCap;
	}
	
	/**
	 * Enrolls the Student to the Course if able to
	 * @param s Student
	 * @throws IllegalArgumentException if
	 * Student is null or already enrolled or there is no room in class or 
	 * if there is any exception in adding the student
	 */
	public void enroll(Student s) {
		if (!canEnroll(s)) {
			throw new IllegalArgumentException("Student cannot be enrolled.");
		}
		else if (roll.size() == enrollmentCap) {
			waitlist.enqueue(s);
		}
		else {
			try {
				roll.add(s);
			}
			catch (Exception e) {
				throw new IllegalArgumentException("Student cannot be enrolled.");
			}
		}
	}
	
	/**
	 * Drops the Student from the Course if able to
	 * @param s Student
	 * @throws IllegalArgumentException if Student is null or if there is any exception in removing the Student
	 */
	public void drop(Student s) {
		if (s == null) {
			throw new IllegalArgumentException("Student cannot be dropped.");
		}
		
		if (roll.contains(s)) {
			roll.remove(s);
			if (waitlist.size() > 0) {
				Student studentAdd = waitlist.dequeue();
				roll.add(studentAdd);
				studentAdd.getSchedule().addCourseToSchedule(course);
			}
		}
		else if (waitlist.contains(s)) {
			// Search for the student in the waitlist and remove it if it exists
			LinkedQueue<Student> temp = new LinkedQueue<Student>(WAITLIST_CAPACITY);
			boolean removed = false;
			int size = waitlist.size();
			for (int i = 0; i < size; i++) {
				Student tempStudent = waitlist.dequeue();
				if (s.equals(tempStudent)) {
					removed = true;
				}
				else {
					temp.enqueue(tempStudent);
				}
			}
			waitlist = temp;
			if (!removed) {
				throw new IllegalArgumentException("Student cannot be dropped.");
			}
		}
		
	}
	
	/**
	 * Returns the seats open for Students
	 * @return the difference between the enrollmentCap and the size of the roll
	 */
	public int getOpenSeats() {
		return enrollmentCap - roll.size();
	}
	
	/**
	 * Checks if a Student can enroll in a class 
	 * @param s Student
	 * @return false if student is null or 
	 * if there is no room in the class or 
	 * if Student is already enrolled, 
	 * return true otherwise
	 */
	public boolean canEnroll(Student s) {
		if (s == null) {
			return false;
		}
		if (roll.size() == enrollmentCap && waitlist.size() == 10) {
			return false;
		}
		if (roll.contains(s)) {
			return false;
		}
		if (waitlist.contains(s)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Returns the number of students on the wait list
	 * @return the number of students on the wait list
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}
}
