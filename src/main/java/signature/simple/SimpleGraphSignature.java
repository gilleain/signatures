package signature.simple;

import signature.AbstractGraphSignature;
import signature.AbstractVertexSignature;

/**
 * A test implementation of signatures for {@link SimpleGraph}s.
 * 
 * @author maclean
 *
 */
public class SimpleGraphSignature extends AbstractGraphSignature {
    
    public SimpleGraph graph;
    
    public SimpleGraphSignature(SimpleGraph graph) {
        super();
        this.graph = graph;
    }

    public int getVertexCount() {
        return this.graph.getVertexCount();
    }

    @Override
    public String signatureStringForVertex(int vertexIndex) {
        SimpleVertexSignature vertexSignature;
        int height = super.getHeight();
        if (height == -1) {
            vertexSignature = 
                new SimpleVertexSignature(vertexIndex, this.graph);
        } else {
            vertexSignature = 
                new SimpleVertexSignature(vertexIndex, height, this.graph);
        }
        return vertexSignature.toCanonicalString();
    }

    @Override
    public String signatureStringForVertex(int vertexIndex, int height) {
        SimpleVertexSignature vertexSignature  = 
            new SimpleVertexSignature(vertexIndex, height, this.graph);
        return vertexSignature.toCanonicalString();
    }

    public String toCanonicalString() {
        return super.toCanonicalString();
    }

    @Override
    public AbstractVertexSignature signatureForVertex(int vertexIndex) {
        return new SimpleVertexSignature(vertexIndex, this.graph);
    }
}
