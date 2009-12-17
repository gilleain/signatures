package signature;

public interface VisitableDAG {

    public void accept(DAGVisitor visitor);
}
