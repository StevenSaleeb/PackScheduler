package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Interface for a Stack type collection. A stack can pull and push from the top of a list. 
 * @author Liam Yeager
 * @param <E> the generic object in the stack
 */
public interface Stack<E> {
	/**
	 * Adds an element to the top of the stack
	 * @param element the element to be added
	 * @throws IllegalArgumentException if there is no room
	 */
	void push(E element);
	
	/**
	 * Returns and removes the element at top of the stack
	 * @return the element at top of the stack
	 * @throws java.util.EmptyStackException if the stack is empty
	 */
	E pop();
	
	/**
	 * Returns true if the stack is empty
	 * @return true if the stack is empty
	 */
	boolean isEmpty();
	
	/**
	 * Returns the size of the stack
	 * @return the size of the stack
	 */
	int size();
	
	/**
	 * Sets the stack's capacity.
	 * @param capacity the capacity to be set
	 * @throws IllegalArgumentException if capacity is 
	 * negative or less than the number of elements in the stack
	 */
	void setCapacity(int capacity);
	
}
