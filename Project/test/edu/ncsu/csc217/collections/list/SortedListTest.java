package edu.ncsu.csc217.collections.list;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Testing the sorted list class
 * @author steven saleeb
 * @author liam yeager
 * @author shreyash jain
 */
public class SortedListTest {

	/**
	 * Test that the list grows by adding at least 11 elements
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertFalse(list.contains("apple"));
		list.add("n");
		list.add("w");
		list.add("e");
		list.add("r");
		list.add("t");
		list.add("y");
		list.add("u");
		list.add("i");
		list.add("g");
		list.add("h");
		list.add("l");
		assertEquals(11, list.size());
		
	}

	/**
	 * Test adding to the front, middle and back of the list
	 * Test adding a null element
	 * Test adding a duplicate element
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();
		
		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));
		
		list.add("apple");
		assertEquals(2, list.size());
		assertEquals("apple", list.get(0));
		
		list.add("dog");
		assertEquals(3, list.size());
		assertEquals("dog", list.get(2));
		
		list.add("cat");
		assertEquals(4, list.size());
		assertEquals("cat", list.get(2));
		
		try {
			list.add(null);
			fail("there was not a null pointer exception");
			
		} catch (NullPointerException e) {
			assertEquals(4, list.size());
		}
		
		try {
			list.add("dog");
			fail("there was a duplicate element");
			
		} catch (IllegalArgumentException e) {
			assertEquals(4, list.size());
		}

	}
	
	/**
	 * Test getting an element from an empty list
	 * Test getting an element at an index < 0
	 * Test getting an element at size
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();
		
		try {
			list.get(0);
			fail("Exception not thrown.");
		}
		catch(IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
		}
		
		list.add("c");
		list.add("b");
		list.add("a");
		
		try {
			list.get(-1);
			fail("Exception not thrown.");
		}
		catch (IndexOutOfBoundsException e) {
			assertEquals(3, list.size());
			
		}
		
		try {
			list.get(list.size());
			fail("Exception not thrown.");
		}
		catch (IndexOutOfBoundsException e) {
			assertEquals(3, list.size());
			
		}
		
		assertEquals("a", list.get(0));
		assertEquals("b", list.get(1));
		assertEquals("c", list.get(2));
		
	}
	
	/**
	 * Test removing from an empty list
	 * Test removing an element at an index < 0
	 * Test removing an element at size
	 * Test removing a middle element
	 * Test removing the last element
	 * Test removing the first element
	 * Test removing the last element
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();
		
		try {
			list.remove(0);
			fail("Exception not thrown.");
		}
		catch (IndexOutOfBoundsException e) {
			assertEquals(0, list.size());
			
		}
		
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		list.add("e");
		
		try {
			list.remove(-1);
			fail("Exception not thrown.");
		}
		catch (IndexOutOfBoundsException e) {
			assertEquals(5, list.size());
			
		}
		
		try {
			list.remove(list.size());
			fail("Exception not thrown.");
		}
		catch (IndexOutOfBoundsException e) {
			assertEquals(5, list.size());
			
		}
		
		list.remove(2);
		assertEquals(4, list.size());
		
		list.remove(3);
		assertEquals(3, list.size());
		
		list.remove(0);
		assertEquals(2, list.size());
		
		list.remove(1);
		assertEquals(1, list.size());
		
	}
	
	/**
	 * Test indexOf on an empty list
	 * Test various calls to indexOf for elements in the list and not in the list
	 * Test checking the index of null
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();
	
		assertEquals(-1, list.indexOf("cat"));
		list.add("c");
		list.add("a");
		list.add("b");
		list.add("g");
		assertEquals(2, list.indexOf("c"));
		assertEquals(0, list.indexOf("a"));
		assertEquals(1, list.indexOf("b"));
		assertEquals(3, list.indexOf("g"));
		assertEquals(-1, list.indexOf("dog"));
		assertEquals(-1, list.indexOf("bark"));
		try {
			list.add(null);
			fail("Exception not thrown.");
		}
		catch(NullPointerException e) {
			assertEquals(4, list.size());
		}
		
		
		
		
	}
	
	/**
	 * Test that the list is empty
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();
		
		list.add("cat");
		list.add("lion");
		list.add("hippo");
		list.add("dog");
		list.clear();
		assertEquals(0, list.size());
	
	}

	/**
	 * Test that the list starts empty
	 * Check that the list is no longer empty
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();
		
		assertTrue(list.isEmpty());
		list.add("cat");
		assertFalse(list.isEmpty());
	
	}

	/**
	 * Test the empty list case
	 * Test some true and false cases
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();
		assertFalse(list.contains("cat"));
		list.add("cat");
		list.add("lion");
		list.add("hippo");
		list.add("dog");
		assertTrue(list.contains("lion"));
		assertFalse(list.contains("wolf"));
		
	}
	
	/**
	 * Test for equality and non equality
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		list1.add("cat");
		list1.add("lion");
		list2.add("cat");
		list2.add("lion");
		list3.add("cat");
		list3.add("wolf");
		assertTrue(list1.equals(list2));
		assertFalse(list1.equals(list3));
		assertFalse(list2.equals(list3));
	
	}
	
	
	/**
	 * Test for the same and different hashCodes
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		list1.add("cat");
		list1.add("lion");
		list2.add("cat");
		list2.add("lion");
		list3.add("cat");
		list3.add("wolf");
		assertTrue(list1.hashCode() == list2.hashCode());
		assertFalse(list1.hashCode() == list3.hashCode());
		assertFalse(list2.hashCode() == list3.hashCode());
		
	}

}
 