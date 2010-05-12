package signature.chemistry;

import java.util.ArrayList;
import java.util.List;

public class ChiralCenterFinder {
    
    public static List<Integer> findTetrahedralChiralCenters(Molecule molecule) {
        List<Integer> chiralCenterIndices = new ArrayList<Integer>();
        MoleculeSignature molSig = new MoleculeSignature(molecule);
        List<String> signatureStrings = molSig.getVertexSignatureStrings();
        for (int i = 0; i < molecule.getAtomCount(); i++) {
            int[] connected = molecule.getConnected(i);
            if (connected.length < 4) {
                continue;
            } else {
                String s0 = signatureStrings.get(connected[0]);
                String s1 = signatureStrings.get(connected[1]);
                String s2 = signatureStrings.get(connected[2]);
                String s3 = signatureStrings.get(connected[3]);
                if (s0.equals(s1) 
                 || s0.equals(s2) 
                 || s0.equals(s3)
                 || s1.equals(s2)
                 || s1.equals(s3)
                 || s2.equals(s3)) {
                    continue;
                } else {
                    chiralCenterIndices.add(i);
                }
            }
        }
        
        return chiralCenterIndices;
    }

}
