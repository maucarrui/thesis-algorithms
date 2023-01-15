package com.github.maucarrui.algorithms.treeisomorphism;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.LinkedList;

/**
 * Package-private class to represent a multiset.
 *
 * Informally a multiset is simply a set of elements where the repetition of its
 * elements is allowed.
 */
class MultiSet<T> {

    /** Mapping to keep track of which elements are in the multiset and their
     * amount. */
    private HashMap<T, Integer> elements;

    /** The amount of elements the multiset has. */
    private int size;

    /**
     * Unique constructor which initializes the hashmap of the elements.
     */
    MultiSet() {
	this.elements = new HashMap<>();
	this.size = 0;
    }

    /**
     * Add an element to the multiset. If the element is already contained, then
     * increase the amount of ocurrances it has on the multiset.
     * @param element the element to add to the multiset.
     */
    void add(T element) {
	if (this.elements.containsKey(element)) {
	    int oldValue = this.elements.get(element);
	    this.elements.put(element, oldValue++);
	} else {
	    this.elements.put(element, 1);
	}

	/* Increase the number of elements in the multiset. */
	this.size++;
    }

    /**
     * Returns if the multiset contains the given element.
     * @return true if the multiset contains the given element, false otherwise.
     */
    boolean contains(T element) {
	return this.elements.containsKey(element);
    }

    /**
     * Returns the hashmap of the elements of the multiset.
     * @return the hashmap of the elements of the multiset.
     */
    HashMap<T, Integer> elements() {
	return this.elements;
    }

    /**
     * Returns the elements of the multiset as a linked list.
     * @return the elements of the multiset as a linked list.
     */
    LinkedList<T> values() {
	LinkedList<T> vals = new LinkedList<>();

	for (Entry<T, Integer> entry : this.elements.entrySet()) {
	    for (int i = 0; i < entry.getValue(); i++) {
		vals.add(entry.getKey());
	    }
	}

	return vals;
    }

    /**
     * Returns the amount of elements contained in the multiset.
     * @return the amount of elements contained in the multiset.
     */
    int size() {
	return this.size;
    }

    /**
     * Returns whether the multiset is equal to the given object.
     * @return true if the multiset is equal to the given object, false
     *         otherwise.
     */
    @Override public boolean equals(Object object) {
	/* If the object is null or from another class, return false. */
	if (object == null || getClass() != object.getClass()) {
	    return false;
	}

	/* Supress the warning when converting to the specified class. */
	@SuppressWarnings("unchecked") MultiSet<T> M = (MultiSet<T>) object;

	/* If the size of the multisets varies or the hashmap varies, return
	 * false. */
	if (this.size() != M.size()) {
	    return false;
	}

	/* If the hashmap of the multisets varies, return false. */
	if (!this.elements().equals(M.elements())) {
	    return false;
	}

	/* If the size and hashmap are equal, return true. */
	return true;
    }

    /**
     * Returns the hash code of the multiset. The hash code of the multiset is
     * the sum of each entry in the map multiplied by the amount of occurrances
     * it has.
     * @return the hash code of the multiset.
     */
    @Override public int hashCode() {
	int hash = 0;

	/* Traverse all the elements of the multiset. */
	for (Entry<T, Integer> entry : this.elements.entrySet()) {
	    T element = entry.getKey();
	    int occurrance = entry.getValue();

	    /* Get the hash code of the element, multiply it by the amount of
	     * occurrances, and add it to the hash code of the multiset. */
	    hash += (element.hashCode() * occurrance);
	}

	return hash;
    }
}
