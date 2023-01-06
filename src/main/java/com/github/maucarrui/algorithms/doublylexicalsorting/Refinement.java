package com.github.maucarrui.algorithms.doublylexicalordering;

import java.util.HashSet;

/**
 * Package-private class to represent a refinement of a set.
 *
 * A refinement of a set is an ordered bipartition of a given set. The first set
 * is called the left refinement and the second is called the right refinement.
 */
class Refinement {
    /** The left refinement. */
    private HashSet<Integer> left;
    
    /** The right refinement. */
    private HashSet<Integer> right;
    
    /**
     * Unique constructor which receives the left and right refinement.
     * @param left the left refinement.
     * @param right the right refinement.
     */
    Refinement(HashSet<Integer> left, HashSet<Integer> right) {
	this.left = left;
	this.right = right;
    }
    
    /**
     * Returns the left refinement.
     * @return the left refinement.
     */
    HashSet<Integer> getLeft(){
	return this.left;
    }

    /**
     * Returns the right refinement.
     * @return the right refinement.
     */    
    HashSet<Integer> getRight(){
	return this.right;
    }
}
