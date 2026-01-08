package edu.ncsu.csc216.pack_scheduler.directory;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of the FacultyDirectory class
 * @author Shreyash Jain
 */
public class FacultyDirectoryTest {

	/**
	 * Tests FacultyDirectory constructor
	 */
	@Test
	public void testFacultyDirectory() {
		FacultyDirectory facultyDirectory = new FacultyDirectory();
		assertEquals(0, facultyDirectory.getFacultyDirectory().length);
	}

	/**
	 * Tests FacultyDirectory.newFacultyDirectory()
	 */
	@Test
	public void testNewFacultyDirectory() {
		FacultyDirectory facultyDirectory = new FacultyDirectory();
		assertTrue(facultyDirectory.addFaculty("firstName", "lastName", "id", "email@ncsu.edu", "pw", "pw", 2));
		assertEquals(1, facultyDirectory.getFacultyDirectory().length);
		facultyDirectory.newFacultyDirectory();
		assertEquals(0, facultyDirectory.getFacultyDirectory().length);
	}
	

	/**
	 * Tests FacultyDirectory.loadFacultyFromFile()
	 */
	@Test
	public void testLoadFacultyFromFile() {
		FacultyDirectory facultyDirectory = new FacultyDirectory();
		facultyDirectory.loadFacultyFromFile("test-files/invalid_faculty_records.txt");
		assertEquals(0, facultyDirectory.getFacultyDirectory().length);
		facultyDirectory.loadFacultyFromFile("test-files/faculty_records.txt");
		assertEquals(8, facultyDirectory.getFacultyDirectory().length);
	}
	
	/**
	 * Tests FacultyDirectory.saveFacultyDirectory()
	 */
	@Test
	public void testSaveFacultyDirectory() {
		FacultyDirectory facultyDirectory = new FacultyDirectory();
		Exception e = assertThrows(IllegalArgumentException.class, () -> facultyDirectory.saveFacultyDirectory("test-files\\/file.txt"));
		assertEquals("Unable to write to file test-files\\/file.txt", e.getMessage());
	}

	/**
	 * Tests FacultyDirectory.addFaculty()
	 */
	@Test
	public void testAddFaculty() {
		FacultyDirectory facultyDirectory = new FacultyDirectory();
		assertEquals(0, facultyDirectory.getFacultyDirectory().length);
		assertTrue(facultyDirectory.addFaculty("firstName", "lastName", "id", "email@ncsu.edu", "pw", "pw", 2));
		assertEquals(1, facultyDirectory.getFacultyDirectory().length);
		assertFalse(facultyDirectory.addFaculty("firstName", "lastName", "id", "email@ncsu.edu", "pw", "pw", 2));
		assertEquals(1, facultyDirectory.getFacultyDirectory().length);
		
		Exception e = assertThrows(IllegalArgumentException.class, () -> facultyDirectory.addFaculty("firstName", "lastName", "id", "email@ncsu.edu", null, "", 2));
		assertEquals("Invalid password", e.getMessage());
		e = assertThrows(IllegalArgumentException.class, () -> facultyDirectory.addFaculty("firstName", "lastName", "id", "email@ncsu.edu", "password", "pw", 2));
		assertEquals("Passwords do not match", e.getMessage());
	}

	/**
	 * Tests FacultyDirectory.removeFaculty()
	 */
	@Test
	public void testRemoveFaculty() {
		FacultyDirectory facultyDirectory = new FacultyDirectory();
		assertTrue(facultyDirectory.addFaculty("firstName", "lastName", "id", "email@ncsu.edu", "pw", "pw", 2));
		assertTrue(facultyDirectory.addFaculty("firstName", "lastName", "firstlast", "email@ncsu.edu", "pw", "pw", 2));
		assertEquals(2, facultyDirectory.getFacultyDirectory().length);
		assertTrue(facultyDirectory.removeFaculty("id"));
		assertFalse(facultyDirectory.removeFaculty("firstNamelastName"));
		assertEquals(1, facultyDirectory.getFacultyDirectory().length);
		assertEquals("firstlast", facultyDirectory.getFacultyDirectory()[0][2]);
	}

	/**
	 * Tests FacultyDirectory.getFacultyDirectory()
	 */
	@Test
	public void testGetFacultyDirectory() {
		FacultyDirectory facultyDirectory = new FacultyDirectory();
		assertTrue(facultyDirectory.addFaculty("firstName", "lastName", "id", "email@ncsu.edu", "pw", "pw", 2));
		assertTrue(facultyDirectory.addFaculty("firstname", "lastname", "firstlast", "firstlast@ncsu.edu", "pw", "pw", 2));
		assertEquals(2, facultyDirectory.getFacultyDirectory().length);
		assertEquals("firstName", facultyDirectory.getFacultyDirectory()[0][0]);
		assertEquals("lastName", facultyDirectory.getFacultyDirectory()[0][1]);
		assertEquals("id", facultyDirectory.getFacultyDirectory()[0][2]);
		assertEquals("firstname", facultyDirectory.getFacultyDirectory()[1][0]);
		assertEquals("lastname", facultyDirectory.getFacultyDirectory()[1][1]);
		assertEquals("firstlast", facultyDirectory.getFacultyDirectory()[1][2]);
	}

	/**
	 * Tests FacultyDirectory.getFacultyById()
	 */
	@Test
	public void testGetFacultyById() {
		FacultyDirectory facultyDirectory = new FacultyDirectory();
		assertTrue(facultyDirectory.addFaculty("firstName", "lastName", "id", "email@ncsu.edu", "pw", "pw", 2));
		assertTrue(facultyDirectory.addFaculty("firstName", "lastName", "firstlast", "email@ncsu.edu", "pw", "pw", 2));
		assertEquals("id", facultyDirectory.getFacultyById("id").getId());
	}

}
