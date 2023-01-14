package com.github.maucarrui.algorithms.treeisomorphism;

import java.util.Map.Entry;
import java.util.Iterator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Class to represent a tree isomorpher.
 *
 * A tree isomorpher is capable of telling if two rooted trees are isomorphic,
 * and if so, return an isomorphism between the two.
 */
public class TreeIsomorpher<U, V> {

    /**
     * Unique empty constructor to initialize a tree isomorpher and have access
     * to its methods.
     */
    public TreeIsomorpher(){}

    /**
     * Auxiliary method to append the leaves found on the 0-th level to its
     * parent list.
     * @param T the rooted tree.
     * @param children the mapping of the ordered children.
     */
    private <K> void
    setInitialChildren(RootedTree<K> T, HashMap<K, LinkedList<K>> children) {
	for (K leaf : T.getVerticesOfLevel(0)) {
	    K parent = T.getParent(leaf);

	    if (children.containsKey(parent)) {
		children.get(parent).add(leaf);
	    } else {
		LinkedList<K> ch = new LinkedList<>();
		ch.add(leaf);
		children.put(parent, ch);
	    }
	}
    }

    /**
     * Auxiliary method to obtain the structures found on the current level.
     * @param T the rooted tree.
     * @param children the map that contains the ordered list of children of the
     *        vertices on the current level.
     * @param values the values map, where each structure is associated with an
     *        unique value.
     * @param level the current level.
     * @return a mapping to the structures found on the current level.
     */
    private <K> HashMap<K, MultiSet<Integer>>
    getStructsOnLevel(RootedTree<K> T, HashMap<K, LinkedList<K>> children,
		      HashMap<K, Integer> values, int level) {
	/* Define an empty mapping of structures. */
	HashMap<K, MultiSet<Integer>> structures = new HashMap<>();

	/* Traverse each vertex found on the current level. */
	for (K v : T.getVerticesOfLevel(level)) {
	    MultiSet<Integer> struct = new MultiSet<>();

	    /* Build the structure by adding the values of the children. */
	    for (K child : children.get(v)) {
		struct.add(values.get(child));
	    }

	    structures.put(v, struct);
	}

	return structures;
    }

    /**
     * Auxiliary method to obtain a mapping that tells which vertices have said
     * structure.
     * @param structures the structures mapping.
     * @return a mapping that tells which vertices have said structure.
     */
    private <K> HashMap<MultiSet<Integer>, HashSet<K>>
    getVerticesOfStructMap(HashMap<K, MultiSet<Integer>> structures) {
	/* Define an empty mapping of structures to vertices. */
	HashMap<MultiSet<Integer>, HashSet<K>> verticesOfStruct = new HashMap<>();

	/* Traverse each entry in the map and build the inverse mapping. */
	for (Entry<K, MultiSet<Integer>> entry : structures.entrySet()) {
	    K vertex = entry.getKey();
	    MultiSet<Integer> structure = entry.getValue();

	    if (verticesOfStruct.containsKey(structure)) {
		verticesOfStruct.get(structure).add(vertex);
	    } else {
		HashSet<K> set = new HashSet<>();
		set.add(vertex);
		verticesOfStruct.put(structure, set);
	    }
	}

	return verticesOfStruct;
    }

    /**
     * Auxiliary method to group all the structures found on the current level
     * into a single multiset.
     * @param structures the structures of the current level.
     * @return a multiset that contains all the structures found on the current
     *         level.
     */
    private <K> MultiSet<MultiSet<Integer>>
    groupStructures(HashMap<K, MultiSet<Integer>> structures) {
	/* Define an empty multiset. */
	MultiSet<MultiSet<Integer>> structuresOfLevel = new MultiSet<>();

	/* Traverse each structure and add it to the multiset. */
	for (MultiSet<Integer> struct : structures.values()) {
	    structuresOfLevel.add(struct);
	}

	return structuresOfLevel;
    }

    /**
     * Auxiliary method to update the values of the vertices of the current
     * level.
     * @param vertices the set of vertices that have the previous structure.
     * @param values the values of the vertices.
     * @param value the value to associate to these vertices.
     */
    private <K> void
    updateValues(HashSet<K> vertices, HashMap<K, Integer> values, int value) {
	/* Traverse the vertices. */
	for (K vertex : vertices) {
	    /* In the current level, only the leaves have a value, so if the
	     * current vertex doesn't have a value, associate one to it. */
	    if (!values.containsKey(vertex)) { values.put(vertex, value); }
	}
    }

    /**
     * Auxiliary method to update the children list of the vertices of the next
     * level.
     * @param T the rooted tree.
     * @param vertices the vertices that are the children of the vertices of the
     *        next level on the rooted tree.
     * @param children the children mapping.
     */
    private <K> void
    updateChildren(RootedTree<K> T, HashSet<K> vertices,
		   HashMap<K, LinkedList<K>> children) {
	/* Traverse each vertex. */
	for (K vertex : vertices) {
	    /* Get the parent of the current vertex. */
	    K parent = T.getParent(vertex);

	    /* Append the current vertex to the list of children of its
	     * parent. */
	    if (children.containsKey(parent)) {
		children.get(parent).add(vertex);
	    } else {
		LinkedList<K> ch = new LinkedList<>();
		ch.add(vertex);
		children.put(parent, ch);
	    }
	}
    }

    /**
     * Update the children and values of the current and next level vertices.
     * @param T1 one of the rooted trees.
     * @param T2 other of the rooted trees.
     * @param valuesT1 the values of the vertices of the rooted tree T1.
     * @param valuesT2 the values of the vertices of the rooted tree T1.
     * @param childrenT1 the ordered list of children of the vertices of the
     *        rooted tree T1.
     * @param childrenT2 the ordered list of children of the vertices of the
     *        rooted tree T2.
     * @param vOfStructT1 the map to the set of vertices that have a structure
     *        on the rooted tree T1.
     * @param vOfStructT2 the map to the set of vertices that have a structure
     *        on the rooted tree T2.
     * @param structuresOfLevel the structures found on the current level.
     */
    private void
    updateInformation(RootedTree<U> T1, RootedTree<V> T2,
		      HashMap<U, Integer> valuesT1,
		      HashMap<V, Integer> valuesT2,
		      HashMap<U, LinkedList<U>> childrenT1,
		      HashMap<V, LinkedList<V>> childrenT2,
		      HashMap<MultiSet<Integer>, HashSet<U>> vOfStructT1,
		      HashMap<MultiSet<Integer>, HashSet<V>> vOfStructT2,
		      MultiSet<MultiSet<Integer>> structuresOfLevel) {
	/* Associate to each structure an unique value, the initial value is 2
	 * as 1 is reserved for leaves. */
	int currentValue = 2;

	/* Traverse each structure and associate an unique value to it. */
	for (MultiSet<Integer> struct : structuresOfLevel.values()) {
	    /* Get the vertices of T1 and T2 that have said structure. */
	    HashSet<U> verticesT1 = vOfStructT1.get(struct);
	    HashSet<V> verticesT2 = vOfStructT2.get(struct);

	    /* Update the values of the previous vertices. */
	    updateValues(verticesT1, valuesT1, currentValue);
	    updateValues(verticesT2, valuesT2, currentValue);

	    /* Update the children list of the vertices in the next level. */
	    updateChildren(T1, verticesT1, childrenT1);
	    updateChildren(T2, verticesT2, childrenT2);

	    /* Update the current value as we move to the next structure. */
	    currentValue++;
	}
    }

    /**
     * Builds an isomorphism of the vertices of T1 onto the vertices of T2.
     * @param T1 one of the rooted trees.
     * @param T2 other of the rooted trees.
     * @param childrenT1 a mapping to the ordered list of children of the
     *        vertices of the rooted tree T1.
     * @param childrenT2 a mapping to the ordered list of children of the
     *        vertices of the rooted tree T2.
     * @return an isomorphism of the vertices of T1 onto the vertices of T2.
     */
    private HashMap<U, V>
    buildIsomorphism(RootedTree<U> T1, RootedTree<V> T2,
		     HashMap<U, LinkedList<U>> childrenT1,
		     HashMap<V, LinkedList<V>> childrenT2) {
	/* Define an empty isomorphism. */
	HashMap<U, V> isomorphism = new HashMap<>();

	/* Perform a DFS traversal at the same time on both trees to build the
	 * isomorphism. */
	LinkedList<U> stackT1 = new LinkedList<>();
	LinkedList<V> stackT2 = new LinkedList<>();
	stackT1.push(T1.root());
	stackT2.push(T2.root());

	while (stackT1.size() > 0) {
	    /* Get the top of the stacks. */
	    U u = stackT1.pop();
	    V v = stackT2.pop();

	    /* Define the isomorphism for the current vertices. */
	    isomorphism.put(u, v);

	    /* Add the children of the current vertices to the stacks. */
	    if (childrenT1.containsKey(u)) {
		Iterator<U> itu = childrenT1.get(u).iterator();
		Iterator<V> itv = childrenT2.get(v).iterator();

		while (itu.hasNext() && itv.hasNext()) {
		    stackT1.push(itu.next());
		    stackT2.push(itv.next());
		}
	    }
	}

	return isomorphism;
    }

    /**
     * Verifies that the all the levels of both rooted trees share the same
     * structure. That is, the structures found on the i-th level of one rooted
     * tree are also found on the i-th level of the other rooted tree.
     * @param T1 one of the rooted trees.
     * @param T2 the other rooted tree.
     * @return the isomorphism between the two rooted trees, if the trees are
     *         not isomorphic it returns null.
     */
    private HashMap<U, V>
    levelsVerification(RootedTree<U> T1, RootedTree<V> T2) {
	/* Define a mapping for the values associated with a structure of a
	 * vertex */
	HashMap<U, Integer> valuesT1 = new HashMap<>();
	HashMap<V, Integer> valuesT2 = new HashMap<>();

	/* Define a mapping for the list of ordered children of a vertex. */
	HashMap<U, LinkedList<U>> childrenT1 = new HashMap<>();
	HashMap<V, LinkedList<V>> childrenT2 = new HashMap<>();

	/* Define the value of the leaves as 1. */
	for (U leaf : T1.leaves()) { valuesT1.put(leaf, 1); }
	for (V leaf : T2.leaves()) { valuesT2.put(leaf, 1); }

	/* Append the leaves found on the 0-th level to its parent list. */
	this.setInitialChildren(T1, childrenT1);
	this.setInitialChildren(T2, childrenT2);

	/* Traverse each level and check if they have the same structure on both
	 * trees. Start at level 1 as all the vertices on the 0-th level are
	 * leaves. */
	for (int lvl = 1; lvl <= T1.height(); lvl++) {
	    /* Define a mapping to keep track of the structures found on the
	     * current level. */
	    HashMap<U, MultiSet<Integer>> structOfVertexT1;
	    HashMap<V, MultiSet<Integer>> structOfVertexT2;

	    structOfVertexT1 = getStructsOnLevel(T1, childrenT1, valuesT1, lvl);
	    structOfVertexT2 = getStructsOnLevel(T2, childrenT2, valuesT2, lvl);

	    /* Define the inverse mapping of the previous map. */
	    HashMap<MultiSet<Integer>, HashSet<U>> verticesOfStructT1;
	    HashMap<MultiSet<Integer>, HashSet<V>> verticesOfStructT2;

	    verticesOfStructT1 = getVerticesOfStructMap(structOfVertexT1);
	    verticesOfStructT2 = getVerticesOfStructMap(structOfVertexT2);

	    /* Group all the structures of the level. */
	    MultiSet<MultiSet<Integer>> structuresOfLevelT1;
	    MultiSet<MultiSet<Integer>> structuresOfLevelT2;

	    structuresOfLevelT1 = groupStructures(structOfVertexT1);
	    structuresOfLevelT2 = groupStructures(structOfVertexT2);

	    /* If the levels have different structures, they're not
	     * isomorphic. */
	    if (!structuresOfLevelT1.equals(structuresOfLevelT2)) {
		return null;
	    }

	    /* If they share the same structures, update the values and
	     * children. */
	    updateInformation(T1, T2,
			      valuesT1, valuesT2,
			      childrenT1, childrenT2,
			      verticesOfStructT1, verticesOfStructT2,
			      structuresOfLevelT1);
	}

	/* If all the levels share the same structure, then they're
	 * isomorphic. Build an isomorphism and return it. */
	return buildIsomorphism(T1, T2, childrenT1, childrenT2);
    }

    /**
     * Returns whether two rooted trees are isomorphic.
     * @param G on of the rooted trees to the check for isomorphism.
     * @param rootG the root of the rooted tree G.
     * @param H the other rooted tree to check for isomorphism.
     * @param rootH the root of the rooted tree H.
     * @return the isomorphism between the two rooted trees, if the trees are
     *         not isomorphic it returns null.
     */
    public HashMap<U, V>
    areIsomorphic(Graph<U> G, U rootG, Graph<V> H, V rootH) {
	/* Check that both graphs are indeed trees. */
	if (!G.isTree() || !H.isTree()) { return null; }

	/* Check that both trees have the same order. */
	if (G.order() != H.order()) { return null; }

	/* Define an empty isomorphism. */
	HashMap<U, V> isomorphism = new HashMap<>();

	/* If both rooted trees are trivial, return the trivial isomorphism. */
	if (G.order() == 1) {
	    isomorphism.put(rootG, rootH);
	    return isomorphism;
	}

	/* Properly define the rooted trees. */
	RootedTree<U> T1 = new RootedTree(G, rootG);
	RootedTree<V> T2 = new RootedTree(H, rootH);
	T1.setInitialValues();
	T2.setInitialValues();

	/* Check that the height of both trees are the same. */
	if (T1.height() != T2.height()) { return null; }

	/* Check that they have the same amount of vertices in each level. */
	for (int lvl = 0; lvl <= T1.height(); lvl++) {
	    int levelSizeT1 = T1.getVerticesOfLevel(lvl).size();
	    int levelSizeT2 = T2.getVerticesOfLevel(lvl).size();

	    if (levelSizeT1 != levelSizeT2) { return null; }
	}

	/* Check that all the levels have the same structure. */
	return this.levelsVerification(T1, T2);
    }


    /**
     * Returns whether two trees are isomorphic.
     * @param G on of the trees to the check for isomorphism.
     * @param H the other tree to check for isomorphism.
     * @return the isomorphism between the two trees, if the trees are not
     *         isomorphic it returns null.
     */
    public HashMap<U, V> areIsomorphic(Graph<U> G, Graph<V> H) {
	/* Check that both graphs are indeed trees. */
	if (!G.isTree() || !H.isTree()) { return null; }

	/* Check that both trees have the same order. */
	if (G.order() != H.order()) { return null; }

	/* If both trees are empty graphs, return the trivial isomorphism. */
	if (G.order() == 0) { return new HashMap<U, V>(); }

	HashSet<U> centersG = G.getCentersOfTree();
	HashSet<V> centersH = H.getCentersOfTree();

	/* Check that both trees have the same amount of centers. */
	if (centersG.size() != centersH.size()) { return null; }

	/* Root each graph in each center and check if they're isomorphic. */
	HashMap<U, V> isomorphism = null;
	for (U centerG : centersG) {
	    for (V centerH : centersH) {
		isomorphism = this.areIsomorphic(G, centerG, H, centerH);

		if (isomorphism != null) { return isomorphism; }
	    }

	    /* If the previous rooed trees were not isomorphic, then the trees
	     * are not isomorphic. */
	    break;
	}

	return null;
    }
}
