package signature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Only intended for use in creating 'virtual' graphs for checking canonicity.
 * 
 * @author maclean
 *
 */
public class VirtualGraphBuilder extends AbstractGraphBuilder {
    
    private class VirtualEdge implements Comparable<VirtualEdge> {
        
        public final int lowerVertexIndex;
        
        public final int upperVertexIndex;
        
        public final String lowerVertexSymbol;
        
        public final String upperVertexSymbol;
        
        public final String edgeLabel;
        
        public VirtualEdge(int vertexIndex1, int vertexIndex2, 
                String vertexSymbol1, String vertexSymbol2, String edgeLabel) {
            if (vertexIndex1 < vertexIndex2) {
                this.lowerVertexIndex = vertexIndex1;
                this.upperVertexIndex = vertexIndex2;
                this.lowerVertexSymbol = vertexSymbol1;
                this.upperVertexSymbol = vertexSymbol2;
            } else {
                this.lowerVertexIndex = vertexIndex2;
                this.upperVertexIndex = vertexIndex1;
                this.lowerVertexSymbol = vertexSymbol2;
                this.upperVertexSymbol = vertexSymbol1;
            }
            this.edgeLabel = edgeLabel;
        }

        public int compareTo(VirtualEdge o) {
            if (this.lowerVertexIndex < o.lowerVertexIndex) {
                return -1;
            } else if (this.lowerVertexIndex == o.lowerVertexIndex){
                if (this.upperVertexIndex < o.upperVertexIndex) {
                    return -1;
                } else if (this.upperVertexIndex == o.upperVertexIndex) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return 1;
            }
        }
        
        public String toString() {
            return this.lowerVertexIndex + this.lowerVertexSymbol + 
                    ":" + this.upperVertexIndex + this.upperVertexSymbol
                    + "(" + edgeLabel + ")";
        }
    }
    
    private List<VirtualEdge> edges;
    
    public VirtualGraphBuilder() {
        super();
        this.edges = new ArrayList<VirtualEdge>();
    }
    
    public String toEdgeString() {
        StringBuffer edgeString = new StringBuffer();
        Collections.sort(this.edges);
        for (VirtualEdge edge : this.edges) {
            edgeString.append(edge.toString()).append(",");
        }
        return edgeString.toString();
    }
    
    @Override
    public void makeEdge(int vertexIndex1, int vertexIndex2, 
            String vertexSymbol1, String vertexSymbol2, String edgeLabel) {
        this.edges.add(
                new VirtualEdge(
                     vertexIndex1, vertexIndex2, 
                     vertexSymbol1, vertexSymbol2, edgeLabel));
    }

    @Override
    public void makeGraph() {
        this.edges.clear();
    }

    @Override
    public void makeVertex(String label) {
        // do nothing
    }

}
