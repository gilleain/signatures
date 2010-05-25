package signature.chemistry;

import signature.AbstractGraphSignature;
import signature.AbstractVertexSignature;
import signature.AbstractVertexSignature.InvariantType;

public class MoleculeSignature extends AbstractGraphSignature {
    
    private Molecule molecule;
    
    private InvariantType invariantType;
    
    public MoleculeSignature(Molecule molecule) {
        this(molecule, InvariantType.STRING);
    }
    
    public MoleculeSignature(Molecule molecule, InvariantType invariantType) {
        super(" + ");
        this.molecule = molecule;
        this.invariantType = invariantType;
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
        int height = super.getHeight();
        AtomSignature atomSignature = 
            new AtomSignature(molecule, vertexIndex, height, invariantType);
        return atomSignature.toCanonicalString();
    }

    @Override
    public String signatureStringForVertex(int vertexIndex, int height) {
        AtomSignature atomSignature = 
            new AtomSignature(molecule, vertexIndex, height, invariantType);
        return atomSignature.toCanonicalString();
    }

    @Override
    public AbstractVertexSignature signatureForVertex(int vertexIndex) {
        return new AtomSignature(this.molecule, vertexIndex, -1, invariantType);
    }

}
