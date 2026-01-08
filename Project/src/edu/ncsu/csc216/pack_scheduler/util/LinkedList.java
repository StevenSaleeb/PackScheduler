package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Custom doubly-linked list with an iterator that holds a generic type object
 * @param <E> the object that the list holds
 * @author Liam Yeager
 * @author Shreyash Jain
 * @author Steven Saleeb
 */
public class LinkedList<E> extends AbstractSequentialList<E> {

	/** The size of the linked list */
	private int size;
	
	/** The front of the list */
	private ListNode front;
	
	/** The back of the list */
	private ListNode back;

	/**
	 * Constructor for a linked list
	 */
	public LinkedList() {
		front = new ListNode(null);
		back = new ListNode(null);
		front.next = back;
		back.prev = front;
		size = 0;
	}
	
	/**
	 * This method is responsible to add object and checks if 
	 * the object is already there then it throws a IAE, and 
	 * if it is null it throws a NPE
	 * @throws IllegalArgumentException if the object is duplicated
	 * @throws NullPointerException if the object is null
	 */
	@Override
	public void add(int idx, E object) {
		
		if (super.contains(object)) {
			throw new IllegalArgumentException();
		}
		
		super.add(idx, object);
		
	}

	
	/**
	 * Returns an iterator for this list
	 * @param index the index the iterator will start at
	 * @return the list iterator
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		return new LinkedListIterator(index);
	}
	
	/**
	 * Sets a given object to the index given in the list
	 * @param idx the index to put the object at
	 * @param object the object to be placed
	 * @throws IllegalArgumentException if the object is a duplicate
	 * @throws IllegalStateException if neither {@code next} nor
     *         {@code previous} have been called, or {@code remove} or
     *         {@code add} have been called after the last call to
     *         {@code next} or {@code previous}
     * @throws NullPointerException if the object to be set is null
	 * 
	 */
	@Override
	public E set(int idx, E object) {
		if (super.contains(object)) {
			throw new IllegalArgumentException();
		}
		
		E oldObject = super.get(idx);
		super.set(idx, object);
		
		return oldObject;
		
	}
	
	/**
	 * Returns the size of the list
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return size;
	}
	
	
	/**
	 * An inner class representing one node of a linked list.
	 * Because the list is doubly-linked, each node has a previous and next reference.
	 * @author Liam Yeager
	 * @author Shreyash Jain
	 * @author Steven Saleeb
	 */
	private class ListNode {
		
		/** The object contained in the list node */
		private E data;
		
		/** A reference to the next list node in the linked list */
		private ListNode next;
		
		/** A reference to the previous list node in the linked list */
		private ListNode prev;
		
		/**
		 * A constructor for a list node without any references
		 * @param data the object that will be in the list node
		 */
		public ListNode(E data) {
			this(data, null, null);
			
		}
		
		/**
		 * A constructor for a list node
		 * @param data the object that will be in the list node
		 * @param prev the reference to the previous list node
		 * @param next the reference to the next list node
		 */
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
		
	}
	
	/**
	 * An inner class that iterates through a linked list.
	 * The iterator can move forward, backward, and add/remove what it is
	 * iterating through.
	 */
	private class LinkedListIterator implements ListIterator<E> {

		/** The reference to the list node directly before the iterator's location */
		private ListNode previous;
		
		/** The reference to the list node directly after the iterator's location */
		private ListNode next;
		
		/** The index of the previous list node */
		private int previousIndex;
		
		/** The index of the next list node */
		private int nextIndex;
		
		/** The list node that has been last retrieved */
		private ListNode lastRetrieved;
		
		/**
		 * Constructor for a list iterator
		 * @param index the index that the list iterator will start at
		 * @throws IndexOutOfBoundsException if the index is out of bounds
		 */
		public LinkedListIterator(int index) {
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException();
			}
			
			ListNode current = front;
			for (int i = 0; i < index; i++) {
				current = current.next;
			}
			previous = current;
			next = current.next;
			previousIndex = index - 1;
			nextIndex = index;
			lastRetrieved = null;
		}
		
		/**
	     * Returns {@code true} if this list iterator has more elements when
	     * traversing the list in the forward direction. (In other words,
	     * returns {@code true} if {@link #next} would return an element rather
	     * than throwing an exception.)
	     *
	     * @return {@code true} if the list iterator has more elements when
	     *         traversing the list in the forward direction
	     */
		@Override
		public boolean hasNext() {
			return next.data != null;
		}

		/**
	     * Returns the next element in the list and advances the cursor position.
	     * This method may be called repeatedly to iterate through the list,
	     * or intermixed with calls to {@link #previous} to go back and forth.
	     * (Note that alternating calls to {@code next} and {@code previous}
	     * will return the same element repeatedly.)
	     *
	     * @return the next element in the list
	     * @throws NoSuchElementException if the iteration has no next element
	     */
		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			lastRetrieved = next;
			next = next.next;
			previous = previous.next;
			nextIndex++;
			previousIndex++; 
			return lastRetrieved.data;
			
		}

		/**
	     * Returns {@code true} if this list iterator has more elements when
	     * traversing the list in the reverse direction.  (In other words,
	     * returns {@code true} if {@link #previous} would return an element
	     * rather than throwing an exception.)
	     *
	     * @return {@code true} if the list iterator has more elements when
	     *         traversing the list in the reverse direction
	     */
		@Override
		public boolean hasPrevious() {
			return previous.data != null;
		}

		/**
	     * Returns the previous element in the list and moves the cursor
	     * position backwards.  This method may be called repeatedly to
	     * iterate through the list backwards, or intermixed with calls to
	     * {@link #next} to go back and forth.  (Note that alternating calls
	     * to {@code next} and {@code previous} will return the same
	     * element repeatedly.)
	     *
	     * @return the previous element in the list
	     * @throws NoSuchElementException if the iteration has no previous
	     *         element
	     */
		@Override
		public E previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			lastRetrieved = previous;
			next = next.prev;
			previous = previous.prev;
			nextIndex--;
			previousIndex--; 
			return lastRetrieved.data;
		}

		/**
	     * Returns the index of the element that would be returned by a
	     * subsequent call to {@link #next}. (Returns list size if the list
	     * iterator is at the end of the list.)
	     *
	     * @return the index of the element that would be returned by a
	     *         subsequent call to {@code next}, or list size if the list
	     *         iterator is at the end of the list
	     */
		@Override
		public int nextIndex() {
			return nextIndex;
		}

		/**
	     * Returns the index of the element that would be returned by a
	     * subsequent call to {@link #previous}. (Returns -1 if the list
	     * iterator is at the beginning of the list.)
	     *
	     * @return the index of the element that would be returned by a
	     *         subsequent call to {@code previous}, or -1 if the list
	     *         iterator is at the beginning of the list
	     */
		@Override
		public int previousIndex() {
			return previousIndex;
		}

		/**
	     * Removes from the list the last element that was returned by {@link
	     * #next} or {@link #previous} (optional operation).  This call can
	     * only be made once per call to {@code next} or {@code previous}.
	     * It can be made only if {@link #add} has not been
	     * called after the last call to {@code next} or {@code previous}.
	     *
	     * @throws IllegalStateException if neither {@code next} nor
	     *         {@code previous} have been called, or {@code remove} or
	     *         {@code add} have been called after the last call to
	     *         {@code next} or {@code previous}
	     */
		@Override
		public void remove() {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			
			lastRetrieved.prev.next = lastRetrieved.next;
			lastRetrieved.next.prev = lastRetrieved.prev;
			previousIndex--;
			nextIndex--;
			previous = lastRetrieved.prev;
			size--;
			lastRetrieved = null;
		}

		/**
	     * Replaces the last element returned by {@link #next} or
	     * {@link #previous} with the specified element (optional operation).
	     * This call can be made only if neither {@link #remove} nor {@link
	     * #add} have been called after the last call to {@code next} or
	     * {@code previous}.
	     *
	     * @param e the element with which to replace the last element returned by
	     *          {@code next} or {@code previous}
	     * @throws ClassCastException if the class of the specified element
	     *         prevents it from being added to this list
	     * @throws IllegalArgumentException if some aspect of the specified
	     *         element prevents it from being added to this list
	     * @throws IllegalStateException if neither {@code next} nor
	     *         {@code previous} have been called, or {@code remove} or
	     *         {@code add} have been called after the last call to
	     *         {@code next} or {@code previous}
	     * @throws NullPointerException if the object to be set is null
	     */
		@Override
		public void set(E e) {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			
			if (e == null) {
				throw new NullPointerException();
			}
			
			lastRetrieved.data = e;
		}

		/**
	     * Inserts the specified element into the list (optional operation).
	     * The element is inserted immediately before the element that
	     * would be returned by {@link #next}, if any, and after the element
	     * that would be returned by {@link #previous}, if any.  (If the
	     * list contains no elements, the new element becomes the sole element
	     * on the list.)  The new element is inserted before the implicit
	     * cursor: a subsequent call to {@code next} would be unaffected, and a
	     * subsequent call to {@code previous} would return the new element.
	     * (This call increases by one the value that would be returned by a
	     * call to {@code nextIndex} or {@code previousIndex}.)
	     *
	     * @param e the element to insert
	     * @throws ClassCastException if the class of the specified element
	     *         prevents it from being added to this list
	     * @throws NullPointerException if the object to be added is null
	     */
		@Override
		public void add(E e) {
			if (e == null) {
				throw new NullPointerException();
			}
			
			ListNode node = new ListNode(e, previous, next);
			previous.next = node;
			next.prev = node;
			previous = node;
			previousIndex++;
			nextIndex++;
			size++;
			lastRetrieved = null;
		}
		
	}

	
	

}
