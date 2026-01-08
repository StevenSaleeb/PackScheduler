package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * Collection that allows you to add at the bottom and remove from the top.
 * ArrayQueue encapsulates ArrayList to implement the abstract behavior of the Queue Interface.
 * @param <E> generic type object
 * @author Steven Saleeb
 */
public class ArrayQueue<E> implements Queue<E> {
	
	/** list used to implement the stack functionality */
	private ArrayList<E> list;
	
	/** capacity of the ArrayQueue */
	private int capacity;
	
	
	/**
	 * Constructs an ArrayQueue object and creates a new ArrayList with an unused capacity
	 * @param capacity the capacity of the queue
	 * @throws IllegalArgumentException if capacity is less than 0 or less than size
	 */
	public ArrayQueue(int capacity) {
		list = new ArrayList<E>();
		if (capacity < 0 || capacity < size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}
	
	/**
	 * Adds an element to the top of the queue
	 * @param element the element to be added
	 * @throws IllegalArgumentException if trying to add when capacity is reached
	 */
	@Override
	public void enqueue(E element) {
		if (size() == capacity) {
			throw new IllegalArgumentException();
		}
		list.add(list.size(), element);
		
	}

	/**
	 * Returns and removes the element at top of the queue
	 * @return the element at top of the queue
	 * @throws NoSuchElementException if the queue is empty
	 */
	@Override
	public E dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return list.remove(0);
	}

	/**
	 * Returns true if the queue is empty
	 * @return true if the queue is empty
	 */
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/**
	 * Returns the size of the queue
	 * @return the size of the queue
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * ArrayQueue cannot have capacity
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
