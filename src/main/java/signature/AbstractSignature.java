package signature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The abstract base class for signatures. This class creates and manages a DAG
 * and the input/output of signature strings.
 * 
 * To properly subclass this class, the constuctor of the derived class should
 * follow this pattern:
 * <pre>
 *   public MySignature(GRAPH_TYPE inputGraph, int rootVertexIndex) {
 *     super("[", "]");  // or super();
 *     this.inputGraph = inputGraph;
 *     create(rootVertexIndex);
 *   } 
 * </pre>
 * 
 * where GRAPH_TYPE is some type specific to the client library.
 * 
 * The abstract methods <code>getVertexSymbol</code>, <code>getConnected</code>,
 *  and <code>getEdgeSymbol</code> also need to be implemented. The vertex 
 * symbol can be anything except one of the branch or node symbols.
 *  
 * @author maclean
 *
 */
public abstract class AbstractSignature {
	
	public static final char START_BRANCH_SYMBOL = '(';
	
	public static final char END_BRANCH_SYMBOL = ')';
	
	private final String startNodeSymbol;
	
	private final String endNodeSymbol;
	
	private DAG dag;
	
	private boolean isCanonicalLabel = false;
	
	public List<List<Integer>> canonicalLabelMapping;

	private List<Integer> currentCanonicalLabelMapping;
	
	private List<String> vertexSignatures;
	
	private String graphSignature;

	public AbstractSignature() {
		this.startNodeSymbol = "";
		this.endNodeSymbol = "";
	}
	
	public AbstractSignature(String startNodeSymbol, String endNodeSymbol) {
		this.startNodeSymbol = startNodeSymbol;
		this.endNodeSymbol = endNodeSymbol;
	}
	
	public DAG getDAG() {
	    return this.dag;
	}
	
	public int[] getCanonicalLabelling() {
	    CanonicalLabellingVisitor labeller = 
	        new CanonicalLabellingVisitor(this.getVertexCount());
	    this.dag.accept(labeller);
	    return labeller.getLabelling();
	}
	
    public void canonize(int color, StringBuffer canonicalVertexSignature) {
        // assume that the atom invariants have been initialized
        if (this.getVertexCount() == 0) return;
        
        this.dag.updateVertexInvariants();
        
        List<Integer> orbit = this.dag.createOrbit();
        
        if (orbit.size() < 2) {
            // Color all uncolored atoms having two parents 
            // or more according to their invariant.
            for (InvariantIntIntPair pair : this.dag.getInvariantPairs()) {
                this.dag.setColor(pair.index, color);
                color++;
            }
            this.currentCanonicalLabelMapping = new ArrayList<Integer>();
            String signature = this.toString(); // Creating the root signature string.
            if (signature.compareTo(canonicalVertexSignature.toString()) > 0) {
                int l = canonicalVertexSignature.length();
                canonicalVertexSignature.replace(0, l, signature);
                //this.canonicalLabelMapping = this.currentCanonicalLabelMapping;
                copyToCanonicalLabelMapping(this.currentCanonicalLabelMapping);
                System.out.println(signature + this.currentCanonicalLabelMapping.toString());
            }
            return;
        } else {
            for (int o : orbit) {
                this.dag.setColor(o, color);
                Invariants invariantsCopy = this.dag.copyInvariants();
                this.canonize(color + 1, canonicalVertexSignature);
                this.dag.setInvariants(invariantsCopy);
                this.dag.setColor(o, 0);
            }
        }
    }
	
	public void create(int rootVertexIndex) {
	    if (this.getVertexCount() == 0) return;
		this.dag = new DAG(rootVertexIndex, 
		                   this.getVertexCount(), 
		                   this.getVertexSymbol(rootVertexIndex));
		build(1, this.dag.getRootLayer(), new ArrayList<DAG.Arc>());
		this.dag.initialize();
	}
	
	private void build(int layer, 
	        List<DAG.Node> previousLayer, List<DAG.Arc> usedArcs) {
		List<DAG.Node> nextLayer = new ArrayList<DAG.Node>();
		List<DAG.Arc> layerArcs = new ArrayList<DAG.Arc>();
		for (DAG.Node node : previousLayer) {
			for (int connectedVertex : getConnected(node.vertexIndex)) {
				addNode(
				  layer, node, connectedVertex, layerArcs, usedArcs, nextLayer);
			}
		}
		usedArcs.addAll(layerArcs);
		if (nextLayer.isEmpty()) {
			return;
		} else {
			dag.addLayer(nextLayer);
			build(layer + 1, nextLayer, usedArcs);
		}
	}

	private void addNode(int layer, DAG.Node parentNode, int vertexIndex,
			List<DAG.Arc> layerArcs, List<DAG.Arc> usedArcs, 
			List<DAG.Node> nextLayer) {
		DAG.Arc arc = dag.new Arc(parentNode.vertexIndex, vertexIndex);
		if (usedArcs.contains(arc)) return;
		DAG.Node existingNode = null;
		for (DAG.Node otherNode : nextLayer) {
			if (otherNode.vertexIndex == vertexIndex) {
				existingNode = otherNode;
				break;
			}
		}
		if (existingNode == null) {
			existingNode = dag.makeNode(
			        vertexIndex, layer, getVertexSymbol(vertexIndex));
			nextLayer.add(existingNode);
		}
		dag.addRelation(existingNode, parentNode);
		layerArcs.add(arc);
	}
	
	/**
	 * Get the number of vertices.
	 * 
	 * @return the number of vertices in the  input graph
	 */
	public abstract int getVertexCount();
	
	/**
	 * Get the symbol to use in the output signature string for this vertex of 
	 * the input graph.
	 *  
	 * @param vertexIndex the index of the vertex in the input graph
	 * @return a String symbol
	 */
	public abstract String getVertexSymbol(int vertexIndex);
	
	/**
	 * Get a list of the indices of the vertices connected to the vertex with 
	 * the supplied index.
	 * 
	 * @param vertexIndex the index of the vertex to use
	 * @return the indices of connected vertices in the input graph
	 */
	public abstract int[] getConnected(int vertexIndex);
	
	/**
	 * Get the symbol (if any) for the edge between the vertices with these two
	 * indices.
	 * 
	 * @param vertexIndex the index of one of the vertices in the edge
	 * @param otherVertexIndex the index of the other vertex in the edge 
	 * @return a string symbol for this edge
	 */
	public abstract String getEdgeSymbol(int vertexIndex, int otherVertexIndex);
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		print(buffer, this.dag.getRoot(), null, new ArrayList<DAG.Arc>());
		return buffer.toString();
	}
	
	/**
	 * Recursively print the signature into the buffer.
	 * 
	 * @param buffer the string buffer to print into
	 * @param node the current node of the signature
	 * @param parent the parent node, or null
	 * @param arcs the list of already visited arcs
	 */
	private void print(StringBuffer buffer, DAG.Node node,
			DAG.Node parent, List<DAG.Arc> arcs) {
		
		// print out any symbol for the edge in the input graph
		if (parent != null) {
			buffer.append(getEdgeSymbol(node.vertexIndex, parent.vertexIndex));
		}
		
		// print out the text that represents the node itself
		buffer.append(this.startNodeSymbol);
		buffer.append(getVertexSymbol(node.vertexIndex));
		// Add the vertexIndex if it hasn't already been added.
		if ( !(inCurrentCanonicalMapping(node.vertexIndex)) ){
			this.currentCanonicalLabelMapping.add(node.vertexIndex);
		}
		int color = dag.colorFor(node.vertexIndex);
		if (color != 0) {
			buffer.append(',').append(color);
		}
		buffer.append(this.endNodeSymbol);
		
		// Need to sort the children here, so that they are printed in an order 
		// according to their invariants.
		Collections.sort(node.children);
		
		// now print the sorted children, surrounded by branch symbols
		boolean addedBranchSymbol = false;
		for (DAG.Node child : node.children) {
			DAG.Arc arc = dag.new Arc(node.vertexIndex, child.vertexIndex);
			if (arcs.contains(arc)) {
				continue;
			} else {
				if (!addedBranchSymbol) {
					buffer.append(AbstractSignature.START_BRANCH_SYMBOL);
					addedBranchSymbol = true;
				}
				arcs.add(arc);
				print(buffer, child, node, arcs);
			}
		}
		if (addedBranchSymbol) {
			buffer.append(AbstractSignature.END_BRANCH_SYMBOL);
		}
	}
	
	private void resetDAG(int vertexRootIndex) {
	    String rootSymbol = this.getVertexSymbol(vertexRootIndex);
	    dag.resetDAG(vertexRootIndex, rootSymbol);
	    build(1, this.dag.getRootLayer(), new ArrayList<DAG.Arc>());
        this.dag.initialize();
	}
	
	private void generateVertexSignatures() {
		// Loop through all vertices and create a vertex signature for each one them.

		this.create(0);
	    this.canonicalLabelMapping.add(new ArrayList<Integer>());
		this.vertexSignatures = new ArrayList<String>();
		this.vertexSignatures.add(this.toCanonicalVertexString());
        for (int vertexIndex = 1; vertexIndex < this.getVertexCount(); vertexIndex++) {
		    this.resetDAG(vertexIndex);
		    this.canonicalLabelMapping.add(new ArrayList<Integer>());
			this.vertexSignatures.add(this.toCanonicalVertexString());
		}
	}
	
	
	/**
	 * Use the lexicographically largest (or smallest) as the graph signature
	 */
	private void generateGraphSignature() {
		this.generateVertexSignatures();
		Collections.sort(this.vertexSignatures);
		this.graphSignature = this.vertexSignatures.get(0);
	}
	
	
	/**
	 * Convenience function that creates a String to hold the 
     * signature instead of a StringBuffer.
     * 
	 * @return
	 */
	private String toCanonicalVertexString() {
		StringBuffer canonicalVertexStringBuffer = new StringBuffer();
//		this.canonize(0, canonicalVertexStringBuffer);
		this.canonize(1, canonicalVertexStringBuffer);
		return canonicalVertexStringBuffer.toString();
	}
	

	public String getGraphSignature(){
		// Generates and returns a graph signature
		this.generateGraphSignature();
		return this.graphSignature;
	}

	
	public String getVertexSignature(int vertexId){
		// Generates and returns a graph signature
		this.generateVertexSignatures();
		return this.vertexSignatures.get(vertexId);
	}

    public List<String> getAllVertexSignatures() {
        this.generateVertexSignatures();
        return this.vertexSignatures;
    }
    
//    public boolean isCanonicallyLabelled() {
//    	// Generate the vertex signatures and identify the graph signature.
//    	this.generateVertexSignatures();
//		Collections.sort(this.vertexSignatures);
//		this.graphSignature = this.vertexSignatures.get(0);
//		
//		// See which of the vertex signatures match the graph signature and return true if any of these are ordered 0, 1, ..., n.
//		// Where n is the number of vertices in the graph.
//		int el = 0;
//		for (String vertexSignature : this.vertexSignatures) {
//			if ( this.graphSignature.equals(vertexSignature) ) {
//				if ( isInIncreasingOrder(this.canonicalLabelMapping.get(this.vertexSignatures.get(el).))) {
//					return true;
//				}
//			}
//		el++;
//		}
//		return false;
//    }
    
    private boolean isInIncreasingOrder(List<Integer> integerList) {
    	for (int i = 1; i < integerList.size(); i++) {
    		if ( integerList.get(i-1) > integerList.get(i) ) {
    			return false;
    		}
    	}
    	return true;
    }
    private boolean inCurrentCanonicalMapping(int id) {
    	if ( this.currentCanonicalLabelMapping.isEmpty() ) {
    		return false;
    	}
    	for (int el : this.currentCanonicalLabelMapping){
    		if ( el == id )
    			return true;
    	}
		return false;
    	
    }
    
    private void copyToCanonicalLabelMapping(List<Integer> labelList) {
    	this.canonicalLabelMapping.get(this.canonicalLabelMapping.size()-1).clear();
    	for ( int l : labelList ) {
    		this.canonicalLabelMapping.get(this.canonicalLabelMapping.size()-1).add(l);
    	}
    }
}
