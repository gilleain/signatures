package signature.simple;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import signature.SymmetryClass;

public class CanonicalLabellingTest {
    
    public int[] getLabels(SimpleGraph graph) {
        SimpleGraphSignature signature = new SimpleGraphSignature(graph);
        return signature.getCanonicalLabels();
    }
    
    public void permuteTest(SimpleGraph graph) {
        System.out.println(Arrays.toString(getLabels(graph)) + " " + graph);
        SimpleGraphPermutor permutor = new SimpleGraphPermutor(graph);
        Set<String> relabelledStrings = new HashSet<String>();
        
        // permute, and relabel
        while (permutor.hasNext()) {
            SimpleGraph permutation = permutor.next();
            int[] labels = getLabels(permutation);
            SimpleGraph relabelled = new SimpleGraph(permutation, labels);
            boolean isIdentity = isIdentity(labels);
            System.out.println(
                Arrays.toString(labels) + " " + permutation + " " + relabelled 
                + " " + isIdentity);
            relabelledStrings.add(relabelled.toString());
        }
        Assert.assertEquals(1, relabelledStrings.size());
        
        // list the number of unique strings
        Iterator<String> values = relabelledStrings.iterator();
        for (int i = 0; i < relabelledStrings.size(); i++) {
            System.out.println(i + " " + values.next());
        }
    }
    
    public boolean isIdentity(int[] permutation) {
        if (permutation.length < 1) return true;
        int prev = permutation[0];
        for (int i = 1; i < permutation.length; i++) {
            if (permutation[i] < prev) return false;
            prev = permutation[i];
        }
        return true;
    }
    
    @Test
    public void testSimpleGraphLabelling() {
        String graphString = "0:1,1:2";
        SimpleGraph graph = new SimpleGraph(graphString);
        SimpleGraphSignature signature = new SimpleGraphSignature(graph);
        int[] labels = signature.getCanonicalLabels();
        Assert.assertTrue(isIdentity(labels));
    }

    @Test
    public void fourCycleTest() {
        permuteTest(new SimpleGraph("0:1,0:3,1:2,2:3"));
    }
    
    @Test
    public void fiveCycleTest() {
        permuteTest(new SimpleGraph("0:1,0:4,1:2,2:3,3:4"));
    }
    
    @Test
    public void threeFourFusedCycle() {
        permuteTest(new SimpleGraph("0:1,0:2,1:2,1:3,2:4,3:4"));
    }
    
    @Test
    public void threeThreeFusedCycle() {
        permuteTest(new SimpleGraph("0:1,0:2,0:3,1:4,2:4,3:4"));
    }
    
    @Test
    public void largePermuteTestA() {
        permuteTest(new SimpleGraph("5:7,6:7,0:6,1:6,2:5,3:5,0:4,1:4,2:4,3:4,0:3,2:3,0:1,1:2"));
    }
    
    @Test
    public void largePermuteTestB() {
        permuteTest(new SimpleGraph("5:7,6:7,0:6,2:6,1:5,3:5,0:4,1:4,2:4,3:4,0:3,1:3,0:2,1:2"));
    }
    
    @Test
    public void tmpOrbitsTest() {
        SimpleGraph a = new SimpleGraph("5:7,6:7,0:6,1:6,2:5,3:5,0:4,1:4,2:4,3:4,0:3,2:3,0:1,1:2");
//        SimpleGraph b = new SimpleGraph("5:7,6:7,0:6,2:6,1:5,3:5,0:4,1:4,2:4,3:4,0:3,1:3,0:2,1:2");
        SimpleGraphSignature sigA = new SimpleGraphSignature(a);
        for (SymmetryClass cls : sigA.getSymmetryClasses()) {
            System.out.println(cls);
        }
    }
    
    @Test
    public void isomorphicPair() {
        SimpleGraph a = new SimpleGraph("5:7,6:7,0:6,1:6,2:5,3:5,0:4,1:4,2:4,3:4,0:3,2:3,0:1,1:2");
        SimpleGraph b = new SimpleGraph("5:7,6:7,0:6,2:6,1:5,3:5,0:4,1:4,2:4,3:4,0:3,1:3,0:2,1:2");
        
        SimpleGraphSignature sigA = new SimpleGraphSignature(a);
        SimpleGraphSignature sigB = new SimpleGraphSignature(b);
        Assert.assertEquals(sigA.toCanonicalString(), sigB.toCanonicalString());
    }
    
}
