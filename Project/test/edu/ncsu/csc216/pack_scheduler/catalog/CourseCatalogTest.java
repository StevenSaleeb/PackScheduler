package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Activity;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;


/**
 * Tests the CourseCatalog class
 * @author Sarah Heckman
 * @author Shreyash Jain 
 */
public class CourseCatalogTest {
	
	/** Valid course records */
	private final String validTestFile = "test-files/course_records.txt";
	/** Invalid course records */
	private final String invalidTestFile = "test-files/invalid_course_records.txt";
	
	/** Course name */
	private static final String NAME = "CSC216";
	/** Course title */
	private static final String TITLE = "Software Development Fundamentals";
	/** Course section */
	private static final String SECTION = "001";
	/** Course credits */
	private static final int CREDITS = 3;
	/** Course instructor id */
	private static final String INSTRUCTOR_ID = "sesmith5";
	/** Course enrollment capacity */
	private static final int ENROLLMENT_CAP = 11;
	/** Course meeting days */
	private static final String MEETING_DAYS = "TH";
	/** Course start time */
	private static final int START_TIME = 1330;
	/** Course end time */
	private static final int END_TIME = 1445;
	
	/**
	 * Resets course_records.txt for use in other tests.
	 */
	@Before
	public void setUp() throws Exception {
		//Reset course_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "starter_course_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "course_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}
	
	/**
	 * Tests CourseCatalog().
	 */
	@Test
	public void testCourseCatalog() {
		//Test with invalid file.  Should have an empty catalog. 
		CourseCatalog ws1 = new CourseCatalog();
		ws1.loadCoursesFromFile(invalidTestFile);
		assertEquals(0, ws1.getCourseCatalog().length);
		assertEquals(0, ws1.getCourseCatalog().length);
		ws1.saveCourseCatalog("test-files/actual_empty_export.txt");
		checkFiles("test-files/expected_empty_export.txt", "test-files/actual_empty_export.txt");
		
	}
	
	/**
	 * Test CourseCatalog.getCourseFromCatalog().
	 */
	@Test
	public void testGetCourseFromCatalog() {
		CourseCatalog ws = new CourseCatalog();
		RegistrationManager.getInstance().getFacultyDirectory().addFaculty("First", "Last", "sesmith5", "@.", "pw", "pw", 3);
		ws.loadCoursesFromFile(validTestFile);
		
		//Attempt to get a course that doesn't exist
		assertNull(ws.getCourseFromCatalog("CSC492", "001"));
		
		//Attempt to get a course that does exist
		Activity c = new Course(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME);
		assertEquals(c, ws.getCourseFromCatalog("CSC216", "001"));
	}

	/**
	 * Test CourseCatalog.addCourseToCatalog().
	 */
	@Test
	public void testAddCourseToCatalog() {
		CourseCatalog ws = new CourseCatalog();
		ws.loadCoursesFromFile(validTestFile);
		assertTrue(ws.addCourseToCatalog("CSC246", "Operating Systems", "004", 3, "instructorId", 100, "MW", 828, 829));
		assertEquals(14, ws.getCourseCatalog().length);
		String [] course = ws.getCourseCatalog()[0];
		assertEquals(5, course.length);
		assertEquals("CSC116", course[0]);
		assertEquals("001", course[1]);
		assertEquals("Intro to Programming - Java", course[2]);
		assertEquals("MW 9:10AM-11:00AM", course[3]);
		assertEquals("10", course[4]);
		
		assertFalse(ws.addCourseToCatalog(NAME, TITLE, "002", CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
	}
	
	
	/**
	 * Test CourseCatalog.removeCourseFromCatalog().
	 */
	@Test
	public void testRemoveCourseFromCatalog() {
		CourseCatalog ws = new CourseCatalog();
		
		//Attempt to remove from empty schedule
		assertFalse(ws.removeCourseFromCatalog(NAME, SECTION));
		
		//Add some courses and remove them
		assertTrue(ws.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
		assertTrue(ws.addCourseToCatalog("CSC226", "a", "001", 4, "ad", 100, "MW", 1101, 1200));
		assertTrue(ws.addCourseToCatalog("CSC116", "a", "002", 4, "ad", 100, "MW", 1333, 1444));
		assertEquals(3, ws.getCourseCatalog().length);
		
		//Check that removing a course that doesn't exist when there are 
		//scheduled courses doesn't break anything
		assertFalse(ws.removeCourseFromCatalog("CSC755", SECTION));
		assertEquals(3, ws.getCourseCatalog().length);
		
		//Remove Exercise
		assertTrue(ws.removeCourseFromCatalog(NAME, SECTION));
		assertEquals(2, ws.getCourseCatalog().length);
		
		//Remove CSC226
		assertTrue(ws.removeCourseFromCatalog("CSC226", "001"));
		assertEquals(1, ws.getCourseCatalog().length);
		
		//Remove CSC116
		assertTrue(ws.removeCourseFromCatalog("CSC116", "002"));
		assertEquals(0, ws.getCourseCatalog().length);
		
	}
	
	/**
	 * Test CourseCatalog.newCourseCatalog()
	 */
	@Test
	public void testNewCourseCatalog() {
		CourseCatalog ws = new CourseCatalog();
		
		//Add some courses and reset catalog
		assertTrue(ws.addCourseToCatalog(NAME, TITLE, SECTION, CREDITS, INSTRUCTOR_ID, ENROLLMENT_CAP, MEETING_DAYS, START_TIME, END_TIME));
		assertTrue(ws.addCourseToCatalog("CSC226", "av", "005", 4, "ad", 100, "MW", 1004, 1005));
		assertTrue(ws.addCourseToCatalog("CSC116", "av", "003", 4, "ad", 100, "MW", 1006, 1009));
		assertEquals(3, ws.getCourseCatalog().length);
		
		ws.newCourseCatalog();
		assertEquals(0, ws.getCourseCatalog().length);
		
		//Check that resetting doesn't break future adds
		assertTrue(ws.addCourseToCatalog("CSC230", "av", "003", 4, "ad", 100, "MW", 1016, 1029));
		assertEquals(1, ws.getCourseCatalog().length);
	}
	
	/**
	 * Test CourseCatalog.getCourseCatalog().
	 */
	@Test
	public void testGetCourseCatalog() {
		CourseCatalog ws = new CourseCatalog();
		ws.loadCoursesFromFile(validTestFile);
		
		//Get the catalog and make sure contents are correct
		//Name, section, title
		String [][] catalog = ws.getCourseCatalog();
		//Row 0
		assertEquals("CSC116", catalog[0][0]);
		assertEquals("001", catalog[0][1]);
		assertEquals("Intro to Programming - Java", catalog[0][2]);
		//Row 1
		assertEquals("CSC116", catalog[1][0]);
		assertEquals("002", catalog[1][1]);
		assertEquals("Intro to Programming - Java", catalog[1][2]);
		//Row 2
		assertEquals("CSC116", catalog[2][0]);
		assertEquals("003", catalog[2][1]);
		assertEquals("Intro to Programming - Java", catalog[2][2]);
		//Row 3
		assertEquals("CSC216", catalog[3][0]);
		assertEquals("001", catalog[3][1]);
		assertEquals("Software Development Fundamentals", catalog[3][2]);
		//Row 4
		assertEquals("CSC216", catalog[4][0]);
		assertEquals("002", catalog[4][1]);
		assertEquals("Software Development Fundamentals", catalog[4][2]);
		//Row 5
		assertEquals("CSC216", catalog[5][0]);
		assertEquals("601", catalog[5][1]);
		assertEquals("Software Development Fundamentals", catalog[5][2]);
		//Row 6
		assertEquals("CSC217", catalog[6][0]);
		assertEquals("202", catalog[6][1]);
		assertEquals("Software Development Fundamentals Lab", catalog[6][2]);
		//Row 7
		assertEquals("CSC217", catalog[7][0]);
		assertEquals("211", catalog[7][1]);
		assertEquals("Software Development Fundamentals Lab", catalog[7][2]);
		//Row 8
		assertEquals("CSC217", catalog[8][0]);
		assertEquals("223", catalog[8][1]);
		assertEquals("Software Development Fundamentals Lab", catalog[8][2]);
		//Row 9
		assertEquals("CSC217", catalog[9][0]);
		assertEquals("601", catalog[9][1]);
		assertEquals("Software Development Fundamentals Lab", catalog[9][2]);
		//Row 10
		assertEquals("CSC226", catalog[10][0]);
		assertEquals("001", catalog[10][1]);
		assertEquals("Discrete Mathematics for Computer Scientists", catalog[10][2]);
		//Row 11
		assertEquals("CSC230", catalog[11][0]);
		assertEquals("001", catalog[11][1]);
		assertEquals("C and Software Tools", catalog[11][2]);
		//Row 12
		assertEquals("CSC316", catalog[12][0]);
		assertEquals("001", catalog[12][1]);
		assertEquals("Data Structures and Algorithms", catalog[12][2]);
	}
	
	
	/**
	 * Test CourseCatalog.saveCourseCatalog().
	 */
	@Test
	public void testSaveCourseCatalog() {
		//Test that empty schedule exports correctly
		CourseCatalog ws = new CourseCatalog();
		ws.saveCourseCatalog("test-files/actual_empty_export.txt");
		checkFiles("test-files/expected_empty_export.txt", "test-files/actual_empty_export.txt");
		
		//Add courses and test that exports correctly
		ws.addCourseToCatalog("CSC216", "Software Development Fundamentals", "002", 3, "ixdoming", 10, "MW", 1330, 1445);
		ws.addCourseToCatalog("CSC226", "Discrete Mathematics for Computer Scientists", "001", 3, "tmbarnes", 10, "MWF", 935, 1025);
		assertEquals(2, ws.getCourseCatalog().length);
		ws.saveCourseCatalog("test-files/actual_schedule_export.txt");
		checkFiles("test-files/expected_schedule_export.txt", "test-files/actual_schedule_export.txt");
	}
	
	/** 
	 * Tests that hashCode is consistent
	 */ 
	@Test public void testHashCodeConsistency() { 
		CourseCatalog hash = new CourseCatalog(); 
		int hash1 = hash.hashCode(); 
		int hash2 = hash.hashCode(); 
		assertEquals(hash1, hash2, "hashCode()"); 
		}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new File(expFile));
			 Scanner actScanner = new Scanner(new File(actFile));) {
			
			while (actScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			if (expScanner.hasNextLine()) {
				fail();
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}

}


