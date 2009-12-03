package signature.implementation.chemistry;

import signature.AbstractSignature;

public class MoleculeSignature extends AbstractSignature {
    
    private Molecule molecule;
    
    public MoleculeSignature(Molecule molecule) {
        this.molecule = molecule;
    }

    @Override
    public int[] getConnected(int vertexIndex) {
        return this.molecule.getConnected(vertexIndex);
    }

    @Override
    public String getEdgeSymbol(int vertexIndex, int otherVertexIndex) {
        int bondOrder = 
            this.molecule.getBondOrder(vertexIndex, otherVertexIndex);
        switch (bondOrder) {
            case 1: return "-";
            case 2: return "=";
            case 3: return "#";
            default: return "";
        }
    }

    @Override
    public int getVertexCount() {
        return this.molecule.getAtomCount();
    }

    @Override
    public String getVertexSymbol(int vertexIndex) {
        return this.molecule.getSymbolFor(vertexIndex);
    }

}
