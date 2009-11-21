package test;

import java.util.ArrayList;
import java.util.List;

public class Graph {
	
	public class Edge {
		
		public Vertex a;
		
		public Vertex b;
		
		public Edge(Vertex a, Vertex b) {
			this.a = a;
			this.b = b;
		}
	}
	
	public List<Edge> edges;
	
	public Graph() {
		this.edges = new ArrayList<Edge>();
	}
	
	public void addEdge(Vertex a, Vertex b) {
		this.edges.add(new Edge(a, b));
	}

	public List<Vertex> getConnected(Vertex innerVertex) {
		List<Vertex> connected = new ArrayList<Vertex>();
		for (Edge edge : edges) {
			if (edge.a == innerVertex) {
				connected.add(edge.b);
			} else if (edge.b == innerVertex) {
				connected.add(edge.a);
			} else {
				continue;
			}
		}
		return connected;
	}

}
