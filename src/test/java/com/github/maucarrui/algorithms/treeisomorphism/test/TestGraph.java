package com.github.maucarrui.algorithms.treeisomorphism.test;

import org.junit.Assert;

import org.junit.Test;

import java.lang.String;

import java.util.Random;

import java.util.LinkedList;

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
}
