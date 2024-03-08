package org.test.trout.traversal;


import org.test.trout.engine.RenderingEngine;
import org.test.trout.metrics.MetricsCollector;
import org.test.trout.model.IndirectionTable;
import org.test.trout.model.Node;

/**
 * 
 * The PointerTreeTraversal is an implementation of tree traversal. Algorithm is as below
 * - Start traversal for the top level
 * - Keep reference `toStoreNextBranch` of the head node where we will store the `ref` when encountered the type one node.
 * - when we reach head of the current list, do switch using the head nodes reference and use the indirection node.
 * - Stop the algorithm when currentNode and `toStoreNextBranch` are same
 */
public class PointerTreeTraversal {
	
	public void traverseTree(Node node, IndirectionTable indirectionTable, RenderingEngine renderingEngine, MetricsCollector metricsCollector) {
		assert node != null;
		Node currentNode = node;
		Node toStoreNextBranch = node;
		while (true) {
			renderingEngine.render(currentNode);
			if (currentNode.geType() == 1) {
				toStoreNextBranch.setRef(currentNode.getRef());
				toStoreNextBranch = indirectionTable.getIndirection(currentNode.getRef());
			}
			
			currentNode = currentNode.getNextNode();
			
			if (currentNode.geType() == 0) {
				if (currentNode == toStoreNextBranch) {
					break;
				} else {
					currentNode = indirectionTable.getIndirection(currentNode.getRef());
				}
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
