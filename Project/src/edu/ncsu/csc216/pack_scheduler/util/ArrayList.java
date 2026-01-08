package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * A collections that holds objects of type E.
 * @param <E> the type of elements in this list
 * @author Steven Saleeb
 * @author Liam Yeager
 * @author Shreyash Jain
 */
public class ArrayList<E> extends AbstractList<E> {
	
	/** 
	 * The initial size of the array.
	 */
	private static final int INIT_SIZE = 10;
	
	/**
	 * The array that stores the objects
	 */
	private E[] list;
	
	/**
	 * The size of the array
	 */
	private int size;
	
	/**
	 * Constructor for an Array List object
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		list = (E[]) new Object[INIT_SIZE];
		size = 0;
	}
	
	/**
	 * Adds an object to the list
	 * @param idx the index where the object will be added
	 * @param object the object to be added
	 */
	public void add(int idx, E object) {
		if (object == null) {
			throw new NullPointerException();
		}
		if (idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = 0; i < size; i++) {
			if (object.equals(list[i])) {
				throw new IllegalArgumentException();
			}
		}
		
		if (size == list.length) {
			growArray();
		}
	
		for (int i = size; i > idx; i--) {
			list[i] = list[i - 1];
		}
		list[idx] = object;
		size++;
	}
	
	/**
	 * Doubles the size of the array when it gets full and 
	 * copies all elements from the old array into the new bigger one.
	 */
	@SuppressWarnings("unchecked")
	private void growArray() {
		E[] replaceList = (E[]) new Object[size * 2];
		for (int i = 0; i < size; i++) {
			replaceList[i] = list[i];
		}
		list = replaceList;
	}
	
	/**
	 * Removes the element at the given index and 
	 * shifts all elements after it one position to the left.
	 * @param idx index of the element to remove
	 * @return the element that was removed
	 * @throws IndexOutOfBoundsException if the index is invalid
	 */
	@Override
	public E remove(int idx) {
		
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
		
		E removed = list[idx];
		
		for (int i = idx; i < size; i++) {
			list[i] = list[i + 1];
		}
		
		list[size - 1] = null;
		size--;
		return removed;
		
		
	}
	
	/**
	 * Replaces the element at the given index with a new element
	 * @param idx index of the element to replace
	 * @param object the new element to store
	 * @return the old element that was previously at that index
	 * @throws NullPointerException if the new element is null
	 * @throws IndexOutOfBoundsException if the index is out of range
	 * @throws IllegalArgumentException if the new element already exist
	 */
	@Override
	public E set(int idx, E object) {
		
		if(object == null) {
			throw new NullPointerException();
		}
		
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException();
		}
		
		for (int i = 0; i < size; i++) {
			if (object.equals(list[i]) && i != idx) {
				throw new IllegalArgumentException();
			}
		}
		
		E oldObject = list[idx];
        list[idx] = object;
        return oldObject;
		
	
	}
	
	/**
	 * Returns the element stored at the given index
	 * @param idx index of the element to get
	 * @return the element at that index
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	@Override
	public E get(int idx) {
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException();
		}
		return list[idx];
	}
	
	/**
	 * Returns the number of elements currently in the list
	 * @return how many elements are stored in the list
	 */
	@Override
	public int size() {
		return size;
	}

}
