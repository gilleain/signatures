package signature.edgecolored;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import signature.SymmetryClass;

public class BipartiteGraphTests {
    
    @SuppressWarnings("serial")
    public static final Map<String, Integer> colorMap = 
            new HashMap<String, Integer>() { {
                put("r", 1);
                put("b", 2);
            } 
    };
    
    public EdgeColoredGraph makeBipartiteGraph(int n, int m) {
        EdgeColoredGraph g = new EdgeColoredGraph();
        for (int i = 0; i < n; i++) {
            for (int j = n; j < n + m; j++) {
                if (i % 2 == j % 2) {
                    g.makeEdge(i, j, "r");
                } else {
                    g.makeEdge(i, j, "b");
                }
            }
        }
        return g;
    }
    
    public void printSym(List<SymmetryClass> symmetryClasses) {
        for (SymmetryClass symClass : symmetryClasses) {
            System.out.println(symClass);
        }
    }
    
    @Test
    public void bipOn3By4() {
        EdgeColoredGraph g = makeBipartiteGraph(3, 4);
        EdgeColoredGraphSignature sig = new EdgeColoredGraphSignature(g, colorMap);
        System.out.println(g);
        printSym(sig.getSymmetryClasses());
    }
    
    @Test
    public void bipOn4By4() {
        EdgeColoredGraph g = makeBipartiteGraph(4, 4);
        EdgeColoredGraphSignature sig = new EdgeColoredGraphSignature(g, colorMap);
        System.out.println(g);
        printSym(sig.getSymmetryClasses());
    }
    
}
