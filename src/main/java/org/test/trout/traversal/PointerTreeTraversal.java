package org.test.trout.traversal;

import java.util.BitSet;
import java.util.Stack;

import org.test.trout.engine.RenderingEngine;
import org.test.trout.metrics.MetricsCollector;
import org.test.trout.model.IndirectionTable;
import org.test.trout.model.Node;

/**
 * 
 * The PointerTreeTraversal is an implementation of tree traversal using depth first search. As we traverse the nodes
 * we invoke rendering engine with node as an argument to take decision whether to render or not based on the generation
 * id.
 * As part of this algorithm, using a stack to keep reference of current state where we switched to next level, so once 
 * we traverse next level it would be possible to start from the node from where we left in current level.
 * 
 * Why not using the ref in head node of next level and using stack?
 * - Not sure whether the number of nodes in the tree are same as in Indirection table?
 * - Also ref has space of 8 bits, meaning the whole tree can only have 256 nodes.  
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
