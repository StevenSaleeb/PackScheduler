package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Queue is a collection that allows you to add at the bottom and remove from the top.
 * @param <E> generic type object
 * @author Steven Saleeb
 * @author Liam Yeager
 */
public interface Queue<E> {
	/**
	 * Adds an element to the top of the queue
	 * @param element the element to be added
	 * @throws IllegalArgumentException if there is no room
	 */
	void enqueue(E element);
	
	/**
	 * Returns and removes the element at top of the queue
	 * @return the element at top of the queue
	 * @throws java.util.NoSuchElementException if the queue is empty
	 */
	E dequeue();
	
	/**
	 * Returns true if the queue is empty
	 * @return true if the queue is empty
	 */
	boolean isEmpty();
	
	/**
	 * Returns the size of the queue
	 * @return the size of the queue
	 */
	int size();
	
	/**
	 * Sets the queue's capacity.
	 * @param capacity the capacity to be set
	 * @throws IllegalArgumentException if capacity is 
	 * negative or less than the number of elements in the queue
	 */
	void setCapacity(int capacity);
}
