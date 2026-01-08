package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of LinkedQueue
 * @author Shreyash Jain
 */
public class LinkedQueueTest {

	/**
	 * Test enqueue() and dequeue() method
	 */
	@Test
	public void testEnqueueDequeueMultiple() {
		LinkedQueue<String> q = new LinkedQueue<>(99);
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
	 * Test enqueue() and dequeue() method
	 */
	@Test
	public void testEnqueueDequeueSingle() {
		LinkedQueue<String> q = new LinkedQueue<>(99);
		q.enqueue("A");
		assertEquals(1, q.size());
		assertFalse(q.isEmpty());
		assertEquals("A", q.dequeue());
		assertEquals(0, q.size());
		assertTrue(q.isEmpty());
	}

	/**
	 * Test dequeue() method
	 */
	@Test
	public void testDequeueEmpty() {
		LinkedQueue<String> q = new LinkedQueue<>(99);
		assertThrows(NoSuchElementException.class,
				() -> q.dequeue());
	}

	/**
	 * Test isEmpty() method
	 */
	@Test
	public void testIsEmpty() {
		LinkedQueue<String> q = new LinkedQueue<>(99);
		assertTrue(q.isEmpty());
		q.enqueue("hello");
		assertFalse(q.isEmpty());
	}

	/**
	 * Test setCapacity() method
	 */
	@Test
	public void testSetCapacity() {
		LinkedQueue<String> q = new LinkedQueue<>(99);
		assertThrows(IllegalArgumentException.class,
				() -> q.setCapacity(-1));
		
		q.enqueue("A");
		q.enqueue("B");
		
		assertThrows(IllegalArgumentException.class,
				() -> q.setCapacity(1));
		assertDoesNotThrow(() -> q.setCapacity(3));
	}

}
