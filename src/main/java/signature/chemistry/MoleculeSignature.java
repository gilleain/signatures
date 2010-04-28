package signature.chemistry;

import signature.AbstractGraphSignature;
import signature.AbstractVertexSignature;

public class MoleculeSignature extends AbstractGraphSignature {
    
    private Molecule molecule;
    
    public MoleculeSignature(Molecule molecule) {
        super(" + ");
        this.molecule = molecule;
    }
    
    public static boolean isCanonicallyLabelled(Molecule molecule) {
        return new MoleculeSignature(molecule).isCanonicallyLabelled();
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
        AtomSignature atomSignature;
        if (height == -1) {
            atomSignature = new AtomSignature(molecule, vertexIndex);
        } else {
            atomSignature = new AtomSignature(molecule, vertexIndex, height);
        }
        return atomSignature.toCanonicalString();
    }

    @Override
    public AbstractVertexSignature signatureForVertex(int vertexIndex) {
        return new AtomSignature(this.molecule, vertexIndex);
    }

}
