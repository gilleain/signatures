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
        
        public List<Integer> members;
        
        public String signature;
        
        public Vertex(List<Integer> members, String signature) {
            this.members = members;
            this.signature = signature;
        }
        
        public String toString() {
            return signature + " " + members;
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
    
    public int getVertexCount() {
        return vertices.size();
    }
    
    public int getEdgeCount() {
        return edges.size();
    }
    
    public int numberOfLoopEdges() {
        int loopEdgeCount = 0;
        for (Edge e : edges) {
            if (e.isLoop()) {
                loopEdgeCount++;
            }
        }
        return loopEdgeCount;
    }
    
    public abstract boolean isConnected(int i, int j);
    
    public List<String> getVertexSignatureStrings() {
        List<String> signatureStrings = new ArrayList<String>();
        for (Vertex vertex : vertices) {
            signatureStrings.add(vertex.signature);
        }
        return signatureStrings;
    }
    
    public void construct(List<SymmetryClass> symmetryClasses) {
        // make the vertices from the symmetry classes
        for (int i = 0; i < symmetryClasses.size(); i++) {
            SymmetryClass symmetryClass = symmetryClasses.get(i);
            String signatureString = symmetryClass.getSignatureString();
            List<Integer> members = new ArrayList<Integer>();
            for (int e : symmetryClass) { members.add(e); }
            vertices.add(new Vertex(members, signatureString));
        }
        
        // compare all vertices (classwise) for connectivity
        List<Edge> visitedEdges = new ArrayList<Edge>();
        for (int i = 0; i < symmetryClasses.size(); i++) {
            SymmetryClass symmetryClass = symmetryClasses.get(i);
            for (int j = i; j < symmetryClasses.size(); j++) {
                SymmetryClass otherSymmetryClass = symmetryClasses.get(j);
                int totalCount = 0;
                for (int x : symmetryClass) {
                    int countForX = 0;
                    for (int y : otherSymmetryClass) {
                        if (x == y) continue;
                        if (isConnected(x, y) 
                                && !inVisitedEdges(x, y, visitedEdges)) {
                            countForX++;
                            visitedEdges.add(new Edge(x, y, 0));
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
    
    private boolean inVisitedEdges(int x, int y, List<Edge> visitedEdges) {
        for (Edge edge : visitedEdges) {
            if ((edge.vertexIndexA == x && edge.vertexIndexB == y) 
                    || (edge.vertexIndexA == y && edge.vertexIndexB == x )) {
                return true;
            }
        }
        return false;
    }
    
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        int i = 0;
        for (Vertex vertex : vertices) {
            buffer.append(i).append(' ').append(vertex).append('\n');
            i++;
        }
        buffer.append(edges);
        return buffer.toString();
    }

}
