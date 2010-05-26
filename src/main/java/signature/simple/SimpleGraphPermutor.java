package signature.simple;

import java.util.Iterator;

import signature.Permutor;

public class SimpleGraphPermutor extends Permutor 
       implements Iterator<SimpleGraph> {
    
    private SimpleGraph graph;

    public SimpleGraphPermutor(SimpleGraph graph) {
        super(graph.getVertexCount());
        this.graph = graph; 
    }

    public SimpleGraph next() {
        int[] nextPermutation = super.getNextPermutation();
        return new SimpleGraph(graph, nextPermutation);
    }

    public void remove() {
        super.setRank(super.getRank() + 1);
    }

}
