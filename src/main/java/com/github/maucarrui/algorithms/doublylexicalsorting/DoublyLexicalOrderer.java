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
	 * The block B' that goes after B.
	 */
	private Block next;

	/**
	 * The block B' that goes before B.
	 */
	private Block previous;

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
	public Block(HashSet<int> Ri, HashSet<int> Cj) {
	    this.rows = rows;
	    this.columns = columns;
	    this.size = 0;
	    this.rowSize = new HashMap<int, int>();
	    this.right = null;
	    this.below = null;
	    this.next = null;
	    this.isRightmost = false;
	    this.isBottom = false;
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
	 * Returns the block that's before.
	 * @return the block that's before.
	 */
	public Block getPrevious() {
	    return this.previous;
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
	 * Returns if the current block has a block below.
	 * @return true if the current block has a block below, false otherwise.
	 */
	public bool hasBelow() {
	    return (this.below != null);
	}

	/**
	 * Returns if the current block has a block right to it.
	 * @return true if the current block has a block to the right, false
	 *         otherwise.
	 */
	public bool hasRight() {
	    return (this.right != null);
	}

	/**
	 * Returns if the current block has a next block.
	 * @return true if the current block has a next block, false otherwise.
	 */
	public bool hasNext() {
	    return (this.next != null);
	}

	/**
	 * Returns if the current block has a previous block.
	 * @return true if the current block has a previous block, false
	 *         otherwise.
	 */
	public bool hasPrevious() {
	    return (this.previous != null);
	}
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
	this.orderedRowPartition = new LinkedList<HashSet<int>>();
	this.orderedColumnPartition = new LinkedList<HashSet<int>>();
    }

    /**
     * Determines the size of a block, it also defines the size of each row
     * block.
     * @param B the block to determine its size.
     */
    private void determineSize(Block B) {
	/* Get the rows and columns of the block. */
	HashSet<int> Ri = B.rows();
	HashSet<int> Cj = B.columns();
	
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
	HashSet<int> rowIndexes = B.rows();
	
	/* Get the amount of columns contained in B. */
	int numCols = B.columns().size();
	
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
    private []HashSet<int> getColumnRefinement(int row, HashSet<int> Cj) {
	/* Define a column refinement as an array of sets. */
	[]HashSet<int> columnRefinement = new HashSet<int>[2];
	columnRefinement[0] = new HashSet<int>();
	columnRefinement[1] = new HashSet<int>();
	
	// Iterate through the columns contained in the set Cj.
	for (int col : Cj) {
	    // If the entry M[r][c] is 1, add it to the first set.
	    if (this.original[row][col] == 1) {
		columnRefinement[0].add(col);
	    } else {
		// Otherwise, add it to the second set.
		columnRefinement[1].add(col);
	    }
	}
	
	return columnRefinement;
    }

    /**
     * Defines a row refinement. Given a column c, the first set of rows
     * contains all the non-zero entries, the second set of rows contains all
     * the zero entries.
     * @param col the column for which the row refinement will be defined.
     * @param Ri the original rows set.
     * @return a row refinement of original row set.
     */
    private []HashSet<int> getRowRefinement(int col, HashSet<int> Ri) {
	/* Define a row refinement as an array of sets. */
	[]HashSet<int> rowRefinement = new HashSet<int>[2];
	rowRefinement[0] = new HashSet<int>();
	rowRefinement[1] = new HashSet<int>();
	
	// Iterate through the columns contained in the set Cj.
	for (int row : Ri) {
	    // If the entry M[r][c] is 1, add it to the first set.
	    if (this.original[row][col] == 1) {
		rowRefinement[0].add(row);
	    } else {
		// Otherwise, add it to the second set.
		rowRefinement[1].add(row);
	    }
	}
	
	return rowRefinement;
    }
    
    /**
     * Produce the blocks obtained by performing a column refinement.
     * @param colRef the column refinement.
     * @param B the current block.
     * @return the new current block.
     */
    private Block produceColumnRefinement([]HashSet<int> colRef, Block B) {
	/* Get the left and right refinement */
	HashSet<int> lRef = colRef[0];
	HashSet<int> rRef = colRef[1];
	
	/* Determine which parts are smaller and bigger. */
	HashSet<int> sRef, bRef;
	bool leftSmaller = (lRef.size() <= rRef().size());

	if (leftSmaller) {
	    sRef = lRef;
	    bRef = rRef;
	} else {
	    sRef = rRef;
	    bRef = bRef;
	}

	/* Produce all the blocks obtained by the column refinement. */
	Block current = B;
	Block ogLeft    = null;
	Block ogRight   = null;
	Block prevLeft  = null;
	Block prevRight = null;
	
	do {
	    /* Get the current block's row part. */
	    HashSet<int> Ri = current.rows();

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
		prevRight.setBelow(rightBlock);
		prevRight.setNext(rightBlock);
	    }
	    
	    /* Move to the next block below, if there is no such block, exit the
	     * loop. */
	    if (current.hasBelow()) {
		current = current.getBelow();
	    } else {
		leftBlock.setNext(ogRight);
		rightBlock.setNext(current.getNext());
		break;
	    }

	} while (true);
	
	/* Return the new block we're standing on. */
	return ogLeft;
    }
}
