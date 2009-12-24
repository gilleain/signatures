package signature.simple;

import signature.AbstractVertexSignature;

public class SimpleVertexSignature extends AbstractVertexSignature {
    
    private SimpleGraph graph;
    
    public SimpleVertexSignature(int rootVertexIndex, SimpleGraph graph) {
        this(rootVertexIndex, -1, graph);
    }
    
    public SimpleVertexSignature(
            int rootVertexIndex, int height, SimpleGraph graph) {
        super("[", "]");
        this.graph = graph;
        if (height == -1) {
            super.create(rootVertexIndex);
        } else {
            super.create(rootVertexIndex, height);
        }
    }

    @Override
    public int[] getConnected(int vertexIndex) {
        return this.graph.getConnected(vertexIndex);
    }

    @Override
    public String getEdgeSymbol(int vertexIndex, int otherVertexIndex) {
        return "";
    }

    @Override
    public int getVertexCount() {
        return this.graph.getVertexCount();
    }

    @Override
    public String getVertexSymbol(int vertexIndex) {
        return ".";
    }

}
