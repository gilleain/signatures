package signature;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A directed acyclic graph that is the core data structure of a signature. It
 * is the DAG that is canonized by sorting its layers of nodes.
 * 
 * @author maclean
 *
 */
public class DAG implements Iterable<List<DAG.Node>>{
    
    public enum Direction { UP, DOWN };
	
	/**
	 * A node of the directed acyclic graph
	 *
	 */
	public class Node {
		
		public int vertexIndex;
		
		public List<Node> parents;
		
		public List<Node> children;
		
		public Node(int vertexIndex) {
			this.vertexIndex = vertexIndex;
			this.parents = new ArrayList<Node>();
			this.children = new ArrayList<Node>();
		}
	
		public void addParent(Node node) {
			this.parents.add(node);
		}
		
		public void addChild(Node node) {
			this.children.add(node);
		}
		
		public String toString() {
		    StringBuffer parentString = new StringBuffer();
		    parentString.append('[');
		    for (Node parent : this.parents) {
		        parentString.append(parent.vertexIndex).append(',');
		    }
		    if (parentString.length() > 1) {
		        parentString.setCharAt(parentString.length() - 1, ']');
		    } else {
		        parentString.append(']');
		    }
		    StringBuffer childString = new StringBuffer();
		    childString.append('[');
            for (Node child : this.children) {
                childString.append(child.vertexIndex).append(',');
            }
            if (childString.length() > 1) {
                childString.setCharAt(childString.length() - 1, ']');    
            } else {
                childString.append(']');
            }
            
            return vertexIndex + " (" + parentString + ", " + childString + ")";
		}
	}
	
	/**
	 * An arc of the directed acyclic graph
	 *
	 */
	public class Arc {
		
		public int a;
		
		public int b;
		
		public Arc(int a, int b) {
			this.a = a;
			this.b = b;
		}
		
		public boolean equals(Object other) {
			if (other instanceof Arc) {
				Arc o = (Arc) other;
				return (this.a == o.a && this.b == o.b)
					|| (this.a == o.b && this.b == o.a);
			} else {
				return false;
			}
		}
	}
	
	/**
	 * The layers of the DAG
	 */
	private List<List<Node>> layers;
	
	/**
	 * The colors assigned to vertices of the input graph
	 */
	private int[] colors;
	
	/**
	 * The invariants of the vertices of the input graph
	 */
	private int[] vertexInvariants;
	
	/**
	 * The invariants of the nodes of the DAG
	 */
	private int[] nodeInvariants;
	
	/**
	 * The counts of parents for nodes  
	 */
	private int[] parentCounts;
	
	private Invariants invariants;
	
	/**
	 * Convenience reference to the nodes of the DAG
	 */
	private List<DAG.Node> nodes;
	
    /**
     * Create a DAG from a graph, starting at the root vertex.
     * @param rootVertex the vertex to start from
     */
	public DAG(int rootVertexIndex) {
		this.layers = new ArrayList<List<Node>>();
		List<Node> rootLayer = new ArrayList<Node>();
		rootLayer.add(new Node(rootVertexIndex));
		this.layers.add(rootLayer);
	}
	
	public Iterator<List<Node>> iterator() {
		return layers.iterator();
	}
	
	public List<DAG.Node> getRootLayer() {
	    return this.layers.get(0);
	}
	
	public DAG.Node getRoot() {
		return this.layers.get(0).get(0);
	}
	
	public Invariants copyInvariants() {
	    return (Invariants) this.invariants.clone();
	}
	
	public void initialize(int vertexCount) {
	    this.colors = new int[vertexCount];
	    this.vertexInvariants = new int[vertexCount];
	    this.nodeInvariants = new int[this.nodes.size()];
	    this.invariants = new Invariants(vertexCount, this.nodes.size());
	    this.parentCounts = new int[vertexCount];
	}
	
	public void setColor(int vertexIndex, int color) {
	    this.invariants.colors[vertexIndex] = color;
	}
	
	public void setInvariants(Invariants invariants) {
	    this.invariants = invariants;
	}
	
	public DAG.Node makeNode(int vertexIndex) {
	    DAG.Node node = new DAG.Node(vertexIndex);
	    this.nodes.add(node);
	    return node;
	}
	
	public void addParent(DAG.Node node, DAG.Node parent) {
	    node.parents.add(parent);
	    this.parentCounts[node.vertexIndex]++;
	}
	
	public int colorFor(int vertexIndex) {
		return this.colors[vertexIndex];
	}

	public void addLayer(List<Node> layer) {
		this.layers.add(layer);
	}
	
	public void initializeVertexInvariants() {
        // TODO
	}
	
	public int[] createOrbit() {
	    return null;   // TODO
	}
	
	public void computeVertexInvariants() {
        // TODO    
	}
	
	public void updateVertexInvariants() {
        // TODO    
	}
	
	public void updateVertexInvariants(DAG.Direction direction) {
	    if (direction == Direction.UP) {
            // TODO	        
	    } else {
	        // TODO
	    }
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (List<Node> layer : this) {
			buffer.append(layer);
			buffer.append("\n");
		}
		return buffer.toString();
	}
}
