package signature.simple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A very simple graph class - the equivalent of a client library class.
 * 
 * @author maclean
 *
 */
public class SimpleGraph {
    
    public class Edge implements Comparable<Edge> {
        public int a;
        public int b;
        
        public Edge(int a, int b) {
            if (a < b) {
                this.a = a;
                this.b = b;
            } else {
                this.a = b;
                this.b = a;
            }
        }
        
        public int compareTo(Edge other) {
            if (this.a < other.a || (this.a == other.a && this.b < other.b)) {
                return -1;
            } else {
                if (this.a == other.a && this.b == other.b) {
                    return 0;
                } else {
                    return 1;
                }
            }
        }
        
        public String toString() {
            return this.a + "-" + this.b;
        }

    }
    
    public List<Edge> edges;
    
    public int maxVertexIndex;
    
    public String name;
    
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
    
    public SimpleGraph(SimpleGraph graph, int[] permutation) {
        this();
        for (Edge e : graph.edges) {
            makeEdge(permutation[e.a], permutation[e.b]);
        }
    }

    public void makeEdge(int a, int b) {
        if (a > maxVertexIndex) maxVertexIndex = a;
        if (b > maxVertexIndex) maxVertexIndex = b;
        this.edges.add(new Edge(a, b));
    }
    
    public void makeEdges(int a, int... bs) {
    	for (int b : bs) {
    		makeEdge(a, b);
    	}
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
    
    public int degree(int vertexIndex) {
        int degreeCount = 0;
        for (Edge e : edges) {
            if (e.a == vertexIndex || e.b == vertexIndex) {
                degreeCount++;
            }
        }
        return degreeCount;
    }

    public String toString() {
        Collections.sort(edges);
        return edges.toString();
    }
    
}
