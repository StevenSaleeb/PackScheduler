package edu.ncsu.csc216.pack_scheduler.directory;


import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Tests StudentDirectory.
 * @author Sarah Heckman
 * @author Shreyash Jain
 * @author Steven Saleeb
 */
public class StudentDirectoryTest {
	
	/** Valid course records */
	private final String validTestFile = "test-files/student_records.txt";
	/** Test first name */
	private static final String FIRST_NAME = "Stu";
	/** Test last name */
	private static final String LAST_NAME = "Dent";
	/** Test id */
	private static final String ID = "sdent";
	/** Test email */
	private static final String EMAIL = "sdent@ncsu.edu";
	/** Test password */
	private static final String PASSWORD = "pw";
	/** Test max credits */
	private static final int MAX_CREDITS = 15;
	
	/**
	 * Resets course_records.txt for use in other tests.
	 * @throws Exception if something fails during setup.
	 */
	@Before
	public void setUp() throws Exception {		
		//Reset student_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "expected_full_student_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "student_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
	}

	/**
	 * Tests StudentDirectory().
	 */
	@Test
	public void testStudentDirectory() {
		//Test that the StudentDirectory is initialized to an empty list
		StudentDirectory sd = new StudentDirectory();
		assertFalse(sd.removeStudent("sesmith5"));
		assertEquals(0, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.testNewStudentDirectory().
	 */
	@Test
	public void testNewStudentDirectory() {
		//Test that if there are students in the directory, they 
		//are removed after calling newStudentDirectory().
		StudentDirectory sd = new StudentDirectory();
		
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
		
		sd.newStudentDirectory();
		assertEquals(0, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.loadStudentsFromFile().
	 */
	@Test
	public void testLoadStudentsFromFile() {
		StudentDirectory sd = new StudentDirectory();
				
		//Test valid file
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
	}

	/**
	 * Tests StudentDirectory.addStudent().
	 */
	@Test
	public void testAddStudent() {
		StudentDirectory sd = new StudentDirectory();
		
		//Test valid Student
		sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		String [][] studentDirectory = sd.getStudentDirectory();
		assertEquals(1, studentDirectory.length);
		assertEquals(FIRST_NAME, studentDirectory[0][0]);
		assertEquals(LAST_NAME, studentDirectory[0][1]);
		assertEquals(ID, studentDirectory[0][2]);
	}

	/**
	 * Tests StudentDirectory.removeStudent().
	 */
	@Test
	public void testRemoveStudent() {
		StudentDirectory sd = new StudentDirectory();
				
		//Add students and remove
		sd.loadStudentsFromFile(validTestFile);
		assertEquals(10, sd.getStudentDirectory().length);
		assertTrue(sd.removeStudent("efrost"));
		String [][] studentDirectory = sd.getStudentDirectory();
		assertEquals(9, studentDirectory.length);
		assertEquals("Lane", studentDirectory[1][0]);
		assertEquals("Berg", studentDirectory[1][1]);
		assertEquals("lberg", studentDirectory[1][2]);
	}

	/**
	 * Tests StudentDirectory.saveStudentDirectory().
	 */
	@Test
	public void testSaveStudentDirectory() {
		StudentDirectory sd = new StudentDirectory();
		
		//Add a student
		sd.addStudent("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 15);
		assertEquals(1, sd.getStudentDirectory().length);
		sd.saveStudentDirectory("test-files/actual_student_records.txt");
		checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try {
			Scanner expScanner = new Scanner(new FileInputStream(expFile));
			Scanner actScanner = new Scanner(new FileInputStream(actFile));
			
			while (expScanner.hasNextLine()) {
				assertEquals(expScanner.nextLine(), actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
	
	/**
	 * Tests StudentDirectory.removeStudent() with more students
	 */
	@Test
	public void testRemoveStudent2() {
		StudentDirectory directory = new StudentDirectory();
		
		directory.loadStudentsFromFile("test-files/expected_full_student_records.txt");
		assertEquals(10, directory.getStudentDirectory().length);
		assertTrue(directory.removeStudent("zking"));
		String [][] studentDirectory = directory.getStudentDirectory();
		assertEquals(9, studentDirectory.length);
		assertEquals("Hansen", studentDirectory[4][1]);
		assertEquals("Althea", studentDirectory[5][0]);
		assertEquals("Hicks", studentDirectory[5][1]);
	}
	
	/**
	 * Tests StudentDirectory.addStudent() with more students
	 */
	@Test
	public void testAddStudent2() {
		StudentDirectory directory = new StudentDirectory();
	
		directory.addStudent("Griffith", "Stone", "gstone", "porta@magnamalesuadavel.net", "pw", "pw", 17);
		String [][] studentDirectory = directory.getStudentDirectory();
		assertEquals(1, studentDirectory.length);
		assertEquals("Griffith", studentDirectory[0][0]);
		assertEquals("Stone", studentDirectory[0][1]);
		assertEquals("gstone", studentDirectory[0][2]);
	}
	
	/**
	 * Tests StudentDirectory class's removeStudent and getStudentDirectory return values
	 */
	@Test
	public void testStudentDirectory2() {
		StudentDirectory directory = new StudentDirectory();
		assertFalse(directory.removeStudent("sjain35"));
		assertFalse(directory.removeStudent(ID));
		assertEquals(0, directory.getStudentDirectory().length);
	}
	
	/**
	 * Tests StudentDirectory.getStudentDirectory() function
	 */
	@Test
	public void testGetStudentDirectory() {
		StudentDirectory directory = new StudentDirectory();
		
		directory.addStudent("Griffith", "Stone", "gstone", "porta@magnamalesuadavel.net", "pw", "pw", 17);
		String [][] studentDirectory = directory.getStudentDirectory();
		assertEquals(1, studentDirectory.length);
		assertEquals("Griffith", studentDirectory[0][0]);
		assertEquals("Stone", studentDirectory[0][1]);
		assertEquals("gstone", studentDirectory[0][2]);
		
		directory.addStudent("Cassandra", "Schwartz", "cschwartz", "cschwartz,semper@imperdietornare.co.uk", "pw", "pw", 4);
		studentDirectory = directory.getStudentDirectory();
		assertEquals(2, studentDirectory.length);
		assertEquals("Griffith", studentDirectory[1][0]);
		assertEquals("Schwartz", studentDirectory[0][1]);
		assertEquals("cschwartz", studentDirectory[0][2]);
		
		directory.addStudent(LAST_NAME, FIRST_NAME, ID, EMAIL, PASSWORD, PASSWORD, MAX_CREDITS);
		studentDirectory = directory.getStudentDirectory();
		assertEquals(3, studentDirectory.length);
		assertEquals("Griffith", studentDirectory[1][0]);
		assertEquals("Schwartz", studentDirectory[0][1]);
		assertEquals(ID, studentDirectory[2][2]);
		
	}
	
	/**
	 * Tests StudentDirectory class's loading and reset functions
	 */
	@Test 
	public void testNewStudentDirectory2() {
		StudentDirectory directory = new StudentDirectory();
		
		directory.loadStudentsFromFile("test-files/expected_full_student_records.txt");
		assertEquals(10, directory.getStudentDirectory().length);
		directory.newStudentDirectory();
		assertEquals(0, directory.getStudentDirectory().length);
	}
	
	/**
	 * Tests StudentDirectory.loadStudentsFromFile() function with different file
	 */
	@Test
	public void testLoadStudentsFromFile2() {
		StudentDirectory directory = new StudentDirectory();
		
		directory.loadStudentsFromFile("test-files/expected_student_records.txt");
		assertEquals(1, directory.getStudentDirectory().length);
	}
	
	/**
	 * Tests StudentDirectory.saveStudentDirectory() with more students
	 */
	@Test
	public void testSaveStudentDirectory2() {
		StudentDirectory directory = new StudentDirectory();
		
		directory.addStudent("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 15);
		directory.addStudent("Cassandra", "Schwartz", "cschwartz", "semper@imperdietornare.co.uk", "pw", "pw", 4);
		directory.addStudent("Shannon", "Hansen", "shansen", "convallis.est.vitae@arcu.ca", "pw", "pw", 14);
		directory.addStudent("Demetrius", "Austin", "daustin", "Curabitur.egestas.nunc@placeratorcilacus.co.uk", "pw", "pw", 18);
		directory.addStudent("Raymond", "Brennan", "rbrennan", "litora.torquent@pellentesquemassalobortis.ca", "pw", "pw", 12);
		directory.addStudent("Emerald", "Frost", "efrost", "adipiscing@acipsumPhasellus.edu", "pw", "pw", 3);
		directory.addStudent("Lane", "Berg", "lberg", "sociis@non.org", "pw", "pw", 14);
		directory.addStudent("Griffith", "Stone", "gstone", "porta@magnamalesuadavel.net", "pw", "pw", 17);
		directory.addStudent("Althea", "Hicks", "ahicks", "Phasellus.dapibus@luctusfelis.com", "pw", "pw", 11);
		directory.addStudent("Dylan", "Nolan", "dnolan", "placerat.Cras.dictum@dictum.net", "pw", "pw", 5);
		
		assertEquals(10, directory.getStudentDirectory().length);
		directory.saveStudentDirectory("test-files/actual_student_records.txt");
		checkFiles("test-files/expected_full_student_records.txt", "test-files/actual_student_records.txt");
	}
	
	/**
	 * Tests the getStudentById() function from StudentDirectory.
	 */
	@Test 
	public void testGetStudentById() {
		StudentDirectory directory = new StudentDirectory();
		Student zahir = new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 15);
		directory.addStudent("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "pw", "pw", 15);
		directory.addStudent("Cassandra", "Schwartz", "cschwartz", "semper@imperdietornare.co.uk", "pw", "pw", 4);
		directory.addStudent("Shannon", "Hansen", "shansen", "convallis.est.vitae@arcu.ca", "pw", "pw", 14);
		assertEquals(zahir, directory.getStudentById("zking"));
	}
	
	/**
	 * Tests add student with invalid max credits number.
	 */
	@Test
	public void testAddStudentInvalidMaxCredits() {
	    StudentDirectory sd = new StudentDirectory();
	    assertTrue(sd.addStudent(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, PASSWORD, 2));
	    assertTrue(sd.addStudent("steven", "saleeb", "ssalebs", "ssalebs@ncsu.edu", "pass", "pass", 19));
	}
	
	/** 
	 * Tests that hashCode is consistent
	 */ 
	@Test 
	public void testHashCodeConsistency() { 
		StudentDirectory hash = new StudentDirectory(); 
		int hash1 = hash.hashCode(); 
		int hash2 = hash.hashCode(); 
		assertEquals(hash1, hash2, "hashCode()"); 
	}
	
	/**
	 * Tests the addStudent() method's thrown exceptions
	 */
	@Test
	public void testAddStudentExceptions() {
		StudentDirectory sd = new StudentDirectory();
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> sd.addStudent("steven", "saleeb", "ssalebs", "ssalebs@ncsu.edu", "", "pass", 19));
	    assertEquals("Invalid password", e1.getMessage());
	    Exception e2 = assertThrows(IllegalArgumentException.class, () -> sd.addStudent("steven", "saleeb", "ssalebs", "ssalebs@ncsu.edu", "pass", "password", 19));
	    assertEquals("Passwords do not match", e2.getMessage());
	}
	
}
