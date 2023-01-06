package com.github.maucarrui.algorithms.doublylexicalordering;

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

    /** The ordered row partition. */
    LinkedList<HashSet<Integer>> orderedRowPartition;

    /** The ordered column partition. */
    LinkedList<HashSet<Integer>> orderedColumnPartition;

    /** The original matrix */
    int[][] original;

    /**
     * Unique constructor for a Doubly Lexical Orderer that receives the
     * matrix to be ordered.
     * @param matrix the matrix to be ordered.
     */
    public DoublyLexicalOrderer(int[][] matrix) {
	this.original = matrix;
	this.orderedRowPartition = new LinkedList<HashSet<Integer>>();
	this.orderedColumnPartition = new LinkedList<HashSet<Integer>>();
    }

    /**
     * Determines the size of a block, it also defines the size of each row
     * block.
     * @param B the block to determine its size.
     */
    private void determineSize(Block B) {
	/* Get the rows and columns of the block. */
	HashSet<Integer> Ri = B.rows();
	HashSet<Integer> Cj = B.columns();

	/* B's size.*/
	int sizeB = 0;

	/* Iterate each row in Ri and determine the size of (r, Cj). */
	for (int r : Ri) {
	    /* (r, Cj)'s size. */
	    int sizeR = 0;

	    for (int c : Cj) {
		sizeR += this.original[r][c];
	    }

	    B.setRowSize(r, sizeR);

	    sizeB += sizeR;
	}

	B.setSize(sizeB);
    }

    /**
     * Returns the index of a splitting row of B.
     *
     * A splitting row of B = (Ri, Cj) is a row r in Ri such that the row block
     * (r, Cj) in non-constant.
     * @param B the block to determine if it has a splitting row.
     * @return the index of a splitting row of B, if B has no splitting row
     *         return -1.
     */
    private int getSplittingRow(Block B) {
	/* Get the indexes of the rows contained in the block. */
	HashSet<Integer> rowIndexes = B.rows();

	/* Get the amount of columns contained in B. */
	int numCols = B.columns().size();

	/* Get the row part of the block */
	HashSet<Integer> Ri = B.rows();

	/* Iterate each row in Ri to determine if a block (r, Cj) is
	 * non-constant */
	for (int r : Ri) {
	    if ((B.getRowSize(r) > 0) && (B.getRowSize(r) < numCols)) {
		return r;
	    }
	}

	/* If all row blocks are constant, there is no splitting row. */
	return -1;
    }

    /**
     * Defines a column refinement. Given a row r, the first set of columns
     * contains all the non-zero entries, the second set of columns contains all
     * the zero entries.
     * @param row the row for which the column refinement will be defined.
     * @param Cj the original columns set.
     * @return a column refinement of original columns set.
     */
    private Refinement getColumnRefinement(int row, HashSet<Integer> Cj) {
	/* Define a column refinement as an array of sets. */
	HashSet<Integer> left  = new HashSet<Integer>();
	HashSet<Integer> right = new HashSet<Integer>();

	// Iterate through the columns contained in the set Cj.
	for (int col : Cj) {
	    // If the entry M[r][c] is 1, add it to the first set.
	    if (this.original[row][col] == 1) {
		left.add(col);
	    } else {
		// Otherwise, add it to the second set.
		right.add(col);
	    }
	}

	return new Refinement(left, right);
    }

    /**
     * Defines a row refinement. Given a column c, the first set of rows
     * contains all the non-zero entries, the second set of rows contains all
     * the zero entries.
     * @param col the column for which the row refinement will be defined.
     * @param Ri the original rows set.
     * @return a row refinement of original row set.
     */
    private Refinement getRowRefinement(int col, HashSet<Integer> Ri) {
	/* Define a row refinement as an array of sets. */
	HashSet<Integer> left  = new HashSet<Integer>();
	HashSet<Integer> right = new HashSet<Integer>();

	// Iterate through the columns contained in the set Cj.
	for (int row : Ri) {
	    // If the entry M[r][c] is 1, add it to the first set.
	    if (this.original[row][col] == 1) {
		left.add(row);
	    } else {
		// Otherwise, add it to the second set.
		right.add(row);
	    }
	}

	return new Refinement(left, right);
    }

    /**
     * Produce the blocks obtained by performing a column refinement.
     * @param colRef the column refinement.
     * @param B the current block.
     * @return the new current block.
     */
    private Block produceColumnRefinement(Refinement Ref, Block B) {
	/* Get the left and right refinement */
	HashSet<Integer> lRef = Ref.getLeft();
	HashSet<Integer> rRef = Ref.getRight();

	/* Determine which parts are smaller and bigger. */
	HashSet<Integer> sRef, bRef;
	boolean leftSmaller = (lRef.size() <= rRef.size());

	if (leftSmaller) {
	    sRef = lRef;
	    bRef = rRef;
	} else {
	    sRef = rRef;
	    bRef = lRef;
	}

	/* Produce all the blocks obtained by the column refinement. */
	Block current = B;
	Block ogLeft    = null;
	Block ogRight   = null;
	Block prevLeft  = null;
	Block prevRight = null;

	do {
	    /* Get the current block's row part. */
	    HashSet<Integer> Ri = current.rows();

	    Block smallBlock = new Block(Ri, sRef);
	    Block bigBlock   = new Block(Ri, bRef);

	    /* Determine the smaller block's size. */
	    determineSize(smallBlock);

	    /* Use the previous information to determine the bigger block's size. */
	    int sizeBig = 0;
	    for (int r : Ri) {
		int currentRowSize = current.getRowSize(r);
		int smallRowSize   = smallBlock.getRowSize(r);
		int bigRowSize     = currentRowSize - smallRowSize;

		bigBlock.setRowSize(r, bigRowSize);

		sizeBig += bigRowSize;
	    }

	    bigBlock.setSize(sizeBig);

	    /* Determine which block is on the left and right. */
	    Block leftBlock, rightBlock;
	    if (leftSmaller) {
		leftBlock  = smallBlock;
		rightBlock = bigBlock;
	    } else {
		leftBlock  = bigBlock;
		rightBlock = smallBlock;
	    }

	    /* Adjust the blocks' pointers. */
	    leftBlock.setRight(rightBlock);
	    rightBlock.setRight(current.getRight());

	    if (prevLeft == null) {
		ogLeft  = leftBlock;
		ogRight = rightBlock;
	    } else {
		prevLeft.setBelow(leftBlock);
		prevLeft.setNext(leftBlock);
		leftBlock.setPrevious(prevLeft);
		prevRight.setBelow(rightBlock);
		prevRight.setNext(rightBlock);
	    }

	    /* The current blocks become the previous. */
	    prevLeft  = leftBlock;
	    prevRight = rightBlock;

	    /* Move to the next block below, if there is no such block, exit the
	     * loop. */
	    if (current.hasBelow()) {
		current = current.getBelow();
	    } else {
		leftBlock.setNext(ogRight);
		ogRight.setPrevious(leftBlock);
		rightBlock.setNext(current.getNext());
		if (current.hasNext()) {
		    current.getNext().setPrevious(rightBlock);
		}
		break;
	    }

	} while (true);

	/* Return the new block we're standing on. */
	return ogLeft;
    }

    /**
     * Produce the blocks obtained by performing a row refinement.
     * @param rowRef the row refinement.
     * @param B the current block.
     * @return the new current block.
     */
    private Block produceRowRefinement(Refinement rowRef, Block B) {
	/* Get the left and right refinement */
	HashSet<Integer> lRef = rowRef.getLeft();
	HashSet<Integer> rRef = rowRef.getRight();

	/* Determine which parts are smaller and bigger. */
	HashSet<Integer> sRef, bRef;
	boolean leftSmaller = (lRef.size() <= rRef.size());

	if (leftSmaller) {
	    sRef = lRef;
	    bRef = rRef;
	} else {
	    sRef = rRef;
	    bRef = lRef;
	}

	/* Produce all the blocks obtained by the column refinement. */
	Block current = B;
	Block ogTop   = null;
	Block ogBot   = null;
	Block prevTop = null;
	Block prevBot = null;

	do {
	    /* Get the current block's column part. */
	    HashSet<Integer> Cj = current.columns();

	    Block smallBlock = new Block(sRef, Cj);
	    Block bigBlock   = new Block(bRef, Cj);

	    /* Determine the smaller block's size. */
	    HashMap<Integer, Integer> currentRowMap = current.getRowSizeMap();
	    int sizeSmall = 0;
	    for (int r : sRef) {
		int rowBlockSize = currentRowMap.get(r);
		smallBlock.setRowSize(r, rowBlockSize);
		sizeSmall += rowBlockSize;

		/* Remove the appended row of the current map. */
		currentRowMap.remove(r);
	    }
	    smallBlock.setSize(sizeSmall);

	    /* Use the previous information to determine the bigger block's size. */
	    int sizeBig = current.size() - sizeSmall;
	    bigBlock.setSize(sizeBig);
	    bigBlock.setRowSizeMap(currentRowMap);

	    /* Determine which block is on the top and bot. */
	    Block topBlock, botBlock;
	    if (leftSmaller) {
		topBlock  = smallBlock;
		botBlock = bigBlock;
	    } else {
		topBlock  = bigBlock;
		botBlock = smallBlock;
	    }

	    /* Adjust the blocks' pointers. */
	    topBlock.setBelow(botBlock);
	    topBlock.setNext(botBlock);
	    botBlock.setPrevious(topBlock);
	    botBlock.setBelow(current.getBelow());
	    botBlock.setNext(current.getNext());

	    if (current.hasNext()) {
		current.getNext().setPrevious(botBlock);
	    }

	    if (current.hasPrevious()) {
		current.getPrevious().setNext(topBlock);
	    }

	    if (prevTop == null) {
		ogTop = topBlock;
		ogBot = botBlock;
	    } else {
		prevTop.setRight(topBlock);
		prevBot.setRight(botBlock);
	    }

	    /* The current blocks become the previous. */
	    prevTop = topBlock;
	    prevBot = botBlock;

	    /* Move to the next block to the right, if there is no such block,
	     * exit the loop. */
	    if (current.hasRight()) {
		current = current.getRight();
	    } else {
		break;
	    }

	} while (true);

	/* Return the new block we're standing on. */
	return ogTop;
    }

    /**
     * Returns the ordered partition of rows as an array.
     * @return the ordered partition of rows as an array.
     */
    public int[] getOrderedRows() {
	/* Get the amount of rows in the matrix. */
	int numRows = this.original.length;

	int[] orderedRows = new int[numRows];

	/* Traverse the ordered partition to obtain an array representation. */
	int k = 0;
	for (HashSet<Integer> rowPart : orderedRowPartition) {
	    for (int r : rowPart) {
		orderedRows[k++] = r;
	    }
	}

	return orderedRows;
    }

    /**
     * Returns the ordered partition of columns as an array.
     * @return the ordered partition of columns as an array.
     */
    public int[] getOrderedColumns() {
	/* Get the amount of rows in the matrix. */
	int numCols = this.original.length;

	int[] orderedCols = new int[numCols];

	/* Traverse the ordered partition to obtain an array representation. */
	int k = 0;
	for (HashSet<Integer> colPart : orderedColumnPartition) {
	    for (int c : colPart) {
		orderedCols[k++] = c;
	    }
	}

	return orderedCols;
    }

    /**
     * Returns a doubly lexicographical ordering of the original matrix.
     * @return a doubly lexicographical ordering of the original matrix.
     */
    public int[][] getOrderedMatrix() {
	return this.original;
    }
}
