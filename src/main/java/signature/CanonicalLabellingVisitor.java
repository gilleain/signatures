package signature;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class CanonicalLabellingVisitor implements DAGVisitor {
    
    private int[] labelling;
    
    private int currentLabel;
    
    private Comparator<DAG.Node> comparator;
    
    
    public CanonicalLabellingVisitor(
            int vertexCount, Comparator<DAG.Node> comparator) {
        labelling = new int[vertexCount];
        Arrays.fill(labelling, -1);
        currentLabel = 0;
    }

    public void visit(DAG.Node node) {
        // only label if this vertex has not yet been labeled
        if (this.labelling[node.vertexIndex] == -1) {
            this.labelling[node.vertexIndex] = this.currentLabel;
            this.currentLabel++;
        }
        Collections.sort(node.children, comparator);
        for (DAG.Node child : node.children) {
            child.accept(this);
        }
    }
    
    public int[] getLabelling() {
        return this.labelling;
    }

}
