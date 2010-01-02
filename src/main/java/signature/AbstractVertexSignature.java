package signature;

import java.util.ArrayList;
import java.util.Arrays;
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
    
    private int height;
    
    private List<Integer> currentCanonicalLabelMapping;
    
    private List<Integer> canonicalLabelMapping;
    
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
     * Get the height of the signature.
     * 
     * @return the height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * This is a kind of constructor that builds the internal representation of
     * the signature given the index of the vertex to use as a root.
     * 
     * @param rootVertexIndex
     *            the index in the graph of the root for this signature
     * @param height
     *            the maximum height of the signature
     */
    public void create(int rootVertexIndex, int height) {
        this.height = height;
        if (height == 0 || this.getVertexCount() == 0) return;
        this.dag = new DAG(rootVertexIndex, 
                           this.getVertexCount(), 
                           this.getVertexSymbol(rootVertexIndex));
        build(1, this.dag.getRootLayer(), new ArrayList<DAG.Arc>(), this.height);
        this.dag.initialize();
    }

    /**
     * This is a kind of constructor that builds the internal representation of
     * the signature given the index of the vertex to use as a root.
     * 
     * @param rootVertexIndex
     *            the index in the graph of the root for this signature
     */
    public void create(int rootVertexIndex) {
        this.create(rootVertexIndex, -1);
    }
    
    private void build(int layer, 
            List<DAG.Node> previousLayer, List<DAG.Arc> usedArcs, int height) {
        if (height == 0) return;
        List<DAG.Node> nextLayer = new ArrayList<DAG.Node>();
        List<DAG.Arc> layerArcs = new ArrayList<DAG.Arc>();
        for (DAG.Node node : previousLayer) {
            int[] connected = getConnected(node.vertexIndex);
            Arrays.sort(connected);
            for (int connectedVertex : connected) {
                addNode(
                  layer, node, connectedVertex, layerArcs, usedArcs, nextLayer);
            }
        }
        usedArcs.addAll(layerArcs);
        if (nextLayer.isEmpty()) {
            return;
        } else {
            dag.addLayer(nextLayer);
            build(layer + 1, nextLayer, usedArcs, height - 1);
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
        
        // Only add a new list of Integers if this is the first time this 
        // function is called for a particular root vertex.
        // The labelling that corresponds to the mapping for the vertex
        // signature should be the only one stored.

        if ( color == 1 ) {
            this.currentCanonicalLabelMapping = new ArrayList<Integer>();
        }
        
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
                this.canonicalLabelMapping = this.currentCanonicalLabelMapping;
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
     * 
     * @return 
     *    the permutation necessary to transform the graph into a canonical form
     */
    public int[] getCanonicalLabelling() {
        CanonicalLabellingVisitor labeller = 
            new CanonicalLabellingVisitor(this.getVertexCount());
        this.dag.accept(labeller);
        return labeller.getLabelling();
    }
    
    public List<Integer> getCanonicalLabelMapping() {
        return this.canonicalLabelMapping;
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
        
        // Add the vertexIndex to the labels if it hasn't already been added.
        if (!(this.currentCanonicalLabelMapping.contains(node.vertexIndex))) {
            this.currentCanonicalLabelMapping.add(node.vertexIndex);
        }
        
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
    
    public List<Integer> postorderCanonicalLabelling() {
        List<Integer> labelling = new ArrayList<Integer>();
        postorder(labelling, this.dag.getRoot(), new ArrayList<DAG.Arc>());
        return labelling;
    }
    
    private void postorder(
            List<Integer> labelling, DAG.Node node, List<DAG.Arc> arcs) {
        if (!labelling.contains(node.vertexIndex)) {
            labelling.add(node.vertexIndex);
        }
//        for (int i = node.children.size() - 1; i > -1; i--) {
        for (int i = 0; i < node.children.size(); i++) {
            DAG.Node child = node.children.get(i);
            DAG.Arc arc = dag.new Arc(node.vertexIndex, child.vertexIndex);
            if (arcs.contains(arc)) {
                continue;
            } else {
                arcs.add(arc);
                postorder(labelling, child, arcs);
            }
        }
    }
    
    public ColoredTree parse(String s) {
        ColoredTree tree = null;
        ColoredTree.Node parent = null;
        ColoredTree.Node current = null;
        int currentHeight = 1;
        int color = 0;
        int j = 0;
        int k = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == AbstractVertexSignature.START_BRANCH_SYMBOL) {
                parent = current;
                currentHeight++;
                tree.updateHeight(currentHeight);
            } else if (c == AbstractVertexSignature.END_BRANCH_SYMBOL) {
                parent = parent.parent;
                currentHeight--;
            } else if (c == '[') {  // TODO : use start node symbol
                j = i + 1;
            } else if (c == ']') {  // TODO : use end node symbol
                String ss;
                if (k < j) {    // no color
                    ss = s.substring(j, i);
                    color = 0;
                } else {        // color
                    ss = s.substring(j, k - 1);
                    color = Integer.parseInt(s.substring(k, i));    
                }
                if (tree == null) {
                    tree = new ColoredTree(ss);
                    parent = tree.getRoot();
                    current = tree.getRoot();
                } else {
                    current = tree.makeNode(ss, parent, currentHeight, color);
                }
            } else if (c == ',') {
                k = i + 1;
            } 
        }
        return tree;
    }

}
