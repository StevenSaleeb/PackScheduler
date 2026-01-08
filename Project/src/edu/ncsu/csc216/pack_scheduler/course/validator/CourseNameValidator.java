package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Class for validation of course names. 
 * Uses a Finite State Machine for validation.
 * @author Liam Yeager
 */
public class CourseNameValidator {
	
	/** Field representing if the finite state machine ended in a valid state or not */
	private boolean validEndState;
	
	/** Field representing the number of letters that are counted when going through the course name */
	private int letterCount;
	
	/** Field representing the number of digits that are counted when going through the course name */
	private int digitCount;
	
	/** Field representing the current state of the finite state machine */
	private State currentState;
	
	/** Field representing the letter state of the finite state machine */
	private State stateLetter = new LetterState();
	/** Field representing the suffix state of the finite state machine */
	private State stateNumber = new NumberState();
	/** Field representing the initial state of the finite state machine */
	private State stateInitial = new InitialState();
	/** Field representing the number state of the finite state machine */
	private State stateSuffix = new SuffixState();
	
	/**
	 * Constructor for a CourseNameValidator object.
	 */
	public CourseNameValidator() {
		// Fields are handled in the isValid() method
	}
	
	/**
	 * Returns true if a given course name is valid, and false if not.
	 * @param courseName the course name to be validated.
	 * @return true if a given course name is valid, and false if not.
	 * @throws InvalidTransitionException if the name contains an invalid transition
	 */
	public boolean isValid(String courseName) throws InvalidTransitionException {
		currentState = stateInitial;
		
		validEndState = false;
		letterCount = 0;
		digitCount = 0;
		
		// If courseName is null or empty, throw an exception
		if (courseName == null || "".equals(courseName)) {
			throw new InvalidTransitionException("Course name can not be null or empty.");
		}
		
		// Go through each letter of the course name
		for (int i = 0; i < courseName.length(); i++) {
			if (Character.isDigit(courseName.charAt(i))) {
				currentState.onDigit();
			}
			else if (Character.isLetter(courseName.charAt(i))) {
				currentState.onLetter();
			}
			else {
				currentState.onOther();
			}
		}
		
		if ((currentState instanceof NumberState && digitCount == 3) ||
				(currentState instanceof SuffixState)) {
			validEndState = true;
		}
		
		return validEndState;
	}
	
	/**
	 * Abstract inner class representing a state in the finite state machine.
	 * A state has three transitions: letter, digit, or other. Other transitions will throw
	 * an exception.
	 * @author Shreyash Jain
	 */
	private abstract class State {
		
		/**
		 * Method representing a letter transition from the current state
		 * @throws InvalidTransitionException if this transition is invalid
		 */
		public abstract void onLetter() throws InvalidTransitionException;
		
		/**
		 * Method representing a digit transition from the current state
		 * @throws InvalidTransitionException if this transition is invalid
		 */
		public abstract void onDigit() throws InvalidTransitionException;
		
		/**
		 * Method representing a transition that is not letter or digit from the current state
		 * @throws InvalidTransitionException whenever the method is called
		 */
		public void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}
		
	}
	
	/**
	 * Class representing a letter state in the finite state machine.
	 * @author Shreyash Jain
	 */
	private class LetterState extends State {
		
		/** Constant representing the maximum amount of prefix letters a course name can have */
		private static final int MAX_PREFIX_LETTERS = 4;
		
		/**
		 * Method representing a letter transition from the current state
		 * @throws InvalidTransitionException if this transition is invalid
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			letterCount++;
			if (letterCount > MAX_PREFIX_LETTERS) {
				throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			}
		}
		
		/**
		 * Method representing a digit transition from the current state
		 * @throws InvalidTransitionException if this transition is invalid
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			digitCount++;
			currentState = stateNumber;
		}
		
	}
	
	/**
	 * Class representing a suffix state in the finite state machine.
	 * @author Shreyash Jain
	 */
	private class SuffixState extends State {
		
		/**
		 * Method representing a letter transition from the current state
		 * @throws InvalidTransitionException if this transition is invalid
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
		}
		
		/**
		 * Method representing a digit transition from the current state
		 * @throws InvalidTransitionException if this transition is invalid
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
		}
		
	}
	
	/**
	 * Class representing a initial state in the finite state machine.
	 * @author Shreyash Jain
	 */
	private class InitialState extends State {
		
		/**
		 * Method representing a letter transition from the current state
		 */
		@Override
		public void onLetter() {
			letterCount++;
			currentState = stateLetter;
		}
		
		/**
		 * Method representing a digit transition from the current state
		 * @throws InvalidTransitionException if this transition is invalid
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must start with a letter.");
		}
		
	}
	
	/**
	 * Class representing a number state in the finite state machine.
	 * @author Shreyash Jain
	 */
	private class NumberState extends State {
		
		/** Constant representing the maximum amount of numbers a course name can have */
		private static final int COURSE_NUMBER_LENGTH = 3;
		
		/**
		 * Method representing a letter transition from the current state
		 * @throws InvalidTransitionException if this transition is invalid
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			
			if (digitCount == COURSE_NUMBER_LENGTH) {
				currentState = stateSuffix;
			}
			else {
				throw new InvalidTransitionException("Course name must have 3 digits.");
			}
		}
		
		/**
		 * Method representing a digit transition from the current state
		 * @throws InvalidTransitionException if this transition is invalid
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			digitCount++;
			if (digitCount > COURSE_NUMBER_LENGTH) {
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			}
		}
		
	}
}
