package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * Collection that allows you to add at the bottom and remove from the top.
 * LinkedQueue encapsulates LinkedAbstractList to implement the abstract behavior of the Queue Interface.
 * @param <E> generic type object
 * @author Steven Saleeb
 * @author Shreyash Jain
 */
public class LinkedQueue<E> implements Queue<E> {
	
	/** list used to implement the stack functionality */
	private LinkedAbstractList<E> list;
	
	/**
	 * Creates a LinkedQueue object and a new LinkedAbstractList with a capacity
	 * @param capacity capacity
	 */
	public LinkedQueue(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
	}

	/**
	 * Adds an element to the top of the queue
	 * @param element the element to be added
	 * @throws IllegalArgumentException if there is no room
	 */
	@Override
	public void enqueue(E element) {
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
	 * Sets the queue's capacity.
	 * @param capacity the capacity to be set
	 * @throws IllegalArgumentException if capacity is 
	 * negative or less than the number of elements in the queue
	 */
	@Override
	public void setCapacity(int capacity) {
		list.setCapacity(capacity);
	}

	/**
	 * Returns true if the queue contains the student, false if not.
	 * @param obj the object to be checked
	 * @return true if the queue contains the student
	 */
	public boolean contains(E obj) {
		for (int i = 0; i < list.size(); i++) {
			if (obj.equals(list.get(i))) {
				return true;
			}
		}
		return false;
	}

}
