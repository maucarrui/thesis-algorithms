package com.github.maucarrui.algorithms.treeisomorphism;

import java.util.HashMap;

/**
 * Class to represent a tree isomorpher.
 *
 * A tree isomorpher is capable of telling if two rooted trees are isomorphic,
 * and if so, return an isomorphism between the two.
 */
public class TreeIsomorpher<U extends Comparable<U>, V extends Comparable<V>> {

    /**
     * Unique empty constructor to initialize a tree isomorpher and have access
     * to its methods.
     */
    public TreeIsomorpher(){}

    /**
     * Returns whether two rooted trees are isomorphic.
     * @param G on of the rooted trees to the check for isomorphism.
     * @param rootG the root of the rooted tree G.
     * @param H the other rooted tree to check for isomorphism.
     * @param rootH the root of the rooted tree H.
     * @return the isomorphism between the two rooted trees, if the trees are
     *         not isomorphic it returns null.
     */
    public HashMap<U, V> areIsomorphic(Graph<U> G, U rootG, Graph<V> H, V rootH) {
	return new HashMap<U, V>();
    }


    /**
     * Returns whether two trees are isomorphic.
     * @param G on of the trees to the check for isomorphism.
     * @param H the other tree to check for isomorphism.
     * @return the isomorphism between the two trees, if the trees are not
     *         isomorphic it returns null.
     */
    public HashMap<U, V> areIsomorphic(Graph<U> G, Graph<V> H) {
	return new HashMap<U, V>();
    }
}
