package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Exception class for Invalid Transitions in FSM
 * @author Shreyash Jain
 */
public class InvalidTransitionException extends Exception {
	
	/** ID used for serialization */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs an invalid transition exception given a message
	 * @param message the message of the exception
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}
	
	/**
	 * Constructs an invalid transition exception with the default message
	 */
	public InvalidTransitionException() {
		super("Invalid FSM Transition.");
	}
}
