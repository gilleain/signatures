package signature.edgecolored;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import signature.SymmetryClass;

public class CompleteGraphTests {
    
    @SuppressWarnings("serial")
    public static final Map<String, Integer> colorMap = 
            new HashMap<String, Integer>() { {
                put("r", 1);
                put("b", 2);
            } 
    };
    
    public EdgeColoredGraph makeCompleteGraphOnN(int n) {
        EdgeColoredGraph g = new EdgeColoredGraph();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                String colorLabel = (i % 2 == j % 2)? "r" : "b";
                g.makeEdge(i, j, colorLabel); 
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
    public void k4Test() {
        EdgeColoredGraph k4 = makeCompleteGraphOnN(4);
        EdgeColoredGraphSignature gs = new EdgeColoredGraphSignature(k4, colorMap);
        System.out.println(k4);
        printSym(gs.getSymmetryClasses());
    }
    
    @Test
    public void k5Test() {
        EdgeColoredGraph k5 = makeCompleteGraphOnN(5);
        EdgeColoredGraphSignature gs = new EdgeColoredGraphSignature(k5, colorMap);
        System.out.println(k5);
        printSym(gs.getSymmetryClasses());
    }
    
    @Test
    public void k6Test() {
        EdgeColoredGraph k6 = makeCompleteGraphOnN(6);
        EdgeColoredGraphSignature gs = new EdgeColoredGraphSignature(k6, colorMap);
        System.out.println(k6);
        printSym(gs.getSymmetryClasses());
    }
    
    @Test
    public void k7Test() {
        EdgeColoredGraph k7 = makeCompleteGraphOnN(7);
        EdgeColoredGraphSignature gs = new EdgeColoredGraphSignature(k7, colorMap);
        System.out.println(k7);
        printSym(gs.getSymmetryClasses());
    }
    
}
