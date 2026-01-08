package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of LinkedStack 
 * @author Liam Yeager
 */
public class LinkedStackTest {

	/**
	 * Inserting and removing a single element into the stack
	 */
	@Test
	public void testPushPopSingle() {
		LinkedStack<String> stack = new LinkedStack<>(99);
		
		stack.push("A");
		assertEquals(1, stack.size());
		assertFalse(stack.isEmpty());
		assertEquals("A", stack.pop());
		assertEquals(0, stack.size());
		assertTrue(stack.isEmpty());
	}
	
	/**
	 * Inserting and removing multiple elements into the stack
	 */
	@Test
	public void testPushPopMultiple() {
		LinkedStack<String> stack = new LinkedStack<>(99);
		
		stack.push("A");
		stack.push("B");
		stack.push("C");
		stack.push("D");
		stack.push("E");
		assertEquals(5, stack.size());
		assertFalse(stack.isEmpty());
		
		assertEquals("E", stack.pop());
		assertEquals(4, stack.size());
		assertEquals("D", stack.pop());
		assertEquals(3, stack.size());
		assertEquals("C", stack.pop());
		assertEquals(2, stack.size());
		assertEquals("B", stack.pop());
		assertEquals(1, stack.size());
		assertEquals("A", stack.pop());
		assertEquals(0, stack.size());
		assertTrue(stack.isEmpty());
	}
	
	/**
	 * Interleaved inserts and removes
	 */
	@Test
	public void testPopPushBetween() {
		LinkedStack<String> stack = new LinkedStack<>(99);
		stack.push("A");
		stack.push("B");
		stack.push("C");
		stack.push("D");
		stack.push("E");
		assertEquals(5, stack.size());
		assertFalse(stack.isEmpty());
		
		assertEquals("E", stack.pop());
		assertEquals(4, stack.size());
		assertEquals("D", stack.pop());
		assertEquals(3, stack.size());
		
		stack.push("Inbetween");
		assertEquals(4, stack.size());
		assertFalse(stack.isEmpty());
		
		assertEquals("Inbetween", stack.pop());
		assertEquals(3, stack.size());
	}
	
	/**
	 * Attempting to remove an element from an empty stack
	 */
	@Test
	public void testPopEmpty() {
		LinkedStack<String> stack = new LinkedStack<>(99);
		assertThrows(EmptyStackException.class,
				() -> stack.pop());
	}
	
	/**
	 * Setting the capacity
	 */
	@Test
	public void testSetCapacity() {
		LinkedStack<String> stack = new LinkedStack<>(99);
		assertThrows(IllegalArgumentException.class,
				() -> stack.setCapacity(-1));
		
		stack.push("A");
		stack.push("B");
		
		assertThrows(IllegalArgumentException.class,
				() -> stack.setCapacity(1));
		
		assertDoesNotThrow(() -> stack.setCapacity(3));
		
	}
}
