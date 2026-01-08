package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * A stack can pull and push from the top of a list.
 * LinkedStack encapsulates LinkedAbstractList to implement the abstract behavior of the Stack Interface.
 * @param <E> generic object type
 * @author Steven Saleeb
 * @author Shreyash Jain
 */
public class LinkedStack<E> implements Stack<E> {
	
	/** list used to implement the stack functionality */
	private LinkedAbstractList<E> list;
	
	/**
	 * Constructs a LinkedStack object and creates a new LinkedAbstractList with a capacity
	 * @param capacity capacity
	 */
	public LinkedStack(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
	}
	
	/**
	 * Adds an element to the top of the stack
	 * @param element the element to be added
	 * @throws IllegalArgumentException if there is no room
	 */
	@Override
	public void push(E element) {
		list.add(0, element);
		
	}

	/**
	 * Returns and removes the element at top of the stack
	 * @return the element at top of the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	@Override
	public E pop() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		return list.remove(0);
	}

	/**
	 * Returns true if the stack is empty
	 * @return true if the stack is empty
	 */
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/**
	 * Returns the size of the stack
	 * @return the size of the stack
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Sets the stack's capacity.
	 * @param capacity the capacity to be set
	 * @throws IllegalArgumentException if capacity is 
	 * negative or less than the number of elements in the stack
	 */
	@Override
	public void setCapacity(int capacity) {
		list.setCapacity(capacity);
	}

}
