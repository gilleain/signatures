package signature;

import java.util.List;

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
        List<Integer> labelling = signature.canonicalLabel();
        System.out.println(labelling);
    }
}
