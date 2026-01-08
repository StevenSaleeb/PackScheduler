package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests the CourseNameValidatorFSM class.
 * @author Steven Saleeb
 * @author Liam Yeager
 */
class CourseNameValidatorFSMTest {

	/** Course name validator object to be used for testing */
	public CourseNameValidatorFSM cnv = new CourseNameValidatorFSM();
	
	@Test
	void testStateLValid() {
		try {
			assertTrue(cnv.isValid("C123"));
		}
		catch (InvalidTransitionException e) {
			fail("Invalid transition exception thrown.");
		}
	}
	
	/**
	 * Tests the StateLL for a valid course name
	 */
	@Test
	void testStateLLValid() {
		try {
			assertTrue(cnv.isValid("CA123"));
		}
		catch (InvalidTransitionException e) {
			fail("Invalid transition exception thrown.");
		}
	}
	
	/**
	 * Tests the StateLLL for a valid course name
	 */
	@Test
	void testStateLLLValid() {
		try {
			assertTrue(cnv.isValid("CAB123"));
		}
		catch (InvalidTransitionException e) {
			fail("Invalid transition exception thrown.");
		}
	}
	
	/**
	 * Tests the StateLLLL for a valid course name
	 */
	@Test
	void testStateLLLLValid() {
		try {
			assertTrue(cnv.isValid("CABE123"));
		}
		catch (InvalidTransitionException e) {
			fail("Invalid transition exception thrown.");
		}
	}
	
	/**
	 * Tests the StateDDD for a valid course name
	 */
	@Test
	void testStateDDDValid() {
		try {
			assertTrue(cnv.isValid("B123"));
		}
		catch (InvalidTransitionException e) {
			fail("Invalid transition exception thrown.");
		}
	}
	
	/**
	 * Tests the Suffix state for a valid course name
	 */
	@Test
	void testSuffixValid() {
		try {
			assertTrue(cnv.isValid("B123A"));
		}
		catch (InvalidTransitionException e) {
			fail("Invalid transition exception thrown.");
		}
	}
	
	/**
	 * Tests the StateInitial state with an invalid transition that is not a digit or letter
	 */
	@Test
	void testStateInitialInvalid() {
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("@"));
		assertEquals("Course name can only contain letters and digits.", e1.getMessage());
	}
	
	/**
	 * Tests the StateInitial state with an invalid transition that is not a letter
	 */
	@Test
	void testStateInitialInvalidDigit() {
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("9"));
		assertEquals("Course name must start with a letter.", e1.getMessage());
	} 
	
	/**
	 * Tests the StateLLLL state with an invalid transition
	 */
	@Test
	void testStateLLLLInvalid() {
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("ABCDE"));
		assertEquals("Course name cannot start with more than 4 letters.", e1.getMessage());
	}
	
	/**
	 * Tests the StateD state with an invalid transition
	 */
	@Test
	void testStateDInvalid() {
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("A1D"));
		assertEquals("Course name must have 3 digits.", e1.getMessage());
	}
	
	/**
	 * Tests the StateDD state with an invalid transition
	 */
	@Test
	void testStateDDInvalid() {
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("A11D"));
		assertEquals("Course name must have 3 digits.", e1.getMessage());
	}
	
	/**
	 * Tests the StateDDD state with an invalid transition
	 */
	@Test
	void testStateDDDInvalid() {
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("A1112"));
		assertEquals("Course name can only have 3 digits.", e1.getMessage());
	}
	
	/**
	 * Tests the Suffix state with an invalid transition of a letter
	 */
	@Test
	void testSuffixInvalidLetter() {
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("A111AB"));
		assertEquals("Course name can only have a 1 letter suffix.", e1.getMessage());
	}
	
	/**
	 * Tests the Suffix state with an invalid transition of a digit
	 */
	@Test
	void testSuffixInvalidDigit() {
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> cnv.isValid("A111A2"));
		assertEquals("Course name cannot contain digits after the suffix.", e1.getMessage());
	}
	
}
