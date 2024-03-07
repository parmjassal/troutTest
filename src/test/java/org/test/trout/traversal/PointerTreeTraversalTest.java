package org.test.trout.traversal;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import org.junit.Before;
import org.junit.Test;
import org.test.trout.engine.RenderingEngine;
import org.test.trout.metrics.MetricsCollector;
import org.test.trout.metrics.NullMetricCollector;
import org.test.trout.model.IndirectionTable;
import org.test.trout.model.Node;


public class PointerTreeTraversalTest {

	Node head;
	IndirectionTable indirectionTable;
	ProxyRenderingEngine renderingEngine;
	MetricsCollector metricsCollector;
	
	
	@Before
	public void init() {
		renderingEngine = new ProxyRenderingEngine();
		metricsCollector = new NullMetricCollector();
		indirectionTable = new IndirectionTable();
	}
	
	@Test( expected = Error.class) 
	public void testFailFastScenariosNullHead() {
		PointerTreeTraversal pointerTreeTraversal = new PointerTreeTraversal();
		pointerTreeTraversal.traverseTree(null, indirectionTable, renderingEngine, metricsCollector);
	}
	
	@Test( expected = Error.class) 
	public void testFailFastScenariosNextNodeNull() {
		byte data[] = {(byte)0, (byte)1,(byte)1,(byte)1};
		Node head = new Node(data);
		head.setNode(null);
		PointerTreeTraversal pointerTreeTraversal = new PointerTreeTraversal();
		pointerTreeTraversal.traverseTree(head, indirectionTable, renderingEngine, metricsCollector);
	}
	
	@Test( expected = Error.class) 
	public void testFailFastScenariosIndirectionDontContainReference() {
		byte data0[] = {(byte)0, (byte)1,(byte)0,(byte)1};
		byte data1[] = {(byte)0, (byte)1,(byte)1,(byte)1};
		Node a0 = new Node(data0);
		Node a1 = new Node(data1);
		a0.setNode(a1);
		a1.setNode(a0);
		PointerTreeTraversal pointerTreeTraversal = new PointerTreeTraversal();
		pointerTreeTraversal.traverseTree(head, indirectionTable, renderingEngine, metricsCollector);
	}
	
	@Test
	public void testExampleTraversal() throws Exception {
		buildExampleFromTest();
		PointerTreeTraversal pointerTreeTraversal = new PointerTreeTraversal();
		pointerTreeTraversal.traverseTree(head, indirectionTable, renderingEngine, metricsCollector);
		assertEquals(8, renderingEngine.accessCount);
	}
	
	
	private void buildExampleFromTest() {
		byte [][] ndata = new byte[8][4];
		updateData(ndata,  0, (short)1, (byte) 0, (byte)2); // level a
		updateData(ndata,  1, (short)2, (byte) 5, (byte)17); // level a 
		updateData(ndata,  2, (short)1, (byte) 1, (byte)0); // level a
		updateData(ndata,  3, (short)1, (byte) 0, (byte)2); // level b
		updateData(ndata,  4, (short)2, (byte) 1, (byte)1); // level b
		updateData(ndata,  5, (short)2, (byte) 5, (byte)2); // level b
		updateData(ndata,  6, (short)1, (byte) 4, (byte)2); // level b
		updateData(ndata,  7, (short)0, (byte) 0, (byte)0); // level c
		
		Node c0 = new Node(ndata[7]);
		
		Node b0 = new Node(ndata[3]);
		Node b1 = new Node(ndata[4]);
		Node b2 = new Node(ndata[5]);
		Node b3 = new Node(ndata[6]);
		b0.setNode(b1);
		b1.setNode(b2);
		b2.setNode(b3);
		b3.setNode(b0);
		
		Node a0 = new Node(ndata[0]);
		Node a1 = new Node(ndata[1]);
		Node a2 = new Node(ndata[2]);
		a0.setNode(a1);
		a1.setNode(a2);
		a2.setNode(a0);
		
		head = a0;
		
		indirectionTable = new IndirectionTable();
		indirectionTable.setIndirection(0,b0);
		indirectionTable.setIndirection(1, c0);
		
	}

	private void updateData(byte[][] ndata, int index, short gen, byte type, byte ref) {
		ByteBuffer.wrap(ndata[index], 0, 2).asShortBuffer().put(gen);
		ndata[index][2] = (byte) type;
		ndata[index][3] = (byte) ref;
	}
	
	// Capture number of time rendering engine is invoked. 
	class ProxyRenderingEngine extends RenderingEngine {

		int accessCount = 0;
		@Override
		public void render(Node node) {
			// TODO Auto-generated method stub
			accessCount++;
			super.render(node);
		}
		
	}
	
	
	// Capture number of time, this node is traversed.
	// Depends on `getNextNode` which is invoked only once in algorithm
	class ProxyNode extends Node {

		int accessCount = 0;
		public ProxyNode(byte[] data) {
			super(data);
		}

		@Override
		public Node getNextNode() {
			// TODO Auto-generated method stub
			accessCount++;
			return super.getNextNode();
		}
	} 
}
