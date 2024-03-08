package org.test.trout.traversal;

import java.util.BitSet;
import java.util.Stack;

import org.test.trout.engine.RenderingEngine;
import org.test.trout.metrics.MetricsCollector;
import org.test.trout.model.IndirectionTable;
import org.test.trout.model.Node;

/**
 * 
 * The PointerTreeTraversal is an implementation of tree traversal uses the given head node for top level traversal.
 * The used the indirection table to provide the head node for other levels.
 */
public class PointerTreeTraversal {
	
	public void traverseTree(Node node, IndirectionTable indirectionTable, RenderingEngine renderingEngine, MetricsCollector metricsCollector) {
		assert node != null;
		
		traverseLevel(node, renderingEngine, metricsCollector);
		for (int i=0;i<256;i++) {
			Node refNodes = indirectionTable.getIndirection(i);
			if (refNodes != null) {
				traverseLevel(refNodes, renderingEngine, metricsCollector);
			} 
		}
		
	}
	
	public void traverseLevel(Node node, RenderingEngine renderingEngine, MetricsCollector metricsCollector) {
		Node current = node;
		do {
			renderingEngine.render(current);
			current = current.getNextNode();
		} while(current!=null && current.geType()!=0);
	}
	

}
