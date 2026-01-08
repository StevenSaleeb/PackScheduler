package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Tests the Student object.
 * @author Steven Saleeb
 * @author Shreyash Jain
 * @author Liam Yeager
 */
class StudentRecordIOTest {

	/** The path of a file that contains valid students in it */
	private String validTestFile = "test-files/student_records.txt";
	/** The path of a file that contains invalid students in it */
	private String invalidTestFile = "test-files/invalid_student_records.txt";
	/** The path of a file that contains a list of invalid students that are missing each field */
	private String invalidTestFileEachField = "test-files/invalid_student_records_each_field.txt";
	/** The invalid path of a nonexistent file */
	private String invalidFilePath = "test-files/thisisnotafileintestfiles.txt";
	
	/** A valid line that would display on a file representing a student, line 4 of student_records.txt */
	private String validStudent0 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";
	/** A valid line that would display on a file representing a student, line 7 of student_records.txt */
	private String validStudent1 = "Lane,Berg,lberg,sociis@non.org,pw,14";
	/** A valid line that would display on a file representing a student, line 5 of student_records.txt */
	private String validStudent2 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
	/** A valid line that would display on a file representing a student, line 6 of student_records.txt */
	private String validStudent3 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
	/** A valid line that would display on a file representing a student, line 3 of student_records.txt */
	private String validStudent4 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
	/** A valid line that would display on a file representing a student, line 9 of student_records.txt */
	private String validStudent5 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
	/** A valid line that would display on a file representing a student, line 1 of student_records.txt */
	private String validStudent6 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
	/** A valid line that would display on a file representing a student, line 10 of student_records.txt */
	private String validStudent7 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";
	/** A valid line that would display on a file representing a student, line 2 of student_records.txt */
	private String validStudent8 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
	/** A valid line that would display on a file representing a student, line 8 of student_records.txt */
	private String validStudent9 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";
	
	
	/** An array of valid lines of student information, representing the ten line student_records.txt test file */
	private String [] validStudents = {validStudent0, validStudent1, validStudent2, validStudent3, validStudent4, validStudent5,
	        validStudent6, validStudent7, validStudent8, validStudent9};

	/** The hashed version of the password a student would have */
	private String hashPW;
	/** The specific algorithm the hash function uses */
	private static final String HASH_ALGORITHM = "SHA-256";

	/**
	 * Set up for test
	 */
	@BeforeEach
	public void setUp() {
	    try {
	        String password = "pw";
	        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
	        digest.update(password.getBytes());
	        hashPW = Base64.getEncoder().encodeToString(digest.digest());
	        
	        for (int i = 0; i < validStudents.length; i++) {
	            validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
	        }
	    } catch (NoSuchAlgorithmException e) {
	        fail("Unable to create hash during setup");
	    }
	}
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
			 Scanner actScanner = new Scanner(new FileInputStream(actFile));) {
			
			while (expScanner.hasNextLine()  && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals(exp, act, "Expected: " + exp + " Actual: " + act); 
				//The third argument helps with debugging!
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
	
	/**
	 * Tests the readStudentRecords method with all valid students in a file
	 */
	@Test
	void testReadStudentRecordsValidStudents() {
		try {
			SortedList<Student> students = StudentRecordIO.readStudentRecords(validTestFile);
			for(int i = 0; i < students.size(); i++) {
				assertEquals(validStudents[i], students.get(i).toString());
			}
		}
		catch (FileNotFoundException e){
			fail("Unable to read file at " + validTestFile);
		}
	}
	
	/**
	 * Tests the readStudentRecords method with no valid students in a file
	 */
	@Test
	void testReadStudentRecordsInvalidStudents() {
		try {
			SortedList<Student> students = StudentRecordIO.readStudentRecords(invalidTestFile);
			assertEquals(0, students.size());
		}
		catch(FileNotFoundException e) {
			fail("Unable to read file at " + invalidTestFile);
		}
	}
	
	/**
	 * Tests the readStudentRecords method with a list of invalid students that are missing each field
	 */
	@Test
	void testReadStudentRecordsInvalidStudentsEachField() {
		try {
			SortedList<Student> students = StudentRecordIO.readStudentRecords(invalidTestFileEachField);
			assertEquals(0, students.size());
		}
		catch(FileNotFoundException e) {
			fail("Unable to read file at " + invalidTestFileEachField);
		}
	}
	
	/**
	 * Tests the readStudentRecords method with an invalid file path
	 */
	@Test
	void testReadStudentRecordsInvalidFilepath() {
		assertThrows(FileNotFoundException.class,
				() -> StudentRecordIO.readStudentRecords(invalidFilePath));
	}
	
	/**
	 * Tests the writeStudentRecords function's output to a file
	 */
	@Test
	void testWriteStudentRecords() {
		SortedList<Student> courses = new SortedList<Student>();
		courses.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 15));
		
		try {
			StudentRecordIO.writeStudentRecords("test-files/actual_student_records.txt", courses);
		} catch (IOException e) {
			fail("Cannot write to course records file");
		}
		
		checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");
	}
	
	/**
	 * Tests the writeStudentRecord's IOException that should be thrown when it cannot write to a file
	 */
	@Test
	public void testWriteStudentRecordsNoPermissions() {
		SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
		
		Exception exception = assertThrows(IOException.class, 
				() -> StudentRecordIO.writeStudentRecords("/home/sesmith5/actual_student_records.txt", students));
		assertEquals("/home/sesmith5/actual_student_records.txt (No such file or directory)", exception.getMessage());
	}

}
