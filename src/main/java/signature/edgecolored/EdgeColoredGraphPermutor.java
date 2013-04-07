package signature.edgecolored;

import java.util.Iterator;

import signature.Permutor;

public class EdgeColoredGraphPermutor extends Permutor 
       implements Iterator<EdgeColoredGraph> {
    
    private EdgeColoredGraph graph;

    public EdgeColoredGraphPermutor(EdgeColoredGraph graph) {
        super(graph.getVertexCount());
        this.graph = graph; 
    }

    public EdgeColoredGraph next() {
        int[] nextPermutation = super.getNextPermutation();
        return new EdgeColoredGraph(graph, nextPermutation);
    }

    public void remove() {
        super.setRank(super.getRank() + 1);
    }

}
