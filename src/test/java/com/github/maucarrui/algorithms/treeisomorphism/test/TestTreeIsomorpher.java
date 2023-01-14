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
}
