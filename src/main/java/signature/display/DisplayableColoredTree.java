package signature.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import signature.ColoredTree;

public class DisplayableColoredTree {
    
    public int maxDepth;
    
    private DrawNode root;
    
    private Map<Integer, Color> colorMap;
    
    private int width;
    
    private int height;
    
    private boolean drawKey;
    
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
        
        public String edgeLabel;
        
        public DrawNode(String label, DrawNode parent, int d, int color) {
            this.label = label;
            this.parent = parent;
            this.depth = d;
            this.color = color;
            edgeLabel = "";
        }
        
        public DrawNode(
            String label, DrawNode parent, int d, int color, String edgeLabel) {
            this(label, parent, d, color);
            this.edgeLabel = edgeLabel;
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
    
    public DisplayableColoredTree(int width, int height) {
        this.root = null;
        this.width = width;
        this.height = height;
        this.drawKey = false;
    }
    
    public DisplayableColoredTree(ColoredTree tree, int width, int height) {
        this.width = width;
        this.height = height;
        makeFromColoredTree(tree);
        colorMap = makeColorMap(tree.numberOfColors());
        this.drawKey = false;
    }
    
    public void setDrawKey(boolean drawKey) {
        this.drawKey = drawKey;
    }
    
    private Map<Integer, Color> makeColorMap(int max) {
        Map<Integer, Color> colorMap = new HashMap<Integer, Color>();
        for (int i = 0; i <= max; i++) {
            colorMap.put(i, colourRamp(i, 0, max));
        }
        return colorMap;
    }
    
    public void makeFromColoredTree(ColoredTree tree) {
        this.root = treeToTree(tree);
        this.maxDepth = tree.getHeight();
        this.colorMap = makeColorMap(tree.numberOfColors());
    }
    
    private DrawNode treeToTree(ColoredTree tree) {
        return treeToTree(tree.getRoot(), null);
    }
    
    private DrawNode treeToTree(ColoredTree.Node treeNode, DrawNode parent) {
        DrawNode node;
        if (treeNode.edgeLabel == "") {
            node = new DrawNode(
                    treeNode.label, parent, treeNode.height, treeNode.color);
        } else {
            node = new DrawNode(
                    treeNode.label, parent, 
                    treeNode.height, treeNode.color, treeNode.edgeLabel);
        }
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
        if (drawKey) {
            paintKey(g);
        }
        new TreeLayout().layoutTree(root, this.width, this.height);
        g.setColor(Color.BLACK);
        paint(g, root);
    }
    
    public void paintKey(Graphics g) {
        int xoffset = 10;
        int boxsize = 25;
        int y = 5;
        
        int colors = colorMap.size();
        int x = xoffset;
        for (int i = 0; i < colors; i++) {
            Color color = colorMap.get(i);
            g.setColor(color);
            g.fillRect(x, y, boxsize, boxsize);
            g.setColor(Color.BLACK);
            g.drawString(i + "", x + (boxsize / 3), y + (boxsize / 2));
            x += boxsize;
        }
    }
    
    public void paint(Graphics g, DrawNode node) {
        for (DrawNode child : node.children) {
            g.drawLine(node.x, node.y, child.x, child.y);
            paint(g, child);
        }
        String label = node.edgeLabel + node.label;
        Rectangle2D r = g.getFontMetrics().getStringBounds(label, g);
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
        g.drawString(label, textX, textY);
    }
    
    private Color colourRamp(int v, int vmin, int vmax) {
        double r = 1.0;
        double g = 1.0;
        double b = 1.0;
        if (v < vmin) { v = vmin; }
        if (v > vmax) { v = vmax; }
        int dv = vmax - vmin;

        try  {
            if (v < (vmin + 0.25 * dv)) {
                r = 0.0;
                g = 4.0 * (v - vmin) / dv;
            } else if (v < (vmin + 0.5 * dv)) {
                r = 0.0;
                b = 1.0 + 4.0 * (vmin + 0.25 * dv - v) / dv;
            } else if (v < (vmin + 0.75 * dv)) {
                r = 4.0 * (v - vmin - 0.5  * dv) / dv;
                b = 0.0;
            } else {
                g = 1.0 + 4.0 * (vmin + 0.75 * dv - v) / dv;
                b = 0.0;
            }
            float[] hsb = Color.RGBtoHSB(
                    (int)(r * 255), (int)(g * 255), (int)(b * 255), null);
            return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
        } catch (ArithmeticException zde) {
            float[] hsb = Color.RGBtoHSB(0, 0, 0, null);
            return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
        }

    }

}
