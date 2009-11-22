package test;

import signature.DAG;

public class SimpleSignature {
	
	private DAG dag;
	
	public SimpleSignature(Graph graph, Vertex root) {
		SimpleSignatureGraph graphWrapper = new SimpleSignatureGraph(graph);
		SimpleSignatureVertex rootWrapper = 
			(SimpleSignatureVertex) graphWrapper.get(root); 
		this.dag = new DAG(graphWrapper, rootWrapper);
	}
	
	public String toString() {
		return this.dag.toString();
	}

}
