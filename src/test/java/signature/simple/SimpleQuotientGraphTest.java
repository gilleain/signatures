package signature.simple;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import signature.display.TreeDrawer;

public class SimpleQuotientGraphTest {
    
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
    
    public void check4Regularity(SimpleGraph graph) {
        System.out.println(graph);
        for (int i = 0; i < graph.getVertexCount(); i++) {
            Assert.assertEquals("failed for " + i, 4, graph.degree(i));
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
    public void fourCubeTest() {
        SimpleGraph fourCube = SimpleGraphFactory.make4Cube();
        check4Regularity(fourCube);
        SimpleQuotientGraph qgraph = new SimpleQuotientGraph(fourCube);
        checkParameters(qgraph, 1, 1, 1);
    }
    
    @Test
    public void fourRegularExampleTest() {
        SimpleGraph fourRegular = SimpleGraphFactory.makeFourRegularExample();
        check4Regularity(fourRegular);
        SimpleQuotientGraph qgraph = new SimpleQuotientGraph(fourRegular);
        checkParameters(qgraph, 1, 1, 1);
    }
   
    @Test
    public void petersensGraphTest() {
        SimpleGraph petersens = SimpleGraphFactory.makePetersensGraph();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(petersens);
        checkParameters(quotientGraph, 1, 1, 1);
    }
    
    @Test
    public void pappusGraphTest() {
        SimpleGraph pappus = SimpleGraphFactory.makePappusGraph();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(pappus);
        checkParameters(quotientGraph, 1, 1, 1);
    }
    
    @Test
    public void triangularPrismTest() {
        SimpleGraph pentaprism = SimpleGraphFactory.makePrism(3);
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(pentaprism);
        checkParameters(quotientGraph, 1, 1, 1);
    }
    
    @Test
    public void cubeTest() {
        SimpleGraph cube = SimpleGraphFactory.makePrism(4);
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(cube);
        checkParameters(quotientGraph, 1, 1, 1);
    }
    
    @Test
    public void pentagonalPrismTest() {
        SimpleGraph pentaprism = SimpleGraphFactory.makePrism(5);
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(pentaprism);
        checkParameters(quotientGraph, 1, 1, 1);
    }
    
    @Test
    public void truncatedTetrahedronTest() {
        SimpleGraph truncatedTetrahedron = 
            SimpleGraphFactory.makeTruncatedTetrahedron();
        SimpleQuotientGraph quotientGraph = 
            new SimpleQuotientGraph(truncatedTetrahedron);
        checkParameters(quotientGraph, 1, 1, 1);
    }

    @Test
    public void adamantaneTest() {
        SimpleGraph adamantane = SimpleGraphFactory.makeAdamantane();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(adamantane);
        checkParameters(quotientGraph, 2, 1, 0);
    }
    
    @Test
    public void triangleSandwichTest() {
        SimpleGraph triangle = SimpleGraphFactory.makeSandwich(3);
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(triangle);
        checkParameters(quotientGraph, 2, 2, 1);
    }
    
    @Test
    public void squareSandwichTest() {
        SimpleGraph square = SimpleGraphFactory.makeSandwich(4);
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(square);
        checkParameters(quotientGraph, 2, 2, 1);
    }
    
    @Test
    public void pentagonalSandwichTest() {
        SimpleGraph pentagon = SimpleGraphFactory.makeSandwich(5);
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(pentagon);
        checkParameters(quotientGraph, 2, 2, 1);
    }
    
    @Test
    public void hexagonalSandwichTest() {
        SimpleGraph hexagon = SimpleGraphFactory.makeSandwich(6);
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(hexagon);
        checkParameters(quotientGraph, 2, 2, 1);
    }
    
    @Test
    public void symmetric1TwistaneTest() {
        SimpleGraph symmetric1Twistane = SimpleGraphFactory.makeSymmetric1Twistane();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(symmetric1Twistane);
        checkParameters(quotientGraph, 2, 2, 1);
    }
    
    @Test
    public void symmetric2TwistaneTest() {
        SimpleGraph symmetric2Twistane = SimpleGraphFactory.makeSymmetric2Twistane();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(symmetric2Twistane);
        checkParameters(quotientGraph, 2, 3, 2);
    }
    
    @Test
    public void herschelGraphTest() {
        SimpleGraph herschel = SimpleGraphFactory.makeHerschelGraph();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(herschel);
        checkParameters(quotientGraph, 3, 2, 0);
    }
    
    @Test
    public void diamantaneTest() {
        SimpleGraph diamantane = SimpleGraphFactory.makeDiamantane();
        SimpleQuotientGraph qGraph = new SimpleQuotientGraph(diamantane);
        checkParameters(qGraph, 3, 3, 1);
    }
    
    @Test
    public void grotschGraphTest() {
        SimpleGraph grotschGraph = SimpleGraphFactory.makeGrotschGraph();
        SimpleQuotientGraph qgraph = new SimpleQuotientGraph(grotschGraph);
        checkParameters(qgraph, 3, 3, 1);
    }

    @Test
    public void quadricyclaneTest() {
        SimpleGraph quadricyclane = SimpleGraphFactory.makeQuadricyclane();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(quadricyclane);
        checkParameters(quotientGraph, 3, 3, 1);
    }
    
    @Test
    public void spiroPentagonTest() {
        SimpleGraph spiroPentagons = SimpleGraphFactory.makeSpiroPentagons();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(spiroPentagons);
        checkParameters(quotientGraph, 3, 3, 1);
    }
    
    @Test
    public void threeFourFiveTwistedGraphTest() {
        SimpleGraph threeFourFive = 
            SimpleGraphFactory.makeThreeFourFiveTwisted();
        SimpleQuotientGraph quotientGraph = 
            new SimpleQuotientGraph(threeFourFive);
        checkParameters(quotientGraph, 3, 3, 1);
    }
    
    @Test
    public void twistaneTest() {
        SimpleGraph twistane = SimpleGraphFactory.makeTwistane();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(twistane);
        checkParameters(quotientGraph, 3, 4, 2);
    }
    
    @Test
    public void napthaleneTest() {
        SimpleGraph napthalene = SimpleGraphFactory.makeNapthalene();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(napthalene);
        checkParameters(quotientGraph, 3, 4, 2);
    }
    
    @Test
    public void tietzesGraphTest() {
        SimpleGraph tietzes = SimpleGraphFactory.makeTietzesGraph();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(tietzes);
        checkParameters(quotientGraph, 3, 4, 2);
    }

    @Test
    public void cuneaneTest() {
        SimpleGraph cuneane = SimpleGraphFactory.makeCuneane();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(cuneane);
        checkParameters(quotientGraph, 3, 5, 3);
    }

    @Test
    public void squareQuotientGraphTest() {
        SimpleGraph squareQuotientGraph = 
            SimpleGraphFactory.makeSquareQuotientGraph();
        SimpleQuotientGraph quotientGraph = 
            new SimpleQuotientGraph(squareQuotientGraph);
        checkParameters(quotientGraph, 4, 4, 0);
    }
    
    @Test
    public void doubleBridgedPentagonTest() {
        SimpleGraph g = SimpleGraphFactory.makeDoubleBridgedPentagon();
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(g);
        checkParameters(quotientGraph, 4, 5, 1);
    }

    @Test
    public void bowtieaneQuotientGraphTest() {
        SimpleGraph bowtieane = SimpleGraphFactory.makeBowtieane();
//        check3Regularity(bowtieane);
        SimpleQuotientGraph quotientGraph = new SimpleQuotientGraph(bowtieane);
//        draw(quotientGraph);
        // TODO : FIXME
        checkParameters(quotientGraph, 4, 5, 2);
    }
    
    @Test
    public void fullerene26Test() {
        SimpleGraph fullerene26 = SimpleGraphFactory.make26Fullerene();
        check3Regularity(fullerene26);
        SimpleQuotientGraph quotientGraph = 
            new SimpleQuotientGraph(fullerene26);
        checkParameters(quotientGraph, 4, 5, 2);
    }
    
    @Test
    public void diSpiroOctaneQuotientGraphTest() {
        SimpleGraph diSpiroOctane = SimpleGraphFactory.makeDiSpiroOctane();
        SimpleQuotientGraph quotientGraph = 
            new SimpleQuotientGraph(diSpiroOctane);
        checkParameters(quotientGraph, 5, 6, 1);
    }
    
    @Test
    public void tricycloPropaIndeneQuotientGraphTest() {
        SimpleGraph tricycloPropaIndene = 
            SimpleGraphFactory.makeTricycloPropaIndene();
        SimpleQuotientGraph quotientGraph =
            new SimpleQuotientGraph(tricycloPropaIndene);
        checkParameters(quotientGraph, 6, 8, 2);
    }
    

}
