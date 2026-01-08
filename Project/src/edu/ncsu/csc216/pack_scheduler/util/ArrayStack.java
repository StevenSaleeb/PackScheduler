package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * A stack can pull and push from the top of a list. 
 * ArrayStack encapsulates ArrayList to implement the abstract behavior of the Stack Interface.
 * @param <E> generic object type
 * @author Steven Saleeb
 */
public class ArrayStack<E> implements Stack<E> {
	
	/** list used to implement the stack functionality */
	private ArrayList<E> list;
	
	/** capacity of ArrayStack */
	private int capacity;
	
	
	/**
	 * Constructs an ArrayStack object and creates a new ArrayList with an unused capacity
	 * @param capacity capacity of the array (not implemented)
	 * @throws IllegalArgumentException if capacity is less than 0 or less than size
	 */
	public ArrayStack(int capacity) {
		list = new ArrayList<E>();
		if (capacity < 0 || capacity < size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}
	
	/**
	 * Adds an element to the top of the stack
	 * @param element the element to be added
	 * @throws IllegalArgumentException if trying to add when capacity is reached
	 */
	@Override
	public void push(E element) {
		if (size() == capacity) {
			throw new IllegalArgumentException();
		}
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
	 * Returns true if the stack is empty, false otherwise
	 * @return true if the stack is empty, false otherwise
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
	 * ArrayStack cannot have capacity
	 * @param capacity the capacity to be set
	 * @throws IllegalArgumentException if capacity is less than 0 or less than size
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}
	
}
