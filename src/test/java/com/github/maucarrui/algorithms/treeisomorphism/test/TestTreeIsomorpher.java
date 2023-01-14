package com.github.maucarrui.algorithms.treeisomorphism.test;

import org.junit.Assert;

import org.junit.Test;

import java.lang.String;

import java.util.Random;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;

import com.github.maucarrui.algorithms.treeisomorphism.Graph;
import com.github.maucarrui.algorithms.treeisomorphism.TreeIsomorpher;

/**
 * Class for the unit tests related to the Tree Isomorpher class.
 */
public class TestTreeIsomorpher {

    /**
     * Test the areIsomorphic method with some trivial graphs as rooted trees.
     */
    @Test
    public void testAreIsomorphicRootedTrivialCases() {
	/* Define a tree isomorpher to have access to its methods. */
	TreeIsomorpher<Integer, String> TI = new TreeIsomorpher<>();
	HashMap<Integer, String> isomorphism, expectedIsomorphism;

	/* Test for two trivial graphs. */
	Graph<Integer> G = new Graph<>();
	Graph<String> H = new Graph<>();
	G.addVertex(0);
	H.addVertex("a");

	expectedIsomorphism = new HashMap<Integer, String>();
	expectedIsomorphism.put(0, "a");

	isomorphism = TI.areIsomorphic(G, 0, H, "a");
	Assert.assertEquals(expectedIsomorphism, isomorphism);

	/* Add another vertex and the trees should not be isomorphic. */
	G.addVertex(1);
	isomorphism = TI.areIsomorphic(G, 0, H, "a");
	Assert.assertNull(isomorphism);

	/* Connect the previous vertex, and the trees should still be not
	 * isomorphic. */
	G.connectVertices(0, 1);
	isomorphism = TI.areIsomorphic(G, 0, H, "a");
	Assert.assertNull(isomorphism);
    }

    /**
     * Test the areIsomorphic method with some small hardcoded graphs as rooted
     * trees.
     */
    @Test
    public void testAreIsomorphicRootedHardcodedSmall() {
	/* Define a tree isomorpher to have access to its methods. */
	TreeIsomorpher<Integer, String> TI = new TreeIsomorpher<>();
	HashMap<Integer, String> isomorphism, expectedIsomorphism;

	/* Test for two P2 (graphs that consist only of a path with 2
	 * vertices). */
	Graph<Integer> G = new Graph<>();
	Graph<String> H = new Graph<>();
	G.addVertex(0); G.addVertex(1); G.connectVertices(0, 1);
	H.addVertex("a"); H.addVertex("b"); H.connectVertices("a", "b");

	/* The expected isomorphism of the rooted trees. */
	expectedIsomorphism = new HashMap<Integer, String>();
	expectedIsomorphism.put(0, "a");
	expectedIsomorphism.put(1, "b");

	/* Check that the obtained isomorphism is correct. */
	isomorphism = TI.areIsomorphic(G, 0, H, "a");
	Assert.assertEquals(expectedIsomorphism, isomorphism);
    }

    /**
     * Test the areIsomorphic method with some hardcoded graphs as rooted trees
     * that only have one isomorphism.
     */
    @Test
    public void testAreIsomorphicRootedHardcodedSingleIsomorphism() {
	/* Define a tree isomorpher to have access to its method. */
	TreeIsomorpher<Integer, String> TI = new TreeIsomorpher<>();
	HashMap<Integer, String> isomorphism, expectedIsomorphism;

	/* Hardcoded graphs. */
	Graph<Integer> G = new Graph<>();
	Graph<String> H = new Graph<>();

	G.addVertex(0); G.addVertex(1); G.addVertex(2); G.addVertex(3);
	G.addVertex(4); G.addVertex(5); G.addVertex(6);
	H.addVertex("a"); H.addVertex("b"); H.addVertex("c"); H.addVertex("d");
	H.addVertex("e"); H.addVertex("f"); H.addVertex("g");

	G.connectVertices(0, 1); G.connectVertices(0, 2);
	G.connectVertices(0, 3); G.connectVertices(2, 4);
	G.connectVertices(3, 5); G.connectVertices(5, 6);

	H.connectVertices("a", "b"); H.connectVertices("a", "c");
	H.connectVertices("a", "d"); H.connectVertices("c", "e");
	H.connectVertices("d", "f"); H.connectVertices("f", "g");

	/* The expected isomorphism. */
	expectedIsomorphism = new HashMap<>();
	expectedIsomorphism.put(0, "a");
	expectedIsomorphism.put(1, "b");
	expectedIsomorphism.put(2, "c");
	expectedIsomorphism.put(3, "d");
	expectedIsomorphism.put(4, "e");
	expectedIsomorphism.put(5, "f");
	expectedIsomorphism.put(6, "g");

	/* Check that the obtained isomorphism is correct. */
	isomorphism = TI.areIsomorphic(G, 0, H, "a");
	Assert.assertEquals(expectedIsomorphism, isomorphism);

	/* Mirror one graph. */
	H = new Graph<>();
	H.addVertex("a"); H.addVertex("b"); H.addVertex("c"); H.addVertex("d");
	H.addVertex("e"); H.addVertex("f"); H.addVertex("g");
	H.connectVertices("a", "b"); H.connectVertices("a", "c");
	H.connectVertices("a", "d"); H.connectVertices("b", "e");
	H.connectVertices("c", "f"); H.connectVertices("f", "g");

	/* The expected isomorphism. */
	expectedIsomorphism = new HashMap<>();
	expectedIsomorphism.put(0, "a");
	expectedIsomorphism.put(1, "d");
	expectedIsomorphism.put(2, "b");
	expectedIsomorphism.put(3, "c");
	expectedIsomorphism.put(4, "e");
	expectedIsomorphism.put(5, "f");
	expectedIsomorphism.put(6, "g");

	/* Check that the obtained isomorphism is correct. */
	isomorphism = TI.areIsomorphic(G, 0, H, "a");
	Assert.assertEquals(expectedIsomorphism, isomorphism);
    }
}
