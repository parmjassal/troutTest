package org.test.trout.model;

import java.nio.ByteBuffer;

/**
 * The node holds data related to generation, type and ref in a byte array of 4.
 * In C/C++,  it can be struct of int and pointer of struct only, holding 12 bytes
 * continuous (actual 16 bytes because of padding)
 */
public class Node {
	private byte data[] = new byte[4];
	private Node node;
	
	public Node(byte[] data) {
		super();
		this.data = data;
		this.node = this;
	}
	
	public void setNode(Node node) {
		this.node = node;
	}

	// Need to use `int` here as java doesn't provide unsigned storage.
	// In C/C++, could have used unsigned short.
	public int getGeneration() {
		return Short.toUnsignedInt(ByteBuffer.wrap(data, 0, 2).getShort());
	}

	public byte geType() {
		return data[2];
	}
	
	public byte getRef() {
		return data[3];
	}
	
	public Node getNextNode() {
		return node;
	}
}