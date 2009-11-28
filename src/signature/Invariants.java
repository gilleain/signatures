package signature;

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
        this.nodeInvariants = new int[nodeCount];
        this.vertexInvariants = new int[vertexCount];
    }
    
    public Object clone() {
        Invariants copy = new Invariants(
                this.colors.length, this.vertexInvariants.length);
        copy.colors = (int[]) this.colors.clone();
        copy.nodeInvariants = (int[]) this.nodeInvariants.clone();
        copy.vertexInvariants = (int[]) this.vertexInvariants.clone();
        return copy;
    }
    
    public String toString() {
        return "colors: " + this.colors + ", node inv" + this.nodeInvariants
                + ", vertex inv : " + this.vertexInvariants;
    }
    
}
