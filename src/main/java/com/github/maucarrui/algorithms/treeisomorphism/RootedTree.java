package com.github.maucarrui.algorithms.treeisomorphism;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Package-private class to represent a rooted tree.
 *
 * A rooted tree T, with a root, is a graph which is a tree and all of its edges
 * have a father-child relationship, the only vertex who doesn't have a parent
 * is the root; in other words, the edges have an orientation. Thus, for any
 * vertex v in the set of vertices of T, there exists an unique rv-path.
 */
class RootedTree<T extends Comparable<T>> {

    /** The graph corresponding to the rooted tree. */
    private Graph<T> tree;

    /** The root of the rooted tree. */
    private T root;

    /** The height of the rooted tree. */
    private int height;

    /** A mapping to the vertices found on a level of the tree. */
    private HashMap<Integer, HashSet<T>> verticesInLevel;

    /** The parenthood mapping defined in the rooted-tree. */
    private HashMap<T, T> parenthood;

    /** The leaves of the tree. */
    private HashSet<T> leaves;

    /**
     * Unique constructor which receives a graph that represents the rooted tree
     * and the root of the tree.
     * @param tree the rooted tree.
     * @param root the root of the rooted tree.
     */
    RootedTree(Graph<T> tree, T root) {
	this.tree = tree;
	this.root = root;
	this.height = -1;
	this.verticesInLevel = new HashMap<>();
	this.parenthood = new HashMap<>();
	this.leaves = new HashSet<>();
    }

    /**
     * Determine the values corresponding to the rooted tree. That is define the
     * height of the tree, determine the vertices found on each level of the
     * tree, define the parenthood mapping, and determine which vertices are the
     * leaves of the rooted tree.
     */
    public void setInitialValues() {
	/* Get the edges obtained by performing a BFS traversal on the rooted
	 * tree. */
	LinkedList<LinkedList<T>> edges = this.tree.getBFSEdges(this.root);

	/* Auxiliary variables. */
	HashMap<T, Integer> distanceToRoot = new HashMap<>();
	HashMap<Integer, HashSet<T>> fromDistance = new HashMap<>();
	HashSet<T> temp = new HashSet();

	/* Define the root's distance. */
	distanceToRoot.put(this.root, 0);
	temp.add(this.root);
	fromDistance.put(0, temp);

	/* Traverse the edges to determine the necessary information. */
	for (LinkedList<T> edge : edges) {
	    /* Get the parent. */
	    T parent = edge.peekFirst();

	    /* Get the child. */
	    T child = edge.peekLast();

	    /* Define the parenthood relationship. */
	    this.parenthood.put(child, parent);

	    /* A parent can't be a leaf. */
	    this.leaves.remove(parent);

	    /* The child could be a leaf. */
	    this.leaves.add(child);

	    /* d(child, root) = d(parent, root) + 1. */
	    int distChild = distanceToRoot.get(parent) + 1;
	    distanceToRoot.put(child, distChild);

	    /* Add the child to the set of vertices that have the same distance
	     * to the root. */
	    if (fromDistance.containsKey(distChild)) {
		fromDistance.get(distChild).add(child);
	    } else {
		temp = new HashSet<>();
		temp.add(child);
		fromDistance.put(distChild, temp);
	    }
	}

	/* The rooted tree's height is the lenght of the longest between the
	 * root and some vertex. */
	this.height = fromDistance.size() - 1;

	/* Let h be the rooted tree's height. The only vertex found on the h-th
	 * level is the root. If a vertex is on the level i-th, then its
	 * children are on the level (i-1)-th. */
	for (int level = this.height; level >= 0; level--) {
	    int distance = this.height - level;
	    this.verticesInLevel.put(level, fromDistance.get(distance));
	}
    }

    /**
     * Returns the set of leaves of the tree.
     * @return the set of leaves of the tree.
     */
    public HashSet<T> leaves() {
	return this.leaves;
    }

    /**
     * Returns the height of the rooted tree.
     * @return the height of the rooted tree.
     */
    public int height() {
	return this.height;
    }

    /**
     * Returns the root of the rooted tree.
     * @return the root of the rooted tree.
     */
    public T root() {
	return this.root;
    }

    /**
     * Returns the parent of the child in the rooted tree.
     * @return the parent of the child in the rooted tree.
     */
    public T getParent(T childID) {
	return this.parenthood.get(childID);
    }

    /**
     * Returns the set of vertices found on the specified level in the rooted
     * tree.
     * @return the set of vertices found on the specified level in the rooted
     *         tree.
     */
    public HashSet<T> getVerticesOfLevel(int level) {
	return this.verticesInLevel.get(level);
    }
}
