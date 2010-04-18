package signature.chemistry;

import signature.AbstractVertexSignature;
import signature.ColoredTree;

public class AtomSignature extends AbstractVertexSignature {
    
    private Molecule molecule;
    
    public static final char START = '[';
    
    public static final char END = ']';
    
    public AtomSignature(Molecule molecule, int atomNumber) {
        super(AtomSignature.START, AtomSignature.END);
        this.molecule = molecule;
        this.createMaximumHeight(atomNumber, molecule.getAtomCount());
    }
    
    public AtomSignature(Molecule molecule, int atomNumber, int height) {
        super(AtomSignature.START, AtomSignature.END);
        this.molecule = molecule;
        this.create(atomNumber, molecule.getAtomCount(), height);
    }

    @Override
    public int[] getConnected(int vertexIndex) {
        return this.molecule.getConnected(vertexIndex);
    }

    @Override
    public String getEdgeLabel(int vertexIndex, int otherVertexIndex) {
        return "";
    }

    @Override
    public String getVertexSymbol(int vertexIndex) {
        return this.molecule.getSymbolFor(vertexIndex);
    }
    
    public static ColoredTree parse(String stringForm) {
        return AbstractVertexSignature.parseWithNodeSymbols(
                stringForm, AtomSignature.START, AtomSignature.END);
    }

}
