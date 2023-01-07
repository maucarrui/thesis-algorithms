package com.github.maucarrui.algorithms.doublylexicalsorting.test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.lang.String;

import java.util.Random;

import com.github.maucarrui.algorithms.doublylexicalordering.DoublyLexicalOrderer;

/**
 * Class for the unit tests of the Doubly Lexical Orderer class.
 */
public class TestDoublyLexicalOrderer {
    
    /**
     * Auxiliary method to test whether or not a matrix is double
     * lexicographical ordered, that is, its rows as vectors are ordered
     * lexicographically from top to bottom, and its columns as vectors are
     * lexicographically from left to right.
     * @param ordered the ordered matrix.
     * @return true if the ordered matrix is, indeed, ordered, false otherwise.
     */
    private boolean isOrdered(int[][] ordered) {
	/* Get the size of the matrix */
	int n = ordered.length;
	
	/* Check that the rows are ordered */
	for (int i = 0; i < (n - 1); i++) {
	    String a = "";
	    String b = "";
	    
	    for (int j = 0; j < n; j++) {
		a += Integer.toString(ordered[i][j]);
		b += Integer.toString(ordered[i+1][j]);
	    }
	    
	    /* If the i-th row vector is lexicographically lesser than the
	     * (i+1)-th row, return false. */
	    if (a.compareTo(b) < 0) {
		return false;
	    }
	}

	/* Check that the columns are ordered */
	for (int j = 0; j < (n - 1); j++) {
	    String a = "";
	    String b = "";
	    
	    for (int i = 0; i < n; i++) {
		a += Integer.toString(ordered[i][j]);
		b += Integer.toString(ordered[i][j+1]);
	    }
	    
	    /* If the i-th row vector is lexicographically lesser than the
	     * (i+1)-th row, return false. */
	    if (a.compareTo(b) < 0) {
		return false;
	    }
	}
	
	/* If the matrix is lexicographically ordered by rows and columns return
	 * true */
	return true;
    }
    
    /**
     * Checks whether the ordered matrix is consistent with the original matrix,
     * that is that all the entries of the ordered correspond to the original
     * entries.
     * @param original the original matrix.
     * @param ordered the ordered matrix.
     * @param orderedRows the ordered rows indexes.
     * @param orderedCols the ordered columns indexes.
     * @return true if the ordered matrix is consistent, false otherwise.
     */
    private boolean isConsistent(int[][] original, int[][] ordered, 
				 int[] orderedRows, int[] orderedCols) {
	/* Get the size of the original and ordered matrix */
	int n = original.length;
	int m = ordered.length;
	
	/* If sizes vary, return false. */
	if (n != m) { return false; }
	
	/* Check that the entries are consistent. */
	int i = 0;
	for (int r : orderedRows) {
	    int j = 0;
	    for (int c : orderedCols) {
		if (ordered[i][j] != original[r][c]) {
		    /* If an entry varies from the original, return false. */
		    return false;
		}
		j++;
	    }
	    
	    i++;
	}
	
	/* If the entries and sizes match, return true. */
	return true;
    }
    
    @Test
    public void shouldReturnOrderedMatrixHardcoded() {
	int[][][] testMatrices = new int[][][]{
	    {
		{1, 0, 1, 1},
		{0, 0, 1, 0},
		{1, 0, 0, 1},
		{0, 0, 1, 1},
	    },
	    {
		{0, 1, 0, 1, 1},
		{0, 1, 0, 0, 0},
		{0, 0, 1, 0, 1},
		{0, 0, 0, 1, 1},
		{0, 0, 0, 0, 0},
	    },
	    {
		{0, 1, 1, 1, 1, 0, 1},
		{1, 1, 0, 0, 1, 1, 1},
		{0, 0, 0, 1, 1, 1, 0},
		{1, 1, 1, 1, 0, 1, 1},
		{0, 0, 1, 1, 0, 0, 1},
		{1, 0, 1, 1, 1, 1, 1},
		{0, 1, 1, 1, 0, 0, 1},
	    },
	    {
		{0, 1, 1, 1, 1, 0, 1, 0},
		{1, 1, 0, 0, 1, 1, 1, 0},
		{0, 0, 0, 1, 1, 1, 0, 1},
		{1, 1, 1, 1, 0, 1, 1, 1},
		{0, 0, 1, 1, 0, 0, 1, 0},
		{1, 0, 1, 1, 1, 1, 1, 1},
		{0, 1, 1, 1, 0, 0, 1, 0},
		{0, 0, 1, 1, 1, 1, 0, 1},
	    },
	    {
		{1, 1, 1, 1, 0},
		{1, 1, 1, 0, 0},
		{1, 1, 0, 0, 0},
		{1, 0, 0, 0, 0},
		{0, 0, 0, 0, 0},
	    },
	    {
		{0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0},
	    },
	    {
		{1, 1, 1, 1},
		{1, 1, 1, 1},
		{1, 1, 1, 1},
		{1, 1, 1, 1},
	    },
	    {
		{0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0},
		{0, 0, 1, 0, 0},
		{0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0},
	    },
	    {
		{1, 0, 0, 0, 0},
		{0, 1, 0, 0, 0},
		{0, 0, 1, 0, 0},
		{0, 0, 0, 1, 0},
		{0, 0, 0, 0, 1},
	    },
	    {
		{0, 0, 0, 0, 1},
		{0, 0, 0, 1, 0},
		{0, 0, 1, 0, 0},
		{0, 1, 0, 0, 0},
		{1, 0, 0, 0, 0},
	    },
	};

	/* Check that for every matrix, the obtained matrix is ordered and
	 * consistent. */
	for (int i = 0; i < testMatrices.length; i++) {
	    int[][] original = testMatrices[i];
	    
	    DoublyLexicalOrderer orderer = new DoublyLexicalOrderer(original);
	    int[][] ordered   = orderer.getOrderedMatrix();
	    int[] orderedRows = orderer.getOrderedRows();
	    int[] orderedCols = orderer.getOrderedColumns();
	    
	    assertTrue(isOrdered(ordered));
	    assertTrue(isConsistent(original, ordered, orderedRows, orderedCols));
	}
    }
    
    /**
     * Auxiliary method to return a random (0,1)-matrix of the given size.
     * @param size the size of the random matrix.
     * @return a matrix where each entry is a random value between 0 and 1.
     */
    private int[][] buildRandomMatrix(int size) {
	int[][] randM = new int[size][size];

	Random rand = new Random();
	
	for (int i = 0; i < size; i++) {
	    for (int j = 0; j < size; j++) {
		int k = rand.nextInt(100);
		randM[i][j] = (k < 50) ? 0 : 1;
	    }
	}
	
	return randM;
    }
    
    @Test
    public void shouldReturnOrderedMatrixRandoms(){
	int numRandMatrices = 100;
	int maxSize = 200;
	
	Random rand = new Random();
	
	for (int i = 0; i < numRandMatrices; i++) {
	    int size = rand.nextInt(maxSize) + 1;
	    
	    int[][] original = buildRandomMatrix(size);

	    DoublyLexicalOrderer orderer = new DoublyLexicalOrderer(original);
	    int[][] ordered   = orderer.getOrderedMatrix();
	    int[] orderedRows = orderer.getOrderedRows();
	    int[] orderedCols = orderer.getOrderedColumns();
	    
	    assertTrue(isOrdered(ordered));
	    assertTrue(isConsistent(original, ordered, orderedRows, orderedCols));
	}
    }
}
