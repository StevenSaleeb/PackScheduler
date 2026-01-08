package edu.ncsu.csc216.pack_scheduler.course;

/**
 * An exception that is thrown when two activities are in conflict.
 * In conflict activities are defined as having at least one day when their times overlap.
 */
public class ConflictException extends Exception {

	/** ID used for serialization */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs an conflict exception given a message
	 * @param message the message of the exception
	 */
	public ConflictException(String message) {
		super(message);
	}
	
	/**
	 * Constructs an conflict exception with the default message
	 */
	public ConflictException() {
		super("Schedule conflict.");
	}
	
}
