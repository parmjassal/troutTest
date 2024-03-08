package org.test.trout.engine;

import org.test.trout.model.Node;

public class RenderingEngine {

	public void render(Node node) {
		System.out.println(String.format("Got node with gen = %d, type = %d, ref = %d", node.getGeneration(), node.geType(), Byte.toUnsignedInt(node.getRef())));
	}

}
