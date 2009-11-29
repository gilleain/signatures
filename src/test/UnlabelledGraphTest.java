package test;

import org.junit.Test;

import signature.implementation.unlabelled.UnlabelledGraph;
import signature.implementation.unlabelled.UnlabelledGraphSignature;

public class UnlabelledGraphTest {
    
    public UnlabelledGraphSignature signatureFromString(String string) {
        UnlabelledGraph graph = new UnlabelledGraph(string);
        return new UnlabelledGraphSignature(graph, 0); 
    }
	
    @Test 
    public void testChain() {
        String chain = "0:1,1:2,2:3,3:4";
        UnlabelledGraphSignature signature = signatureFromString(chain);
        System.out.println(signature.getDAG());
        System.out.println(signature.toString());
        StringBuffer maxSignature = new StringBuffer();
        signature.canonize(0, maxSignature);
        System.out.println("max" + maxSignature);
    }
	
}
