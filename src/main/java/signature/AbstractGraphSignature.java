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
    
    private String graphSignature; // XXX
    
    private List<List<Integer>> canonicalLabelMapping;  // XXX
    
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
        
        this.canonicalLabelMapping = new ArrayList<List<Integer>>();
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
     * Generate and return an AbstractVertexSignature rooted at the vertex with
     * index <code>vertexIndex</code>.
     * 
     * @param vertexIndex the vertex to use
     * @return an AbstractSignature object
     */
    public abstract AbstractVertexSignature signatureForVertex(int vertexIndex);
    
    /**
     * Run through the vertices of the graph, generating a signature string for
     * each vertex, and return the one that is lexicographically minimal.
     * 
     * @return the lexicographically minimal vertex string
     */
    public String toCanonicalString() {
        String canonicalString = null;
        for (int i = 0; i < this.getVertexCount(); i++) {
            String signatureString = this.signatureStringForVertex(i);
            if (canonicalString == null ||
                    canonicalString.compareTo(signatureString) < 0) {
                canonicalString = signatureString; 
            }
        }
        if (canonicalString == null) {
            return "";
        } else {
            return canonicalString;
        }
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
        return getSymmetryClasses(-1);
    }
        
    public List<SymmetryClass> getSymmetryClasses(int height) {
        List<SymmetryClass> symmetryClasses = new ArrayList<SymmetryClass>();
        for (int i = 0; i < this.getVertexCount(); i++) {
            String signatureString = this.signatureStringForVertex(i, height);
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
    
    /**
     * Use the lexicographically largest (or smallest) as the graph signature
     */
    public String getGraphSignature(){
        // Generates and returns a graph signature
        List<String> vertexSignatures = this.getVertexSignatureStrings();
        Collections.sort(vertexSignatures);
        this.graphSignature = vertexSignatures.get(vertexSignatures.size() - 1);
        return this.graphSignature;
    }

    /**
     * Create the canonical signature strings for each vertex. They are 
     * unsorted, so will be in the same order as the vertices.
     * 
     * @return a list of canonical signature strings
     */
    public List<String> getVertexSignatureStrings() {
        List<String> vertexSignatures = new ArrayList<String>();
        for (int i = 0; i < this.getVertexCount(); i++) {
            vertexSignatures.add(this.signatureStringForVertex(i));
        }
        return vertexSignatures;
    }
    
    /**
     * Create a list of vertex signatures, one for each vertex.They are 
     * unsorted, so will be in the same order as the vertices.
     * 
     * @return a list of vertex signatures
     */
    public List<AbstractVertexSignature> getVertexSignatures() {
        List<AbstractVertexSignature> signatures = 
            new ArrayList<AbstractVertexSignature>();
        for (int i = 0; i < this.getVertexCount(); i++) {
            signatures.add(this.signatureForVertex(i));
        }
        return signatures;
    }
    
    /**
     * Test the the vertices in the graph, to see if the order they are in
     * (confusingly called the 'labelling' of the graph) is canonical. The 
     * order that is canonical according to this method may not be the same as
     * the canonical order from another method.
     * 
     * @return true if the vertices are in a canonical order
     */
    public boolean isCanonicallyLabelled() {
        
        // get the first signature string, to compare with the others
        AbstractVertexSignature first = this.signatureForVertex(0); 
        String firstString = first.toCanonicalString();

        // the vertex indices must be ordered
        List<Integer> labels = first.getCanonicalLabelMapping();
        List<Integer> postLabels = first.postorderCanonicalLabelling(); 
        if (isInIncreasingOrder(labels) || isInIncreasingOrder(postLabels)) {
            
            // check that no subsequent string is lexicographically smaller
            for (int i = 1; i < this.getVertexCount(); i++) {
                AbstractVertexSignature a = this.signatureForVertex(i);
                if (firstString.compareTo(a.toCanonicalString()) > 0) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
    
    public void reconstructCanonicalGraph(
            AbstractVertexSignature signature, AbstractGraphBuilder builder) {
        String canonicalString = this.toCanonicalString();
        ColoredTree tree = AbstractVertexSignature.parse(canonicalString);
        builder.makeFromColoredTree(tree);
    }
    
    public List<Integer> canonicalLabel() {
        List<Integer> mapping = new ArrayList<Integer>();

        int elementToChange = 0;
        while (!this.isCanonicallyLabelled()) {
            // Reorder the vertices of the graph.
            // Look for the vertexSignature corresponding to the graphSignature 
            // that has the lowest vertexId in the elementToChange position.
            int el = 0; 
            int minValue = this.getVertexCount();
            List<Integer> currentLabels =  this.canonicalLabelMapping.get(el);
            for (String vertexSignature : this.getVertexSignatureStrings()) {
                if ( this.graphSignature.equals(vertexSignature) ) {
                    int i = currentLabels.get(elementToChange);
                    if (minValue > i) {
                        minValue = i;
                    }
                }
                el++;
            }
            // Swap the order of vertexId minValue and elementToChange.
            // do the swapping here.
            mapping.add(minValue);
            elementToChange++;
        }

        return mapping;
    }
    
    public boolean isInIncreasingOrder(List<Integer> integerList) {
        for (int i = 1; i < integerList.size(); i++) {
            if (integerList.get(i - 1) > integerList.get(i)) {
                return false;
            }
        }
        return true;
    }
    
    public String reconstructCanonicalEdgeString() {
        String canonicalString = this.toCanonicalString();
        VirtualGraphBuilder builder = new VirtualGraphBuilder();
        
        builder.makeFromColoredTree(
                AbstractVertexSignature.parse(canonicalString));
        return builder.toEdgeString();
    }
 
}
