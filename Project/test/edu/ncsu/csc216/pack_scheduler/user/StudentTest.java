package edu.ncsu.csc216.pack_scheduler.user;


import static org.junit.jupiter.api.Assertions.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.Test;

/**
 * Tests the Student object.
 * @author SarahHeckman
 * @author Steven Saleeb
 */
public class StudentTest {
	
	/** Test Student's first name. */
	private String firstName = "first";
	/** Test Student's last name */
	private String lastName = "last";
	/** Test Student's id */
	private String id = "flast";
	/** Test Student's email */
	private String email = "first_last@ncsu.edu";
	/** Test Student's hashed password */
	private String hashPW;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	//This is a block of code that is executed when the StudentTest object is
	//created by JUnit.  Since we only need to generate the hashed version
	//of the plaintext password once, we want to create it as the StudentTest object is
	//constructed.  By automating the hash of the plaintext password, we are
	//not tied to a specific hash implementation.  We can change the algorithm
	//easily.
	{
		try {
			String plaintextPW = "password";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(plaintextPW.getBytes());
			this.hashPW = Base64.getEncoder().encodeToString(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			fail("An unexpected NoSuchAlgorithmException was thrown.");
		}
	}
	
	/**
	 * Tests toString method and returns the correct informations as a string.
	 */
	@Test
	public void testToString() {
		Student s1 = new Student(firstName, lastName, id, email, hashPW);
		assertEquals("first,last,flast,first_last@ncsu.edu," + hashPW + ",18", s1.toString());
	}
	
	/**
     * Tests the constructor with all fields.
     */
    @Test
    void testConstructorValid() {
        Student s = new Student(firstName, lastName, id, email, hashPW, 15);
        assertEquals(firstName, s.getFirstName());
        assertEquals(lastName, s.getLastName());
        assertEquals(id, s.getId());
        assertEquals(email, s.getEmail());
        assertEquals(hashPW, s.getPassword());
        assertEquals(15, s.getMaxCredits());
    }
    
    
	/**
	 * Tests hashCode and equals methods with students that 
	 * are the same and different.
	 */
	@Test
	public void testHashCodeEqualsObjects() {
		Student s1 = new Student(firstName, lastName, id, email, hashPW);
        Student s2 = new Student(firstName, lastName, id, email, hashPW);
        assertEquals(s1.hashCode(), s2.hashCode());
        Student s3 = new Student("diff", lastName, id, email, hashPW);
        assertNotEquals(s1.hashCode(), s3.hashCode());
	}
	

	/**
	 * Tests setFirstName when the first name is valid.
	 */
	@Test
	void testSetFirstNameValid() {
		Student s = new Student(firstName, lastName, id, email, hashPW);
		s.setFirstName("Steven");
		assertEquals("Steven", s.getFirstName());
	}

	/**
	 * Tests setFirstName when the first name is Invalid because it is null.
	 */
	@Test
	void testSetFirstNameInvalid() {
		Student s = new Student(firstName, lastName, id, email, hashPW);
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> s.setFirstName(null));
		assertEquals("Invalid first name", e.getMessage());
	}
	
	/**
	 * Tests setFirstName when the first name is Invalid because it is empty.
	 */
	@Test
	void testSetFirstNameInvalidEmpty() {
	    Student s = new Student(firstName, lastName, id, email, hashPW);
	    Exception e = assertThrows(IllegalArgumentException.class,
	            () -> s.setFirstName("")); // empty string branch
	    assertEquals("Invalid first name", e.getMessage());
	}

	/**
	 * Tests setLastName when last name is valid.
	 */
	@Test
	void testSetLastNameValid() {
		Student s = new Student(firstName, lastName, id, email, hashPW);
		s.setLastName("Saleeb");
		assertEquals("Saleeb", s.getLastName());
	}
	
	/**
	 * Tests setLastName when last name is Invalid because it is null.
	 */
	@Test
	void testSetLastNameInvalidNull() {
	    Student s = new Student(firstName, lastName, id, email, hashPW);
	    Exception e = assertThrows(IllegalArgumentException.class,
	            () -> s.setLastName(null));
	    assertEquals("Invalid last name", e.getMessage());
	}

	/**
	 * Tests setLastName when last name is Invalid because it is empty.
	 */
	@Test
	void testSetLastNameInvalidEmpty() {
		Student s = new Student(firstName, lastName, id, email, hashPW);
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> s.setLastName(""));
		assertEquals("Invalid last name", e.getMessage());
	}

	/**
	 * Tests SetId when the ID is valid.
	 */
	@Test
	void testSetIdValid() {
		Student s = new Student(firstName, lastName, id, email, hashPW);
		assertEquals(id, s.getId());
	}

	/**
	 * Tests setId with invalid ID because it is null.
	 */
	@Test
	void testSetIdInvalid() {
		Exception e = assertThrows(IllegalArgumentException.class,
				() -> new Student(firstName, lastName, null, email, hashPW));
		assertEquals("Invalid id", e.getMessage());
	}
	
	/**
	 * Tests Invalid ID when it is null.
	 */
	@Test
	void testInvalidId() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student("Steven", "Saleeb", null, "ssaleb@ncsu.edu", "password", 16));
		assertEquals("Invalid id", e1.getMessage());
	}

	/**
	 * Tests first name when it is null.
	 */
	@Test
	void testInvalidFirstName() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student(null, "Saleeb", "ssaleb", "ssaleb@ncsu.edu", "password", 16));
		assertEquals("Invalid first name", e1.getMessage());
	}
	
	/**
	 * Tests last name when it is null
	 */
	@Test
	void testInvalidLastName() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student("Steven", null, "ssaleb", "ssaleb@ncsu.edu", "password", 16));
		assertEquals("Invalid last name", e1.getMessage());
	}
	
	
	/**
	 * Tests setEmail when Email is valid.
	 */
	@Test
	void testSetEmailValid() {
		Student s = new Student(firstName, lastName, id, email, hashPW);
		s.setEmail("ssalebs@ncsu.edu");
		assertEquals("ssalebs@ncsu.edu", s.getEmail());
	}

	/**
	 * Tests setEmail when Email is invalid because it is null.
	 */
	@Test
	void testSetEmailInvalid() {
		Student s = new Student(firstName, lastName, id, email, hashPW);
		assertThrows(IllegalArgumentException.class, () -> s.setEmail(null));
		assertThrows(IllegalArgumentException.class, () -> s.setEmail("bademail"));
		assertThrows(IllegalArgumentException.class, () -> s.setEmail("a@b"));
	}
	
	/**
	 *  Tests setEmail when Email is invalid because it is missing "@".
	 */
	@Test
	void testSetEmailInvalidMissingAt() {
	    Student s = new Student(firstName, lastName, id, email, hashPW);
	    assertThrows(IllegalArgumentException.class, () -> s.setEmail("ssalebs"));
	}

	/**
	 * Tests setPassword when password is valid.
	 */
	@Test
	void testSetPasswordValid() {
		Student s = new Student(firstName, lastName, id, email, hashPW);
		s.setPassword("openIt");
		assertEquals("openIt", s.getPassword());
	}

	/**
	 *  Tests setPassword when password is invalid.
	 */
	@Test
	void testSetPasswordInvalid() {
		Student s = new Student(firstName, lastName, id, email, hashPW);
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> s.setPassword(""));
		assertEquals("Invalid password", e1.getMessage());
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> s.setPassword(null));
		assertEquals("Invalid password", e2.getMessage());
	}

	/**
	 * Tests setMaxCredits when max credits number is valid.
	 */
	@Test
	void testSetMaxCreditsValid() {
		Student s = new Student(firstName, lastName, id, email, hashPW);
		s.setMaxCredits(12);
		assertEquals(12, s.getMaxCredits());
	}
	
	/**
	 * Tests setMaxCredits when max credits number is invalid.
	 */
	@Test
	void testSetMaxCreditsInvalid() {
		Student s = new Student(firstName, lastName, id, email, hashPW);
		assertThrows(IllegalArgumentException.class, () -> s.setMaxCredits(2));
		assertThrows(IllegalArgumentException.class, () -> s.setMaxCredits(19));
	}

	/**
	 * Tests equals method with two students that are the same.
	 */
	@Test
	void testEqualsObjectValid() {
		Student s1 = new Student(firstName, lastName, id, email, hashPW);
		Student s2 = new Student(firstName, lastName, id, email, hashPW);
		assertTrue(s1.equals(s2));
	}

	/**
	 * Tests equals method when a student is different and compare it with null.
	 */
	@Test
	void testEqualsObjectInvalid() {
		Student s1 = new Student(firstName, lastName, id, email, hashPW);
		Student s2 = new Student("diff", lastName, id, email, hashPW);
		Student s3 = new Student(firstName, lastName, id, email, "diff");
		assertFalse(s1.equals(s2));
		assertFalse(s1 == null);
		assertFalse(s1.equals(s3));
	}
	
	/**
	 * Tests the compare to function implemented from the Comparable interface.
	 * Students should be ordered by last name, then first name, then their unity id.
	 */
	@Test
	void testCompareTo() {
		Student student1 = new Student("a", "b", "ab", "@email.com", "pw");
		Student student2 = new Student("a", "d", "ad", "@email.com", "pw");
		Student student3 = new Student("b", "d", "bd", "@email.com", "pw");
		Student student4 = new Student("b", "d", "bd1", "@email.com", "pw");
		Student student5 = new Student("b", "d", "bd1", "@email.com", "pw");
		
		assertEquals(-1, student1.compareTo(student2));
		assertEquals(1, student2.compareTo(student1));
		assertEquals(-1, student2.compareTo(student3));
		assertEquals(1, student3.compareTo(student2));
		assertEquals(-1, student1.compareTo(student3));
		assertEquals(-1, student3.compareTo(student4));
		assertEquals(1, student4.compareTo(student3));
		assertEquals(0, student4.compareTo(student5));
		
		try {
			student1.compareTo(null);
			fail("Compare to function did not throw null pointer exception.");
		}
		catch (NullPointerException e) {
			assertEquals("Given student is null.", e.getMessage());
		}
		
	}

}
