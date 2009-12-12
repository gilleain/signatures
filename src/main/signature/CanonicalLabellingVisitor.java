package signature;

import java.util.Arrays;

public class CanonicalLabellingVisitor implements DAGVisitor {
    
    private int[] labelling;
    
    private int currentLabel;
    
    public CanonicalLabellingVisitor(int vertexCount) {
        this.labelling = new int[vertexCount];
        Arrays.fill(this.labelling, -1);
        this.currentLabel = 0;
    }

    public void visit(DAG.Node node) {
        // only label if this vertex has not yet been labeled
        if (this.labelling[node.vertexIndex] == -1) {
            this.labelling[node.vertexIndex] = this.currentLabel;
            this.currentLabel++;
        }
        for (DAG.Node child : node.children) {
            child.accept(this);
        }
    }
    
    public int[] getLabelling() {
        return this.labelling;
    }

}
