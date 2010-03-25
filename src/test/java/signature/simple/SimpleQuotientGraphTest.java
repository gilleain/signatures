package signature.simple;

import junit.framework.Assert;

import org.junit.Test;

public class SimpleQuotientGraphTest extends AbstractSimpleGraphTest {
    
    @Test
    public void cubeTest() {
        SimpleGraph cube = AbstractSimpleGraphTest.makeCube();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(cube);
        System.out.println(quotientGraph);
        
        int expectedVertexCount = 1;
        Assert.assertEquals(expectedVertexCount, quotientGraph.getVertexCount());
        
        int expectedEdgeCount = 1;
        Assert.assertEquals(expectedEdgeCount, quotientGraph.getEdgeCount());
        
        int expectedLoopEdgeCount = 1;
        Assert.assertEquals(expectedLoopEdgeCount, quotientGraph.numberOfLoopEdges());
    }
    
    @Test
    public void truncatedTetrahedronTest() {
        SimpleGraph truncatedTetrahedron = 
            AbstractSimpleGraphTest.makeTruncatedTetrahedron();
        SimpleQuotientGraph quotientGraph = 
            new SimpleQuotientGraph(truncatedTetrahedron);
        System.out.println(quotientGraph);
        
        int expectedVertexCount = 1;
        Assert.assertEquals(expectedVertexCount, quotientGraph.getVertexCount());
        
        int expectedEdgeCount = 1;
        Assert.assertEquals(expectedEdgeCount, quotientGraph.getEdgeCount());
        
        int expectedLoopEdgeCount = 1;
        Assert.assertEquals(expectedLoopEdgeCount, quotientGraph.numberOfLoopEdges());
    }

    @Test
    public void adamantaneTest() {
        SimpleGraph adamantane = AbstractSimpleGraphTest.makeAdamantane();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(adamantane);
        System.out.println(quotientGraph);
        
        int expectedVertexCount = 2;
        Assert.assertEquals(expectedVertexCount, quotientGraph.getVertexCount());
        
        int expectedEdgeCount = 1;
        Assert.assertEquals(expectedEdgeCount, quotientGraph.getEdgeCount());
        
        int expectedLoopEdgeCount = 0;
        Assert.assertEquals(expectedLoopEdgeCount, quotientGraph.numberOfLoopEdges());
    }
    
    @Test
    public void cuneaneTest() {
        SimpleGraph cuneane = AbstractSimpleGraphTest.makeCuneane();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(cuneane);
        System.out.println(quotientGraph);
        
        int expectedVertexCount = 3;
        Assert.assertEquals(expectedVertexCount, quotientGraph.getVertexCount());
        
        int expectedEdgeCount = 5;
        Assert.assertEquals(expectedEdgeCount, quotientGraph.getEdgeCount());
        
        int expectedLoopEdgeCount = 3;
        Assert.assertEquals(expectedLoopEdgeCount, quotientGraph.numberOfLoopEdges());
    }
    
    @Test
    public void twistaneTest() {
        SimpleGraph twistane = AbstractSimpleGraphTest.makeTwistane();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(twistane);
        System.out.println(quotientGraph);
        
        int expectedVertexCount = 3;
        Assert.assertEquals(expectedVertexCount, quotientGraph.getVertexCount());
        
        int expectedEdgeCount = 4;
        Assert.assertEquals(expectedEdgeCount, quotientGraph.getEdgeCount());
        
        int expectedLoopEdgeCount = 2;
        Assert.assertEquals(expectedLoopEdgeCount, quotientGraph.numberOfLoopEdges());
    }
    
    @Test
    public void napthaleneTest() {
        SimpleGraph napthalene = AbstractSimpleGraphTest.makeNapthalene();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(napthalene);
        System.out.println(quotientGraph);
        
        int expectedVertexCount = 3;
        Assert.assertEquals(expectedVertexCount, quotientGraph.getVertexCount());
        
        int expectedEdgeCount = 4;
        Assert.assertEquals(expectedEdgeCount, quotientGraph.getEdgeCount());
        
        int expectedLoopEdgeCount = 2;
        Assert.assertEquals(expectedLoopEdgeCount, quotientGraph.numberOfLoopEdges());
    }

}
