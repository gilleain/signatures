package signature.implementation.chemistry;

import signature.AbstractSignature;

public class MoleculeSignature extends AbstractSignature {
    
    private Molecule molecule;
    
    public MoleculeSignature(Molecule molecule) {
        super("[", "]");
        this.molecule = molecule;
        super.create(0);
    }

    @Override
    public int[] getConnected(int vertexIndex) {
        return this.molecule.getConnected(vertexIndex);
    }

    @Override
    public String getEdgeSymbol(int vertexIndex, int otherVertexIndex) {
        int bondOrder = 
            this.molecule.getBondOrder(vertexIndex, otherVertexIndex);
        return "";
//        switch (bondOrder) {
//            case 1: return "-";
//            case 2: return "=";
//            case 3: return "#";
//            default: return "";
//        }
    }

    @Override
    public int getVertexCount() {
        return this.molecule.getAtomCount();
    }

    @Override
    public String getVertexSymbol(int vertexIndex) {
        return this.molecule.getSymbolFor(vertexIndex);
    }

    public String getMolecularSignature() {
        return super.getGraphSignature();
    }

}
