package signature;

import java.util.ArrayList;
import java.util.List;


/**
 * A signature string is read back in as a colored tree, not a DAG since some 
 * of the information in the DAG is lost when printing out. A colored tree can
 * be reconstructed into a graph.
 * 
 * @author maclean
 *
 */
public class ColoredTree {
    
    public class Node implements VisitableColoredTree {
        
        public List<Node> children = new ArrayList<Node>();
        
        public final String label;
        
        public final String edgeLabel;
        
        public final Node parent;
        
        public final int color;
        
        public final int height;
        
        public Node(String label, Node parent, int height) {
            this.label = label;
            this.parent = parent;
            this.color = -1;
            this.height = height;
            this.edgeLabel = "";
            if (parent != null) {
                parent.children.add(this);
            }
        }
        
        public Node(String label, Node parent, int height, int color) {
            this.label = label;
            this.parent = parent;
            this.color = color;
            this.height = height;
            this.edgeLabel = "";
            if (parent != null) {
                parent.children.add(this);
            }
        }
        
        public Node(String label, Node parent, 
                int height, int color, String edgeLabel) {
            this.label = label;
            this.parent = parent;
            this.color = color;
            this.height = height;
            this.edgeLabel = edgeLabel;
            if (parent != null) {
                parent.children.add(this);
            }
        }
        
        public void accept(ColoredTreeVisitor visitor) {
            visitor.visit(this);
            for (Node child : this.children) {
                child.accept(visitor);
            }
        }
        
        public boolean isColored() {
            return this.color != -1;
        }
        
        public void buildString(StringBuilder builder) {
            if (this.isColored()) {
                builder.append(this.edgeLabel);
                builder.append("[").append(this.label);
                builder.append(",").append(this.color).append("]");
            } else {
                builder.append(this.edgeLabel);
                builder.append("[").append(this.label).append("]");
            }
            if (this.children.size() > 0) { builder.append("("); }
            for (Node child : this.children) {
                child.buildString(builder);
            }
            if (this.children.size() > 0) { builder.append(")"); }
        }
        
        public String toString() {
            StringBuilder builder = new StringBuilder();
            this.buildString(builder);
            return builder.toString();
        }
    }
    
    private int height;
    
    private Node root;
    
    private int maxColor;
    
    public ColoredTree(String rootLabel) {
        this.root = new Node(rootLabel, null, 1);
        this.height = 1;
    }
    
    public void accept(ColoredTreeVisitor visitor) {
        this.root.accept(visitor);
    }
    
    public Node getRoot() {
        return this.root;
    }
    
    public Node makeNode(String label, Node parent, int currentHeight, int color) {
        if (color > maxColor) {
            maxColor = color;
        }
        return new Node(label, parent, currentHeight, color);
    }
    
    public Node makeNode(String label, Node parent, int currentHeight, 
            int color, String edgeSymbol) {
        if (color > maxColor) {
            maxColor = color;
        }
        return new Node(label, parent, currentHeight, color, edgeSymbol);
    }

    public void updateHeight(int height) {
        if (height > this.height) {
            this.height = height;
        }
    }
    
    public String toString() {
        StringBuilder builder = new StringBuilder();
        this.root.buildString(builder);
        return builder.toString();
    }

    public int getHeight() {
        return this.height;
    }

    public int numberOfColors() {
        return maxColor;
    }
}
