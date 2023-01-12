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
	
    }
    
    /** Vertices of the graph. */
    private HashMap<T, Vertex<T>> vertices;
    
    /** Auxiliary list to have constant-time access to a vertex graph. */
    private LinkedList<T> elements;

    /** Empty constructor for a graph. */
    public Graph() {
	this.vertices = new HashMap<>();
    }
    
    /** 
     * Constructor which receives an array of edges to build the graph.
     * @param edges the array of edges.
     */
    public Graph(T[][] edges) {
	this.vertices = new HashMap<>();
	
	/* Traverse the edges and add them to the graph. */
	for (T[] edge : edges) {
	    /* Get the first vertex's ID in the pair */
	    T vID = edge[0];

	    /* Get the second vertex's ID in the pair */
	    T uID = edge[1];
	    
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
    public void addPath(T[] path) {
	/* The path's length. */
	int lp = path.length;
	
	/* Check if the first vertex on the path is in the graph. */
	if (!this.containsVertex(path[0])) {
	    this.addVertex(path[0]);
	}

	/* Traverse the vertices in the path */
	for (int i = 1; i < lp; i++) {
	    T vID = path[i];
	    
	    /* If the current vertex is not in the graph, add it. */
	    if (!this.containsVertex(vID)) {
		this.addVertex(vID);
	    }
	    
	    /* Add an edge between the current vertex and the previous one. */
	    T uID = path[i-1];
	    this.connectVertices(vID, uID);
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
	if (this.containsVertex(rootID)) {
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
	
}
