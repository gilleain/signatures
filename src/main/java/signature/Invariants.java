package signature;

import java.util.HashMap;
import java.util.Map;

public class Invariants implements Cloneable {

    /**
     * The colors assigned to vertices of the input graph
     */
    private HashMap<Integer, Integer> colors;
    
    /**
     * The invariants of the nodes of the DAG
     */
    private HashMap<Integer, Integer> nodeInvariants;
    
    /**
     * The invariants of the vertices of the input graph
     */
    private HashMap<Integer, Integer> vertexInvariants;
    
    public Invariants() {
        this.colors = new HashMap<Integer, Integer>();
        this.nodeInvariants = new HashMap<Integer, Integer>();
        this.vertexInvariants = new HashMap<Integer, Integer>();
    }
    
    public int getColor(int vertexIndex) {
        if (colors.containsKey(vertexIndex)) {
            return colors.get(vertexIndex);
        } else {
            return 0;
        }
    }
    
    public void setColor(int vertexIndex, int color) {
        colors.put(vertexIndex, color);
    }
    
    public int getVertexInvariant(int vertexIndex) {
        if (vertexInvariants.containsKey(vertexIndex)) {
            return vertexInvariants.get(vertexIndex);
        } else {
            return 0;
        }
    }
    
    public Map<Integer, Integer> getVertexInvariants() {
        return vertexInvariants;
    }
    
    public Map<Integer, Integer> getVertexInvariantCopy() {
        Map<Integer, Integer> invariantsCopy = new HashMap<Integer, Integer>();
        for (Integer key : vertexInvariants.keySet()) {
            invariantsCopy.put(key, vertexInvariants.get(key));
        }
        return invariantsCopy;
    }
    
    public void setVertexInvariant(int vertexIndex, int value) {
        vertexInvariants.put(vertexIndex, value);
    }
    
    public int getNodeInvariant(int nodeIndex) {
        if (nodeInvariants.containsKey(nodeIndex)) {
            return nodeInvariants.get(nodeIndex);
        } else {
            return 0;
        }
    }
    
    public void setNodeInvariant(int nodeIndex, int value) {
        nodeInvariants.put(nodeIndex, value);
    }
    
    public Object clone() {
        Invariants copy = new Invariants();
        copy.colors = new HashMap<Integer, Integer>();
        for (int key : colors.keySet()) { 
            copy.colors.put(key, colors.get(key));
        }
        copy.nodeInvariants = new HashMap<Integer, Integer>();
        for (int key : nodeInvariants.keySet()) { 
            copy.nodeInvariants.put(key, nodeInvariants.get(key));
        }
        copy.vertexInvariants = new HashMap<Integer, Integer>();
        for (int key : vertexInvariants.keySet()) { 
            copy.vertexInvariants.put(key, vertexInvariants.get(key));
        }
        return copy;
    }
    
    public String toString() {
        return "colors: " + colors + ", "
        	 + "node inv" + nodeInvariants
        	 + ", vertex inv : " + vertexInvariants;
    }
    
}
