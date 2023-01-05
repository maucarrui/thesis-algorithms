package com.github.maucarrui.algorithms.doublylexicalordering

import java.util.HashSet;
import java.util.LinkedList;
import java.util.HashMap;

/**
 * Class to represent a Doubly Lexical Orderer.
 *
 *
 * Given a (0,1)-matrix M, a doubly lexical orderer can return a double lexical
 * ordering of M, including the ordered rows and columns.
 */
public class DoublyLexicalOrderer {

    /**
     * Internal protected class to represent a block in a matrix.
     *
     * For a matrix M with an ordered partition (R1, R2, ..., Rk) of its row set
     * R, and an ordered partition (C1, C2, ..., Cl) of its column set C. A
     * block is an ordered pair B = (Ri, Cj), which represents the sub-matrix of
     * M defined by the rows and columns indexes contained in Ri and Cj,
     * respectively.
     */
    protected class Block {
	/** The row part Ri of the block B = (Ri, Cj). */
	private HashSet<int> rows;

	/** The column part Cj of the block B = (Ri, Cj). */
	private HashSet<int> columns;

	/** The amount of non-zero entries contained in the block B */
	private int size;

	/** The amount of non-zero entries for each row in the row part. */
	private HashMap<int, int> rowSize;

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

	/**
	 * The block B' that goes next to B.
	 */
	private Block next;

	/** Indicates if the block is the rightmost block. */
	private bool isRightmost;

	/** Indicates if the block is the bottom block. */
	private bool isBottom;

	/**
	 * Unique constructor which receives the row part Ri, and the column
	 * part Cj that define the block B = (Ri, Cj).
	 * @param Ri the row part of the block.
	 * @param Cj the column part of the block.
	 */
	public Block(set<int> Ri, Cj) {
	    this.rows = rows;
	    this.columns = columns;
	    this.size = 0;
	}

	/**
	 * Returns the row part of the block.
	 * @return the row part of the block.
	 */
	public set<int> rows() {
	    return this.rows;
	}

	/**
	 * Returns the column part of the block.
	 * @return the column part of the block.
	 */
	public set<int> columns() {
	    return this.columns;
	}

	/**
	 * Returns the amount of non-zero entries in the block.
	 * @return the amount of non-zero entries in the block.
	 */
	public int size() {
	    return this.size;
	}

	/**
	 * Sets the new size of the block.
	 * @param the new size of the block.
	 */
	public void setSize(int size) {
	    this.size = size;
	}

	/**
	 * Sets the new size of the row block.
	 * @param the new size of the row block.
	 */
	public void setRowSize(int row, int size) {
	    this.rowSize.put(row, size);
	}

	/**
	 * Sets the new hash map for the sizes of the rows block.
	 * @param newRowSizes the new hash map for the sizes of the rows block.
	 */
	public void setRowSize(HashMap<int, int> newRowSizes) {
	    this.rowSize = newRowSizes;
	}

	/**
	 * Returns the amount of non-zero entries in the row.
	 * @return the amount of non-zero entries in the row.
	 */
	public int getRowSize(int row) {
	    return this.rowSize.get(row)
	}

	/**
	 * Indicates which block is to the right.
	 * @param right the block to the right.
	 */
	public void setRight(Block right) {
	    this.right = right;
	}

	/**
	 * Indicates which block is below.
	 * @param below the block that's below.
	 */
	public void setBelow(Block below) {
	    this.below = below;
	}

	/**
	 * Indicates which block is next.
	 * @param next the block that's next.
	 */
	public void setNext(Block next) {
	    this.next = next;
	}

	/**
	 * Returns the block to the right.
	 * @return the block to the right.
	 */
	public Block getRight() {
	    return this.right;
	}

	/**
	 * Returns the block that's below.
	 * @return the block that's below.
	 */
	public Block getBelow(w) {
	    return this.below;
	}

	/**
	 * Returns the block that's next.
	 * @return the block that's next.
	 */
	public Block getNext() {
	    return this.next;
	}

	/**
	 * Sets the current block as the rightmost.
	 */
	public void setAsRightmost() {
	    this.isRightmost = true;
	}

	/**
	 * Sets the current block as the bottom.
	 */
	public void setAsRightmost() {
	    this.isBottom = true;
	}

	/**
	 * Indicates if the current block is the rightmost.
	 * @return true if the current block is the rightmost, false otherwise.
	 */
	public bool isRightmost() {
	    return this.isRightmost;
	}

	/**
	 * Indicates if the current block is at the bottom.
	 * @return true if the current block is at the bottom, false otherwise.
	 */
	public bool isBottom() {
	    return this.bottom;
	}

	/** The ordered row partition. */
	LinkedList<HashSet<int>> orderedRowPartition;

	/** The ordered column partition. */
	LinkedList<HashSet<int>> orderedColumnPartition;

	/** The original matrix */
	int[][] original;

	/**
	 * Unique constructor for a Doubly Lexical Orderer that receives the
	 * matrix to be ordered.
	 * @param matrix the matrix to be ordered.
	 */
	public DoublyLexicalOrderer(int[][] matrix) {
	    this.original = matrix;
	}

    }
}
