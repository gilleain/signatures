package signature.display;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import signature.ColoredTree;

public class ColoredTreePanel extends JPanel {
    
    public int maxDepth;
    
    private DisplayableColoredTree displayTree;
    
    public ColoredTreePanel(int width, int height) {
        this.setPreferredSize(new Dimension(width, height));
        this.displayTree = new DisplayableColoredTree(width, height);
    }
    
    public ColoredTreePanel(ColoredTree tree, int width, int height) {
        displayTree = new DisplayableColoredTree(tree, width, height);
        this.setPreferredSize(new Dimension(width, height));
    }
    
    public void setTree(ColoredTree tree) {
        System.out.println("setting tree " + tree);
        this.displayTree.makeFromColoredTree(tree);
        this.maxDepth = tree.getHeight();
    }
    
    public void paint(Graphics g) {
        this.displayTree.paint(g);
    }

}
