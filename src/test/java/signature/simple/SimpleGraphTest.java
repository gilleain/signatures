package signature.simple;

import junit.framework.Assert;

import org.junit.Test;

import signature.ColoredTree;
import signature.simple.SimpleGraph;
import signature.simple.SimpleGraphBuilder;
import signature.simple.SimpleGraphSignature;
import signature.simple.SimpleVertexSignature;

public class SimpleGraphTest extends AbstractSimpleGraphTest {
    
    public SimpleGraphSignature signatureFromString(String string) {
        SimpleGraph graph = new SimpleGraph(string);
        return new SimpleGraphSignature(graph); 
    }
	
    @Test 
    public void testChain() {
        String chain = "0:1,1:2,2:3,3:4";
        SimpleGraphSignature signature = signatureFromString(chain);
        String uncanonizedString = signature.toCanonicalString(); 
        String maxSignature = signature.getGraphSignature();
        System.out.println("max" + maxSignature);
        Assert.assertEquals(uncanonizedString, maxSignature);
    }
    
    @Test
    public void testColoredTreeRoundtrip() {
        String signatureString = "[.]([.]([.,1])[.]([.,1]))";
        ColoredTree tree = SimpleVertexSignature.parse(signatureString);
        Assert.assertEquals(signatureString, tree.toString());
        
        SimpleGraphBuilder builder = new SimpleGraphBuilder();
        SimpleGraph graph = builder.fromTree(tree);
        System.out.println(graph + "\n" + tree);
    }
    
    @Test
    public void testVertexCount() {
        
    }
    
    @Test
    public void signatureHeightTest() {
        SimpleGraph g = AbstractSimpleGraphTest.makeCuneane();
        SimpleGraphSignature signature = new SimpleGraphSignature(g);
        for (int h = 1; h < g.getVertexCount(); h++) {
            for (int i = 0; i < g.getVertexCount(); i++) {
                String sig = signature.signatureStringForVertex(i, h);
                System.out.println(h + "\t" + i + "\t" + sig);
            }
        }
    }
	
}
