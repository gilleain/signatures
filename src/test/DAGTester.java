package test;

import org.junit.Assert;
import org.junit.Test;


import signature.DAG;
import signature.Invariants;

public class DAGTester {
    
    public void testInvariants(
            int[] nodeInv, int[] vertexInv, Invariants invariants) {
        Assert.assertArrayEquals(nodeInv, invariants.nodeInvariants);
        Assert.assertArrayEquals(vertexInv, invariants.vertexInvariants);
    }

    @Test 
    public void testSimpleUnlabelledDAG() { 
        // Sets up a simple test case with a graph that looks like this:
        //             0 - Node (vertexIndex - label)
        //            / \
        //    1 - Node  2 - Node

        DAG simpleDAG = new DAG(0, 3, "Node");


        // First do all the initializations related to the nodes of the graph.
        // Create the nodes.
        DAG.Node parentNode = simpleDAG.getRoot();
        // Add the first child.
        DAG.Node childNode = simpleDAG.makeNodeInLayer(1, 1, "Node");
        simpleDAG.addRelation(childNode, parentNode);
        // Add the second child.
        childNode = simpleDAG.makeNodeInLayer(2, 1, "Node");
        simpleDAG.addRelation(childNode, parentNode);

        // Initialize the all invariants.
        simpleDAG.initialize();

        // Canonize DAG by a simple Hopcroft-Tarjan sweep.
        int [] nodeInvariants = {0, 0, 0};
        int [] vertexInvariants = {1, 2, 2};
        testInvariants(
                nodeInvariants, vertexInvariants, simpleDAG.copyInvariants());

        simpleDAG.updateNodeInvariants(DAG.Direction.DOWN);
        int [] nodeInvariantsAfterDown = {1, 0, 0};
        int [] vertexInvariantsAfterDown = {1, 2, 2};
        testInvariants(nodeInvariantsAfterDown, 
                       vertexInvariantsAfterDown,
                       simpleDAG.copyInvariants());

        simpleDAG.computeVertexInvariants();
        int [] nodeInvariantsAfterComputeVertexInv = {1, 0, 0};
        int [] vertexInvariantsAfterComputeVertexInv = {2, 1, 1};
        testInvariants(nodeInvariantsAfterComputeVertexInv, 
                       vertexInvariantsAfterComputeVertexInv,
                       simpleDAG.copyInvariants());

        simpleDAG.updateNodeInvariants(DAG.Direction.UP);
        int [] nodeInvariantsAfterUp = {1, 1, 1};
        int [] vertexInvariantsAfterUp = {2, 1, 1};
        testInvariants(nodeInvariantsAfterUp, 
                       vertexInvariantsAfterUp,
                       simpleDAG.copyInvariants());

        String simpleDAGString = simpleDAG.toString();
        String expected = "[0 Node ([], [1,2])]\n[1 Node ([0], []), " +
        		          "2 Node ([0], [])]\n";
        Assert.assertEquals(expected,simpleDAGString);
    }

    @Test 
    public void testSimpleLabelledDAG(){ // throws Exception {
        // Sets up a simple test case with a graph that looks like this:
        //             0 - Node0 (vertexIndex - label)
        //            / \
        //    1 - Node2  2 - Node1

        DAG simpleDAG = new DAG(0, 3, "Node0");


        // First do all the initializations related to the nodes of the graph.
        // Create the nodes.
        DAG.Node parentNode = simpleDAG.getRoot();
        // Add the first child.
        DAG.Node childNode = simpleDAG.makeNodeInLayer(1, 1, "Node2");
        simpleDAG.addRelation(childNode, parentNode);
        // Add the second child.
        childNode = simpleDAG.makeNodeInLayer(2, 1, "Node1");
        simpleDAG.addRelation(childNode, parentNode);

        // Initialize the all invariants.
        simpleDAG.initialize();

        //System.out.println(simpleDAG.toString());

        // Canonize DAG by a simple Hopcroft-Tarjan sweep.
        int [] nodeInvariants = {0, 0, 0};
        int [] vertexInvariants = {1, 3, 2};
        testInvariants(
                nodeInvariants, vertexInvariants, simpleDAG.copyInvariants());

        simpleDAG.updateNodeInvariants(DAG.Direction.DOWN);
        int [] nodeInvariantsAfterDown = {1, 0, 0};
        int [] vertexInvariantsAfterDown = {1, 3, 2};
        testInvariants(nodeInvariantsAfterDown, 
                       vertexInvariantsAfterDown,
                       simpleDAG.copyInvariants());
        
        simpleDAG.computeVertexInvariants();
        int [] nodeInvariantsAfterComputeVertexInv = {1, 0, 0};
        int [] vertexInvariantsAfterComputeVertexInv = {2, 1, 1};
        testInvariants(nodeInvariantsAfterComputeVertexInv, 
                       vertexInvariantsAfterComputeVertexInv,
                       simpleDAG.copyInvariants());
        
        simpleDAG.updateNodeInvariants(DAG.Direction.UP);
        int [] nodeInvariantsAfterUp = {1, 1, 1};
        int [] vertexInvariantsAfterUp = {2, 1, 1};
        testInvariants(nodeInvariantsAfterUp, 
                       vertexInvariantsAfterUp,
                       simpleDAG.copyInvariants());
    }


}
