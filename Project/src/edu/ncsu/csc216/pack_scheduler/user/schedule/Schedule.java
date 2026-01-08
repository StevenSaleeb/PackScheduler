package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * Class representing a student's schedule. A schedule is a list of courses that 
 * can be added to and removed from. A schedule has a title.
 * @author Shreyash Jain
 */
public class Schedule {
	
	/** title of schedule */
	private String title;
	/** list of courses that form a schedule */
	private ArrayList<Course> schedule;
	
	/**
	 * Constructs a new Schedule
	 */
	public Schedule() {
		title = "My Schedule";
		schedule = new ArrayList<Course>();
	}
	
	/**
	 * Adds given Course to Schedule if it is not a duplicate of another Course in the Schedule
	 * @param course course
	 * @return true if Course can be added to Schedule
	 * @throws IllegalArgumentException if Course is a duplicate or has a conflict with another Course
	 * @throws NullPointerException if Course is null
	 */
	public boolean addCourseToSchedule(Course course) {
		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i).isDuplicate(course)) {
				throw new IllegalArgumentException("You are already enrolled in " + course.getName());
			}
			try {
				schedule.get(i).checkConflict(course);
			}
			catch (ConflictException e) {
				throw new IllegalArgumentException("The course cannot be added due to a conflict.");
			}
		}
		schedule.add(course);
		return true;
	}
	
	/**
	 * Removes given Course from the Schedule if it is in the Schedule
	 * @param course course
	 * @return true if Course can be removed from Schedule, false otherwise
	 */
	public boolean removeCourseFromSchedule(Course course) {
		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i).equals(course)) {
				schedule.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Resets the Courses in Schedule
	 */
	public void resetSchedule() {
		title = "My Schedule";
		schedule = new ArrayList<Course>();
	}
	
	/**
	 * Returns a 2D array of Course information
	 * Each row should be a Course and the columns are name, section, title, and the meeting string
	 * @return 2d array of the Schedule Courses
	 */
	public String[][] getScheduledCourses() {
		String[][] array = new String[schedule.size()][5];
		for (int i = 0; i < schedule.size(); i++) {
			array[i] = schedule.get(i).getShortDisplayArray();
		}
		return array;
	}
	
	/**
	 * Sets the title of the Schedule
	 * @param title title of Schedule
	 * @throws IllegalArgumentException if title is null
	 */
	public void setTitle(String title) {
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		this.title = title;
	}
	
	/**
	 * Returns the title of the Schedule
	 * @return title of Schedule
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Returns the total credits in the schedule
	 * @return the total credits in the schedule
	 */
	public int getScheduleCredits() {
		int credits = 0;
		for (int i = 0; i < schedule.size(); i++) {
			credits += schedule.get(i).getCredits();
		}
		return credits;
	}
	
	/**
	 * Returns true if the Course can be added to the schedule.
	 * If the Course is null, if the Course is already in the 
	 * schedule, or if there is a conflict, return false.
	 * @param c the course to be checked
	 * @return if the Course can be added to the schedule.
	 */
	public boolean canAdd(Course c) {
		if (c == null) {
			return false;
		}
		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i).isDuplicate(c)) {
				return false;
			}
			try {
				schedule.get(i).checkConflict(c);
			}
			catch (ConflictException e) {
				return false;
			}
		}
		return true;
		
	}
}
