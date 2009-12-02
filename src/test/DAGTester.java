package test;

import org.junit.Test;


import signature.DAG;

public class DAGTester {

	@Test 
	public void testSimpleDAG(){ // throws Exception {
	// Sets up a simple test case with a graph that looks like this:
	//             0 - Node0 (vertexIndex - label)
	//            / \
	//    1 - Node2  2 - Node1
	
	DAG simpleDAG = new DAG(0, 3, "Node0");
	
	
	// First do all the initializations related to the nodes of the graph.
	// Create the nodes.
	DAG.Node parentNode = simpleDAG.getRoot();
	// Add the first child.
	DAG.Node childNode = simpleDAG.makeNode(1, 1, "Node2");
	childNode.addParent(parentNode); parentNode.addChild(childNode);
	// Add the second child.
	childNode = simpleDAG.makeNode(2, 1, "Node1");
	childNode.addParent(parentNode); parentNode.addChild(childNode);
	
	System.out.println(simpleDAG.toString());
	//Assert.assertEquals(true, true);
	
	}
}