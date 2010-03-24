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
    
    @Test
    public void torusTest() {
        int width = 6;
        int height = 5;
        
        SimpleGraph torus = makeTorus(width, height);
        System.out.println(torus);
        int diameter = Math.min(width, height);
        for (int h = 1; h <= diameter; h++) {
            SimpleVertexSignature sig0 = 
                new SimpleVertexSignature(0, h, torus);
            String zeroCanonical = sig0.toCanonicalString();
            System.out.println(h + "\t" + zeroCanonical);
            for (int i = 1; i < torus.getVertexCount(); i++) {
                SimpleVertexSignature sig = 
                    new SimpleVertexSignature(i, h, torus);
                Assert.assertEquals(zeroCanonical, sig.toCanonicalString());
            }
        }
    }

}
