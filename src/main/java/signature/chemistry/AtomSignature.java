package signature.chemistry;

import signature.AbstractVertexSignature;

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
    
    public AtomSignature(Molecule molecule, int atomNumber, 
            int height, AbstractVertexSignature.InvariantType invariantType) {
        super(invariantType);
        this.molecule = molecule;
        this.create(atomNumber, molecule.getAtomCount(), height);
    }
    
    public int getIntLabel(int vertexIndex) {
        String symbol = getVertexSymbol(vertexIndex);
        
        // not exactly comprehensive...
        if (symbol.equals("H")) {
            return 1;
        } else if (symbol.equals("C")) {
            return 12;
        } else if (symbol.equals("O")) {
            return 16;
        } else {
            return -1;
        }
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

}
