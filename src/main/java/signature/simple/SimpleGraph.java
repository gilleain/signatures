package signature.simple;

import java.util.ArrayList;
import java.util.List;

/**
 * A very simple graph class - the equivalent of a client library class.
 * 
 * @author maclean
 *
 */
public class SimpleGraph {
    
    public class Edge {
        public int a;
        public int b;
        
        public Edge(int a, int b) {
            this.a = a;
            this.b = b;
        }
        
        public String toString() {
            return this.a + "-" + this.b;
        }
    }
    
    public List<Edge> edges;
    
    public int maxVertexIndex;
    
    public SimpleGraph() {
        this.edges = new ArrayList<Edge>();
    }
    
    public SimpleGraph(String graphString) {
        this();
        for (String edgeString : graphString.split(",")) {
            String[] vertexStrings = edgeString.split(":");
            int a = Integer.parseInt(vertexStrings[0]);
            int b = Integer.parseInt(vertexStrings[1]);
            makeEdge(a, b);
        }
    }
    
    public void makeEdge(int a, int b) {
        if (a > maxVertexIndex) maxVertexIndex = a;
        if (b > maxVertexIndex) maxVertexIndex = b;
        this.edges.add(new Edge(a, b));
    }
    
    public int getVertexCount() {
        return this.maxVertexIndex + 1;
    }
    
    public boolean isConnected(int i, int j) {
        for (Edge e : edges) {
            if ((e.a == i && e.b == j) || (e.b == i && e.a == j)) {
                return true;
            }
        }
        return false;
    }
    
    public int[] getConnected(int vertexIndex) {
        List<Integer> connected = new ArrayList<Integer>();
        for (Edge edge : this.edges) {
            if (edge.a == vertexIndex) {
                connected.add(edge.b);
            } else if (edge.b == vertexIndex) {
                connected.add(edge.a);
            } else {
                continue;
            }
        }
        int[] connectedArray = new int[connected.size()];
        int i = 0;
        for (int connectedVertexIndex : connected) {
            connectedArray[i] = connectedVertexIndex;
            i++;
        }
        return connectedArray;
    }
    
    public String toString() {
        return edges.toString();
    }
    
}
