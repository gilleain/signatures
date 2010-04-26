package signature;

import junit.framework.Assert;

import org.junit.Test;

import signature.simple.SimpleGraph;
import signature.simple.SimpleVertexSignature;

public class HeightTest {
    
    public SimpleGraph makeTorus(int width, int height) {
        SimpleGraph graph = new SimpleGraph();
        // make a grid of width * height
        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height; j++) {
                int x = (width * j) + i;
                int y = x + 1;
                graph.makeEdge(x, y);
                if (j < height - 1) {
                    int z = width * (j + 1) + i;
                    graph.makeEdge(x, z);
                }
            }
        }
        // finish off the last column
        for (int i = 0; i < height - 1; i++) {
            int x = ((i + 1) * width) - 1;
            int y = ((i + 2) * width) - 1;
            graph.makeEdge(x, y);
        }
        
        int size = width * height;
        
        // connect the top edge to the bottom
        for (int i = 0; i < width; i++) {
            graph.makeEdge(i, size - width + i);
        }
        
        // connect the left edge to the right
        for (int j = 0; j < height; j++) {
            int x = width * j;
            int y = (x + width) - 1;
            graph.makeEdge(x, y);
        }
        
        return graph;
    }
    
    public void regularGraphTest(SimpleGraph graph, int diameter) {
        for (int h = 1; h <= diameter; h++) {
            SimpleVertexSignature sig0 = 
                new SimpleVertexSignature(0, h, graph);
            String zeroCanonical = sig0.toCanonicalString();
            System.out.println(h + "\t" + zeroCanonical);
            for (int i = 1; i < graph.getVertexCount(); i++) {
                SimpleVertexSignature sig = 
                    new SimpleVertexSignature(i, h, graph);
//                Assert.assertEquals(zeroCanonical, sig.toCanonicalString());
                String canString = sig.toCanonicalString();
                if (zeroCanonical.equals(canString)) {
//                    System.out.println("EQU");
                } else {
                    System.out.println("NEQ "
                            + h + "\t" + i + "\t"
                            + zeroCanonical + " " + canString);
                }
                Assert.assertEquals(zeroCanonical, canString);
            }
        }
    }
    
    @Test
    public void torusTest() {
        int width = 6;
        int height = 6;
        
        SimpleGraph torus = makeTorus(width, height);
        System.out.println(torus);
        int diameter = Math.min(width, height);
        regularGraphTest(torus, diameter);
    }
    
    public SimpleGraph makeCompleteGraph(int n) {
        SimpleGraph g = new SimpleGraph();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                g.makeEdge(i, j);
            }
        }
        return g;
    }
    
    @Test
    public void completeGraphTest() {
        int n = 7;
        SimpleGraph kN = makeCompleteGraph(n);
        int expectedEdgeCount = (n * (n - 1)) / 2;
        Assert.assertEquals(expectedEdgeCount, kN.edges.size());
        regularGraphTest(kN, n - 1);
    }
    
    public static void main(String[] args) {
        new HeightTest().torusTest();
    }

}
