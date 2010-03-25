package signature.display;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;

import junit.framework.Assert;

import org.junit.Test;


import signature.ColoredTree;
import signature.chemistry.AtomSignature;

public class TestColoredTreePanel {
    
    @Test
    public void dummyTest() {
        Assert.assertTrue(true);
    }
    
    public static ColoredTree makeColoredTree(String signatureString) {
        return AtomSignature.parse(signatureString);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        JFrame f = new JFrame();
        int width = 1200;
        int height = 350;
        f.setLayout(new GridLayout(2, 1));
        String sigA = "[C]([C]([C,2][C]([C,1]([C,3])[C,4]([C,5])))[C]([C]([C,4]"
        		    + "[C,3])[C]([C,1][C,5]))[C,2]([C]([C,5][C,3])))";
        String sigB = "[C]([C]([C]([C,1]([C,2])[C,3])[C]([C,4][C,5]))[C]([C,4]"
        		    + "([C,2])[C,3]([C,5]))[C]([C]([C,1][C,2])[C,5]))";
        ColoredTree a = TestColoredTreePanel.makeColoredTree(sigA);
        ColoredTree b = TestColoredTreePanel.makeColoredTree(sigB);
        f.add(new ColoredTreePanel(a, width, height));
        f.add(new ColoredTreePanel(b, width, height));
        f.setPreferredSize(new Dimension(width,2*height));
        f.pack();
        f.setVisible(true);
    }

}
