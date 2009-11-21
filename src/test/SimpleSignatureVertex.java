package test;

import signature.ISignatureVertex;

public class SimpleSignatureVertex implements ISignatureVertex {
	
	private Vertex vertex;
	
	public SimpleSignatureVertex(Vertex vertex) {
		this.vertex = vertex;
	}
	
	public Object getWrappedVertex() {
		return this.vertex;
	}

}
