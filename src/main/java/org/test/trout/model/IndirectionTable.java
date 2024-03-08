package org.test.trout.model;

/**
 * Data structure to hold the mapping between reference id and Node.
 */
public class IndirectionTable {
	Node node[] = new Node[256];
	
	// For testing
	public void setIndirection(byte index, Node node) {
		int cIndex = Byte.toUnsignedInt(index);
		assert cIndex <= 255 && cIndex >=0;
		this.node[cIndex] = node;
	}
	
	public Node getIndirection(byte index) {
		int cIndex = Byte.toUnsignedInt(index);
		assert cIndex <= 255 && cIndex >=0;
		// Fail Fast if tree suggest's an indirection entry but it is not present,
		// the system is in inconsistent state. (May be we could have some other
		// mechanism to fill up the indirection table like on page table miss we fill up)
		assert node[cIndex] != null;
		return node[cIndex];
	}
}