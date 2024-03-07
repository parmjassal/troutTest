package org.test.trout.model;

/**
 * Data structure to hold the mapping between reference id and Node.
 */
public class IndirectionTable {
	Node node[] = new Node[255];
	
	// For testing
	public void setIndirection(int index, Node node) {
		assert index <= 255;
		this.node[index] = node;
	}
	
	public Node getIndirection(int index) {
		// Fail Fast if tree suggest's an indirection entry but it is not present,
		// the system is in inconsistent state. (May be we could have some other
		// mechanism to fill up the indirection table like on page table miss we fill up)
		assert node[index] != null;
		return node[index];
	}
}