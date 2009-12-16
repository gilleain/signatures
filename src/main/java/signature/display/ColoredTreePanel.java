package signature.display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import signature.ColoredTree;

public class ColoredTreePanel extends JPanel {
    
    public int maxDepth;
    
    public class TreeLayout {
        
        public int totalLeafCount = 0;
        
        public int xSep;
        
        public int ySep;
        
        public void layoutTree(DrawNode root, int width, int height) {
            int leafCount = root.countLeaves();
            this.xSep = width / (leafCount + 1);
            this.ySep = height / (maxDepth + 1);
            layout(root);
        }
        
        public int layout(DrawNode node) {
            node.y  = node.depth * ySep;
            if (node.isLeaf()) {
                totalLeafCount += 1;
                node.x = totalLeafCount * xSep;
                return node.x;
            } else {
                int min = 0;
                int max = 0;
                for (DrawNode child : node.children) {
                    int childCenter = layout(child);
                    if (min == 0) {
                        min = childCenter;
                    }
                    max = childCenter;
                }
                if (min == max) {
                    node.x = min;
                } else {
                    node.x = min + (max - min) / 2;
                }
                return node.x;
            }
        }
        
    }
    
    public class DrawNode {
        
        public int x = -1;
        
        public int y = -1;
        
        public int depth;
        
        public int color;
        
        public List<DrawNode> children = new ArrayList<DrawNode>();
        
        public String label;
        
        public DrawNode parent;
        
        public DrawNode(String label, DrawNode parent, int d, int color) {
            this.label = label;
            this.parent = parent;
            this.depth = d;
            this.color = color;
        }
        
        public int countLeaves() {
            if (this.isLeaf()) {
                return 1;
            } else {
                int c = 0;
                for (DrawNode child : this.children) {
                    c += child.countLeaves();
                }
                return c;
            }
        }
        
        public boolean isLeaf() {
            return this.children.size() == 0;
        }
        
        public String toString() {
            StringBuffer buffer = new StringBuffer();
            this.toString(buffer);
            return buffer.toString();
        }
        
        private void toString(StringBuffer buffer) {
            buffer.append(label);
            buffer.append('[').append(x).append(',').append(y).append(']');
            for (DrawNode child : this.children) {
                child.toString(buffer);
            }
        }
    }
    
    private DrawNode root;
    
    private int width;
    
    private int height;
    
    private Map<Integer, Color> colorMap;
    
    public ColoredTreePanel(ColoredTree tree, int width, int height) {
        this.root = treeToTree(tree);
        this.maxDepth = tree.getHeight();
        this.width = width;
        this.height = height;
        this.setPreferredSize(new Dimension(width, height));
        this.colorMap = makeColorMap();
    }
    
    private Map<Integer, Color> makeColorMap() {
        Map<Integer, Color> colorMap = new HashMap<Integer, Color>();
        colorMap.put(1, Color.RED);
        colorMap.put(2, Color.PINK);
        colorMap.put(3, Color.ORANGE);
        colorMap.put(4, Color.YELLOW);
        colorMap.put(5, Color.GREEN);
        colorMap.put(6, Color.BLUE);
        colorMap.put(7, Color.MAGENTA);
        return colorMap;
    }

    private DrawNode treeToTree(ColoredTree tree) {
        return treeToTree(tree.getRoot(), null);
    }
    
    private DrawNode treeToTree(ColoredTree.Node treeNode, DrawNode parent) {
        DrawNode node = new DrawNode(
                treeNode.label, parent, treeNode.height, treeNode.color);
        if (parent != null) parent.children.add(node);
        node.parent = parent;
        for (ColoredTree.Node child : treeNode.children) {
            treeToTree(child, node);
        }
        return node;
    }
    
    public void paint(Graphics g) {
        if (root == null) return;
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        new TreeLayout().layoutTree(root, this.width, this.height);
        g.setColor(Color.BLACK);
        paint(g, root);
    }
    
    public void paint(Graphics g, DrawNode node) {
        for (DrawNode child : node.children) {
            g.drawLine(node.x, node.y, child.x, child.y);
            paint(g, child);
        }
        Rectangle2D r = g.getFontMetrics().getStringBounds(node.label, g);
        int rw = (int)r.getWidth();
        int rh = (int)r.getHeight();
        int textX = node.x - (rw / 2);
        int textY = node.y + (rh / 2);
        int border = 3;
        int boundX = textX - border;
        int boundY = node.y - (rh / 2) - border;
        int boundW = rw + (2 * border);
        int boundH = rh + (2 * border);
        if (colorMap.containsKey(node.color)) {
            g.setColor(this.colorMap.get(node.color));
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillRect(boundX, boundY, boundW, boundH);
        g.setColor(Color.BLACK);
        g.drawRect(boundX, boundY, boundW, boundH);
        g.drawString(node.label, textX, textY);
    }
}
