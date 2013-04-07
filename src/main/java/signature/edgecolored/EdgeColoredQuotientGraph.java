package signature.edgecolored;

import java.util.Map;

import signature.AbstractQuotientGraph;

public class EdgeColoredQuotientGraph extends AbstractQuotientGraph {
    
    private EdgeColoredGraph graph;
    
    public EdgeColoredQuotientGraph(EdgeColoredGraph graph, Map<String, Integer> colorMap) {
        super();
        this.graph = graph;
        
        EdgeColoredGraphSignature graphSignature = new EdgeColoredGraphSignature(graph, colorMap);
        super.construct(graphSignature.getSymmetryClasses());
    }
    
    public EdgeColoredQuotientGraph(EdgeColoredGraph graph, Map<String, Integer> colorMap, int height) {
        super();
        this.graph = graph;
        
        EdgeColoredGraphSignature graphSignature = new EdgeColoredGraphSignature(graph, colorMap);
        super.construct(graphSignature.getSymmetryClasses(height));
    }

    public boolean isConnected(int i, int j) {
        return graph.isConnected(i, j);
    }

}
