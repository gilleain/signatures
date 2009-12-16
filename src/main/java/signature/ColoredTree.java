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
    
    public class Node {
        
        public List<Node> children = new ArrayList<Node>();
        
        public String label;
        
        public Node parent;
        
        public int color;
        
        public int height;
        
        public Node(String label, Node parent, int height, int color) {
            this.label = label;
            this.parent = parent;
            this.color = color;
            if (parent != null) {
                parent.children.add(this);
            }
        }
        
        public boolean isColored() {
            return this.color != 0;
        }
    }
    
    private int height;
    
    private Node root;
    
    public ColoredTree(String rootLabel) {
        this.root = new Node(rootLabel, null, 1, 0);
        this.height = 1;
    }
    
    public Node getRoot() {
        return this.root;
    }
    
    public Node makeNode(String label, Node parent, int height, int color) {
        return new Node(label, parent, height, color);
    }
    
    public void updateHeight(int height) {
        if (height > this.height) {
            this.height = height;
        }
    }

}
