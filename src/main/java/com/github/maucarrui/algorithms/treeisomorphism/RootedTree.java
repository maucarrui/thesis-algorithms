package com.github.maucarrui.algorithms.treeisomorphism;

import java.util.HashMap;
import java.util.HashSet;

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
    private HashMap<Integer, T> verticesInLevel;
    
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
}
