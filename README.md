# TroutTest

## Summary
The idea of this document is to give an overview of this project, its structure, assumptions made, and the algorithm used.


### Overview
The project contains the solution for the assignment for interview evaluation focused on the actual scenarios faced daily in the team.
The problem statement is to traverse the tree referring to the html dom structure and make it available to the rendering engine to evaluate whether it should be rendered.

### Structure
The solution to this assignment is done in Java. So the structure of the project is simple maven project-based. At the root level, we have two folders src and tst folder.

Under src, below is the summary packages:-
- `org.test.trout.engine` contains an implementation of the echo engine. It just prints the node which is used for invocation.
- `org.test.trout.metrics` contains an interface for the MetricsCollector. As of now, it contains a blackhole dummy implementation which does nothing. It is just a representation that instrumentation of code is required.
- `org.test.trout.model` contains tree node data in enconded format using byte array so it can be cache friendly. But the `Node` pointer for next link doesn't beahviour similiar to as what we have in c/c++.

```
In C++ total tree can represented as if we know the size upfront
struct Node {
  int data;
  struct *; 
};
Node *fullTree = malloc(sizeof(Node) * num_nodes); It will lead to wastage because of padding.

OR
We can have 

*data = malloc(12 * num_nodes);

provide accessors for each element like gen, type, ref, and link. Possibly `link` can be replaced with `index` only.


```

- `org.test.trout.traversal` contains the actual implementation of the algorithm. As of now, it has only implementation. We should have interfaces as well.


### Assumptions
- The indirection table provides the reference to the head of the node at all levels except the top level.
- Both the tree and indirection table are stable and represent the same truth.

### Algorithm

- Start traversal for the top-level
- Keep reference `toStoreNextBranch` of the head node where we will store the `ref` when encountering the type one node.
- when we reach the head of the current list, do switch using the head nodes reference and use the indirection node.
- Stop the algorithm when currentNode and `toStoreNextBranch` are the same

```
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
```

Time Complexity :- O(num nodes)
Space Complexity :- O(constant)
 
### Testing
Added a function test for the example given in the document and some basic unit test scenarios to fail fast. 
