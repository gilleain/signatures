package signature;

public interface Visitable {

    public void accept(DAGVisitor visitor);
}
