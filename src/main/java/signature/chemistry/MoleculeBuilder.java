package signature.chemistry;

import signature.AbstractGraphBuilder;
import signature.ColoredTree;

public class MoleculeBuilder extends AbstractGraphBuilder {
    
    private Molecule molecule;
    
    public MoleculeBuilder() {
        super();
    }

    @Override
    public void makeEdge(int vertexIndex1, int vertexIndex2, String symbolA, String symbolB) {
        this.molecule.addBond(vertexIndex1, vertexIndex2, 1);   // TODO : order
    }

    @Override
    public void makeGraph() {
        this.molecule = new Molecule();
    }

    @Override
    public void makeVertex(String label) {
        this.molecule.addAtom(label);
    }
    
    public Molecule fromTree(ColoredTree tree) {
        super.makeFromColoredTree(tree);
        return this.molecule;
    }

    public Molecule getMolecule() {
        return this.molecule;
    }
}
