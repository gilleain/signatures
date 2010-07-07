package signature.chemistry;

import signature.AbstractGraphBuilder;
import signature.ColoredTree;
import signature.chemistry.Molecule.BondOrder;

public class MoleculeBuilder extends AbstractGraphBuilder {
    
    private Molecule molecule;
    
    public MoleculeBuilder() {
        super();
    }

    @Override
    public void makeEdge(
            int vertexIndex1, int vertexIndex2, 
            String symbolA, String symbolB, String edgeLabel) {
        if (edgeLabel.equals("")) {
            this.molecule.addBond(vertexIndex1, vertexIndex2, BondOrder.SINGLE);
        } else if (edgeLabel.equals("=")) {
            this.molecule.addBond(vertexIndex1, vertexIndex2, BondOrder.DOUBLE);
        } else if (edgeLabel.equals("#")) {
            this.molecule.addBond(vertexIndex1, vertexIndex2, BondOrder.TRIPLE);
        }
        
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
