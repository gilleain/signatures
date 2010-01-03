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
        
        public int lowerVertexIndex;
        
        public int upperVertexIndex;
        
        public VirtualEdge(int vertexIndex1, int vertexIndex2) {
            if (vertexIndex1 < vertexIndex2) {
                this.lowerVertexIndex = vertexIndex1;
                this.upperVertexIndex = vertexIndex2;
            } else {
                this.lowerVertexIndex = vertexIndex2;
                this.upperVertexIndex = vertexIndex1;
            }
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
            return this.lowerVertexIndex + ":" + this.upperVertexIndex;
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
    public void makeEdge(int vertexIndex1, int vertexIndex2) {
        this.edges.add(new VirtualEdge(vertexIndex1, vertexIndex2));
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
