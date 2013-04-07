package signature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    public static final char START_NODE_SYMBOL = '[';
    
    public static final char END_NODE_SYMBOL = ']';
    
    private DAG dag;
    
    /**
     * If the signature is considered as a tree, the height is the maximum 
     * distance from the root to the leaves. A height of -1 is taken to mean
     * the same as the maximum possible height, which is the graph diameter
     */
    private int height;

    /**
     * The number of vertices from the graph that were visited to make the
     * signature. This is either the number of vertices in the graph - if the
     * height is equal to the graph diameter - or the number of vertices seen up
     * to that height
     */
    private int vertexCount;
    
    /**
     * Mapping between the vertex indices in the original graph and the vertex   
     * indices stored in the Nodes. This is necessary for signatures with a
     * height less than the graph diameter. It is also the order in which the
     * vertices were visited to make the DAG.
     */
    private Map<Integer, Integer> vertexMapping;
    
    public enum InvariantType { STRING, INTEGER };
    
    private InvariantType invariantType;
    
    /**
     * Create an abstract vertex signature.
     */
    public AbstractVertexSignature() {
        this(InvariantType.STRING);
    }
    
    /**
     * Create an abstract vertex signature that uses the given invariant type
     * for the initial invariants. 
     * 
     * @param invariantType
     */
    public AbstractVertexSignature(InvariantType invariantType) {
        this.vertexCount = 0;
        this.invariantType = invariantType;
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
     * Look up the original graph vertex that <code>vertexIndex</code> maps to.  
     * 
     * @param vertexIndex the internal vertex index that 
     * @return the vertex index in the original graph
     */
    public int getOriginalVertexIndex(int vertexIndex) {
        for (int originalVertexIndex : vertexMapping.keySet()) {
            int internalVertexIndex = vertexMapping.get(originalVertexIndex);
            if (internalVertexIndex == vertexIndex) {
                return originalVertexIndex;
            }
        }
        return -1;
    }

    /**
     * This is a kind of constructor that builds the internal representation of
     * the signature given the index of the vertex to use as a root.
     * 
     * @param rootVertexIndex
     *            the index in the graph of the root for this signature
     * @param graphVertexCount
     *            the number of vertices in the graph           
     */
    public void createMaximumHeight(int rootVertexIndex, int graphVertexCount) {
        create(rootVertexIndex, graphVertexCount, -1);
    }

    /**
     * This is a kind of constructor that builds the internal representation of
     * the signature given the index of the vertex to use as a root. It also
     * takes a maximum height, which limits how many vertices will be visited.
     * 
     * @param rootVertexIndex
     *            the index in the graph of the root for this signature
     * @param graphVertexCount
     *            the number of vertices in the graph           
     * @param height
     *            the maximum height of the signature
     */
    public void create(int rootVertexIndex, int graphVertexCount, int height) {
        this.height = height;
        vertexMapping = new HashMap<Integer, Integer>();
        vertexMapping.put(rootVertexIndex, 0);
        dag = new DAG(0, graphVertexCount);
        vertexCount = 1;
        build(1, dag.getRootLayer(), new ArrayList<DAG.Arc>(), height);
        if (invariantType == InvariantType.STRING) {
            createWithStringLabels();
        } else if (invariantType == InvariantType.INTEGER){
            createWithIntLabels();
        } else {
            // XXX TODO : unknown invariant type
            System.err.println("unknown invariant type " + invariantType);
        }
    }
    
    private void createWithIntLabels() {
        int[] vertexLabels = new int[vertexCount];
        for (int externalIndex : vertexMapping.keySet()) {
            int internalIndex = vertexMapping.get(externalIndex);
            vertexLabels[internalIndex] = getIntLabel(externalIndex);
        }
        dag.initializeWithIntLabels(vertexLabels);
    }
    
    private void createWithStringLabels() {
        String[] vertexLabels = new String[vertexCount];
        for (int externalIndex : vertexMapping.keySet()) {
            int internalIndex = vertexMapping.get(externalIndex);
            vertexLabels[internalIndex] = getVertexSymbol(externalIndex);
        }
        dag.initializeWithStringLabels(vertexLabels);
    }

    private void build(int layer, 
            List<DAG.Node> previousLayer, List<DAG.Arc> usedArcs, int height) {
        if (height == 0) return;
        List<DAG.Node> nextLayer = new ArrayList<DAG.Node>();
        List<DAG.Arc> layerArcs = new ArrayList<DAG.Arc>();
        for (DAG.Node node : previousLayer) {
            int mappedIndex = getOriginalVertexIndex(node.vertexIndex);
            int[] connected = getConnected(mappedIndex);
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
        
        // look up the mapping or create a new mapping for the vertex index
        int mappedVertexIndex;
        if (vertexMapping.containsKey(vertexIndex)) {
            mappedVertexIndex = vertexMapping.get(vertexIndex);
        } else {
            vertexMapping.put(vertexIndex, vertexCount);
            mappedVertexIndex = vertexCount;
            vertexCount++;
        }
        
        // find an existing node if there is one
        DAG.Arc arc = dag.new Arc(parentNode.vertexIndex, mappedVertexIndex);
        if (usedArcs.contains(arc)) return;
        DAG.Node existingNode = null;
        for (DAG.Node otherNode : nextLayer) {
            if (otherNode.vertexIndex == mappedVertexIndex) {
                existingNode = otherNode;
                break;
            }
        }
        
        // if there isn't, make a new node and add it to the layer
        if (existingNode == null) {
            existingNode = dag.makeNode(mappedVertexIndex, layer);
            nextLayer.add(existingNode);
        }
        
        // add the edge label to the node's edge label list
        int originalParentIndex = 
            getOriginalVertexIndex(parentNode.vertexIndex);
        String edgeLabel = getEdgeLabel(originalParentIndex, vertexIndex);
        int edgeColor = convertEdgeLabelToColor(edgeLabel);
        existingNode.addEdgeColor(parentNode.vertexIndex, edgeColor);
        parentNode.addEdgeColor(mappedVertexIndex, edgeColor);
        
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
//        System.out.println("CANONIZING " + 
//                getOriginalVertexIndex(dag.getRoot().vertexIndex)
//                + " " + vertexMapping);
//        System.out.println(dag);
        TMP_COLORING_COUNT = 0;
        this.canonize(0, stringBuffer);
//        System.out.println("invariants " + dag.copyInvariants());
//        System.out.println("occur" + getOccurrences());
        
//        System.out.println("COLORINGS " + TMP_COLORING_COUNT);
//        System.out.println(stringBuffer.toString());
        return stringBuffer.toString();
    }
    
    public int TMP_COLORING_COUNT;
    
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
        int[] parents = dag.getParentsInFinalString();
//        System.out.println("pars\t" + Arrays.toString(parents));
        List<Integer> orbit = this.dag.createOrbit(parents);
//        System.out.println(dag.copyInvariants());
        if (orbit.size() < 2) {
            // Color all uncolored atoms having two parents 
            // or more according to their invariant.
            List<InvariantInt> pairs = dag.getInvariantPairs(parents);
//            System.out.println("coloring " + pairs);
            for (InvariantInt pair : pairs) {
                this.dag.setColor(pair.index, color);
                color++;
            }
            
            TMP_COLORING_COUNT++;
        
            // Creating the root signature string.
            String signature = this.toString();
            int cmp = signature.compareTo(canonicalVertexSignature.toString()); 
            int l = canonicalVertexSignature.length();
            if (cmp > 0) {
//                System.out.println(TMP_COLORING_COUNT + " replacing " + signature + " old= " + canonicalVertexSignature);
                canonicalVertexSignature.replace(0, l, signature);
            } else {
//                System.out.println(TMP_COLORING_COUNT + " rejecting " + cmp + " " + signature);
            }
            return;
        } else {
//            System.out.println("setting color " + color + " for orbit " + orbit);
            for (int o : orbit) {
//                System.out.println("setting color " + color + " for element " + o);
                this.dag.setColor(o, color);
                Invariants invariantsCopy = this.dag.copyInvariants();
                this.canonize(color + 1, canonicalVertexSignature);
                this.dag.setInvariants(invariantsCopy);
                this.dag.setColor(o, -1);
            }
        }
    }

    /**
     * Get a canonical labelling for this signature. Note that a signature that
     * does not cover the graph (has a height < graph diameter) will not have
     * labels for every vertex. Unlabelled vertices will have a value of -1. To
     * handle all cases, the total number of vertices must be passed to the 
     * method.
     * 
     * @param totalVertexCount the number of vertices in the graph
     * 
     * @return 
     *    the permutation necessary to transform the graph into a canonical form
     */
    public int[] getCanonicalLabelling(int totalVertexCount) {
        // TODO : get the totalVertexCount from the graph?
        canonize(0, new StringBuffer());
        CanonicalLabellingVisitor labeller = 
            new CanonicalLabellingVisitor(getVertexCount(), dag.nodeComparator);
        this.dag.accept(labeller);
        int[] internalLabels = labeller.getLabelling();
        int[] externalLabels = new int[totalVertexCount];
        Arrays.fill(externalLabels, -1);
        for (int i = 0; i < getVertexCount(); i++) {
            int externalIndex = getOriginalVertexIndex(i);
            externalLabels[externalIndex] = internalLabels[i]; 
        }
        return externalLabels;    
    }
    
    public void accept(DAGVisitor visitor) {
        dag.accept(visitor);
    }

    /**
     * Get the number of vertices.
     * 
     * @return the number of vertices seen when making the signature, which may
     *         be less than the number in the full graph, depending on the 
     *         height
     */
    public int getVertexCount() {
        return this.vertexCount;
    }
    
    /**
     * Convert the edge label (if any) to an integer color, for example the bond
     * order in a chemistry implementation.
     * 
     * @param label
     *            the label for an edge
     * @return an int color
     */
    protected abstract int convertEdgeLabelToColor(String label);
    
    /**
     * Get the integer label for a vertex - in chemistry implementations this
     * will be the element mass.
     * 
     * @param vertexIndex
     *            the index of the vertex in the input graph
     * @return an integer label
     */
    protected abstract int getIntLabel(int vertexIndex);
    
    /**
     * Get the symbol to use in the output signature string for this vertex of 
     * the input graph.
     *  
     * @param vertexIndex the index of the vertex in the input graph
     * @return a String symbol
     */
    protected abstract String getVertexSymbol(int vertexIndex);
    
    /**
     * Get a list of the indices of the vertices connected to the vertex with 
     * the supplied index.
     * 
     * @param vertexIndex the index of the vertex to use
     * @return the indices of connected vertices in the input graph
     */
    protected abstract int[] getConnected(int vertexIndex);
    
    /**
     * Get the symbol (if any) for the edge between the vertices with these two
     * indices.
     * 
     * @param vertexIndex the index of one of the vertices in the edge
     * @param otherVertexIndex the index of the other vertex in the edge 
     * @return a string symbol for this edge
     */
    protected abstract String getEdgeLabel(int vertexIndex, int otherVertexIndex);
    
    /**
     * Recursively print the signature into the buffer.
     * 
     * @param buffer the string buffer to print into
     * @param node the current node of the signature
     * @param parent the parent node, or null
     * @param arcs the list of already visited arcs
     * @param colorMap a map between pre-printed colors and printed colors
     */
    private void print(StringBuffer buffer, DAG.Node node,
            DAG.Node parent, List<DAG.Arc> arcs) {
        int vertexIndex = getOriginalVertexIndex(node.vertexIndex);
        
        // print out any symbol for the edge in the input graph
        if (parent != null) {
            int parentVertexIndex = getOriginalVertexIndex(parent.vertexIndex);
            buffer.append(getEdgeLabel(vertexIndex, parentVertexIndex));
        }
        
        // print out the text that represents the node itself
        buffer.append(AbstractVertexSignature.START_NODE_SYMBOL);
        buffer.append(getVertexSymbol(vertexIndex));
        int color = dag.colorFor(node.vertexIndex);
        if (color != -1) {
            buffer.append(',').append(color);
        }
        buffer.append(AbstractVertexSignature.END_NODE_SYMBOL);
        
        // Need to sort the children here, so that they are printed in an order 
        // according to their invariants.
        Collections.sort(node.children, dag.nodeComparator);
        
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
    
    public static ColoredTree parse(String s) {
        ColoredTree tree = null;
        ColoredTree.Node parent = null;
        ColoredTree.Node current = null;
        int currentHeight = 1;
        int color = -1;
        int j = 0;
        int k = 0;
        int l = 0;
        String edgeSymbol = null;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == AbstractVertexSignature.START_BRANCH_SYMBOL) {
                parent = current;
                currentHeight++;
                tree.updateHeight(currentHeight);
                l = i;
            } else if (c == AbstractVertexSignature.END_BRANCH_SYMBOL) {
                parent = parent.parent;
                currentHeight--;
                l = i;
            } else if (c == START_NODE_SYMBOL) {
                if (l < i) {
                    edgeSymbol = s.substring(l + 1, i);
                    l = i;
                }
                j = i + 1;
            } else if (c == END_NODE_SYMBOL) {
                String ss;
                if (k < j) {    // no color
                    ss = s.substring(j, i);
                    color = -1;
                } else {        // color
                    ss = s.substring(j, k - 1);
                    color = Integer.parseInt(s.substring(k, i));    
                }
                if (tree == null) {
                    tree = new ColoredTree(ss);
                    parent = tree.getRoot();
                    current = tree.getRoot();
                } else {
                    if (edgeSymbol == null) {
                        current = tree.makeNode(
                                ss, parent, currentHeight, color);
                    } else {
                        current = tree.makeNode(
                                ss, parent, currentHeight, color, edgeSymbol);
                    }
                }
                edgeSymbol = null;
                l = i;
            } else if (c == ',') {
                k = i + 1;
            } 
        }
        return tree;
    }
    
}
