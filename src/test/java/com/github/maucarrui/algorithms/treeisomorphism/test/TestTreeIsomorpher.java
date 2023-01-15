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

    /**
     * Auxiliary method to build a pinwheel graph, which has only one
     * isomorphism.
     * @param numBranches the amount of branches the graph has.
     * @return the pinwheel graph.
     */
    private Graph<Integer> pinwheelGraph(int numBranches) {
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
     * Auxiliary method to check if an isomorphism is valid.
     */
    private <U, V> boolean
    isValidIsomorphism(Graph<U> G, Graph<V> H, HashMap<U, V> isomorphism) {
	/* Check that the isomorphism keeps adjacencies and non-adjacencies. */
	for (U u : G.vertices()) {
	    /* Get the vertex of H which maps u. */
	    V fu = isomorphism.get(u);

	    for (U v : G.vertices()) {
		/* Get the vertex of H which maps u. */
		V fv = isomorphism.get(v);

		if (G.areConnected(u, v) && !H.areConnected(fu, fv)) {
		    return false;
		} else if (!G.areConnected(u, v) && H.areConnected(fu, fv)) {
		    return false;
		}
	    }
	}

	return true;
    }

    /**
     * Test the areIsomorphic method with some pinwheel graphs as rooted trees
     * that only have one isomorphism.
     */
    @Test
    public void testAreIsomorphicRootedPinwheel() {
	/* Define a tree isomorpher to have access to its method. */
	TreeIsomorpher<Integer, Integer> TI = new TreeIsomorpher<>();
	HashMap<Integer, Integer> isomorphism;

	/* Number of branches to test. */
	int maxBranches = 10;

	for (int i = 0; i < maxBranches; i++) {
	    /* Define two identic pinwheel graphs. */
	    Graph<Integer> G = pinwheelGraph(i);
	    Graph<Integer> H = pinwheelGraph(i);

	    /* Obtain the isomorphism between the graphs. */
	    isomorphism = TI.areIsomorphic(G, 0, H, 0);
	    Assert.assertTrue(isValidIsomorphism(G, H, isomorphism));
	}
    }

    /**
     * Test the areIsomorphic method with some trivial graphs.
     */
    @Test
    public void testAreIsomorphicTrivialCases() {
	/* Define a tree isomorpher to have access to its methods. */
	TreeIsomorpher<Integer, String> TI = new TreeIsomorpher<>();
	HashMap<Integer, String> isomorphism, expectedIsomorphism;

	/* Test for two empty graphs. */
	Graph<Integer> G = new Graph<>();
	Graph<String> H = new Graph<>();

	/* The expected isomorphism is an empty isomorphism. */
	isomorphism = TI.areIsomorphic(G, H);
	Assert.assertEquals(isomorphism, new HashMap<Integer, String>());

	/* Add a vertex and obtain some trivial graphs. */
	G.addVertex(0);
	H.addVertex("a");

	expectedIsomorphism = new HashMap<Integer, String>();
	expectedIsomorphism.put(0, "a");

	isomorphism = TI.areIsomorphic(G, H);
	Assert.assertEquals(expectedIsomorphism, isomorphism);

	/* Add another vertex and the trees should not be isomorphic. */
	G.addVertex(1);
	isomorphism = TI.areIsomorphic(G, H);
	Assert.assertNull(isomorphism);

	/* Connect the previous vertex, and the trees should still be not
	 * isomorphic. */
	G.connectVertices(0, 1);
	isomorphism = TI.areIsomorphic(G, H);
	Assert.assertNull(isomorphism);
    }

    /**
     * Test the areIsomorphic method with some small hardcoded graphs.
     */
    @Test
    public void testAreIsomorphicHardcodedSmall() {
	/* Define a tree isomorpher to have access to its methods. */
	TreeIsomorpher<Integer, String> TI = new TreeIsomorpher<>();
	HashMap<Integer, String> iso;

	/* Test for two P2 (graphs that consist only of a path with 2
	 * vertices). */
	Graph<Integer> G = new Graph<>();
	Graph<String> H = new Graph<>();
	G.addVertex(0); G.addVertex(1); G.connectVertices(0, 1);
	H.addVertex("a"); H.addVertex("b"); H.connectVertices("a", "b");

	/* The possible expected isomorphism of the rooted trees. */
	HashMap<Integer, String> expIso1 = new HashMap<Integer, String>();
	expIso1.put(0, "a");
	expIso1.put(1, "b");

	HashMap<Integer, String> expIso2 = new HashMap<Integer, String>();
	expIso2.put(0, "b");
	expIso2.put(1, "a");

	/* Check that the obtained isomorphism is correct. */
	iso = TI.areIsomorphic(G, H);
	Assert.assertTrue(iso.equals(expIso1) || iso.equals(expIso2));
    }

    /**
     * Test the areIsomorphic method with some hardcoded graphs.
     */
    @Test
    public void testAreIsomorphicHardcoded() {
	/* Define a tree isomorpher to have access to its method. */
	TreeIsomorpher<String, String> TI = new TreeIsomorpher<>();
	HashMap<String, String> isomorphism;

	/* Hardcoded graphs. */
	Graph<String> G = new Graph<>();
	Graph<String> H = new Graph<>();

	G.addVertex("v0"); G.addVertex("v1"); G.addVertex("v2");
	G.addVertex("v3"); G.addVertex("v4"); G.addVertex("v5");
	G.addVertex("v6"); G.addVertex("v7"); G.addVertex("v8");

	H.addVertex("u0"); H.addVertex("u1"); H.addVertex("u2");
	H.addVertex("u3"); H.addVertex("u4"); H.addVertex("u5");
	H.addVertex("u6"); H.addVertex("u7"); H.addVertex("u8");

	G.connectVertices("v0", "v1"); G.connectVertices("v1", "v3");
	G.connectVertices("v3", "v2"); G.connectVertices("v3", "v4");
	G.connectVertices("v3", "v5"); G.connectVertices("v5", "v6");
	G.connectVertices("v6", "v7"); G.connectVertices("v7", "v8");

	H.connectVertices("u0", "u1"); H.connectVertices("u1", "u2");
	H.connectVertices("u2", "u3"); H.connectVertices("u3", "u4");
	H.connectVertices("u4", "u5"); H.connectVertices("u4", "u6");
	H.connectVertices("u4", "u7"); H.connectVertices("u7", "u8");

	/* Check that the obtained isomorphism is correct. */
	isomorphism = TI.areIsomorphic(G, H);
	Assert.assertTrue(isValidIsomorphism(G, H, isomorphism));
    }

    /**
     * Test the areIsomorphic method with some pinwheel graphs that only have
     * one isomorphism.
     */
    @Test
    public void testAreIsomorphicPinwheel() {
	/* Define a tree isomorpher to have access to its method. */
	TreeIsomorpher<Integer, Integer> TI = new TreeIsomorpher<>();
	HashMap<Integer, Integer> isomorphism;

	/* Number of branches to test. */
	int maxBranches = 10;

	for (int i = 0; i < maxBranches; i++) {
	    /* Define two identic pinwheel graphs. */
	    Graph<Integer> G = pinwheelGraph(i);
	    Graph<Integer> H = pinwheelGraph(i);

	    /* Obtain the isomorphism between the graphs. */
	    isomorphism = TI.areIsomorphic(G, H);
	    Assert.assertTrue(isValidIsomorphism(G, H, isomorphism));
	}
    }
}
