package signature.edgecolored;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class BasicTests {
    
    @SuppressWarnings("serial")
    public static final Map<String, Integer> colorMap = 
            new HashMap<String, Integer>() { {
                put("a", 1);
                put("b", 1);
            } 
    };
    
    @Test
    public void twoColoredCycle() {
        EdgeColoredGraph g = new EdgeColoredGraph();
        g.makeEdge(0, 1, "a");
        g.makeEdge(1, 2, "b");
        g.makeEdge(2, 3, "a");
        g.makeEdge(3, 0, "b");
        EdgeColoredGraphSignature graphSignature = new EdgeColoredGraphSignature(g, colorMap);
        for (int i = 0; i < g.getVertexCount(); i++) {
            System.out.println(graphSignature.signatureStringForVertex(i));
        }
    }
    
}
