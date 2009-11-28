package signature;

import java.util.ArrayList;
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
	
	public void create(int rootVertexIndex) {
		this.dag = new DAG(rootVertexIndex);
		build(this.dag.getRootLayer(), new ArrayList<DAG.Arc>());
		this.dag.initialize(this.getVertexCount());
	}
	
	private void build(List<DAG.Node> previousLayer, List<DAG.Arc> usedArcs) {
		List<DAG.Node> nextLayer = new ArrayList<DAG.Node>();
		List<DAG.Arc> layerArcs = new ArrayList<DAG.Arc>();
		for (DAG.Node node : previousLayer) {
			for (int connectedVertex : getConnected(node.vertexIndex)) {
				addNode(node, connectedVertex, layerArcs, usedArcs, nextLayer);
			}
		}
		usedArcs.addAll(layerArcs);
		if (nextLayer.isEmpty()) {
			return;
		} else {
			dag.addLayer(nextLayer);
			build(nextLayer, usedArcs);
		}
	}

	private void addNode(DAG.Node node, int vertexIndex,
			List<DAG.Arc> layerArcs, List<DAG.Arc> usedArcs, 
			List<DAG.Node> nextLayer) {
		DAG.Arc arc = dag.new Arc(node.vertexIndex, vertexIndex);
		if (usedArcs.contains(arc)) return;
		DAG.Node existingNode = null;
		for (DAG.Node otherNode : nextLayer) {
			if (otherNode.vertexIndex == vertexIndex) {
				existingNode = otherNode;
				break;
			}
		}
		if (existingNode == null) {
			existingNode = dag.makeNode(vertexIndex);
			nextLayer.add(existingNode);
		}
		node.addChild(existingNode);
		dag.addParent(existingNode, node);
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
		DAG.Node root = this.dag.getRoot();
		StringBuffer buffer = new StringBuffer();
		print(buffer, root, null, new ArrayList<DAG.Arc>());
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
		int color = dag.colorFor(node.vertexIndex);
		if (color != 0) {
			buffer.append(',').append(color);
		}
		buffer.append(this.endNodeSymbol);
		
		// now print any children, surrounded by branch symbols
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
	
}