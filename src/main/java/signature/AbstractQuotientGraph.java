package signature;

import java.util.ArrayList;
import java.util.List;

/**
 * A quotient graph Q is derived from a simple graph G (or molecule graph) by 
 * determining the signature for each vertex in G, and making a vertex in Q
 * for each signature. These vertices in Q are then connected to each other -
 * or <b>themselves</b> - if vertices in G with those signatures are connected.
 * 
 * Therefore, the quotient graph is a summary of the original graph.
 * 
 * @author maclean
 *
 */
public abstract class AbstractQuotientGraph {
    
    private class Vertex {
        
        public int count;
        
        public String signature;
        
        public Vertex(int count, String signature) {
            this.count = count;
            this.signature = signature;
        }
        
        public String toString() {
            return signature + " " + count;
        }
    }
    
    private class Edge {
        
        public int count;
        
        public int vertexIndexA;
        
        public int vertexIndexB;
        
        public Edge(int vertexIndexA, int vertexIndexB, int count) {
            this.vertexIndexA = vertexIndexA;
            this.vertexIndexB = vertexIndexB;
            this.count = count;
        }
        
        public boolean isLoop() {
            return vertexIndexA == vertexIndexB;
        }
        
        public String toString() {
            return vertexIndexA + "-" + vertexIndexB + "(" + count + ")";
        }
    }
    
    private List<Vertex> vertices;
    
    private List<Edge> edges;
    
    public AbstractQuotientGraph() {
        vertices = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
    }
    
    public abstract boolean isConnected(int i, int j);
    
    public void construct(List<SymmetryClass> symmetryClasses) {
        // make the vertices from the symmetry classes
        for (int i = 0; i < symmetryClasses.size(); i++) {
            SymmetryClass symmetryClass = symmetryClasses.get(i);
            String signatureString = symmetryClass.getSignatureString();
            int count = symmetryClass.size();
            vertices.add(new Vertex(count, signatureString));
        }
        
        // compare all vertices (classwise) for connectivity
        for (int i = 0; i < symmetryClasses.size(); i++) {
            SymmetryClass symmetryClass = symmetryClasses.get(i);
            for (int j = i; j < symmetryClasses.size(); j++) {
                SymmetryClass otherSymmetryClass = symmetryClasses.get(j);
                int totalCount = 0;
                for (int x : symmetryClass) {
                    int countForX = 0;
                    for (int y : otherSymmetryClass) {
                        if (x == y) continue;
                        if (isConnected(x, y)) {
                            countForX++;
                        }
                    }
                    totalCount += countForX;
                }
                if (totalCount > 0) {
                    edges.add(new Edge(i, j, totalCount));
                }
            }
        }
    }
    
    public String toString() {
        return vertices + "\n" + edges;
    }

}
