package com.github.maucarrui.algorithms.doublylexicalordering;

import java.util.HashSet;
import java.util.HashMap;

/**
 * Package-private class to represent a block in a matrix.
 *
 * For a matrix M with an ordered partition (R1, R2, ..., Rk) of its row set
 * R, and an ordered partition (C1, C2, ..., Cl) of its column set C. A
 * block is an ordered pair B = (Ri, Cj), which represents the sub-matrix of
 * M defined by the rows and columns indexes contained in Ri and Cj,
 * respectively.
 */
class Block {
    /** The row part Ri of the block B = (Ri, Cj). */
    private HashSet<Integer> rows;

    /** The column part Cj of the block B = (Ri, Cj). */
    private HashSet<Integer> columns;

    /** The amount of non-zero entries contained in the block B */
    private int size;

    /** The amount of non-zero entries for each row in the row part. */
    private HashMap<Integer, Integer> rowSize;

    /**
     * The block B' that is right to B.
     *
     * Let B = (Ri, Cj) and B' = (Rm, Cn) be blocks, we say that B' is right
     * to B if and only if i = m and j < n.
     */
    private Block right;

    /**
     * The block B' that is below to B.
     *
     * Let B = (Ri, Cj) and B' = (Rm, Cn) be blocks, we say that B' is below
     * to B if and only if i < m and j = n.
     */
    private Block below;

    /** The block B' that goes after B. */
    private Block next;

    /** The block B' that goes before B. */
    private Block previous;

    /**
     * Unique constructor which receives the row part Ri, and the
     * column part Cj that define the block B = (Ri, Cj).
     * @param Ri the row part of the block.
     * @param Cj the column part of the block.
     */
    Block(HashSet<Integer> Ri, HashSet<Integer> Cj) {
	this.rows = rows;
	this.columns = columns;
	this.size = 0;
	this.rowSize = new HashMap<Integer, Integer>();
	this.right = null;
	this.below = null;
	this.next = null;
	this.previous = null;
    }

    /**
     * Returns the row part of the block.
     * @return the row part of the block.
     */
    HashSet<Integer> rows() {
	return this.rows;
    }

    /**
     * Returns the column part of the block.
     * @return the column part of the block.
     */
    HashSet<Integer> columns() {
	return this.columns;
    }

    /**
     * Returns the amount of non-zero entries in the block.
     * @return the amount of non-zero entries in the block.
     */
    int size() {
	return this.size;
    }

    /**
     * Sets the new size of the block.
     * @param the new size of the block.
     */
    void setSize(int size) {
	this.size = size;
    }

    /**
     * Returns if the block is constant, that is, if all the entries have the
     * same value.
     * @return true if the block is constant, false otherwise.
     */
    boolean isConstant() {
	int numEntries = this.rows.size() * this.columns.size();
	return (this.size == 0) || (this.size == numEntries);
    }

    /**
     * Sets the new size of the row block.
     * @param the new size of the row block.
     */
    void setRowSize(int row, int size) {
	this.rowSize.put(row, size);
    }

    /**
     * Sets the new hash map for the sizes of the rows block.
     * @param newRowSizes the new hash map for the sizes of the rows block.
     */
    void setRowSizeMap(HashMap<Integer, Integer> newRowSizes) {
	this.rowSize = newRowSizes;
    }

    /**
     * Returns the amount of non-zero entries in the row.
     * @return the amount of non-zero entries in the row.
     */
    int getRowSize(int row) {
	return this.rowSize.get(row);
    }

    /**
     * Returns the row size mapping.
     * @return the row size mapping.
     */
    HashMap<Integer, Integer> getRowSizeMap() {
	return this.rowSize;
    }

    /**
     * Indicates which block is to the right.
     * @param right the block to the right.
     */
    void setRight(Block right) {
	this.right = right;
    }

    /**
     * Indicates which block is below.
     * @param below the block that's below.
     */
    void setBelow(Block below) {
	this.below = below;
    }

    /**
     * Indicates which block is next.
     * @param next the block that's next.
     */
    void setNext(Block next) {
	this.next = next;
    }

    /**
     * Indicates which block is before.
     * @param next the block that's before.
     */
    void setPrevious(Block previous) {
	this.previous = previous;
    }

    /**
     * Returns the block to the right.
     * @return the block to the right.
     */
    Block getRight() {
	return this.right;
    }

    /**
     * Returns the block that's below.
     * @return the block that's below.
     */
    Block getBelow() {
	return this.below;
    }

    /**
     * Returns the block that's next.
     * @return the block that's next.
     */
    Block getNext() {
	return this.next;
    }

    /**
     * Returns the block that's before.
     * @return the block that's before.
     */
    Block getPrevious() {
	return this.previous;
    }

    /**
     * Returns if the current block has a block below.
     * @return true if the current block has a block below, false otherwise.
     */
    boolean hasBelow() {
	return (this.below != null);
    }

    /**
     * Returns if the current block has a block right to it.
     * @return true if the current block has a block to the right, false
     *         otherwise.
     */
    boolean hasRight() {
	return (this.right != null);
    }

    /**
     * Returns if the current block has a next block.
     * @return true if the current block has a next block, false otherwise.
     */
    boolean hasNext() {
	return (this.next != null);
    }

    /**
     * Returns if the current block has a previous block.
     * @return true if the current block has a previous block, false
     *         otherwise.
     */
    boolean hasPrevious() {
	return (this.previous != null);
    }
}
