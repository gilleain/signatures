package signature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The base class for signatures that are created from a vertex of a graph. A
 * concrete derived class will implement the methods (getConnected, 
 * getVertexCount() etc.) that communicate between the graph and the signature.
 * 
 * @author maclean
 *
 */
public abstract class AbstractVertexSignature {
    
    public static final char START_BRANCH_SYMBOL = '(';
    
    public static final char END_BRANCH_SYMBOL = ')';
    
    private final String startNodeSymbol;
    
    private final String endNodeSymbol;
    
    private DAG dag;
    
    /**
     * Create an abstract vertex signature with no start or end node symbols.
     */
    public AbstractVertexSignature() {
        this.startNodeSymbol = "";
        this.endNodeSymbol = "";
    }
    
    /**
     * Create an abstract vertex signature with supplied start and end symbols.
     *  
     * @param startNodeSymbol
     * @param endNodeSymbol
     */
    public AbstractVertexSignature(
            String startNodeSymbol, String endNodeSymbol) {
        this.startNodeSymbol = startNodeSymbol;
        this.endNodeSymbol = endNodeSymbol;
    }

    /**
     * This is a kind of constructor that builds the internal representation of
     * the signature given the index of the vertex to use as a root.
     * 
     * @param rootVertexIndex
     *            the index in the graph of the root for this signature
     */
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
     * Convert this signature into a canonical signature string.
     * 
     * @return the canonical string form
     */
    public String toCanonicalString() {
        StringBuffer stringBuffer = new StringBuffer();
        this.canonize(1, stringBuffer);
        return stringBuffer.toString();
    }
    
    /**
     * Find the minimal signature string by trying all colors.
     * 
     * @param color the current color to use
     * @param canonicalVertexSignature the buffer to fill
     */
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
        
            // Creating the root signature string.
            String signature = this.toString(); 
            if (signature.compareTo(canonicalVertexSignature.toString()) > 0) {
                int l = canonicalVertexSignature.length();
                canonicalVertexSignature.replace(0, l, signature);
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
    
    /**
     * Get a canonical labelling for this signature.
     * @return
     */
    public int[] getCanonicalLabelling() {
        CanonicalLabellingVisitor labeller = 
            new CanonicalLabellingVisitor(this.getVertexCount());
        this.dag.accept(labeller);
        return labeller.getLabelling();
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
                    buffer.append(AbstractVertexSignature.START_BRANCH_SYMBOL);
                    addedBranchSymbol = true;
                }
                arcs.add(arc);
                print(buffer, child, node, arcs);
            }
        }
        if (addedBranchSymbol) {
            buffer.append(AbstractVertexSignature.END_BRANCH_SYMBOL);
        }
    }
    
    /* 
     * Convert this vertex signature into a signature string.
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        print(buffer, this.dag.getRoot(), null, new ArrayList<DAG.Arc>());
        return buffer.toString();
    }

}
