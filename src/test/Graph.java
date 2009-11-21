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
	
	public Graph(String graphString) {
		this.edges = new ArrayList<Edge>();
		for (String edgeString : graphString.split(":")) {
			String[] v = edgeString.split("-");
			this.edges.add(new Edge(new Vertex(v[0]), new Vertex(v[1])));
		}
		
	}
	
	public Vertex get(Vertex v) {
		for (Edge e : this.edges) {
			if (e.a == v) return e.a;
			if (e.b == v) return e.b;
		}
		return null;
	}
	
	public Vertex first() {
		return this.edges.get(0).a;
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
