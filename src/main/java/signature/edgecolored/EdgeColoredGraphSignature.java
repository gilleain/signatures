package signature.edgecolored;

import java.util.Map;

import signature.AbstractGraphSignature;
import signature.AbstractVertexSignature;

/**
 * A test implementation of signatures for {@link EdgeColoredGraph}s.
 * 
 * @author maclean
 *
 */
public class EdgeColoredGraphSignature extends AbstractGraphSignature {
    
    public EdgeColoredGraph graph;
    
    private Map<String, Integer> colorMap;
    
    public EdgeColoredGraphSignature(EdgeColoredGraph graph, Map<String, Integer> colorMap) {
        super();
        this.graph = graph;
        this.colorMap = colorMap;
    }

    public int getVertexCount() {
        return this.graph.getVertexCount();
    }

    @Override
    public String signatureStringForVertex(int vertexIndex) {
        EdgeColoredVertexSignature vertexSignature;
        int height = super.getHeight();
        if (height == -1) {
            vertexSignature = 
                new EdgeColoredVertexSignature(vertexIndex, this.graph, this.colorMap);
        } else {
            vertexSignature = 
                new EdgeColoredVertexSignature(vertexIndex, height, this.graph, this.colorMap);
        }
        return vertexSignature.toCanonicalString();
    }

    @Override
    public String signatureStringForVertex(int vertexIndex, int height) {
        EdgeColoredVertexSignature vertexSignature  = 
            new EdgeColoredVertexSignature(vertexIndex, height, this.graph, this.colorMap);
        return vertexSignature.toCanonicalString();
    }

    public String toCanonicalString() {
        return super.toCanonicalString();
    }

    @Override
    public AbstractVertexSignature signatureForVertex(int vertexIndex) {
        return new EdgeColoredVertexSignature(vertexIndex, this.graph, this.colorMap);
    }
}
