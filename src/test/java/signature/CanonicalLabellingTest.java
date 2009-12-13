package signature;

import java.util.Arrays;

import org.junit.Test;

import signature.simple.SimpleGraph;
import signature.simple.SimpleGraphSignature;

public class CanonicalLabellingTest {

    @Test
    public void testSimpleGraphLabelling() {
        String graphString = "0:1,1:2";
        SimpleGraph graph = new SimpleGraph(graphString);
        SimpleGraphSignature signature = 
            new SimpleGraphSignature(graph, 1);
        int[] labelling = signature.getCanonicalLabelling();
        System.out.println(Arrays.toString(labelling));
    }
}
