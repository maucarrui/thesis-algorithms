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

	/**
	 * The block B' that goes after B.
	 */
	private Block next;

	/**
	 * The block B' that goes before B.
	 */
	private Block previous;

	/**
	 * Unique constructor which receives the row part Ri, and the column
	 * part Cj that define the block B = (Ri, Cj).
	 * @param Ri the row part of the block.
	 * @param Cj the column part of the block.
	 */
	public Block(HashSet<Integer> Ri, HashSet<Integer> Cj) {
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
	public HashSet<Integer> rows() {
	    return this.rows;
	}

	/**
	 * Returns the column part of the block.
	 * @return the column part of the block.
	 */
	public HashSet<Integer> columns() {
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
	public void setRowSizeMap(HashMap<Integer, Integer> newRowSizes) {
	    this.rowSize = newRowSizes;
	}

	/**
	 * Returns the amount of non-zero entries in the row.
	 * @return the amount of non-zero entries in the row.
	 */
	public int getRowSize(int row) {
	    return this.rowSize.get(row);
	}

	/**
	 * Returns the row size mapping.
	 * @return the row size mapping.
	 */
	public HashMap<Integer, Integer> getRowSizeMap() {
	    return this.rowSize;
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
	 * Indicates which block is before.
	 * @param next the block that's before.
	 */
	public void setPrevious(Block previous) {
	    this.previous = previous;
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
	public Block getBelow() {
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
	 * Returns the block that's before.
	 * @return the block that's before.
	 */
	public Block getPrevious() {
	    return this.previous;
	}

	/**
	 * Returns if the current block has a block below.
	 * @return true if the current block has a block below, false otherwise.
	 */
	public boolean hasBelow() {
	    return (this.below != null);
	}

	/**
	 * Returns if the current block has a block right to it.
	 * @return true if the current block has a block to the right, false
	 *         otherwise.
	 */
	public boolean hasRight() {
	    return (this.right != null);
	}

	/**
	 * Returns if the current block has a next block.
	 * @return true if the current block has a next block, false otherwise.
	 */
	public boolean hasNext() {
	    return (this.next != null);
	}

	/**
	 * Returns if the current block has a previous block.
	 * @return true if the current block has a previous block, false
	 *         otherwise.
	 */
	public boolean hasPrevious() {
	    return (this.previous != null);
	}
    }

    /**
     *
     */
    protected class Refinement {
	HashSet<Integer> left;
	HashSet<Integer> right;

	public Refinement(HashSet<Integer> left, HashSet<Integer> right) {
	    this.left = left;
	    this.right = right;
	}

	public HashSet<Integer> getLeft(){
	    return this.left;
	}

	public HashSet<Integer> getRight(){
	    return this.right;
	}
    }


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
