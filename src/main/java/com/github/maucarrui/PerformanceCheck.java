package com.github.maucarrui;

import java.time.Instant;
import java.time.Duration;

import java.util.Random;
import java.util.LinkedList;

import java.lang.String;

import com.github.maucarrui.algorithms.doublylexicalordering.DoublyLexicalOrderer;
import com.github.maucarrui.algorithms.treeisomorphism.Graph;
import com.github.maucarrui.algorithms.treeisomorphism.TreeIsomorpher;

/**
 * Main class to check the performance of the implemented algorithms.
 */
public class PerformanceCheck {

    /**
     * Returns a string representation of the usage of the perfomance checker.
     * @return a string representation of the usage of the perfomance checker.
     */
    private static String usage() {
	String str;
	str  = "Usage: ./thesis-algorithms <algorithm> <N>\n";
	str += "Arguments:\n";
	str += "    <algorithm> The algorithm to check its performance.\n";
	str += "    <N>         An integer number specifying the at which\n";
	str += "                entry size to stop the algorithm.\n";
	str += "Algorithm options:\n";
	str += "    treeisomorphism   The tree isomorphism algorithm.\n";
	str += "    doublylexordering The doubly lexical ordering algorithm.\n";
	str += "Notes on the size of the entry:\n";
	str += "    In the case of the tree isomorphism, the <N> argument is\n";
	str += "    the amount of spokes or branches the pinwheel graph has,\n";
	str += "    the amount of nodes contained in the tree scales\n";
	str += "    exponentially; if it has n-spokes it has 2^n vertices.\n";
	str += "    In the case of the doubly lexical ordering, the <N>\n";
	str += "    argument is the number of rows and columns contained in\n";
	str += "    the matrix. For example, if <N> = 500, then the last\n";
	str += "    matrix to be tested will have 250,000 entries.\n";

	return str;

    }

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
    private static String
    checkDoublyLexicalOrderingAlgPerfomance(int N) {
	DoublyLexicalOrderer dlo;
	Instant start, finish;
	String str = "Size,Time\n";

	for (int size = 10; size <= N; size += 10) {
	    int[][] matrix = buildRandomMatrix(size);
	    dlo = new DoublyLexicalOrderer(matrix);

	    start = Instant.now();
	    dlo.getOrderedMatrix();
	    finish = Instant.now();

	    int secs = Duration.between(start, finish).toSecondsPart();
	    int millis = Duration.between(start, finish).toMillisPart();

	    str += String.format("%d,", size);
	    str += String.format("%d", secs);
	    str += ".";
	    str += String.format("%03d\n", millis);
	}

	return str;
    }

    /**
     * Auxiliary method to build a pinwheel graph, which has only one
     * isomorphism.
     * @param numBranches the amount of branches the graph has.
     * @return the pinwheel graph.
     */
    private static Graph<Integer> pinwheelGraph(int numBranches) {
	Graph<Integer> pinwheel = new Graph<>();
	pinwheel.addVertex(0);

	for (int i = 0; i < numBranches; i++) {
	    LinkedList<Integer> path = new LinkedList<>();
	    path.add(0);

	    for (int j = pinwheel.order(); j < 2 * pinwheel.order(); j++) {
		path.add(j);
	    }

	    pinwheel.addPath(path);
	}

	return pinwheel;
    }

    /**
     * Checks the performance of the tree isomorphism algorithm.
     */
    private static String
    checkTreeIsomorphismAlgPerformance(int N) {
	TreeIsomorpher<Integer, Integer> TI = new TreeIsomorpher<>();
	Instant start, finish;
	String str = "Size,Time\n";

	for (int size = 1; size <= N; size++) {
	    Graph<Integer> G = pinwheelGraph(size);
	    Graph<Integer> H = pinwheelGraph(size);

	    start = Instant.now();
	    TI.areIsomorphic(G, H);
	    finish = Instant.now();

	    int secs = Duration.between(start, finish).toSecondsPart();
	    int millis = Duration.between(start, finish).toMillisPart();

	    str += String.format("%d,", size);
	    str += String.format("%d", secs);
	    str += ".";
	    str += String.format("%03d\n", millis);
	}

	return str;
    }

    public static void main(String[] args) {
	String csv = "";

	/* The program must have two arguments to work. */
	if (args.length != 2) {
	    System.out.println(usage());
	    return;
	}

	/* Get the max entry size. */
	int N = Integer.parseInt(args[1]);

	/* Check for arguments. */
	if (args[0].equals("doublelexordering")) {
	    /* The entry has to be greater or equal than 10. */
	    if (N < 10) {
		System.out.println("<N> can't be lesser than 10.");
		return;
	    }

	    /* Test the doubly lexical ordering performance. */
	    csv = checkDoublyLexicalOrderingAlgPerfomance(N);
	} else if (args[0].equals("treeisomorphism")) {
	    /* The entry has to be greater or equal than 0. */
	    if (N < 0) {
		System.out.println("<N> can't be lesser than 0.");
		return;
	    }

	    /* Test the tree isomorphism algorithm performance. */
	    csv = checkTreeIsomorphismAlgPerformance(N);
	} else {
	    /* If non-recognized arguments are found, print the usage. */
	    System.out.println(usage());
	}

	System.out.println(csv);
    }
}
