package test;

import java.util.ArrayList;
import java.util.List;

import signature.ISignatureGraph;
import signature.ISignatureVertex;
import signature.DAG;

public class SimpleSignatureGraph implements ISignatureGraph {
	
	private Graph graph;
	
	public SimpleSignatureGraph(Graph graph) {
		this.graph = graph;
	}

	public List<ISignatureVertex> getConnected(ISignatureVertex vertex) {
		Vertex innerVertex = (Vertex) vertex.getWrappedVertex();
		
		List<Vertex> vertices = this.graph.getConnected(innerVertex);
		List<ISignatureVertex> signatureVertices = 
			new ArrayList<ISignatureVertex>();
		for (Vertex connected : vertices) {
			signatureVertices.add(new SimpleSignatureVertex(connected));
		}
		return signatureVertices;
	}

	public ISignatureVertex getVertex(DAG.Node node) {
		return node.getVertex();
	}

}
