package signature.simple;

import signature.AbstractVertexSignature;
import signature.ColoredTree;

public class SimpleVertexSignature extends AbstractVertexSignature {
    
    private SimpleGraph graph;
    
    public SimpleVertexSignature(int rootVertexIndex, SimpleGraph graph) {
        this(rootVertexIndex, -1, graph);
    }
    
    public SimpleVertexSignature(
            int rootVertexIndex, int height, SimpleGraph graph) {
        super();
        this.graph = graph;
        if (height == -1) {
            super.createMaximumHeight(rootVertexIndex, graph.getVertexCount());
        } else {
            super.create(rootVertexIndex, graph.getVertexCount(), height);
        }
    }

    @Override
    public int[] getConnected(int vertexIndex) {
        return this.graph.getConnected(vertexIndex);
    }

    @Override
    public String getEdgeLabel(int vertexIndex, int otherVertexIndex) {
        return "";
    }

    @Override
    public String getVertexSymbol(int vertexIndex) {
        return ".";
    }
    
    public static ColoredTree parse(String s) {
        return AbstractVertexSignature.parse(s);
    }

}
