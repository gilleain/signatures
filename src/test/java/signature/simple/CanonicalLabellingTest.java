package signature.simple;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import signature.AbstractVertexSignature;
import signature.DAGVisitor;
import signature.DAG.Node;
import signature.simple.SimpleGraph;
import signature.simple.SimpleGraphPermutor;
import signature.simple.SimpleGraphSignature;

public class CanonicalLabellingTest {
    
    public class CanonicalDAGVisitor implements DAGVisitor {

        public int[] labels;
        
        public int index;
        
        public CanonicalDAGVisitor(int n) {
            this.labels = new int[n];
            this.index = 0;
        }
        
        public void visit(Node node) {
            if (index < labels.length) {
                labels[index] = node.vertexIndex;
            }
//            System.out.println("visiting node " + node + " " + Arrays.toString(labels));
            index++;
            for (Node child : node.children) {
                child.accept(this);
            }
        }
        
    }

    @Test
    public void testSimpleGraphLabelling() {
        String graphString = "0:1,1:2";
        SimpleGraph graph = new SimpleGraph(graphString);
        SimpleGraphSignature signature = new SimpleGraphSignature(graph);
        List<Integer> labelling = signature.canonicalLabel();
        System.out.println(labelling);
    }
    
    public int[] getLabels(SimpleGraph graph) {
        SimpleGraphSignature signature = new SimpleGraphSignature(graph);
        AbstractVertexSignature avs0 = signature.signatureForVertex(0);
        int n = graph.getVertexCount();
        int[] labels = new int[n];
        for (int i = 0; i < n; i++) {
            labels[avs0.getOriginalVertexIndex(i)] = i;
        }
        return labels;
    }
    
    public AbstractVertexSignature getCanonicalSignature(SimpleGraphSignature sgs) {
        AbstractVertexSignature canonicalSignature = null;
        String canonicalString = null;
        for (int i = 0; i < sgs.getVertexCount(); i++) {
            AbstractVertexSignature signature = sgs.signatureForVertex(i);
            String sigString = signature.toCanonicalString();
            if (canonicalString == null || 
                    sigString.compareTo(canonicalString) < 0) {
                canonicalSignature = signature;
                canonicalString = sigString;
            }
        }
        return canonicalSignature;
    }
    
    public int[] getLabelsB(SimpleGraph graph) {
        SimpleGraphSignature signature = new SimpleGraphSignature(graph);
        AbstractVertexSignature canon = getCanonicalSignature(signature);
        int n = graph.getVertexCount();
        CanonicalDAGVisitor cdv = new CanonicalDAGVisitor(n);
        canon.accept(cdv);
        int[] labels = new int[n];
        for (int i = 0; i < n; i++) {
            labels[canon.getOriginalVertexIndex(cdv.labels[i])] = i;
        }
        return labels;
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
            System.out.println(
                Arrays.toString(labels) + " " + permutation + " " + relabelled);
            relabelledStrings.add(relabelled.toString());
        }
        
        // list the number of unique strings
        Iterator<String> values = relabelledStrings.iterator();
        for (int i = 0; i < relabelledStrings.size(); i++) {
            System.out.println(i + " " + values.next());
        }
    }
    
    public void permuteTestB(SimpleGraph graph) {
        System.out.println(Arrays.toString(getLabels(graph)) + " " + graph);
        SimpleGraphPermutor permutor = new SimpleGraphPermutor(graph);
        Set<String> relabelledStrings = new HashSet<String>();
        
        // permute, and relabel
        while (permutor.hasNext()) {
            SimpleGraph permutation = permutor.next();
            int[] labels = getLabelsB(permutation);
            SimpleGraph relabelled = new SimpleGraph(permutation, labels);
            System.out.println(
                Arrays.toString(labels) + " " + permutation + " " + relabelled);
            relabelledStrings.add(relabelled.toString());
        }
        
        // list the number of unique strings
        Iterator<String> values = relabelledStrings.iterator();
        for (int i = 0; i < relabelledStrings.size(); i++) {
            System.out.println(i + " " + values.next());
        }
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
        permuteTestB(new SimpleGraph("0:1,0:2,1:2,1:3,2:4,3:4"));
    }
    
    @Test
    public void threeThreeFusedCycle() {
        permuteTestB(new SimpleGraph("0:1,0:2,0:3,1:4,2:4,3:4"));
    }
    
}
