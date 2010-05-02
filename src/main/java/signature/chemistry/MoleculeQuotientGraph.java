package signature.chemistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import signature.AbstractQuotientGraph;
import signature.SymmetryClass;

public class MoleculeQuotientGraph extends AbstractQuotientGraph {
    
    private Molecule molecule;
    
    public MoleculeQuotientGraph(Molecule molecule) {
        this.molecule = molecule;
        MoleculeSignature molSig = new MoleculeSignature(molecule);
        super.construct(molSig.getSymmetryClasses());
    }
    
    public MoleculeQuotientGraph(Molecule molecule, List<String> sigStrings) {
        this.molecule = molecule;
        HashMap<String, List<Integer>> signatureCounts = 
            new HashMap<String, List<Integer>>();
        int i = 0;
        for (String sig : sigStrings) {
            if (!signatureCounts.containsKey(sig)) {
                signatureCounts.put(sig, new ArrayList<Integer>());
            }
            signatureCounts.get(sig).add(i);
            i++;
        }
        List<SymmetryClass> symmetryClasses = new ArrayList<SymmetryClass>();
        for (String sig : signatureCounts.keySet()) {
            SymmetryClass symmetryClass = new SymmetryClass(sig);
            for (int v : signatureCounts.get(sig)) {
                symmetryClass.addIndex(v);
            }
            symmetryClasses.add(symmetryClass);
        }
        super.construct(symmetryClasses);
    }

    @Override
    public boolean isConnected(int i, int j) {
        return molecule.isConnected(i, j);
    }

}
