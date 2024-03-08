package org.test.trout.model;

/**
 * Data structure to hold the mapping between reference id and Node.
 */
public class IndirectionTable {
	Node node[] = new Node[256];
	
	// For testing
	public void setIndirection(int index, Node node) {
		assert index <= 255;
		this.node[index] = node;
	}
	
	public Node getIndirection(int index) {
		return node[index];
	}
}