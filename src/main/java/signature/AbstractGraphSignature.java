package signature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A signature for an entire graph.
 * 
 * @author maclean
 *
 */
public abstract class AbstractGraphSignature {
    
    /**
     * The separator is printed between vertex signature strings
     */
    private final String separator;
    
    /**
     * This is the height the signature is created with, which cannot be 
     * exceeded.
     */
    private int height;
    
    /**
     * Create a graph signature with a default separator.
     */
    public AbstractGraphSignature() {
        this(" + ", -1);
    }
    
    /**
     * Create a graph signature with the given separator.
     * 
     * @param separator the separator to use
     */
    public AbstractGraphSignature(String separator) {
        this(separator, -1);
    }
    
    /**
     * Create a graph signature with a default separator and the given height.
     * 
     * @param height the height of the vertex signatures made from this graph.
     */
    public AbstractGraphSignature(int height) {
        this(" + ", height);
    }
    
    /**
     * Create a graph signature with the given separator and height.
     * 
     * @param separator the separator to use
     * @param height the height of the vertex signatures made from this graph.
     */
    public AbstractGraphSignature(String separator, int height) {
        this.separator = separator;
        this.height = height;
    }
    
    /**
     * Get the height that the graph signature was created with.
     *  
     * @return the height
     */
    public int getHeight() {
        return this.height;
    }
    
    /**
     * Get the vertex count of the graph that this is the signature of.
     * 
     * @return the vertex count
     */
    public abstract int getVertexCount();
    
    /**
     * Return the canonical signature string for the vertex at index 
     * <code>vertexIndex</code> in the graph.
     * 
     * @param vertexIndex the vertex index
     * @return the canonical signature string for this vertex
     */
    public abstract String signatureStringForVertex(int vertexIndex);
    
    /**
     * Return the canonical signature string for the vertex at index 
     * <code>vertexIndex</code> in the graph with a height of 
     * <code>height</code>.
     * 
     * @param vertexIndex the vertex index
     * @param height the maximum height of the signature
     * @return the signature at the given height for a vertex 
     */
    public abstract String signatureStringForVertex(int vertexIndex, int height);
    
    /**
     * Run through the vertices of the graph, generating a signature string for
     * each vertex, and return the one that is lexicographically minimal.
     * 
     * @return the lexicographically minimal vertex string
     */
    public String toCanonicalString() {
        String canonicalString = "";
        for (int i = 0; i < this.getVertexCount(); i++) {
            String signatureString = this.signatureStringForVertex(i);
            if (canonicalString.compareTo(signatureString) < 0) {
                canonicalString = signatureString; 
            }
        }
        return canonicalString;
    }
    
    /**
     * For all the vertices in the graph, get the signature string and group the
     * resulting list of strings into symmetry classes. All vertices in one
     * symmetry class will have the same signature string, and therefore the
     * same environment.
     *  
     * @return a list of symmetry classes
     */
    public List<SymmetryClass> getSymmetryClasses() {
        List<SymmetryClass> symmetryClasses = new ArrayList<SymmetryClass>();
        for (int i = 0; i < this.getVertexCount(); i++) {
            String signatureString = this.signatureStringForVertex(i);
            SymmetryClass foundClass = null;
            for (SymmetryClass symmetryClass : symmetryClasses) {
                if (symmetryClass.hasSignature(signatureString)) {
                    foundClass = symmetryClass;
                    break;
                }
            }
            if (foundClass == null) {
                foundClass = new SymmetryClass(signatureString);
                symmetryClasses.add(foundClass);
            } 
            foundClass.addIndex(i);
        }
        return symmetryClasses;
    }

    /**
     * Generate signature strings for each vertex of the graph, and count up
     * how many of each there are, printing out a final string concatenated
     * together with the separator.
     * 
     * @return a full signature string for this graph
     */
    public String toFullString() {
        Map<String, Integer> sigmap = new HashMap<String, Integer>(); 
        for (int i = 0; i < this.getVertexCount(); i++) {
            String signatureString = this.signatureStringForVertex(i);
            if (sigmap.containsKey(signatureString)) {
                int count = sigmap.get(signatureString);
                sigmap.put(signatureString, count + 1);
            } else {
                sigmap.put(signatureString, 1);
            }
        }
        List<String> keyList = new ArrayList<String>();
        keyList.addAll(sigmap.keySet());
        Collections.sort(keyList);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < keyList.size() - 1; i++) {
            String signature = keyList.get(i);
            int count = sigmap.get(signature);
            buffer.append(count).append(signature).append(this.separator);
        }
        String finalSignature = keyList.get(keyList.size() - 1);
        int count = sigmap.get(finalSignature);
        buffer.append(count).append(finalSignature);
        return buffer.toString();
    }
}
