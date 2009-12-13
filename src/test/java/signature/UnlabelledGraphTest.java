package signature;

import org.junit.Test;

import signature.simple.SimpleGraph;
import signature.simple.SimpleGraphSignature;

public class UnlabelledGraphTest {
    
    public SimpleGraphSignature signatureFromString(String string) {
        SimpleGraph graph = new SimpleGraph(string);
        return new SimpleGraphSignature(graph, 0); 
    }
	
    @Test 
    public void testChain() {
        String chain = "0:1,1:2,2:3,3:4";
        SimpleGraphSignature signature = signatureFromString(chain);
        System.out.println(signature.getDAG());
        System.out.println(signature.toString());
        StringBuffer maxSignature = new StringBuffer();
        signature.canonize(0, maxSignature);
        System.out.println("max" + maxSignature);
    }
	
}
