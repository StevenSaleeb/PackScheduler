
package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the Faculty class to ensure that everything is behaving 
 * correctly including constructor validation, setters, equals,
 * hashCode, and toString formatting
 * @author Steven Saleeb
 */
class FacultyTest {
	
	

	/**
	 * Tests that the constructor throws an exception when maxCourses is outside the allowed range
	 */
    @Test
    void testConstructorInvalidMaxCourses() {
        assertThrows(IllegalArgumentException.class, () ->
            new Faculty("A", "B", "C", "a@ncsu.edu", "pw", 0)
        );

        assertThrows(IllegalArgumentException.class, () ->
            new Faculty("A", "B", "C", "a@ncsu.edu", "pw", 4)
        );
    }

    /**
     * Tests that setMaxCourses correctly updates the value when given valid input
     */
    @Test
    void testSetMaxCoursesValid() {
        Faculty f = new Faculty("Lin", "Moon", "lm", "lm@ncsu.edu", "pw", 1);
        f.setMaxCourses(3);
        assertEquals(3, f.getMaxCourses());
    }

    /**
     * Tests that setMaxCourses throws an exception for values outside the allowed range
     */
    @Test
    void testSetMaxCoursesInvalid() {
        Faculty f = new Faculty("Lin", "Moon", "lm", "lm@ncsu.edu", "pw", 1);

        assertThrows(IllegalArgumentException.class, () -> f.setMaxCourses(0));
        assertThrows(IllegalArgumentException.class, () -> f.setMaxCourses(4));
    }

    /**
     * Tests the equals method for matching objects, if it is not matching and if it is null
     */
    @Test
    void testEqualsMethod() {
        Faculty a = new Faculty("Tom", "Ray", "tray", "t@ncsu.edu", "pw", 2);
        Faculty b = new Faculty("Tom", "Ray", "tray", "t@ncsu.edu", "pw", 2);
        Faculty c = new Faculty("Tom", "Ray", "tray", "t@ncsu.edu", "pw", 1);

        assertTrue(a.equals(b));
        assertFalse(a.equals(c));
        assertFalse(a == null);
    }

    /**
     * Tests that hashCode returns the same value for equal objects and different values for unequal ones
     */
    @Test
    void testHashCodeMethod() {
        Faculty x = new Faculty("Kai", "Jones", "kj", "kj@ncsu.edu", "pw1", 1);
        Faculty y = new Faculty("Kai", "Jones", "kj", "kj@ncsu.edu", "pw1", 1);
        Faculty z = new Faculty("Kai", "Jones", "kj", "kj@ncsu.edu", "pw1", 3);

        assertEquals(x.hashCode(), y.hashCode());
        assertNotEquals(x.hashCode(), z.hashCode());
    }

    /**
     * Tests that toString returns the correct string of the Faculty object
     */
    @Test
    void testToStringFormat() {
        Faculty f = new Faculty("Rita", "Young", "ry", "ry@ncsu.edu", "hashed", 2);
        assertEquals("Rita,Young,ry,ry@ncsu.edu,hashed,2", f.toString());
    }

}
