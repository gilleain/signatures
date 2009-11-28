package test;

import java.util.ArrayList;
import java.util.List;

import signature.AbstractSignature;

public class UnlabelledGraphTest {
	
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
	
	public class UnlabelledGraph {
		
		public List<Edge> edges;
		
		public int maxVertexIndex;
		
		public UnlabelledGraph(String graphString) {
			this.edges = new ArrayList<Edge>();
			for (String edgeString : graphString.split(",")) {
				String[] vertexStrings = edgeString.split(":");
				int a = Integer.parseInt(vertexStrings[0]);
				int b = Integer.parseInt(vertexStrings[1]);
				this.edges.add(new Edge(a, b));
				if (a > maxVertexIndex) maxVertexIndex = a;
				if (b > maxVertexIndex) maxVertexIndex = b;
			}
		}
		
		public int getVertexCount() {
		    return this.maxVertexIndex + 1;
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

	public class UnlabelledGraphSignature extends AbstractSignature {
		
		public UnlabelledGraph graph;
		
		public UnlabelledGraphSignature(UnlabelledGraph graph, int root) {
			super();
			this.graph = graph;
			super.create(root);
		}

		public int getVertexCount() {
		    return this.graph.getVertexCount();
		}
		
		public int[] getConnected(int vertexIndex) {
			return this.graph.getConnected(vertexIndex);
		}

		public String getEdgeSymbol(int vertexIndex, int otherVertexIndex) {
			return "";
		}

		public String getVertexSymbol(int vertexIndex) {
			return ".";
		}

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String chain = "0:1,0:2,0:3,0:4";
		UnlabelledGraphTest test = new UnlabelledGraphTest();
		UnlabelledGraph graph = test.new UnlabelledGraph(chain);
		UnlabelledGraphSignature signature = 
			test.new UnlabelledGraphSignature(graph, 0);
		System.out.println(signature.getDAG());
		System.out.println(graph + "\n" + signature.toString());
	}
}
