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
    
    public AbstractGraphSignature() {
        this.separator = " + ";
    }
    
    public abstract int getVertexCount();
    
    public abstract String signatureStringForVertex(int i);
    
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
