package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of ArrayStack 
 * @author Liam Yeager
 */
public class ArrayStackTest {
	
	/**
	 * Test ArrayStack constructor with a capacity parameter 
	 * Test setCapacity() method
	 */
	@Test
	public void testArrayStackCapacity() {
		ArrayStack<String> stack = new ArrayStack<String>(4);
		assertEquals(0, stack.size());
		assertTrue(stack.isEmpty());
		stack.push("A");
		stack.push("B");
		stack.push("C");
		stack.push("D");
		assertEquals(4, stack.size()); 
		assertThrows(IllegalArgumentException.class, () -> stack.push("E"));
		
		stack.setCapacity(7);
		stack.push("E");
		stack.push("F");
		assertEquals(6, stack.size()); 
	}
	
	/**
	 * Inserting and removing a single element into the stack
	 */
	@Test
	public void testPushPopSingle() {
		ArrayStack<String> stack = new ArrayStack<String>(10);
		
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
		ArrayStack<String> stack = new ArrayStack<String>(10);
		
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
		ArrayStack<String> stack = new ArrayStack<String>(10);
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
		ArrayStack<String> stack = new ArrayStack<String>(10);
		assertThrows(EmptyStackException.class,
				() -> stack.pop());
	}
	
}
