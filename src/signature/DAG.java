package signature;

import java.util.ArrayList;
import java.util.Collections;
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
		
		public int layer;
		
		public String label; // We use a label for the nodes to handle instances where we use bond orders. 
		
		public Node(int vertexIndex, int layer, String label) {
			this.vertexIndex = vertexIndex;
			this.layer = layer;
			this.parents = new ArrayList<Node>();
			this.children = new ArrayList<Node>();
			this.label = label;
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
            
            return vertexIndex + " " + label + " (" + parentString + ", " + childString + ")";
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
	 * The counts of parents for nodes  
	 */
	private int[] parentCounts;
	
	private String[] labels;
	
	private Invariants invariants;
	
	/**
	 * Convenience reference to the nodes of the DAG
	 */
	private List<DAG.Node> nodes;
	
	/**
	 * A convenience record of the number of vertices
	 */
	private int vertexCount;
	
    /**
     * Create a DAG from a graph, starting at the root vertex.
     * @param rootLabel 
     * @param rootVertex the vertex to start from
     */
	public DAG(int rootVertexIndex, int vertexCount, String rootLabel) {
		this.layers = new ArrayList<List<Node>>();
		this.nodes = new ArrayList<Node>();
		List<Node> rootLayer = new ArrayList<Node>();
		Node rootNode = new Node(rootVertexIndex, 0, rootLabel);
		rootLayer.add(rootNode);
		this.layers.add(rootLayer);
		this.nodes.add(rootNode);
		
		this.labels = new String [vertexCount];
		this.labels[rootVertexIndex] = rootLabel;
		this.vertexCount = vertexCount;
		this.parentCounts = new int[vertexCount];
		
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
	
	public void initialize() {
	    this.invariants = new Invariants(this.vertexCount, this.nodes.size());
	    this.initializeVertexInvariants();
	}
	
	public void setLabel(int vertexIndex, String label) {
	    this.labels[vertexIndex] = label;
	}
	
	public void setColor(int vertexIndex, int color) {
	    this.invariants.colors[vertexIndex] = color;
	}
	
	public void setInvariants(Invariants invariants) {
	    this.invariants = invariants;
	}
	
	public DAG.Node makeNode(int vertexIndex, int layer, String label) {
	    DAG.Node node = new DAG.Node(vertexIndex, layer, label);
	    this.labels[vertexIndex] = label;
	    this.nodes.add(node);
	    if (layers.size() <= layer) {
	    	this.layers.add(new ArrayList<DAG.Node>());
	    }
	    this.layers.get(layer).add(node);
	    return node;
	}
	
	public void addRelation(DAG.Node childNode, DAG.Node parentNode) {
	    childNode.parents.add(parentNode);
	    this.parentCounts[childNode.vertexIndex]++;
	    parentNode.children.add(childNode);
	}
	
	public List<InvariantIntIntPair> getInvariantPairs() {
	    List<InvariantIntIntPair> pairs = new ArrayList<InvariantIntIntPair>();
	    for (int i = 0; i < this.vertexCount; i++) {
	        if (this.invariants.colors[i] == 0 && this.parentCounts[i] >= 2) {
	            pairs.add(
	                    new InvariantIntIntPair(
	                            this.invariants.vertexInvariants[i], i));
	        }
	    }
	    Collections.sort(pairs);
	    return pairs;
	}
	
	public int colorFor(int vertexIndex) {
		return this.invariants.colors[vertexIndex];
	}

	public void addLayer(List<Node> layer) {
		this.layers.add(layer);
	}
	
	public void initializeVertexInvariants() {
	    List<InvariantIntStringPair> pairs = 
	        new ArrayList<InvariantIntStringPair>();
	    for (int i = 0; i < this.vertexCount; i++) {
	        String l = this.labels[i];
	        int p = this.parentCounts[i];
	        pairs.add(new InvariantIntStringPair(l, p, i));
	    }
	    Collections.sort(pairs);
	    
	    if (pairs.size() == 0) return;
	    
	    int order = 1;
	    this.invariants.vertexInvariants[pairs.get(0).originalIndex] = order;
	    for (int i = 1; i < pairs.size(); i++) {
	        InvariantIntStringPair a = pairs.get(i - 1);
	        InvariantIntStringPair b = pairs.get(i);
	        if (!a.equals(b)) {
	            order++;
	        }
	        this.invariants.vertexInvariants[b.originalIndex] = order;
	    }
//	    EQUIVALENT TO ABOVE, IN THEORY
//	    for (int i = 0; i < pairs.size(); i++) {
//	        int n = 1;
//	        for (InvariantIntStringPair pair : pairs) {
//	            if (pair.equals(this.labels[i], this.parentCounts[i])) {
//	                this.invariants.vertexInvariants[i] = n;
//	                break;
//	            }
//	            n++;
//	        }
//	    }
	}
	
	public List<Integer> createOrbit() {
	    int maxInvariant = 0;
	    int invariantChoice = 0;
	    List<Integer> orbit = new ArrayList<Integer>();
	    
	    for (int i = 0; i < this.vertexCount; i++) {
	        int n = 0;
	        for (int j = 0; j < this.vertexCount; j++) {
	            if (this.invariants.vertexInvariants[j] == i 
	                    && this.parentCounts[j] >= 2) {
	                n++;
	            }
	        }
	        if (maxInvariant < n) {
	            maxInvariant = n;
	            invariantChoice = i;
	        }
	    }
	    
	    for (int k = 0; k < this.vertexCount; k++) {
	        if (this.invariants.vertexInvariants[k] == invariantChoice) {
	            orbit.add(k);
	        }
	    }
	    return orbit;
	}
	
	public void computeVertexInvariants() {
	    int[][] layerInvariants = new int[this.vertexCount][];
	    for (int i = 0; i < this.nodes.size(); i++) {
	        DAG.Node node = this.nodes.get(i);
	        int j = node.vertexIndex;
	        if (layerInvariants[j] == null) {
	            layerInvariants[j] = new int[this.layers.size()];
	        }
	        layerInvariants[j][node.layer] = this.invariants.nodeInvariants[i]; 
	    }
	    
	    List<InvariantArray> invariantLists = new ArrayList<InvariantArray>();
	    for (int i = 0; i < this.vertexCount; i++) {
	        InvariantArray invArr = new InvariantArray(layerInvariants[i], i); 
	        invariantLists.add(invArr);
	    }
	    Collections.sort(invariantLists);
	    
	    int order = 1;
	    int first = invariantLists.get(0).originalIndex;
	    this.invariants.vertexInvariants[first] = 1;
	    for (int i = 1; i < invariantLists.size(); i++) {
	        InvariantArray a = invariantLists.get(i - 1);
	        InvariantArray b = invariantLists.get(i);
	        if (!a.equals(b)) {
	            order++;
	        }
	        this.invariants.vertexInvariants[b.originalIndex] = order;
	    }

	}
	
	public void updateVertexInvariants() {
	    int[] oldInvariants = new int[this.vertexCount];
	    boolean invariantSame = true;
	    while (invariantSame) {
	        oldInvariants = (int[]) this.invariants.vertexInvariants.clone();
	        
	        updateNodeInvariants(Direction.DOWN);
	        computeVertexInvariants();
	        
	        updateNodeInvariants(Direction.UP);
	        computeVertexInvariants();
	        
	        invariantSame = 
	            checkInvariantChange(
	                    oldInvariants, this.invariants.vertexInvariants);
	        
	    }
	}
	
	public boolean checkInvariantChange(int[] a, int[] b) {
	    for (int i = 0; i < this.vertexCount; i++) {
	        if (a[i] != b[i]) {
	            return true;
	        }
        }
	    return false;
	}
	
	public void updateNodeInvariants(DAG.Direction direction) {
	    int start, end, increment;
	    if (direction == Direction.UP) {
	        start = this.layers.size() - 1;
	        end = 0;
	        increment = -1;
	    } else {
	        start = 0;
	        end = this.layers.size()-1;
	        increment = 1;
	    }
	    
        for (int i = start; i != end; i += increment) {
           this.updateLayer(this.layers.get(i), direction);
        }
        
	}
	
	public void updateLayer(List<DAG.Node> layer, DAG.Direction direction) {
	    List<InvariantList> nodeInvariantList = 
            new ArrayList<InvariantList>();
        for (int i = 0; i < layer.size(); i++) {
            DAG.Node layerNode = layer.get(i);
            int x = layerNode.vertexIndex;
            InvariantList nodeInvariant = new InvariantList(this.nodes.indexOf(layerNode));
            nodeInvariant.add(this.invariants.colors[x]);
            nodeInvariant.add(this.invariants.vertexInvariants[x]);
            
            List<Integer> relativeInvariants = new ArrayList<Integer>();

            List<DAG.Node> relatives = (direction == Direction.UP) ? 
                    layerNode.parents : layerNode.children;
            for (Node relative : relatives ){
            	nodeInvariant.add(this.invariants.nodeInvariants[this.nodes.indexOf(relative)]);
            }
            Collections.sort(relativeInvariants);
            nodeInvariant.addAll(relativeInvariants);
            nodeInvariantList.add(nodeInvariant);
        }
        
        Collections.sort(nodeInvariantList);
        
        int order = 1;
        int first = nodeInvariantList.get(0).originalIndex;
        this.invariants.nodeInvariants[first] = order;
        for (int i = 1; i < nodeInvariantList.size(); i++) {
            InvariantList a = nodeInvariantList.get(i - 1);
            InvariantList b = nodeInvariantList.get(i);
            if (!a.equals(b)) {
                order++;
            }
            this.invariants.nodeInvariants[b.originalIndex] = order;
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
