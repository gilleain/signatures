package test;

import java.util.Arrays;

import org.junit.Test;

import signature.implementation.unlabelled.UnlabelledGraph;
import signature.implementation.unlabelled.UnlabelledGraphSignature;

public class CanonicalLabellingTest {

    @Test
    public void testSimpleGraphLabelling() {
        String graphString = "0:1,1:2";
        UnlabelledGraph graph = new UnlabelledGraph(graphString);
        UnlabelledGraphSignature signature = 
            new UnlabelledGraphSignature(graph, 1);
        int[] labelling = signature.getCanonicalLabelling();
        System.out.println(Arrays.toString(labelling));
    }
}
