package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Interface used to compare objects with fields that cannot overlap. For example, an event class
 * may implement this interface in order to check whether or not one event's time overlaps with another.
 */
public interface Conflict {
	/**
	 * Checks whether or not an activity is in conflict with another activity. They are
	 * in conflict if at least one day and one time overlap between the two.
	 * @param possibleConflictingActivity the activity that is being checked for a time conflict
	 * @throws ConflictException if the activities are in conflict
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
}
