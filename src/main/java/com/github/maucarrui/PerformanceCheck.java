package com.github.maucarrui;

import java.time.Instant;
import java.time.Duration;

import java.util.Random;

import java.lang.String;

import com.github.maucarrui.algorithms.doublylexicalordering.DoublyLexicalOrderer;

/**
 * Main class to check the performance of the implemented algorithms.
 */
public class PerformanceCheck {
    /**
     * Auxiliary method to return a random (0,1)-matrix of the given size.
     * @param size the size of the random matrix.
     * @return a matrix where each entry is a random value between 0 and 1.
     */
    private static int[][] buildRandomMatrix(int size) {
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
    
    /**
     * Checks the performance of the doubly lexical ordering algorithm.
     */
    private static void checkDoublyLexicalOrderingAlgPerfomance() {
	/* The sizes of the test matrices */
	int[]sizes = new int[]{
	    5, 10, 20, 40, 80, 
	    160, 320, 640, 1280,
	    2560, 5120, 10240,
	};
	DoublyLexicalOrderer dlo;
	Instant start, finish;
	String str;
	
	str = "(Num. Entries, Miliseconds)\n";
	for (int size = 10; size <= 2000; size += 10) {
	    int[][] matrix = buildRandomMatrix(size);
	    dlo = new DoublyLexicalOrderer(matrix);

	    start = Instant.now();
	    dlo.getOrderedMatrix();
	    finish = Instant.now();
	    
	    int secs = Duration.between(start, finish).toSecondsPart();
	    int millis = Duration.between(start, finish).toMillisPart();
	    
	    str += String.format("%d, ", size);
	    str += String.format("%d", secs);
	    str += ".";
	    str += String.format("%03d\n", millis);
	}
	
	System.out.println(str);
    }

    public static void main(String[] args) {
	checkDoublyLexicalOrderingAlgPerfomance();
    }
}
