package signature;

import org.junit.Assert;
import org.junit.Test;

public class ParsingTest {
    
    @Test
    public void basicParseTest() {
        String sig = "[A]([B])";
        ColoredTree tree = AbstractVertexSignature.parse(sig);
        Assert.assertEquals(sig, tree.toString());
    }
    
    @Test
    public void multipleChildrenParseTest() {
        String sig = "[A]([B1][B2][B3])";
        ColoredTree tree = AbstractVertexSignature.parse(sig);
        Assert.assertEquals(sig, tree.toString());
    }
    
    @Test
    public void multipleLevelsParseTest() {
        String sig = "[A]([B1]([C])[B2])";
        ColoredTree tree = AbstractVertexSignature.parse(sig);
        Assert.assertEquals(sig, tree.toString());
    }
    
    @Test
    public void edgeLabelParseTest() {
        String sig = "[A](=[B])";
        ColoredTree tree = AbstractVertexSignature.parse(sig);
        System.out.println(tree.toString());
        Assert.assertEquals(sig, tree.toString());
    }
    
    @Test
    public void edgeLabelMultipleChildrenParseTest() {
        String sig = "[A](=[B1]=[B2])";
        ColoredTree tree = AbstractVertexSignature.parse(sig);
        System.out.println(tree.toString());
        Assert.assertEquals(sig, tree.toString());
    }
    
    @Test
    public void edgeLabelMultipleLevelsParseTest() {
        String sig = "[A](=[B1]([C])=[B2])";
        ColoredTree tree = AbstractVertexSignature.parse(sig);
        System.out.println(tree.toString());
        Assert.assertEquals(sig, tree.toString());
    }

}
