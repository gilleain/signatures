package signature.simple;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import signature.display.TreeDrawer;

public class SimpleQuotientGraphTest extends AbstractSimpleGraphTest {
    
    public void draw(SimpleQuotientGraph quotientGraph) {
        String directoryPath = "tmp4";
        List<String> signatureStrings = 
            quotientGraph.getVertexSignatureStrings();
        int w = 1200;
        int h = 400;
        TreeDrawer.makeTreeImages(signatureStrings, directoryPath, w, h);
    }
    
    public void check3Regularity(SimpleGraph graph) {
        System.out.println(graph);
        for (int i = 0; i < graph.getVertexCount(); i++) {
            Assert.assertEquals("failed for " + i, 3, graph.degree(i));
        }
    }
    
    public void checkParameters(SimpleQuotientGraph qGraph,
                                int expectedVertexCount, 
                                int expectedEdgeCount, 
                                int expectedLoopEdgeCount) {
        System.out.println(qGraph);
        Assert.assertEquals(expectedVertexCount, qGraph.getVertexCount());
        Assert.assertEquals(expectedEdgeCount, qGraph.getEdgeCount());
        Assert.assertEquals(expectedLoopEdgeCount, qGraph.numberOfLoopEdges());
        
    }
    
    @Test
    public void petersensGraphTest() {
        SimpleGraph petersens = AbstractSimpleGraphTest.makePetersensGraph();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(petersens);
        checkParameters(quotientGraph, 1, 1, 1);
    }
    
    @Test
    public void pappusGraphTest() {
        SimpleGraph pappus = AbstractSimpleGraphTest.makePappusGraph();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(pappus);
        checkParameters(quotientGraph, 1, 1, 1);
    }
    
    @Test
    public void triangularPrismTest() {
        SimpleGraph pentaprism = AbstractSimpleGraphTest.makePrism(3);
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(pentaprism);
        checkParameters(quotientGraph, 1, 1, 1);
    }
    
    @Test
    public void cubeTest() {
        SimpleGraph cube = AbstractSimpleGraphTest.makePrism(4);
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(cube);
        checkParameters(quotientGraph, 1, 1, 1);
    }
    
    @Test
    public void pentagonalPrismTest() {
        SimpleGraph pentaprism = AbstractSimpleGraphTest.makePrism(5);
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(pentaprism);
        checkParameters(quotientGraph, 1, 1, 1);
    }
    
    @Test
    public void truncatedTetrahedronTest() {
        SimpleGraph truncatedTetrahedron = 
            AbstractSimpleGraphTest.makeTruncatedTetrahedron();
        SimpleQuotientGraph quotientGraph = 
            new SimpleQuotientGraph(truncatedTetrahedron);
        checkParameters(quotientGraph, 1, 1, 1);
    }

    @Test
    public void adamantaneTest() {
        SimpleGraph adamantane = AbstractSimpleGraphTest.makeAdamantane();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(adamantane);
        checkParameters(quotientGraph, 2, 1, 0);
    }
    
    @Test
    public void triangleSandwichTest() {
        SimpleGraph triangle = AbstractSimpleGraphTest.makeSandwich(3);
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(triangle);
        checkParameters(quotientGraph, 2, 2, 1);
    }
    
    @Test
    public void squareSandwichTest() {
        SimpleGraph square = AbstractSimpleGraphTest.makeSandwich(4);
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(square);
        checkParameters(quotientGraph, 2, 2, 1);
    }
    
    @Test
    public void pentagonalSandwichTest() {
        SimpleGraph pentagon = AbstractSimpleGraphTest.makeSandwich(5);
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(pentagon);
        checkParameters(quotientGraph, 2, 2, 1);
    }
    
    @Test
    public void hexagonalSandwichTest() {
        SimpleGraph hexagon = AbstractSimpleGraphTest.makeSandwich(6);
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(hexagon);
        checkParameters(quotientGraph, 2, 2, 1);
    }
    
    @Test
    public void spiroPentagonTest() {
        SimpleGraph spiroPentagons = AbstractSimpleGraphTest.makeSpiroPentagons();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(spiroPentagons);
        checkParameters(quotientGraph, 3, 3, 1);
    }
    
    @Test
    public void twistaneTest() {
        SimpleGraph twistane = AbstractSimpleGraphTest.makeTwistane();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(twistane);
        checkParameters(quotientGraph, 3, 4, 2);
    }
    
    @Test
    public void napthaleneTest() {
        SimpleGraph napthalene = AbstractSimpleGraphTest.makeNapthalene();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(napthalene);
        checkParameters(quotientGraph, 3, 4, 2);
    }
    
    @Test
    public void tietzesGraphTest() {
        SimpleGraph tietzes = AbstractSimpleGraphTest.makeTietzesGraph();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(tietzes);
        checkParameters(quotientGraph, 3, 4, 2);
    }

    @Test
    public void cuneaneTest() {
        SimpleGraph cuneane = AbstractSimpleGraphTest.makeCuneane();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(cuneane);
        checkParameters(quotientGraph, 3, 5, 3);
    }

    @Test
    public void squareQuotientGraphTest() {
        SimpleGraph squareQuotientGraph = 
            AbstractSimpleGraphTest.makeSquareQuotientGraph();
        SimpleQuotientGraph quotientGraph = 
            new SimpleQuotientGraph(squareQuotientGraph);
        checkParameters(quotientGraph, 4, 4, 0);
    }
    
    @Test
    public void doubleBridgedPentagonTest() {
        SimpleGraph g = AbstractSimpleGraphTest.makeDoubleBridgedPentagon();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(g);
        checkParameters(quotientGraph, 4, 5, 1);
    }

    @Test
    public void bowtieaneQuotientGraphTest() {
        SimpleGraph bowtieane = AbstractSimpleGraphTest.makeBowtieane();
        check3Regularity(bowtieane);
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(bowtieane);
//        draw(quotientGraph);
        checkParameters(quotientGraph, 4, 5, 2);
    }
    
    @Test
    public void fullerene26Test() {
        SimpleGraph fullerene26 = AbstractSimpleGraphTest.make26Fullerene();
        check3Regularity(fullerene26);
        SimpleQuotientGraph quotientGraph = 
            new SimpleQuotientGraph(fullerene26);
        checkParameters(quotientGraph, 4, 5, 2);
    }
    
    @Test
    public void diSpiroOctaneQuotientGraphTest() {
        SimpleGraph diSpiroOctane = AbstractSimpleGraphTest.makeDiSpiroOctane();
        SimpleQuotientGraph quotientGraph = 
            new SimpleQuotientGraph(diSpiroOctane);
        checkParameters(quotientGraph, 5, 6, 1);
    }
    
    @Test
    public void tricycloPropaIndeneQuotientGraphTest() {
        SimpleGraph tricycloPropaIndene = 
            AbstractSimpleGraphTest.makeTricycloPropaIndene();
        SimpleQuotientGraph quotientGraph =
            new SimpleQuotientGraph(tricycloPropaIndene);
        checkParameters(quotientGraph, 6, 8, 2);
    }
    

}
