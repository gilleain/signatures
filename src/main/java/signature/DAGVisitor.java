package signature;

import signature.DAG.Node;

public interface DAGVisitor {

    public void visit(Node node);
}
