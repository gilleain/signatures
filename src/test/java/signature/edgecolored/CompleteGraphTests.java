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
    
    public EdgeColoredGraph makeCompleteGraphOnN(int n, int m) {
        EdgeColoredGraph g = new EdgeColoredGraph();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                String colorLabel = (i % m == j % m)? "r" : "b";
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
        EdgeColoredGraph k4 = makeCompleteGraphOnN(4, 2);
        EdgeColoredGraphSignature gs = new EdgeColoredGraphSignature(k4, colorMap);
        System.out.println(k4);
        printSym(gs.getSymmetryClasses());
    }
    
    @Test
    public void k5Test() {
        EdgeColoredGraph k5 = makeCompleteGraphOnN(5, 2);
        EdgeColoredGraphSignature gs = new EdgeColoredGraphSignature(k5, colorMap);
        System.out.println(k5);
        printSym(gs.getSymmetryClasses());
    }
    
    @Test
    public void k6Test() {
        EdgeColoredGraph k6 = makeCompleteGraphOnN(6, 2);
        EdgeColoredGraphSignature gs = new EdgeColoredGraphSignature(k6, colorMap);
        System.out.println(k6);
        printSym(gs.getSymmetryClasses());
    }
    
    @Test
    public void k7Test() {
        EdgeColoredGraph k7 = makeCompleteGraphOnN(7, 2);
        EdgeColoredGraphSignature gs = new EdgeColoredGraphSignature(k7, colorMap);
        System.out.println(k7);
        printSym(gs.getSymmetryClasses());
    }
    
    @Test
    public void k8Test() {
        EdgeColoredGraph k8 = makeCompleteGraphOnN(8, 2);
        EdgeColoredGraphSignature gs = new EdgeColoredGraphSignature(k8, colorMap);
        System.out.println(k8);
        printSym(gs.getSymmetryClasses());
    }
    
    @Test
    public void k9Test() {
        EdgeColoredGraph k9 = makeCompleteGraphOnN(9, 3);
        EdgeColoredGraphSignature gs = new EdgeColoredGraphSignature(k9, colorMap);
        System.out.println(k9);
        printSym(gs.getSymmetryClasses());
    }
    
    @Test
    public void k10Test() {
        EdgeColoredGraph k10 = makeCompleteGraphOnN(10, 2);
        EdgeColoredGraphSignature gs = new EdgeColoredGraphSignature(k10, colorMap);
        System.out.println(k10);
        printSym(gs.getSymmetryClasses());
    }
    
    @Test
    public void k11Test() {
        EdgeColoredGraph k11 = makeCompleteGraphOnN(11, 2);
        EdgeColoredGraphSignature gs = new EdgeColoredGraphSignature(k11, colorMap);
        System.out.println(k11);
        printSym(gs.getSymmetryClasses());
    }
    
    @Test
    public void k12Test() {
        EdgeColoredGraph k12 = makeCompleteGraphOnN(12, 4);
//        EdgeColoredGraphSignature gs = new EdgeColoredGraphSignature(k12, colorMap);
        System.out.println(k12);
//        printSym(gs.getSymmetryClasses());
    }
    
}
