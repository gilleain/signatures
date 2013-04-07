package signature.edgecolored;

import java.util.Map;

import signature.AbstractVertexSignature;

public class EdgeColoredVertexSignature extends AbstractVertexSignature {
    
    private EdgeColoredGraph graph;
    
    private Map<String, Integer> colorMap;
    
    public EdgeColoredVertexSignature(
            int rootVertexIndex, EdgeColoredGraph graph, Map<String, Integer> colorMap) {
        this(rootVertexIndex, -1, graph, colorMap);
    }
    
    public EdgeColoredVertexSignature(
            int rootVertexIndex, int height, EdgeColoredGraph graph, Map<String, Integer> colorMap) {
        super();
        this.graph = graph;
        this.colorMap = colorMap;
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
        EdgeColoredGraph.Edge edge = this.graph.getEdge(vertexIndex, otherVertexIndex);
        if (edge != null) {
            return edge.edgeLabel;
        } else {
            // ??
            return "";
        }
    }

    @Override
    public String getVertexSymbol(int vertexIndex) {
        return ".";
    }

    @Override
    protected int getIntLabel(int vertexIndex) {
        return -1;
    }

    @Override
    protected int convertEdgeLabelToColor(String label) {
        if (colorMap.containsKey(label)) {
            return colorMap.get(label);
        }
        return 1;   // or throw error?
    }
    
}
