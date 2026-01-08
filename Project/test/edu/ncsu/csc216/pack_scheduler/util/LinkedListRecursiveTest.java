package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the LinkedListRecursive class to make sure all methods work correctly
 * and it checks adding, setting, removing, and other basic list behaviors Each
 * test makes sure the list behaves the way it should.
 * 
 * @author Steven Saleeb
 */
class LinkedListRecursiveTest {

	/**
	 * Makes sure a new list starts empty and has size 0
	 */
	@Test
	void testLinkedListRecursive() {
		LinkedListRecursive<String> list = new LinkedListRecursive<String>();
		assertEquals(0, list.size());
		assertTrue(list.isEmpty());
	}

	/**
	 * Tests the add methods it checks that elements can be added at different
	 * positions, that the list grows in size
	 */
	@Test
	void testAdd() {
		LinkedListRecursive<String> l = new LinkedListRecursive<>();
		l.add(0, "A");
		l.add(1, "B");
		l.add(2, "C");
		l.add(3, "D");
		assertEquals(4, l.size());

		assertEquals("A", l.get(0));
		assertEquals("B", l.get(1));
		assertEquals("C", l.get(2));
		assertEquals("D", l.get(3));

		assertThrows(NullPointerException.class, () -> l.add(null));
		assertThrows(IllegalArgumentException.class, () -> l.add("A"));
	}

	/**
	 * Tests the linked list's set method and it makes sure an element at a given
	 * index can be replaced, and that the old element is returned.
	 */
	@Test
	void testSet() {
		LinkedListRecursive<String> l = new LinkedListRecursive<>();
		l.add(0, "A");
		l.add(1, "B");
		l.add(2, "C");
		l.add(3, "D");
		l.set(0, "Zero");
		l.set(3, "Three");
		l.set(2, "Two");
		assertEquals(4, l.size());

		assertEquals("Zero", l.get(0));
		assertEquals("B", l.get(1));
		assertEquals("Two", l.get(2));
		assertEquals("Three", l.get(3));

		assertThrows(NullPointerException.class, () -> l.set(0, null));

	}

	/**
	 * Tests removing by index it makes sure the list removes the correct element,
	 * the size updates correctly and removing from invalid indexes throws an
	 * exception
	 */
	@Test
	void testRemove() {
		LinkedListRecursive<String> l = new LinkedListRecursive<>();
		l.add(0, "A");
		l.add(1, "B");
		l.add(2, "C");
		l.add(3, "D");
		l.add(4, "E");
		assertEquals(5, l.size());
		l.remove(0);
		l.remove(1);
		l.remove(2);
		assertEquals(2, l.size());

		assertEquals("B", l.get(0));
		assertEquals("D", l.get(1));

		assertThrows(IndexOutOfBoundsException.class, () -> l.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> l.remove(2));
	}

	/**
	 * Tests removing by object Checks removing from an empty list, the front, the
	 * middle and the end
	 */
	@Test
	void testRemove2() {
		LinkedListRecursive<String> list = new LinkedListRecursive<>();

		assertFalse(list.remove("X"));

		list.add("A");
		list.add("B");
		list.add("C");

		assertTrue(list.remove("A"));
		assertEquals(2, list.size());
		assertEquals("B", list.get(0));

		list.add(1, "X");
		assertTrue(list.remove("X"));
		assertEquals(2, list.size());
		assertEquals("B", list.get(0));
		assertEquals("C", list.get(1));

		assertTrue(list.remove("C"));
		assertEquals(1, list.size());
		assertEquals("B", list.get(0));

		assertFalse(list.remove("Z"));
	}

}
