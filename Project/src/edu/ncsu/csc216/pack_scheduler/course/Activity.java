package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Class representing an activity with a title, meet days, and meet times.
 * @author Steven Saleeb
 * @author Liam Yeager
 */
public abstract class Activity implements Conflict {

	/** The most hours in a day. */
	private static final int UPPER_HOUR = 24;
	/** The most minutes in an hour. */
	private static final int UPPER_MINUTE = 60;
	/** Course's title. */
	private String title;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;
	
	/**
	 * Class constructor for Activity
	 * @param title the title of the activity
	 * @param meetingDays the list of days of the week the activity happens
	 * @param startTime the start time in military time of the activity
	 * @param endTime the end time in military time of the activity
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
        super();
        setTitle(title);
        setMeetingDaysAndTime(meetingDays, startTime, endTime);
    }
	
	/**
	 * Returns the course's title
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the course's title. If the title is null or an empty string it is
	 * invalid and an IllegalArgumentException is thrown.
	 * 
	 * @param title the title to set
	 * @throws IllegalArgumentException if title parameter is invalid
	 */
	public void setTitle(String title) {
		if (title == null || "".equals(title)) {
			throw new IllegalArgumentException("Invalid title.");
		}
	
		this.title = title;
	}

	/**
	 * Returns the course's meeting days
	 * 
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Returns the course's start time
	 * 
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the course's end time
	 * 
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Sets the meeting days and start and end times. The start time is invalid if it is not between 0000 and 2359
	 * or if it is an invalid military time. The end time is invalid if it is not
	 * between 0000 and 2359 or if it is an invalid military time. The end time is
	 * invalid if it is less than the start time (i.e., no overnight activities).
	 * 
	 * @param meetingDays the days of the week the course meets
	 * @param startTime   the starting time of the course
	 * @param endTime     the ending time of the course
	 * @throws IllegalArgumentException if the meeting day or time fields are
	 *                                  invalid.
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		int startTimeHours = startTime / 100;
		int startTimeMinutes = startTime % 100;
		int endTimeHours = endTime / 100;
		int endTimeMinutes = endTime % 100;

		if (startTimeHours < 0 || startTimeHours >= UPPER_HOUR) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (startTimeMinutes < 0 || startTimeMinutes >= UPPER_MINUTE) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (endTimeHours < 0 || endTimeHours >= UPPER_HOUR) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (endTimeMinutes < 0 || endTimeMinutes >= UPPER_MINUTE) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		if (endTime < startTime) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
	
		this.meetingDays = meetingDays;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Returns a string with the days and times of a course in standard time
	 * 
	 * @return a string with the days and times of a course in standard time
	 */
	public String getMeetingString() {
		if ("A".equals(meetingDays)) {
			return "Arranged";
		}
	
		return "" + meetingDays + " " + getTimeString(startTime) + "-" + getTimeString(endTime);
	
	}

	/**
	 * Converts an integer with military time into a string of standard time
	 * 
	 * @param time the military time
	 * @return the time as a string in standard time
	 */
	private static String getTimeString(int time) {
		int hours = time / 100;
		int minutes = time % 100;
		String end = "";
		if (minutes == 0) {
			end += "0";
		}
	
		if (hours >= 12) {
			if (hours != 12) {
				hours -= 12;
			}
			end += "PM";
		} else {
			if (hours == 0) {
				hours += 12;
			}
			end += "AM";
		}
	
		return "" + hours + ":" + minutes + end;
	}
	
	/**
	 * Returns a string that is used to populate the rows of the course catalog and student schedule
	 * @return a string array of information about the activity
	 */
	public abstract String[] getShortDisplayArray();
	
	/**
	 * Returns a string that is used to display the final schedule
	 * @return a string array of detailed information about the activity
	 */
	public abstract String[] getLongDisplayArray();
	
	/**
	 * Returns true if a given activity is a duplicate of this activity
	 * @param activity the activity being compared with this activity
	 * @return true if the given activity is a duplicate
	 */
	public abstract boolean isDuplicate(Activity activity);

	/**
	 * Checks whether or not an activity is in conflict with another activity. They are
	 * in conflict if at least one day and at least one time overlap between the two.
	 * @param possibleConflictingActivity the activity that is being checked for a time conflict
	 * @throws ConflictException if the activities are in conflict
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		// Check if either activity is arranged
		if (!"A".equals(getMeetingDays()) && !"A".equals(possibleConflictingActivity.getMeetingDays())) {
			// Check if any of the days are the same
			boolean sameDays = false;
			for (int i = 0; i < getMeetingDays().length(); i++) {
				for (int j = 0; j < possibleConflictingActivity.getMeetingDays().length(); j++) {
					if (getMeetingDays().charAt(i) == possibleConflictingActivity.getMeetingDays().charAt(j)) {
						sameDays = true;
					}
				}
			}
			// If any of the meeting days are the same, check if the times overlap
			if (sameDays && 
					getStartTime() <= possibleConflictingActivity.getEndTime() && 
					possibleConflictingActivity.getStartTime() <= getEndTime()) {
				throw new ConflictException();
			}
		}
	}

	/**
	 * Generates a unique hash code based this activity's parameters
	 * @return hashCode for Activity
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Compares a given Activity for equality towards this Activity based on all fields
	 * @param obj the object being compared
	 * @return true if the objects have the same fields and are both Activities
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
}