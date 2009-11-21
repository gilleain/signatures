package test;

public class Test {
	
	public static void main(String[] args) {
		String graphString = "A-B:B-C:C-D";
		Graph graph = new Graph(graphString);
		
		Vertex root = graph.first();
		SimpleSignature signature = new SimpleSignature(graph, root);
	}

}
