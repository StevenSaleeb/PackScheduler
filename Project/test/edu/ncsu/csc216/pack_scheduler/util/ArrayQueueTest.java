package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of ArrayQueue
 * @author Shreyash Jain
 */
public class ArrayQueueTest {
	
	/**
	 * Test ArrayQueue constructor 
	 * Test setCapacity() method
	 */
	@Test
	public void testArrayStackCapacity() {
		ArrayQueue<String> q = new ArrayQueue<String>(3);
		assertEquals(0, q.size());
		assertTrue(q.isEmpty());
		q.enqueue("A");
		q.enqueue("B");
		q.enqueue("C");
		assertThrows(IllegalArgumentException.class, () -> q.enqueue("D"));
		
		q.setCapacity(5);
		q.enqueue("E");
		q.enqueue("F");
		assertEquals(5, q.size()); 
	}

	/**
	 * Test enqueue() and dequeue() methods
	 */
	@Test
	public void testEnqueueDequeueSingle() {
		ArrayQueue<String> q = new ArrayQueue<String>(10);
		
		q.enqueue("A");
		assertEquals(1, q.size());
		assertFalse(q.isEmpty());
		assertEquals("A", q.dequeue());
		assertEquals(0, q.size());
		assertTrue(q.isEmpty());
	}

	/**
	 * Test enqueue() and dequeue() methods
	 */
	@Test
	public void testEnqueueDequeueMultiple() {
		ArrayQueue<String> q = new ArrayQueue<String>(10);
		
		q.enqueue("A");
		q.enqueue("B");
		q.enqueue("C");
		q.enqueue("D");
		q.enqueue("E");
		assertEquals(5, q.size());
		assertFalse(q.isEmpty());
		
		assertEquals("A", q.dequeue());
		assertEquals(4, q.size());
		assertEquals("B", q.dequeue());
		assertEquals(3, q.size());
		assertEquals("C", q.dequeue());
		assertEquals(2, q.size());
		assertEquals("D", q.dequeue());
		assertEquals(1, q.size());
		assertEquals("E", q.dequeue());
		assertEquals(0, q.size());
		assertTrue(q.isEmpty());
	}
	
	/**
	 * Attempting to remove an element from an empty stack
	 */
	@Test
	public void testDequeueEmpty() {
		ArrayQueue<String> q = new ArrayQueue<String>(10);
		assertThrows(NoSuchElementException.class,
				() -> q.dequeue());
	}

}
