package signature.chemistry;

import signature.AbstractVertexSignature;
import signature.ColoredTree;

public class AtomSignature extends AbstractVertexSignature {
    
    private Molecule molecule;
    
    public AtomSignature(Molecule molecule, int atomNumber) {
        super();
        this.molecule = molecule;
        this.createMaximumHeight(atomNumber, molecule.getAtomCount());
    }
    
    public AtomSignature(Molecule molecule, int atomNumber, int height) {
        super();
        this.molecule = molecule;
        this.create(atomNumber, molecule.getAtomCount(), height);
    }

    @Override
    public int[] getConnected(int vertexIndex) {
        return this.molecule.getConnected(vertexIndex);
    }

    @Override
    public String getEdgeLabel(int vertexIndex, int otherVertexIndex) {
        int bondOrder = molecule.getBondOrder(vertexIndex, otherVertexIndex);
//        return "";
        switch (bondOrder) {
//            case 1: return "-";
            case 1: return "";
            case 2: return "=";
            case 3: return "#";
            default: return "";
        }
    }

    @Override
    public String getVertexSymbol(int vertexIndex) {
        return this.molecule.getSymbolFor(vertexIndex);
    }
    
    public static ColoredTree parse(String stringForm) {
        return AbstractVertexSignature.parse(stringForm);
    }

}
