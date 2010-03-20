package signature.chemistry;

import signature.AbstractVertexSignature;

public class AtomSignature extends AbstractVertexSignature {
    
    private Molecule molecule;
    
    public AtomSignature(Molecule molecule, int atomNumber) {
        super("[", "]");
        this.molecule = molecule;
        this.create(atomNumber);
    }
    
    public AtomSignature(Molecule molecule, int atomNumber, int height) {
        super("[", "]");
        this.molecule = molecule;
        this.create(atomNumber, height);
    }

    @Override
    public int[] getConnected(int vertexIndex) {
        return this.molecule.getConnected(vertexIndex);
    }

    @Override
    public String getEdgeSymbol(int vertexIndex, int otherVertexIndex) {
        return "";
    }

    @Override
    public String getVertexSymbol(int vertexIndex) {
        return this.molecule.getSymbolFor(vertexIndex);
    }

}
