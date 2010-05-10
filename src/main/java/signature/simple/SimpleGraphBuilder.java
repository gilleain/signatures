package signature.simple;

import signature.AbstractGraphBuilder;
import signature.ColoredTree;

public class SimpleGraphBuilder extends AbstractGraphBuilder {
    
    private SimpleGraph graph;
    
    public SimpleGraphBuilder() {
        super();
    }

    @Override
    public void makeEdge(
            int vertexIndex1, int vertexIndex2, String a, String b, String edgeLabel) {
        this.graph.makeEdge(vertexIndex1, vertexIndex2);
    }

    @Override
    public void makeGraph() {
        this.graph = new SimpleGraph();
    }

    @Override
    public void makeVertex(String label) {
        // oddly, do nothing
    }
    
    public SimpleGraph fromTree(ColoredTree tree) {
        super.makeFromColoredTree(tree);
        return this.graph;
    }

}
