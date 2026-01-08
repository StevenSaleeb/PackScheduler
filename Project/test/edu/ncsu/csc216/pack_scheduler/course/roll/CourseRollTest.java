package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests the functionality of the CourseRoll class
 * @author Shreyash Jain
 */
public class CourseRollTest {

	/**
	 * Tests CourseRoll constructor
	 */
	@Test
	public void testCourseRoll() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 9, "A"));
		assertEquals("Invalid enrollment cap", e.getMessage());
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 11, "A");
		CourseRoll courseRoll = c.getCourseRoll();
		assertEquals(11, courseRoll.getEnrollmentCap());
		assertEquals(11, courseRoll.getOpenSeats());
	}

	/**
	 * Tests CourseRoll.getEnrollmentCap()
	 */
	@Test
	public void testGetEnrollmentCap() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 28, "A");
		CourseRoll courseRoll = c.getCourseRoll();
		assertEquals(28, courseRoll.getEnrollmentCap());
	}

	/**
	 * Tests CourseRoll.setEnrollmentCap()
	 */
	@Test
	public void testSetEnrollmentCap() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 100, "A");
		CourseRoll courseRoll = c.getCourseRoll();
		assertEquals(100, courseRoll.getEnrollmentCap());
		courseRoll.setEnrollmentCap(200);
		assertEquals(200, courseRoll.getEnrollmentCap());
	}
	
	/**
	 * Tests CourseRoll.enroll()
	 */
	@Test
	public void testEnroll() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 11, "A");
		CourseRoll courseRoll = c.getCourseRoll();
		Exception e = assertThrows(IllegalArgumentException.class, () -> courseRoll.enroll(null));
		assertEquals("Student cannot be enrolled.", e.getMessage());
		courseRoll.enroll(new Student("Stu", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		assertEquals(10, courseRoll.getOpenSeats());
	}
	
	/**
	 * Tests CourseRoll.drop()
	 */
	@Test
	public void testDrop() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 11, "A");
		CourseRoll courseRoll = c.getCourseRoll();
		Exception e = assertThrows(IllegalArgumentException.class, () -> courseRoll.drop(null));
		assertEquals("Student cannot be dropped.", e.getMessage());
		courseRoll.enroll(new Student("Stu", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		assertEquals(10, courseRoll.getOpenSeats());
		courseRoll.drop(new Student("Stu", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		assertEquals(11, courseRoll.getOpenSeats());
	}
	
	/**
	 * Tests CourseRoll.getOpenSeats()
	 */
	@Test
	public void testGetOpenSeats() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 20, "A");
		CourseRoll courseRoll = c.getCourseRoll();
		courseRoll.enroll(new Student("Stu", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		assertEquals(19, courseRoll.getOpenSeats());
	}
	
	/**
	 * Tests CourseRoll.canEnroll()
	 */
	@Test
	public void testCanEnroll() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 11, "A");
		CourseRoll courseRoll = c.getCourseRoll();
		assertFalse(courseRoll.canEnroll(null));
		courseRoll.enroll(new Student("Stu", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("a", "b", "ab", "abaaa@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("b", "c", "bc", "bcbbb@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("c", "d", "cd", "cdccc@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("d", "e", "de", "deddd@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("e", "f", "ef", "efeee@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("f", "g", "fg", "fgfff@ncsu.edu", "pw", 14));
		assertTrue(courseRoll.canEnroll(new Student("g", "h", "gh", "ghggg@ncsu.edu", "pw", 14)));
		courseRoll.enroll(new Student("g", "h", "gh", "ghggg@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("h", "i", "hi", "hihhh@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("i", "j", "ij", "ijiii@ncsu.edu", "pw", 14));
		assertEquals(1, courseRoll.getOpenSeats());
		
		assertFalse(courseRoll.canEnroll(new Student("Stu", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14)));
		
		courseRoll.enroll(new Student("j", "k", "jk", "jkjjj@ncsu.edu", "pw", 14));
		assertEquals(0, courseRoll.getOpenSeats());
		assertFalse(courseRoll.canEnroll(new Student("Stu", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14)));
	}
	
	/**
	 * Tests CourseRoll's wait list
	 */
	@Test
	public void testWaitlist() {
		Course c = new Course("CSC216", "Programming Concepts - Java", "001", 4, "sesmith5", 10, "A");
		CourseRoll courseRoll = c.getCourseRoll();
		
		assertEquals(0, courseRoll.getNumberOnWaitlist());
		
		courseRoll.enroll(new Student("Stu", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("Stua", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("Stub", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("Stuc", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("Stud", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("Stue", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("Stuf", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("Stug", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("Stuh", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("Stui", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		
		assertEquals(0, courseRoll.getOpenSeats());
		assertEquals(0, courseRoll.getNumberOnWaitlist());
		
		courseRoll.enroll(new Student("Stuj", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("Stuk", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("Stul", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("Stum", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("Stun", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		
		assertEquals(5, courseRoll.getNumberOnWaitlist());
		assertFalse(courseRoll.canEnroll(new Student("Stu", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14)));
		assertFalse(courseRoll.canEnroll(new Student("Stuj", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14)));
		
		
		Exception e = assertThrows(IllegalArgumentException.class, 
				() -> courseRoll.enroll(new Student("Stul", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14)));
		assertEquals("Student cannot be enrolled.", e.getMessage());
		
		courseRoll.enroll(new Student("Stuo", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("Stup", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("Stuq", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("Stur", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.enroll(new Student("Stus", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		assertEquals(10, courseRoll.getNumberOnWaitlist());
		
		e = assertThrows(IllegalArgumentException.class, 
				() -> courseRoll.enroll(new Student("Stut", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14)));
		assertEquals("Student cannot be enrolled.", e.getMessage());
		
		courseRoll.drop(new Student("Stun", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		assertEquals(9, courseRoll.getNumberOnWaitlist());
		
		courseRoll.drop(new Student("Stu", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.drop(new Student("Stua", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.drop(new Student("Stub", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.drop(new Student("Stuc", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.drop(new Student("Stud", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.drop(new Student("Stue", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.drop(new Student("Stuf", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.drop(new Student("Stug", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.drop(new Student("Stuh", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		courseRoll.drop(new Student("Stui", "Dent", "sdent", "sdent@ncsu.edu", "pw", 14));
		
		assertEquals(1, courseRoll.getOpenSeats());
		assertEquals(0, courseRoll.getNumberOnWaitlist());
	}

}
