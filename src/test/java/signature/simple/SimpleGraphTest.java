package signature.simple;

import junit.framework.Assert;

import org.junit.Test;

import signature.ColoredTree;
import signature.simple.SimpleGraph;
import signature.simple.SimpleGraphBuilder;
import signature.simple.SimpleGraphSignature;
import signature.simple.SimpleVertexSignature;

public class SimpleGraphTest {
    
    public SimpleGraphSignature signatureFromString(String string) {
        SimpleGraph graph = new SimpleGraph(string);
        return new SimpleGraphSignature(graph); 
    }
	
    @Test 
    public void testChain() {
        String chain = "0:1,1:2,2:3,3:4";
        SimpleGraphSignature signature = signatureFromString(chain);
        
        String uncanonizedString = signature.toCanonicalString(); 
        // TODO : FIXME - maximal / minimal problem
//        String maxSignature = signature.getGraphSignature();
        String maxSignature = signature.getMaximalSignature();
        
        Assert.assertEquals(uncanonizedString, maxSignature);
    }
    
    @Test
    public void testColoredTreeRoundtrip() {
        String signatureString = "[.]([.]([.,0])[.]([.,0]))";
        ColoredTree tree = SimpleVertexSignature.parse(signatureString);
        Assert.assertEquals(signatureString, tree.toString());
        
        SimpleGraphBuilder builder = new SimpleGraphBuilder();
        SimpleGraph graph = builder.fromTree(tree);
        SimpleGraphSignature graphSignature = new SimpleGraphSignature(graph);
        String canonicalString = graphSignature.toCanonicalString();
        Assert.assertEquals(signatureString, canonicalString);
    }
    
    @Test
    public void testVertexCount() {
        
    }
    
    @Test
    public void signatureHeightTest() {
        SimpleGraph g = SimpleGraphFactory.makeCuneane();
        SimpleGraphSignature signature = new SimpleGraphSignature(g);
        for (int h = 1; h < g.getVertexCount(); h++) {
            for (int i = 0; i < g.getVertexCount(); i++) {
                String sig = signature.signatureStringForVertex(i, h);
                System.out.println(h + "\t" + i + "\t" + sig);
            }
        }
    }
	
}
