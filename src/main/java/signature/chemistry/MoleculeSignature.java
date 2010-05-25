package signature.chemistry;

import signature.AbstractGraphSignature;
import signature.AbstractVertexSignature;

public class MoleculeSignature extends AbstractGraphSignature {
    
    private Molecule molecule;
    
    private boolean useStringLabels;
    
    public MoleculeSignature(Molecule molecule) {
        super(" + ");
        this.molecule = molecule;
    }
    
    public MoleculeSignature(Molecule molecule, boolean useStringLabels) {
        super(" + ");
        this.molecule = molecule;
        this.useStringLabels = useStringLabels;
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
            new AtomSignature(molecule, vertexIndex, height, useStringLabels);
        return atomSignature.toCanonicalString();
    }

    @Override
    public String signatureStringForVertex(int vertexIndex, int height) {
        AtomSignature atomSignature = 
            new AtomSignature(molecule, vertexIndex, height, useStringLabels);
        return atomSignature.toCanonicalString();
    }

    @Override
    public AbstractVertexSignature signatureForVertex(int vertexIndex) {
        return new AtomSignature(this.molecule, vertexIndex, -1, useStringLabels);
    }

}
