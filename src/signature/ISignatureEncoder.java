package signature;

/**
 * A signature encoder converts the {@link ISignatureVertex} instances into
 * strings.
 *   
 * @author maclean
 *
 */
public interface ISignatureEncoder {

	/**
	 * Convert a vertex object into its string form.
	 * 
	 * @param vertex the vertex to transform
	 * @return the string representation
	 */
	public String stringForVertex(ISignatureVertex vertex);
}
