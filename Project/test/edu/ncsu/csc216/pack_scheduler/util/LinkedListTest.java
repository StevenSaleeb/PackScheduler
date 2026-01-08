/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests the LinkedList class
 * @author Liam Yeager
 */
class LinkedListTest {
	
	/**
	 * Tests the linked list's add method
	 */
	@Test
	void testAdd() {
		LinkedList<String> l = new LinkedList<>();
		l.add(0, "A");
		l.add(1, "B");
		l.add(2, "C");
		l.add(3, "D");
		assertEquals(4, l.size());
		
		assertEquals("A", l.get(0));
		assertEquals("B", l.get(1));
		assertEquals("C", l.get(2));
		assertEquals("D", l.get(3));
		
		assertThrows(NullPointerException.class,
				() -> l.add(null));
		assertThrows(IllegalArgumentException.class,
				() -> l.add("A"));
	}
	
	/**
	 * Tests the linked list's set method
	 */
	@Test
	void testSet() {
		LinkedList<String> l = new LinkedList<>();
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
		
		assertThrows(NullPointerException.class,
				() -> l.set(0, null));
		assertThrows(IllegalArgumentException.class,
				() -> l.set(0, "B"));
		
		assertThrows(IndexOutOfBoundsException.class,
				() -> l.set(-1, "Set"));
		assertThrows(IndexOutOfBoundsException.class,
				() -> l.set(4, "Set"));
		
	}
	
	/**
	 * Tests the linked list's remove method
	 */
	@Test
	void testRemove() {
		LinkedList<String> l = new LinkedList<>();
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
		
		ListIterator<String> i = l.listIterator(0);
		assertEquals("B", i.next());
		assertEquals("D", i.next());
		assertFalse(i.hasNext());
		
		assertThrows(IndexOutOfBoundsException.class,
				() -> l.remove(-1));
		assertThrows(IndexOutOfBoundsException.class,
				() -> l.remove(2));
	}
	
	/**
	 * Tests the linked list's list iterator
	 */
	@Test
	void testListIterator() {
		LinkedList<String> l = new LinkedList<>();
		ListIterator<String> i = l.listIterator(0);
		assertFalse(i.hasNext());
		assertFalse(i.hasPrevious());
		assertEquals(0, i.nextIndex());
		assertEquals(-1, i.previousIndex());
		
		assertThrows(NoSuchElementException.class,
				() -> i.next());
		
		assertThrows(NoSuchElementException.class,
				() -> i.previous());
		
		l.add("A");
		
		ListIterator<String> i2 = l.listIterator(0);
		assertTrue(i2.hasNext());
		assertFalse(i2.hasPrevious());
		assertEquals(0, i2.nextIndex());
		assertEquals(-1, i2.previousIndex());
		assertEquals("A", i2.next());
		
		i2.add("B");
		i2.add("C");
		assertEquals("C", i2.previous());
		assertEquals("B", i2.previous());
		assertEquals("A", i2.previous());
		assertThrows(NoSuchElementException.class,
				() -> i2.previous());
		
		ListIterator<String> i3 = l.listIterator(2);
		assertTrue(i3.hasNext());
		assertTrue(i3.hasPrevious());
		assertEquals(2, i3.nextIndex());
		assertEquals(1, i3.previousIndex());
		assertEquals("C", i3.next());
		
		ListIterator<String> i4 = l.listIterator(1);
		assertTrue(i4.hasNext());
		assertTrue(i4.hasPrevious());
		assertEquals(1, i4.nextIndex());
		assertEquals(0, i4.previousIndex());
		assertEquals("B", i4.next());
		i4.add("Z");
		assertTrue(i4.hasNext());
		assertTrue(i4.hasPrevious());
		assertEquals(3, i4.nextIndex());
		assertEquals(2, i4.previousIndex());
		assertEquals("Z", i4.previous());
		assertEquals("B", i4.previous());
		assertEquals("A", i4.previous());
		i4.set("Y");
		assertEquals("Y", i4.next());
		i4.set("X");
		assertEquals("X", i4.previous());
		assertFalse(i4.hasPrevious());
		assertEquals("X", i4.next());
		assertEquals("B", i4.next());
		assertEquals("Z", i4.next());
		assertEquals("C", i4.next());
		i4.add("Q");
		assertThrows(IllegalStateException.class,
				() -> i4.set("Bad"));
		assertThrows(IllegalStateException.class,
				() -> i4.remove());
		assertEquals("Q", i4.previous());
		assertEquals("Q", i4.next());
		i4.remove();
		assertEquals("C", i4.previous());
	}

}
