package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the LinkedAbstractList class to ensure its methods work correctly and focuses on verifying that the set method replaces elements properly
 * and throws exceptions for invalid operations
 * @author Steven Saleeb
 */
class LinkedAbstractListTest {

	/**
	 * Tests that the size of the list updates correctly after adding elements
	 * his test class checks different LinkedAbstractList features like adding elements, removing elements, 
	 * getting and setting elements, and checking the list size and it also checks 
	 * that the correct exceptions are thrown when invalid actions are done
	 */
	@Test
	void testSize() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(99);
		assertEquals(0, list.size());
		list.add(0, "A");
		assertEquals(1, list.size());
		list.add(1, "B");
		assertEquals(2, list.size());
	}

	/**
	 *  Tests that a new LinkedAbstractList is created correctly
	 */
	@Test
	void testLinkedAbstractList() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(99);
		assertEquals(0, list.size());
	}

	/**
	 * Tests when adding an elements that all the elements are shifting correctly
	 * Tests if the method throws exceptions when there is something invalid
	 */
	@Test
	void testAdd() {
		
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(12);
		
		// Checks adding a null object
		assertThrows(NullPointerException.class, 
				() -> list.add(0, null));
		assertEquals(0, list.size());
		// Checks adding an object out of bounds
		assertThrows(IndexOutOfBoundsException.class, 
				() -> list.add(1, "S"));
		assertEquals(0, list.size());
		
		
		list.add(0, "A");
		assertEquals(1, list.size());
		assertEquals("A", list.get(0));
		
		// Checks adding a duplicate
		assertThrows(IllegalArgumentException.class, 
				() -> list.add(1, "A"));
		assertEquals(1, list.size());
		
		list.add(1, "B");
		assertEquals(2, list.size());
		assertEquals("A", list.get(0));
		assertEquals("B", list.get(1));
		list.add(2, "C");
		assertEquals(3, list.size());
		assertEquals("A", list.get(0));
		assertEquals("B", list.get(1));
		assertEquals("C", list.get(2));
		
		list.add(0, "D");
		assertEquals(4, list.size());
		assertEquals("D", list.get(0));
		assertEquals("A", list.get(1));
		assertEquals("B", list.get(2));
		assertEquals("C", list.get(3));
		
		list.add(2, "E");
		assertEquals(5, list.size());
		assertEquals("D", list.get(0));
		assertEquals("A", list.get(1));
		assertEquals("E", list.get(2));
		assertEquals("B", list.get(3));
		assertEquals("C", list.get(4));
		
		// Testing grow correctly doubles after 10 additions to the list
		list.add(0, "F");
		list.add(0, "G");
		list.add(0, "H");
		list.add(0, "I");
		list.add(0, "J");
		list.add(0, "K");
		list.add(0, "L");
		assertEquals(12, list.size());
		
		// Test capacity
		assertThrows(IllegalArgumentException.class, 
					() -> list.add(0, "Z"));
		
	}

	/**
	 * Tests removing an element from the middle or beginning or at the end of the list
	 * Tests that the method throw an exceptions when something is invalid
	 */
	@Test
	void testRemoveInt() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(99);
		list.add(0, "A");
		list.add(1, "B");
		list.add(2, "C");
		list.add(3, "D");
		assertEquals(4, list.size());

		// Remove from middle
		String removed = list.remove(1); 
		assertEquals("B", removed);
		assertEquals(3, list.size());
		assertEquals("A", list.get(0));
		assertEquals("C", list.get(1));
		assertEquals("D", list.get(2));

		// Remove first element
		removed = list.remove(0);
		assertEquals("A", removed);
		assertEquals(2, list.size());
		assertEquals("C", list.get(0));
		assertEquals("D", list.get(1));

		// Remove last element
		removed = list.remove(1);
		assertEquals("D", removed);
		assertEquals(1, list.size());
		assertEquals("C", list.get(0));

		// Remove with invalid index
		assertThrows(IndexOutOfBoundsException.class, 
				() -> list.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, 
				() -> list.remove(5));
	}

	/**
	 * Tests the set method to ensure it correctly replaces elements and 
	 * throws exceptions for null, duplicate, and invalid indexes
	 */
	@Test
    void testSet() {
        // Create list with capacity 5
        LinkedAbstractList<String> list = new LinkedAbstractList<>(5);
        list.add(0, "Steven");
        list.add(1, "Bob");
        list.add(2, "Charlie");
        String old = list.set(1, "David");
        assertEquals("Bob", old);      
        assertEquals("David", list.get(1)); 
        assertThrows(NullPointerException.class, () -> list.set(1, null));
        assertThrows(IllegalArgumentException.class, () -> list.set(2, "Steven"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "X"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(3, "Y"));
    }

	/**
	 * Tests that the method returns the correct element at the correct index 
	 * Tests that the method throw exceptions when something is invalid
	 */
	@Test
	void testGetInt() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(99);
		list.add(0, "A");
		list.add(1, "B");
		list.add(2, "C");

		assertEquals("A", list.get(0));
		assertEquals("B", list.get(1));
		assertEquals("C", list.get(2));
		
		assertThrows(IndexOutOfBoundsException.class, 
				() -> list.get(-1));
		assertThrows(IndexOutOfBoundsException.class, 
				() -> list.get(3));
	}

}
