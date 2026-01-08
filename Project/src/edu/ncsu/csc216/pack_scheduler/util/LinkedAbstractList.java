package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * A linked list that stores objects of type e.
 * @author Liam Yeager
 * @author Steven Saleeb
 * @param <E> the type of object to be stored in the list
 */
public class LinkedAbstractList<E> extends AbstractList<E> {

	/** The reference to the first list node in the linked list */
	private ListNode front;
	
	/** The reference to the last list node in the linked list */
	private ListNode back;
	
	/** How many objects are in the linked list */
	private int size;
	
	/** The maximum amount of objects that can be in the list*/
	private int capacity;
	
	/**
	 * Constructor for a linked abstract list. 
	 * @param capacity the max amount of objects that can be in the list.
	 * @throws IllegalArgumentException if the capacity is negative.
	 */
	public LinkedAbstractList(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Capacity cannot be negative.");
		}
		this.front = null;
		this.back = null;
		this.size = 0;
		this.capacity = capacity;
		
	}
	
	/**
	 * Sets the capacity fir the list and the capacity can not be negative or smaller than 
	 * the current number of elements in the list
	 * @param capacity the new maximum number of elements allowed in the list
	 * @throws IllegalArgumentException if the given capacity is negative or smaller than the current list 
	 */
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
		
	}
	
	/**
	 * Returns the size of the linked list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Returns the element at the specified index in the list
	 * @param idx the index of the element to get
	 * @return the element at the given index
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	@Override
	public E get(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		ListNode current = front;
		for (int i = 0; i < idx; i++ ) {
			current = current.next;
			
		}
		return current.data;
		
		
	}
	
	/**
	 * Adds the given element at the specified index in the list and 
	 * it shifts existing elements to the right as needed
	 * @param idx the index where the element should be added
	 * @param object the element to add to the list
	 * @throws IndexOutOfBoundsException if the index is out of range
	 * @throws NullPointerException if the element is null
	 * @throws IllegalArgumentException if the element is a duplicate
	 */
	@Override
	public void add(int idx, E object) {
		
		if (idx < 0 || idx > size) {
			throw new  IndexOutOfBoundsException();
		}
		
		if(object == null) {
			throw new NullPointerException();
		}
		
		if(this.contains(object)) {
			throw new IllegalArgumentException();
		}
		
		if (size == capacity) {
			throw new IllegalArgumentException();
		}
		
		ListNode list = new ListNode(object);
		
		if (size == 0) {
			front = list;
			back = list;
		} else if (idx == 0) {
			list.next = front;
			front = list;
		} else if (idx == size) {
			back.next = list;
			back = list;
			
		}  else {
			ListNode current = front;
			for (int i = 0; i < idx - 1; i++ ) {
				current = current.next;
				
			}
			
			list.next = current.next;
			current.next = list;
			
		}
		
		size++;
		
		
		
	}
	
	/**
	 * Removes and returns the element at the specified index in the list
	 * @param index the index of the element to remove
	 * @return the element that was removed
	 * @throws IndexOutOfBoundsException if the index is out of range 
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		E removed;
		
		if (index == 0) {
			removed = front.data;
			front = front.next;
			
			if (front == null) {
				back = null;
			}
		}
		else {
			ListNode current = front;
			for (int i = 0; i < index - 1; i++ ) {
				current = current.next;
				
			}
			
			removed = current.next.data;
			current.next = current.next.next;
			if (index == size - 1) {
				back = current;
			}
			
		}
		
		size--;
		return removed;
		
	}
	
	/**
	 * Replaces the element at the specified index with the given element
	 * @param index the index of the element to replace
	 * @param element the new element to set
	 * @return the element at the given position
	 * @throws NullPointerException if the new element is null
	 * @throws IllegalArgumentException if the element is a duplicate
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	@Override
	public E set(int index, E element) {
	    if (element == null) {
	        throw new NullPointerException();
	    }
	    if (contains(element)) {
	        throw new IllegalArgumentException("Duplicate element");
	    }
	    if (index < 0 || index >= size) {
	        throw new IndexOutOfBoundsException();
	    }

	    ListNode current = front;
	    for (int i = 0; i < index; i++) {
	        current = current.next;
	    }
	    E old = current.data;
	    current.data = element;
	    return old;
	}
	
	/**
	 * A node in the linked list. A node has an object in it and a reference to the next node in the list.
	 * @author Liam Yeager
	 * @author Steven Saleeb
	 */
	private class ListNode {
		
		/** The object in the list node being stored */
		private E data;
		
		/** The reference to the next object in the list */
		private ListNode next;
		
		/**
		 * Constructor for a linked node with no next node
		 * @param data the object in this node
		 */
		public ListNode(E data) {
			this(data, null);
		}
		
		/**
		 * Constructor for a linked node
		 * @param data the object in this node
		 * @param next the reference to the next node
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
		
		
		
	}
	
}
