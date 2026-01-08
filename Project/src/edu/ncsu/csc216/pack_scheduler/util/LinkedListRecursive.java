package edu.ncsu.csc216.pack_scheduler.util;

/**
 * List class that uses recursion and a linked data structure to store objects
 * of type E.
 * 
 * @param <E> the type of object the list can store
 * @author Lian Yeager
 * @author Shreyash Jain
 * @author Steven Saleeb
 */
public class LinkedListRecursive<E> {

	/** The node at the front of the list */
	private ListNode front;

	/** The number of objects in the list */
	private int size;

	/**
	 * Constructor for LinkedListRecursive object
	 */
	public LinkedListRecursive() {
		front = null;
		size = 0;
	}

	/**
	 * Returns true if the list has no elements
	 * 
	 * @return true if the list is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns the number of elements currently in the list
	 * 
	 * @return the size of the list
	 */
	public int size() {
		return size;
	}

	/**
	 * Adds an element at a specific index in the list Elements after this position
	 * are shifted to the right
	 *
	 * @param idx the position to insert the new element
	 * @param obj the element to add
	 * @throws IndexOutOfBoundsException if the index is invalid
	 * @throws NullPointerException      if the element is null
	 */
	public void add(int idx, E obj) {
		if (idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException();
		}
		if (obj == null) {
			throw new NullPointerException();
		}

		if (idx == 0) {
			front = new ListNode(obj, front);
			size++;
		} else {
			front.add(idx - 1, obj);
		}
	}

	/**
	 * Adds an element to the end of the list and duplicate values are not allowed
	 * 
	 * @param obj the element to add
	 * @return true if the element was added
	 * @throws IllegalArgumentException if the element already exists in the list
	 * @throws NullPointerException     if the element is null
	 */
	public boolean add(E obj) {

		if (contains(obj)) {
			throw new IllegalArgumentException();
		} else if (front == null) {
			front = new ListNode(obj, null);
			size++;
			return true;
		} else {
			return front.add(obj);
		}

	}

	/**
	 * Returns the element at the given index
	 *
	 * @param idx the index of the element to retrieve
	 * @return the element at that index
	 * @throws IndexOutOfBoundsException if the index is invalid
	 */
	public E get(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}

		return front.get(idx);

	}

	/**
	 * Removes and returns the element at the given index
	 * 
	 * @param idx the index of the element to remove
	 * @return the removed element
	 * @throws IndexOutOfBoundsException if the index is invalid
	 */
	public E remove(int idx) {

		if (idx < 0 || idx > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		E removed;
		if (idx == 0) {
			removed = front.data;
			front = front.next;
		} else {
			removed = front.remove(idx - 1);
		}
		size--;
		return removed;
	}

	/**
	 * Removes the first occurrence of the given element from the list
	 * 
	 * @param obj the element to remove
	 * @return true if the element was found and removed and false otherwise
	 */
	public boolean remove(E obj) {
		if (obj == null) {
			return false;
		}
		if (front == null) {
			return false;
		}
		if (front.data.equals(obj)) {
			front = front.next;
			size--;
			return true;
		}

		boolean removed = front.remove(obj);
		if (removed) {
			size--;
		}
		return removed;
	}

	/**
	 * Replaces the element at the given index with a new element and this list does
	 * not allow duplicates
	 *
	 * @param idx the index to replace
	 * @param obj the new value
	 * @return the old value that used to be at that index
	 * @throws NullPointerException      if obj is null
	 * @throws IllegalArgumentException  if obj already exists in the list
	 * @throws IndexOutOfBoundsException if the index is invalid
	 */
	public E set(int idx, E obj) {

		if (obj == null) {
			throw new NullPointerException();
		}
		if (contains(obj)) {
			throw new IllegalArgumentException();
		}
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}

		return front.set(idx, obj);
	}

	/**
	 * Returns true if the list contains the given element.
	 *
	 * @param obj the element to look for
	 * @return true if found and false otherwise
	 */
	public boolean contains(E obj) {
		if (front == null) {
			return false;
		} else {
			return front.contains(obj);
		}
	}

	/**
	 * A private helper class that represents one node in the linked list and each
	 * node stores a piece of data and a link to the next node
	 * 
	 * @author Steven Saleeb
	 */
	private class ListNode {

		/** The data in the list node */
		public E data;

		/** The reference to the next list node in the list */
		public ListNode next;

		/**
		 * Constructor for a list node object
		 * 
		 * @param data the data in the node
		 * @param next the reference to the next list node
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}

		/**
		 * Recursively adds an element at a specific index after this node
		 *
		 * @param idx the relative index from this node
		 * @param obj the object to insert
		 * @throws IllegalArgumentException if the given Object is duplicate
		 */
		private void add(int idx, E obj) {
			if (contains(obj)) {
				throw new IllegalArgumentException("Duplicate element");
			}
			if (idx == 0) {
				next = new ListNode(obj, next);
				size++;
			} else {
				next.add(idx - 1, obj);
			}
		}

		/**
		 * Recursively adds an element to the end of the list
		 *
		 * @param obj the object to add
		 * @return true when the element is added
		 * @throws IllegalArgumentException if the given Object is duplicate
		 */
		private boolean add(E obj) {
			if (contains(obj)) {
				throw new IllegalArgumentException("Duplicate element");
			}
			if (next == null) {
				next = new ListNode(obj, null);
				size++;
				return true;
			} else {
				return next.add(obj);
			}
		}

		/**
		 * Recursively gets the element at the given relative index
		 *
		 * @param idx the relative index from this node
		 * @return the value at that position
		 */
		private E get(int idx) {
			if (idx == 0) {
				return data;
			} else {
				return next.get(idx - 1);
			}
		}

		/**
		 * Recursively removes the element at a given relative index
		 *
		 * @param idx the relative index from this node
		 * @return the removed element
		 */
		private E remove(int idx) {
			E removed;

			if (idx == 0) {
				removed = next.data;
				next = next.next;
				return removed;
			} else {
				return next.remove(idx - 1);
			}
		}

		/**
		 * Recursively removes the first matching element
		 *
		 * @param obj the value to remove
		 * @return true if removed and false otherwise
		 */
		private boolean remove(E obj) {

			if (next == null) {
				return false;
			}

			if (next != null && next.data.equals(obj)) {
				next = next.next;
				return true;
			}
			return next != null && next.remove(obj);
		}

		/**
		 * Recursively updates the element at the given relative index
		 *
		 * @param idx the relative index from this node
		 * @param obj the new value
		 * @return the old value
		 */
		private E set(int idx, E obj) {

			if (idx == 0) {
				E old = data;
				data = obj;
				return old;
			}
			return next.set(idx - 1, obj);

		}

		/**
		 * Recursively checks if this node or any node after it contains the given
		 * element
		 *
		 * @param obj the element to search for
		 * @return true if found and false otherwise
		 */
		private boolean contains(E obj) {
			if (obj.equals(data)) {
				return true;
			}
			if (next == null) {
				return false;
			}
			return next.contains(obj);

		}

	}
}
