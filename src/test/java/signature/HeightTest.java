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
        int size = width * height;
        
        // connect the top edge to the bottom
        for (int i = 0; i < width; i++) {
            graph.makeEdge(i, size - width + i);
        }
        
        // connect the left edge to the right
        for (int j = 0; j < height; j++) {
            int x = width * j;
            int y = x + width;
            graph.makeEdge(x, y);
        }
        
        return graph;
    }
    
    @Test
    public void torusTest() {
        int width = 4;
        int height = 3;
        
        SimpleGraph torus = makeTorus(width, height);
        int diameter = Math.min(width, height);
        for (int h = 1; h <= diameter + 1; h++) {
            SimpleVertexSignature sig0 = 
                new SimpleVertexSignature(0, h, torus);
            String zeroCanonical = sig0.toCanonicalString();
            for (int i = 1; i < torus.getVertexCount(); i++) {
                SimpleVertexSignature sig = 
                    new SimpleVertexSignature(i, h, torus);
                Assert.assertEquals(zeroCanonical, sig.toCanonicalString());
            }
        }
    }

}
