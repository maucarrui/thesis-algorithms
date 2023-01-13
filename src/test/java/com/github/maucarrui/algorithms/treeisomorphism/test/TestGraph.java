package com.github.maucarrui.algorithms.treeisomorphism.test;

import org.junit.Assert;

import org.junit.Test;

import java.lang.String;

import java.util.Random;

import java.util.LinkedList;
import java.util.HashSet;

import com.github.maucarrui.algorithms.treeisomorphism.Graph;

/**
 * Class for the unit tests related to the Graph class.
 */
public class TestGraph {

    /**
     * Test the empty constructor for the Graph class.
     */
    @Test
    public void testEmptyConstructor() {
	Graph<Integer> G = new Graph<>();

	Assert.assertEquals(G.order(), 0);
    }

    /**
     * Test the constructor which receives a list of edges.
     */
    @Test
    public void testEdgesConstructor() {
	/* Define the edges. */
	LinkedList<String> e1 = new LinkedList<>();
	LinkedList<String> e2 = new LinkedList<>();
	LinkedList<String> e3 = new LinkedList<>();
	LinkedList<String> e4 = new LinkedList<>();

	e1.add("a"); e1.add("c");
	e2.add("a"); e2.add("b");
	e3.add("b"); e3.add("e");
	e4.add("b"); e4.add("d");

	LinkedList<LinkedList<String>> edges = new LinkedList<>();
	edges.add(e1);
	edges.add(e2);
	edges.add(e3);
	edges.add(e4);

	/* Define a graph with the previous edges. */
	Graph<String> G = new Graph<String>(edges);

	/* Check that the graph contains the expected vertices. */
	Assert.assertTrue(G.containsVertex("a"));
	Assert.assertTrue(G.containsVertex("b"));
	Assert.assertTrue(G.containsVertex("c"));
	Assert.assertTrue(G.containsVertex("d"));
	Assert.assertTrue(G.containsVertex("e"));

	/* Check that the order of the graph is correct. */
	Assert.assertEquals(G.order(), 5);

	/* Check that the graph contains the expected edges. */
	Assert.assertTrue(G.areConnected("a", "c"));
	Assert.assertTrue(G.areConnected("c", "a"));
	Assert.assertTrue(G.areConnected("a", "b"));
	Assert.assertTrue(G.areConnected("b", "a"));
	Assert.assertTrue(G.areConnected("b", "e"));
	Assert.assertTrue(G.areConnected("e", "b"));
	Assert.assertTrue(G.areConnected("b", "d"));
	Assert.assertTrue(G.areConnected("d", "b"));

	/* Check that there are no extra edges. */
	Assert.assertFalse(G.areConnected("a", "d"));
	Assert.assertFalse(G.areConnected("a", "e"));
	Assert.assertFalse(G.areConnected("b", "c"));
	Assert.assertFalse(G.areConnected("c", "b"));
	Assert.assertFalse(G.areConnected("c", "d"));
	Assert.assertFalse(G.areConnected("c", "e"));
	Assert.assertFalse(G.areConnected("d", "a"));
	Assert.assertFalse(G.areConnected("d", "c"));
	Assert.assertFalse(G.areConnected("d", "e"));
	Assert.assertFalse(G.areConnected("e", "a"));
	Assert.assertFalse(G.areConnected("e", "c"));
	Assert.assertFalse(G.areConnected("e", "d"));
    }

    /**
     * Tests the methods add and contains from the Graph class.
     */
    @Test
    public void testAddAndContains() {
	Graph<Integer> G = new Graph<>();

	for (int i = 0; i < 10; i++) {
	    Assert.assertFalse(G.containsVertex(i));
	    G.addVertex(i);
	    Assert.assertTrue(G.containsVertex(i));
	}
    }

    /**
     * Tests the methods connect and are connected from the Graph class.
     */
    @Test
    public void testConnectAndAreConnected() {
	Graph<Integer> G = new Graph<>();

	G.addVertex(0);
	for (int i = 1; i < 10; i++) {
	    Assert.assertFalse(G.areConnected(i-1, i));
	    G.addVertex(i);
	    Assert.assertFalse(G.areConnected(i-1, i));
	    G.connectVertices(i-1, i);
	    Assert.assertTrue(G.areConnected(i-1, i));
	    Assert.assertTrue(G.areConnected(i, i-1));
	}
    }

    /**
     * Tests the methods get neighbors from the Graph class.
     */
    @Test
    public void testGetNeighborsOf() {
	/* Define a graph. */
	Graph<String> G = new Graph<>();

	G.addVertex("a");
	G.addVertex("b");
	G.addVertex("c");
	G.addVertex("d");
	G.addVertex("e");

	G.connectVertices("a", "c");
	G.connectVertices("a", "b");
	G.connectVertices("b", "d");
	G.connectVertices("b", "e");

	/* The expected neighbors sets. */
	HashSet<String> aNeighbors = new HashSet<>();
	HashSet<String> bNeighbors = new HashSet<>();
	HashSet<String> cNeighbors = new HashSet<>();
	HashSet<String> dNeighbors = new HashSet<>();
	HashSet<String> eNeighbors = new HashSet<>();

	aNeighbors.add("b");
	aNeighbors.add("c");

	bNeighbors.add("a");
	bNeighbors.add("d");
	bNeighbors.add("e");

	cNeighbors.add("a");

	dNeighbors.add("b");

	eNeighbors.add("b");

	/* Check that the expected and obtained neighbors sets are equal. */
	Assert.assertEquals(aNeighbors, G.getNeighborsOf("a"));
	Assert.assertEquals(bNeighbors, G.getNeighborsOf("b"));
	Assert.assertEquals(cNeighbors, G.getNeighborsOf("c"));
	Assert.assertEquals(dNeighbors, G.getNeighborsOf("d"));
	Assert.assertEquals(eNeighbors, G.getNeighborsOf("e"));
    }

    /**
     * Test the method add path from the Graph class.
     */
    @Test
    public void testAddPath() {
	/* Define an empty graph. */
	Graph<Integer> G = new Graph<>();

	/* Create a path from the 0 to 9. */
	LinkedList<Integer> path = new LinkedList<>();
	for (int i = 0; i < 10; i++) {
	    path.add(i);
	}

	/* Add the path to the graph. */
	G.addPath(path);

	/* Check that the path was correctly added. */
	for (int i = 1; i < 10; i++) {
	    Assert.assertTrue(G.containsVertex(i-1));
	    Assert.assertTrue(G.containsVertex(i));
	    Assert.assertTrue(G.areConnected(i-1, i));
	    Assert.assertTrue(G.areConnected(i, i-1));
	}
    }

    /**
     * Test the method get BFS edges from the Graph class.
     */
    @Test
    public void testGetBFSEdges() {
	Graph<Integer> G = new Graph<>();

	/* Create a path from the 0 to 9. */
	LinkedList<Integer> path = new LinkedList<>();
	for (int i = 0; i < 10; i++) {
	    path.add(i);
	}

	/* Add the path to the graph. */
	G.addPath(path);

	/* Expected edges obtained from a BFS traversal from the 0. */
	LinkedList<LinkedList<Integer>> expectedEdges = new LinkedList<>();
	for (int i = 0; i < 9; i++) {
	    LinkedList<Integer> edge = new LinkedList<>();
	    edge.add(i);
	    edge.add(i+1);

	    expectedEdges.add(edge);
	}

	/* Check that the obtained edges are correct. */
	Assert.assertEquals(expectedEdges, G.getBFSEdges(0));

	/* Expected edges obtained from a BFS traversal from the 9. */
	expectedEdges = new LinkedList<>();
	for (int i = 9; i > 0; i--) {
	    LinkedList<Integer> edge = new LinkedList<>();
	    edge.add(i);
	    edge.add(i-1);

	    expectedEdges.add(edge);
	}

	/* Check that the obtained edges are correct. */
	Assert.assertEquals(expectedEdges, G.getBFSEdges(9));
    }

    /**
     * Test the method isTree from the Graph class.
     */
    @Test
    public void testIsTree() {
	Graph<String> G = new Graph<>();

	/* An empty graph is a tree. */
	Assert.assertTrue(G.isTree());

	/* A trivial graph is a tree. */
	G.addVertex("a");
	Assert.assertTrue(G.isTree());

	/* Two isolated vertices do not conform a tree. */
	G.addVertex("b");
	Assert.assertFalse(G.isTree());

	/* When connecting the previous vertices, this produces a tree. */
	G.connectVertices("a", "b");
	Assert.assertTrue(G.isTree());

	/* Add another vertex, but do not connect it. */
	G.addVertex("c");
	Assert.assertFalse(G.isTree());

	/* Connect the previous vertex. */
	G.connectVertices("a", "c");
	Assert.assertTrue(G.isTree());

	/* If we connect b and c, we have a cycle. */
	G.connectVertices("b", "c");
	Assert.assertFalse(G.isTree());

	/* If we add another vertex, we still have a cycle. */
	G.addVertex("d");
	G.connectVertices("a", "d");
	Assert.assertFalse(G.isTree());
    }

}
