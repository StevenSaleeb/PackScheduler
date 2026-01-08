package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Tests the functionality of Schedule
 * @author Shreyash Jain
 */
public class ScheduleTest {
	
	/** Schedule object for testing */
	private Schedule schedule;
	
	/** 
	 * Constructs a new Schedule before each test 
	 */
	@BeforeEach
	public void setUp() {
		schedule = new Schedule();
	}

	/**
	 * Tests Schedule constructor
	 */
	@Test
	public void testSchedule() {
		assertEquals("My Schedule", schedule.getTitle()); 
		assertEquals(0, schedule.getScheduledCourses().length);
	}
	/**
	 * Tests Schedule.addCourseToSchedule()
	 */
	@Test
	public void testAddCourseToSchedule() {
		assertTrue(schedule.addCourseToSchedule(new Course("CSC216", "Software Development Fundamentals", "002", 3, "sjain35", 10, "MW", 1000, 1100)));
		assertEquals(1, schedule.getScheduledCourses().length);
		schedule.addCourseToSchedule(new Course("CSC316", "Data Structures and Algorithims", "001", 3, "sjain35", 10, "MW", 1200, 1300));
		assertEquals(2, schedule.getScheduledCourses().length);
		
		Exception a = assertThrows(IllegalArgumentException.class, () -> schedule.addCourseToSchedule(new Course("CSC216", "Software Development Fundamentals", "002", 3, "sjain35", 10, "MW", 1000, 1100)));
		assertEquals("You are already enrolled in CSC216", a.getMessage());
		
		Exception b = assertThrows(IllegalArgumentException.class, () -> schedule.addCourseToSchedule(new Course("CSC316", "Data Structures and Algorithims", "001", 3, "sjain35", 10, "MT", 1000, 1300)));
		assertEquals("The course cannot be added due to a conflict.", b.getMessage());	
		
		assertThrows(NullPointerException.class, () -> schedule.addCourseToSchedule(null));
		
	}
	/**
	 * Tests Schedule.removeCourseFromSchedule()
	 */
	@Test
	public void testRemoveCourseFromSchedule() {
		schedule.addCourseToSchedule(new Course("CSC216", "Software Development Fundamentals", "002", 3, "sjain35", 10, "MW", 1000, 1100));
		assertEquals(1, schedule.getScheduledCourses().length);
		assertFalse(schedule.removeCourseFromSchedule(new Course("CSC316", "Data Structures and Algorithims", "001", 3, "sjain35", 10, "MT", 1000, 1300)));
		assertTrue(schedule.removeCourseFromSchedule(new Course("CSC216", "Software Development Fundamentals", "002", 3, "sjain35", 10, "MW", 1000, 1100)));
		assertEquals(0, schedule.getScheduledCourses().length);
		
	}
	/**
	 * Tests Schedule.resetSchedule()
	 */
	@Test
	public void testResetSchedule() {
		schedule.addCourseToSchedule(new Course("CSC216", "Software Development Fundamentals", "002", 3, "sjain35", 10, "MW", 1000, 1100));
		schedule.setTitle("Title");
		assertEquals("Title", schedule.getTitle());
		assertEquals(1, schedule.getScheduledCourses().length);
		schedule.resetSchedule();
		assertEquals("My Schedule", schedule.getTitle()); 
		assertEquals(0, schedule.getScheduledCourses().length);
	}
	/**
	 * Tests Schedule.getScheduledCourses()
	 */
	@Test
	public void testGetScheduledCourses() {
		assertTrue(schedule.addCourseToSchedule(new Course("CSC216", "Software Development Fundamentals", "002", 3, "sjain35", 10, "MW", 1000, 1100)));
		assertEquals(1, schedule.getScheduledCourses().length);
		schedule.addCourseToSchedule(new Course("CSC316", "Data Structures and Algorithims", "001", 3, "sjain35", 10, "MW", 1200, 1300));
		assertEquals(2, schedule.getScheduledCourses().length);
		
		assertAll("Course",
				() -> assertEquals("CSC216", schedule.getScheduledCourses()[0][0]),
				() -> assertEquals("002", schedule.getScheduledCourses()[0][1]),
				() -> assertEquals("Software Development Fundamentals", schedule.getScheduledCourses()[0][2]),
				() -> assertEquals("MW 10:00AM-11:00AM", schedule.getScheduledCourses()[0][3]));
		
		assertAll("Course",
				() -> assertEquals("CSC316", schedule.getScheduledCourses()[1][0]),
				() -> assertEquals("001", schedule.getScheduledCourses()[1][1]),
				() -> assertEquals("Data Structures and Algorithims", schedule.getScheduledCourses()[1][2]),
				() -> assertEquals("MW 12:00PM-1:00PM", schedule.getScheduledCourses()[1][3]));
		
	}
	/**
	 * Tests Schedule.setTitle()
	 */
	@Test
	public void testSetTitle() {
		Exception e = assertThrows(IllegalArgumentException.class, () -> schedule.setTitle(null));
		assertEquals("Title cannot be null.", e.getMessage());
		schedule.setTitle("Title");
		assertEquals("Title", schedule.getTitle());
	}
	/**
	 * Tests Schedule.getTitle()
	 */
	@Test
	public void testGetTitle() {
		assertEquals("My Schedule", schedule.getTitle());
		schedule.setTitle("Title");
		assertEquals("Title", schedule.getTitle());
		
	}

}
