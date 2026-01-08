package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Class representing a Course at NC State with a name, title,
 * section, credits, instructor, meeting days and meeting times.
 * Can check for duplicate courses based on name, and implements comparable
 * sorting by title then section number, and a course is less than another 
 * when their title or section number is less than alphabetically.
 * @author Steven Saleeb
 * @author Liam Yeager
 */
public class Course extends Activity implements Comparable<Course> {
	/** The length a section should be. */
	private static final int SECTION_LENGTH = 3;
	/** The maximum credits allowed. */
	private static final int MAX_CREDITS = 5;
	/** The minimum credits allowed. */
	private static final int MIN_CREDITS = 1;
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	
	/** A validator for the course's name */
	private CourseNameValidator validator = new CourseNameValidator();
	/** A list of students in the course */
	private CourseRoll roll;
	
	/**
	 * Construct a course object with values for all fields
	 * 
	 * @param name          the course's name
	 * @param title         the course's title
	 * @param section       the course's section
	 * @param credits       the course's credit hours
	 * @param instructorId  the course's instructor
	 * @param enrollmentCap the course's enrollment capacity
	 * @param meetingDays   the course's meeting time
	 * @param startTime     the course's starting time
	 * @param endTime       the course's ending time
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays,
			int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
		roll = new CourseRoll(this, enrollmentCap);
	}

	/**
	 * Construct a course object with the name, title, section, credits,
	 * instructorId and meetingDays fields for courses that are arranged
	 * 
	 * @param name          the course's name
	 * @param title         the course's title
	 * @param section       the course's section
	 * @param credits       the course's credit hours
	 * @param instructorId  the course's instructor
	 * @param enrollmentCap the course's enrollment capacity
	 * @param meetingDays   the course's meeting time
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays) {
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}

	/**
	 * Returns the course's name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name. If the name is null, has a length less than 5 or more
	 * than 8, does not contain a space between letter characters and number
	 * characters, has less than 1 or more than 4 letter characters, and not exactly
	 * three trailing digit characters, an IllegalArgumentException is thrown.
	 * 
	 * @param name the name to set
	 * @throws IllegalArgumentException if the name parameter is invalid
	 */
	private void setName(String name) {
		try {
			if (validator.isValid(name)) {
				this.name = name;
			}
			else {
				throw new IllegalArgumentException("Invalid course name.");
			}
		}
		catch (InvalidTransitionException e) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
	}

	/**
	 * Returns the course's section
	 * 
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the course's section. If the section is not exactly three digits it is
	 * invalid and an IllegalArgumentException will be thrown.
	 * 
	 * @param section the section to set
	 * @throws IllegalArgumentException if section parameter is invalid
	 */
	public void setSection(String section) {
		if (section == null || section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		}
		for (int i = 0; i < section.length(); i++) {
			if (!Character.isDigit(section.charAt(i))) {
				throw new IllegalArgumentException("Invalid section.");
			}
		}

		this.section = section;
	}

	/**
	 * Returns the course's credits
	 * 
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the course's credits. If the credit hours are not a number or not in the
	 * range 1 to 5, they are invalid and an IllegalArgumentException will be
	 * thrown.
	 * 
	 * @param credits the credits to set
	 * @throws IllegalArgumentException if credits parameter is invalid.
	 */
	public void setCredits(int credits) {
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}

		this.credits = credits;
	}

	/**
	 * Returns the course instructor's id
	 * 
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the course instructor's id. If the instructor's id is null or an empty
	 * string, it is invalid an an IllegalArgumentException will be thrown.
	 * 
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException if instructor id parameter is invalid.
	 */
	public void setInstructorId(String instructorId) {
		if ("".equals(instructorId)) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}
		this.instructorId = instructorId;
	}
	
	/**
	 * Sets the meeting days and start and end times. A meeting day is invalid if it
	 * consists of any characters other than ‘M’, ‘T’, ‘W’, ‘H’, ‘F’, or ‘A’, or has
	 * a duplicate character. If ‘A’ is in the meeting days list, it must be the
	 * only character. The start time is invalid if it is not between 0000 and 2359
	 * or if it is an invalid military time. The end time is invalid if it is not
	 * between 0000 and 2359 or if it is an invalid military time. The end time is
	 * invalid if it is less than the start time (i.e., no overnight classes). The
	 * start time and/or end time is invalid if it is listed when meeting days is
	 * ‘A’
	 * 
	 * @param meetingDays the days of the week the course meets
	 * @param startTime   the starting time of the course
	 * @param endTime     the ending time of the course
	 * @throws IllegalArgumentException if the meeting day or time fields are
	 *                                  invalid.
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null || "".equals(meetingDays)) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if ("A".equals(meetingDays)) { // Arranged
			if (startTime != 0 || endTime != 0) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		} else { // Not arranged
			int countM = 0;
			int countT = 0;
			int countW = 0;
			int countH = 0;
			int countF = 0;
			for (int i = 0; i < meetingDays.length(); i++) {
				if (meetingDays.charAt(i) == 'M') {
					countM++;
				} else if (meetingDays.charAt(i) == 'T') {
					countT++;
				} else if (meetingDays.charAt(i) == 'W') {
					countW++;
				} else if (meetingDays.charAt(i) == 'H') {
					countH++;
				} else if (meetingDays.charAt(i) == 'F') {
					countF++;
				} else {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}
	
			if (countM > 1 || countT > 1 || countW > 1 || countH > 1 || countF > 1) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		}
	
		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Returns a string array containing the Course name, section, title, and meeting string
	 * @return a string array with the Course name, section, title, and meeting string
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] shortDisplay = new String[5];
		shortDisplay[0] = getName();
		shortDisplay[1] = getSection();
		shortDisplay[2] = getTitle();
		shortDisplay[3] = getMeetingString();
		shortDisplay[4] = "" + roll.getOpenSeats();
		return shortDisplay;
	}
	
	/**
	 * Returns a string array containing the Course name, section, title, credits, instructorId, meeting string, 
	 * empty string (for a field that Event will have that Course does not)
	 * @return a string array with the Course name, section, title, credits, instructorId, meeting string, and an empty space
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] longDisplay = new String[7];
		longDisplay[0] = getName();
		longDisplay[1] = getSection();
		longDisplay[2] = getTitle();
		longDisplay[3] = Integer.toString(getCredits());
		longDisplay[4] = getInstructorId();
		longDisplay[5] = getMeetingString();
		longDisplay[6] = "";
		return longDisplay;
	}
	
	/**
	 * Returns true if a given activity is a duplicate of this Course.
	 * Two Courses are considered duplicates if they have the same name.
	 * @param activity the activity being compared with this activity
	 * @return true if the given activity is a duplicate
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		if (getClass() != activity.getClass()) {
			return false;
		}
		Course other = (Course) activity;
		return getName().equals(other.getName());
	}
	
	/**
	 * Returns a comma separated value String of all Course fields.
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if ("A".equals(getMeetingDays())) {
			return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + "," + getMeetingDays();
		}
		return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + "," + getMeetingDays() + ","
				+ getStartTime() + "," + getEndTime();
	}

	/**
	 * Generates a unique hash code based this course's parameters
	 * @return hashCode for Course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * Compares a given Course for equality towards this Course based on all fields
	 * @param obj the object being compared
	 * @return true if the objects have the same fields and are both Courses
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Compares this Course with another Course. 
	 * Returns a negative integer, zero, or a positive integer if this 
	 * Course is less than, equal to, or greater than the other Course.
	 * Courses are ordered by title then section number
	 * and a course is less than when their title or section number
	 * is less than alphabetically (depending on which parameters are the
	 * same).
	 * @param c the Course being compared
	 * @return the integer representing if this Course is less than, equal to, or greater than the other Course
	 * @throws NullPointerException if the given Course is null
	 */
	@Override
	public int compareTo (Course c) {
		if (c == null) {
			throw new NullPointerException("Course is null.");
		}
		
		if (getName().equals(c.getName())) {
			if (getSection().equals(c.getSection())) {
				// Name and section number is the same
				return 0;
			}
			else if (getSection().compareTo(c.getSection()) < 0) {
				// Section is lower
				return -1;
			}
			else {
				// Section is higher
				return 1;
			}
		}
		else if (getName().compareTo(c.getName()) < 0) {
			// Name is lower alphabetically
			return -1;
		}
		else {
			// Name is higher alphabetically
			return 1;
		}
	}
	
	/**
	 * Returns the CourseRoll for the Course
	 * @return CourseRoll for Course
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}
}
