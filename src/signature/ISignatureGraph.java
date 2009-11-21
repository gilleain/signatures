package signature;

import java.util.List;

/**
 * A wrapper interface around the input graph. Implementing classes wrap an
 * instance of whatever data structure is used as input to the signature
 * building code.
 * 
 * @author maclean
 *
 */
public interface ISignatureGraph {
	
	/**
	 * Get the vertices connected to the vertex <code>vertex</code>.
	 * @param vertex the vertex to get connected vertices from
	 * @return a list of connected vertices
	 */
	public List<ISignatureVertex> getConnected(ISignatureVertex vertex);
	
	/**
	 * Get the vertex associated with this node.
	 * @param node the DAG node that refers to the vertex
	 * @return a signature vertex
	 */
	public ISignatureVertex getVertex(DAG.Node node);

}
