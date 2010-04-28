package signature;

import java.util.Arrays;

public class Invariants implements Cloneable {

    /**
     * The colors assigned to vertices of the input graph
     */
    public int[] colors;
    
    /**
     * The invariants of the nodes of the DAG
     */
    public int[] nodeInvariants;
    
    /**
     * The invariants of the vertices of the input graph
     */
    public int[] vertexInvariants;
    
    public Invariants(int vertexCount, int nodeCount) {
        this.colors = new int[vertexCount];
        Arrays.fill(colors, -1);
        this.nodeInvariants = new int[nodeCount];
        this.vertexInvariants = new int[vertexCount];
    }
    
    public int getColor(int vertexIndex) {
        return colors[vertexIndex];
    }
    
    public void setColor(int vertexIndex, int color) {
        colors[vertexIndex] = color;
    }
    
    public int getVertexInvariant(int vertexIndex) {
        return vertexInvariants[vertexIndex];
    }
    
    public int[] getVertexInvariants() {
        return vertexInvariants;
    }
    
    public int[] getVertexInvariantCopy() {
        return (int[]) vertexInvariants.clone();
    }
    
    public void setVertexInvariant(int vertexIndex, int value) {
        vertexInvariants[vertexIndex] = value;
    }
    
    public int getNodeInvariant(int nodeIndex) {
        return nodeInvariants[nodeIndex];
    }
    
    public void setNodeInvariant(int nodeIndex, int value) {
        nodeInvariants[nodeIndex] = value;
    }
    
    public Object clone() {
        Invariants copy = new Invariants(colors.length, vertexInvariants.length);
        copy.colors = (int[]) colors.clone();
        copy.nodeInvariants = (int[]) nodeInvariants.clone();
        copy.vertexInvariants = (int[]) vertexInvariants.clone();
        return copy;
    }
    
    public String toString() {
        return "colors: " + Arrays.toString(colors) + ", "
        	 + "node inv" + Arrays.toString(nodeInvariants)
        	 + ", vertex inv : " + Arrays.toString(vertexInvariants);
    }
    
}
