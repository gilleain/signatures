package signature.chemistry;

import signature.AbstractGraphSignature;

public class MoleculeSignature extends AbstractGraphSignature {
    
    private Molecule molecule;
    
    public MoleculeSignature(Molecule molecule) {
        super(" + ", "[", "]");
        this.molecule = molecule;
    }

    public String getEdgeSymbol(int vertexIndex, int otherVertexIndex) {
//        int bondOrder = 
//            this.molecule.getBondOrder(vertexIndex, otherVertexIndex);
        return "";
//        switch (bondOrder) {
//            case 1: return "-";
//            case 2: return "=";
//            case 3: return "#";
//            default: return "";
//        }
    }
    
    public String getMolecularSignature() {
        return super.getGraphSignature();
    }

    @Override
    public int getVertexCount() {
        return this.molecule.getAtomCount();
    }

    @Override
    public String signatureStringForVertex(int vertexIndex) {
        AtomSignature atomSignature;
        int height = super.getHeight();
        if (height == -1) {
            atomSignature = new AtomSignature(this.molecule, vertexIndex);
        } else {
            atomSignature = new AtomSignature(this.molecule, vertexIndex, height);
        }
        return atomSignature.toCanonicalString();
    }

    @Override
    public String signatureStringForVertex(int vertexIndex, int height) {
        // TODO Auto-generated method stub
        return null;
    }

}
