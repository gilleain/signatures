package signature.implementation.unlabelled;

import signature.AbstractSignature;

/**
 * A test implementation of signatures for {@link UnlabelledGraph}s.
 * 
 * @author maclean
 *
 */
public class UnlabelledGraphSignature extends AbstractSignature {
    
    public UnlabelledGraph graph;
    
    public UnlabelledGraphSignature(UnlabelledGraph graph, int root) {
        super();
        this.graph = graph;
        super.create(root);
    }

    public int getVertexCount() {
        return this.graph.getVertexCount();
    }
    
    public int[] getConnected(int vertexIndex) {
        return this.graph.getConnected(vertexIndex);
    }

    public String getEdgeSymbol(int vertexIndex, int otherVertexIndex) {
        return "";
    }

    public String getVertexSymbol(int vertexIndex) {
        return ".";
    }

}
