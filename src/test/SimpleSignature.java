package test;

import signature.DAG;

public class SimpleSignature {
	
	public SimpleSignature(Graph graph, Vertex root) {
		SimpleSignatureGraph graphWrapper = new SimpleSignatureGraph(graph);
		SimpleSignatureVertex rootWrapper = 
			(SimpleSignatureVertex) graphWrapper.get(root); 
		DAG dag = new DAG(graphWrapper, rootWrapper);
	}

}
