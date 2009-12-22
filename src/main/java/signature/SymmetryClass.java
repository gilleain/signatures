package signature;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of vertex indices with the same canonical signature string.
 * 
 * @author maclean
 *
 */
public class SymmetryClass implements Comparable<SymmetryClass> {
    
    /**
     * The signature string that the vertices all share
     */
    private final String signatureString;
    
    /**
     * The list of vertex indices that have this signature string
     */
    private final List<Integer> vertexIndices;

    /**
     * Make a symmetry class for the signature string 
     * <code>signatureString</code>.
     * @param signatureString the signature string for this symmetry class
     */
    public SymmetryClass(String signatureString) {
       this.signatureString = signatureString;
       this.vertexIndices = new ArrayList<Integer>();
    }
    
    /**
     * Check that the symmetry class' string is the same as the supplied string.
     * 
     * @param otherSignatureString the string to check
     * @return true if the strings are equal
     */
    public boolean hasSignature(String otherSignatureString) {
        return this.signatureString.equals(otherSignatureString);
    }
    
    /**
     * Add a vertex index to the list.
     * 
     * @param vertexIndex the vertex index to add
     */
    public void addIndex(int vertexIndex) {
        this.vertexIndices.add(vertexIndex);
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(SymmetryClass o) {
        return this.signatureString.compareTo(o.signatureString);
    }
    
}
