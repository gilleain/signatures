package signature.edgecolored;

import signature.AbstractGraphBuilder;
import signature.ColoredTree;

public class EdgeColoredGraphBuilder extends AbstractGraphBuilder {
    
    private EdgeColoredGraph graph;
    
    public EdgeColoredGraphBuilder() {
        super();
    }

    @Override
    public void makeEdge(
            int vertexIndex1, int vertexIndex2, String a, String b, String edgeLabel) {
        this.graph.makeEdge(vertexIndex1, vertexIndex2, edgeLabel);
    }

    @Override
    public void makeGraph() {
        this.graph = new EdgeColoredGraph();
    }

    @Override
    public void makeVertex(String label) {
        // oddly, do nothing
    }
    
    public EdgeColoredGraph fromTree(ColoredTree tree) {
        super.makeFromColoredTree(tree);
        return this.graph;
    }

}
