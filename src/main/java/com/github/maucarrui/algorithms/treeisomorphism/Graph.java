package com.github.maucarrui.algorithms.treeisomorphism;

import java.util.HashSet;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Class to represent a graph.
 */
public class Graph<T extends Comparable<T>> {

    /**
     * Private internal class to represent a vertex in the graph.
     */
    private class Vertex<T> {
	/** Identifier of the vertex. */
	private T ID;

	/** The identifiers of the neighbors of the vertex. */
	private HashSet<T> neighbors;

	/**
	 * Unique constructor which receives the identifier of the vertex.
	 * @param element the identifier of the vertex.
	 */
	public Vertex(T ID) {
	    this.ID = ID;
	    this.neighbors = new HashSet<>();
	}

	/**
	 * Returns the vertex's identifier.
	 * @return the vertex's identifier.
	 */
	public T getID() {
	    return this.ID;
	}

	/**
	 * Returns the set of neighbors of the vertex.
	 * @return the set of neighbors of the vertex.
	 */
	public HashSet<T> getNeighbors() {
	    return this.neighbors;
	}

	/**
	 * Add the neighbor's ID to the set of neighbors.
	 * @param nID the neighbor's ID.
	 */
	public void addNeighbor(T nID) {
	    this.neighbors.add(nID);
	}

	/**
	 * Returns whether the current vertex is connected to the other vertex.
	 * @return true if the current vertex is connected to the other vertex,
	 *         false otherwise.
	 */
	public boolean hasNeighbor(T nID) {
	    return this.neighbors.contains(nID);
	}

    }

    /** Vertices of the graph. */
    private HashMap<T, Vertex<T>> vertices;

    /** Auxiliary list to have constant-time access to a vertex graph. */
    private LinkedList<T> elements;

    /** Empty constructor for a graph. */
    public Graph() {
	this.vertices = new HashMap<>();
	this.elements = new LinkedList<>();
    }

    /**
     * Constructor which receives a list of edges to build the graph.
     * @param edges the linked list of edges.
     */
    public Graph(LinkedList<LinkedList<T>> edges) {
	this.vertices = new HashMap<>();
	this.elements = new LinkedList<>();

	/* Traverse the edges and add them to the graph. */
	for (LinkedList<T> edge : edges) {
	    /* Get the first vertex's ID in the pair */
	    T vID = edge.peekFirst();

	    /* Get the second vertex's ID in the pair */
	    T uID = edge.peekLast();

	    /* If there is no vertex in the tree with first ID, create it. */
	    if (!this.containsVertex(vID)) {
		this.addVertex(vID);
	    }

	    /* If there is no vertex in the tree with second ID, create it. */
	    if (!this.containsVertex(uID)) {
		this.addVertex(uID);
	    }

	    /* Create an edge between the previous vertices. */
	    this.connectVertices(vID, uID);
	}
    }

    /**
     * Returns the order of the graph, i.e. the amount of vertices it has.
     * @return the order of the graph.
     */
    public int order() {
	return this.vertices.size();
    }

    /**
     * Returns whether the graph contains a vertex whose ID is the given one.
     * @param ID the vertex's ID.
     * @return true if the graph contains a vertex whose ID is the given one,
     *         false otherwise.
     */
    public boolean containsVertex(T ID) {
	return this.vertices.containsKey(ID);
    }

    /**
     * Adds a vertex whose ID is the given one.
     * @param ID the vertex's ID.
     */
    public void addVertex(T ID) {
	Vertex<T> v = new Vertex<>(ID);
	this.vertices.put(ID, v);
	this.elements.add(ID);
    }

    /**
     * Adds an edge between the given vertices.
     * @param vID the ID of one vertex.
     * @param uID the ID of the other vertex.
     */
    public void connectVertices(T vID, T uID) {
	/* Get the vertices that correspond to the ID. */
	Vertex<T> v = this.vertices.get(vID);
	Vertex<T> u = this.vertices.get(uID);

	/* The vertex v is adjacent to u, and viceversa. */
	v.addNeighbor(uID);
	u.addNeighbor(vID);
    }

    /**
     * Returns whether two vertices are connected in the current graph.
     * @param vID the ID of one vertex.
     * @param uID the ID of the other vertex.
     * @return true if the two vertices are adjacent in the graph, false
     *         otherwise.
     */
    public boolean areConnected(T vID, T uID) {
	if (!this.containsVertex(vID) || !this.containsVertex(uID)) {
	    return false;
	}

	Vertex<T> v = this.vertices.get(vID);
	return v.hasNeighbor(uID);
    }

    /**
     * Returns the neighbors of a vertex.
     * @param vID the vertex's ID.
     * @return the neighbors of a vertex or null if the vertex is not in the
     *         graph.
     */
    public HashSet<T> getNeighborsOf(T vID) {
	/* If the vertex is not contained in the graph, return null. */
	if (!this.containsVertex(vID)) {
	    return null;
	}

	return this.vertices.get(vID).getNeighbors();
    }

    /**
     * Adds a path in the graph. A path is a sequence of vertices, such that, if
     * one vertex is before or after another vertex in the tree, then they have
     * an edge that connects them. If some vertex in the path is not contained
     * in the graph, create it and add it.
     * @param path the sequence of vertices.
     */
    public void addPath(LinkedList<T> path) {
	T prevID = null;
	/* Traverse the vertices in the path */
	for (T currentID : path) {
	    /* If the current vertex is not in the graph, add it. */
	    if (!this.containsVertex(currentID)) {
		this.addVertex(currentID);
	    }

	    /* Add an edge between the current vertex and the previous one. */
	    if (prevID != null) {
		this.connectVertices(prevID, currentID);
	    }

	    /* Set the previous vertex as the current one. */
	    prevID = currentID;
	}
    }

    /**
     * Returns the edges obtained by performing a BFS traversal starting from
     * the specified root, if the root is not contained in the graph, it returns
     * null.
     * @param rootID the root's ID, where the BFS traversal starts from.
     * @return the edges obtained by performing a BFS traversal.
     */
    public LinkedList<LinkedList<T>> getBFSEdges(T rootID) {
	/* If the root is not contained in the tree, return null. */
	if (!this.containsVertex(rootID)) {
	    return null;
	}

	/* Define an empty list of edges. */
	LinkedList<LinkedList<T>> edges = new LinkedList<>();

	/* Define a queue to perform the BFS traversal */
	LinkedList<T> Q = new LinkedList<>();
	HashSet<T> explored = new HashSet<>();

	/* Add the root to the queue and explored vertices. */
	Q.add(rootID);
	explored.add(rootID);

	/* Start the BFS traversal. */
	while(Q.size() > 0) {
	    /* Get the queue's head. */
	    T vID = Q.peek();

	    /* Iterate through the neighbors of the head. */
	    for (T nID : this.getNeighborsOf(vID)) {
		/* If the neighbor hasn't been explored, add the edge between
		 * the head and the neighbor and append the neighbor to the
		 * queue. */
		if (!explored.contains(nID)) {
		    /* Create an edge as a linked list. */
		    LinkedList<T> edge = new LinkedList<>();

		    /* The first ID corresponds to the father. */
		    edge.add(vID);

		    /* The second ID corresponds to the neighbor. */
		    edge.add(nID);

		    /* Add the edge to the list of edges. */
		    edges.add(edge);

		    /* Add the neighbor to the queue and the explored
		     * vertices. */
		    Q.add(nID);
		    explored.add(nID);
		}
	    }

	    /* Once all the neighbors have been explored, remove the head. */
	    Q.remove();
	}

	return edges;
    }

    /**
     * Returns whether the current graph is a tree.
     *
     * A tree is a connected non-ciclic graph.
     * @return true if the current graph is a tree, false otherwise.
     */
    public boolean isTree(){
	/* If the graph is empty, then by definition it's a tree. */
	if (this.vertices.size() == 0) { return true; }

	/* Set a random vertex of the graph as a root. */
	T rootID = this.elements.peek();

	/* Perform a BFS traversal from this vertex. */
	HashMap<T, T> parenthood = new HashMap<>();
	LinkedList<T> Q = new LinkedList<>();
	HashSet<T> explored = new HashSet<>();

	/* Add the root to the queue and explored vertices. */
	Q.add(rootID);
	explored.add(rootID);
	parenthood.put(rootID, null);

	/* Start the BFS traversal. */
	while(Q.size() > 0) {
	    /* Get the queue's head. */
	    T vID = Q.peek();

	    /* Iterate through the neighbors of the head. */
	    for (T nID : this.getNeighborsOf(vID)) {
		if (!explored.contains(nID)) {
		    /* If the neighbor hasn't been explored, append the neighbor
		     * to the queue, add it to the set of explored vertices and
		     * define the parent-child relation. */
		    Q.add(nID);
		    explored.add(nID);
		    parenthood.put(nID, vID);
		} else {
		    /* If the neighbor has been explored, at it isn't the
		     * queue's head, then the graph contains a cycle; it's not a
		     * tree. */
		    if (!nID.equals(vID)) {
			return false;
		    }
		}
	    }

	    /* Once all the neighbors have been explored, remove the head. */
	    Q.remove();
	}

	/* If there are no cycles, then after a BFS traversal, all the vertices
	 * must have been explored. */
	return (explored.size() != this.vertices.size());
    }

    /**
     * Returns the center or centers of the current tree (this is supposing the
     * current graph is a tree).
     * @return the center or centers of the current tree.
     */
    public HashSet<T> getCentersOfTree() {
	/* Set a random vertex of the graph as a root. */
	T rootID = this.elements.peek();

	/* Get the last vertex explored by doing a BFS traversal from the
	 * root. */
	LinkedList<LinkedList<T>> edges = this.getBFSEdges(rootID);
	T xID = edges.peekLast().peekLast();

	/* Do a BFS traversal from the new vertex x. */
	HashMap<T, T> parenthood = new HashMap<>();
	edges = this.getBFSEdges(xID);
	for (LinkedList<T> edge : edges) {
	    T vID = edge.peekFirst();
	    T uID = edge.peekLast();

	    parenthood.put(vID, uID);
	}

	/* Get the last explored vertex of the previous BFS traversal. */
	T yID = edges.peekLast().peekLast();

	/* Build an xy-path using the parenthood mapping previously defined. */
	LinkedList<T> path = new LinkedList<>();
	T currentID = yID;
	while (currentID != null) {
	    path.add(currentID);
	    currentID = parenthood.get(currentID);
	}

	/* Return the centers of the tree. */
	int length = path.size();
	HashSet<T> centers = new HashSet<>();
	if (length % 2 == 0) {
	    /* If the xy-path is of length even, then the vertices found on the
	     * (n/2)-th and ((n/2)-1)-th position are the centers of the
	     * tree. */
	    T cu = path.get(length / 2);
	    T cv = path.get((length / 2) - 1);

	    centers.add(cu);
	    centers.add(cv);
	} else {
	    /* If the xy-path is of length odd, then the vertex found on the
	     * ((n-1)/2)-th position is the center of the tree. */
	    T cv = path.get(((length - 1)/2));
	    centers.add(cv);
	}

	return centers;
    }

}
