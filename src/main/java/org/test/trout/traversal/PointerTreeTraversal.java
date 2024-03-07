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
		// Though it is tree, this shouldn't be cyclic, 
		BitSet visitedSet = new BitSet(256);
		
		// The head ref can be use as stack for dfs but the problem is it is 8 bits only.
		// Not mentioned in the problem if the IndirectionTable entries and number of nodes in the tree are 1:1?
		// Therefore using stack for traversal, not recursion. 
		Stack<Node> stack = new Stack<Node>(); // In CPP/C, we can pass memory allocator or it can be initialized on stack itself.
		stack.add(node);
		while(!stack.isEmpty()) {
			traverseTreeLevel(stack, visitedSet, indirectionTable, renderingEngine, metricsCollector);
		}
	}
	
	
	private void traverseTreeLevel(Stack<Node> stack, BitSet visitedSet, IndirectionTable indirectionTable, RenderingEngine renderingEngine, MetricsCollector metricsCollector) {
		Node node = stack.pop();
		
		do {
			if (node.geType() == 1 && !visitedSet.get(node.getRef())) { // We got to the node which has ref for another level.
				Node refNode = indirectionTable.getIndirection(node.getRef());
				visitedSet.flip(node.getRef());
				stack.add(node); // add current node to stack for traversal later.
				stack.add(refNode); // add next level node to the stack for traversal.
				return; // break to move to next level
			}
			renderingEngine.render(node); // Let rendering engine render the node, as of now passing it for all node types
			node = node.getNextNode();
			assert node != null;
		} while(node.geType()!=0); // break when we reached head of the current level.
		
	}

	
}
