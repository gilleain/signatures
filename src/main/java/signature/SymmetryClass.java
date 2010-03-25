package signature;

import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * A collection of vertex indices with the same canonical signature string.
 * 
 * @author maclean
 *
 */
public class SymmetryClass implements Comparable<SymmetryClass>, Iterable<Integer> {
    
    /**
     * The signature string that the vertices all share
     */
    private final String signatureString;
    
    /**
     * The set of vertex indices that have this signature string
     */
    private final SortedSet<Integer> vertexIndices;

    /**
     * Make a symmetry class for the signature string 
     * <code>signatureString</code>.
     * @param signatureString the signature string for this symmetry class
     */
    public SymmetryClass(String signatureString) {
       this.signatureString = signatureString;
       this.vertexIndices = new TreeSet<Integer>();
    }
    
    public Iterator<Integer> iterator() {
        return this.vertexIndices.iterator();
    }

    public int size() {
        return vertexIndices.size();
    }

    public String getSignatureString() {
        return this.signatureString;
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
    
    /**
     * If the vertex indexed by <code>vertexIndex</code> is in the symmetry 
     * class then return the smaller of it and the lowest element. If it is not
     * in the symmetry class, return -1.
     * 
     * @param vertexIndex
     * @return
     */
    public int getMinimal(int vertexIndex, List<Integer> used) {
        int min = -1;
        for (int classIndex : this.vertexIndices) {
            if (classIndex == vertexIndex) {
                if (min == -1) {
                    return vertexIndex;
                } else {
                    return min;
                }
            } else {
                if (used.contains(classIndex)) {
                    continue;
                } else {
                    min = classIndex;
                }
            }
        }
        
        // the vertexIndex is not in the symmetry class
        return -1;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(SymmetryClass o) {
        return this.signatureString.compareTo(o.signatureString);
    }
    
    public String toString() {
        return this.signatureString + " " + this.vertexIndices;
    }
    
}
