package signature;

import org.junit.Test;

import signature.chemistry.Molecule;
import signature.chemistry.MoleculeSignature;

public class LargeMoleculeTest {
    
    public void addPhenyl(int atomToAttachTo, Molecule molecule) {
        int numberOfAtoms = molecule.getAtomCount();
        int previous = atomToAttachTo;
        for (int i = 0; i < 6; i++) {
            molecule.addAtom("C");
            molecule.addSingleBond(previous, numberOfAtoms + i);
        }
        molecule.addSingleBond(numberOfAtoms, numberOfAtoms + 5);
    }
    
    public Molecule makeTetrakisTriphenylPhosphoranylRhodium() {
        Molecule ttpr = new Molecule();
        ttpr.addAtom("Rh");
        for (int i = 1; i < 5; i++) {
            ttpr.addAtom("P");
            ttpr.addSingleBond(0, i);
        }
        
        for (int j = 1; j < 5; j++) {
            for (int k = 0; k < 3; k++) {
                addPhenyl(j, ttpr);
            }
        }
        return ttpr;
    }
    
    @Test
    public void ttprTest() {
        Molecule ttpr = makeTetrakisTriphenylPhosphoranylRhodium();
        MoleculeSignature molSig = new MoleculeSignature(ttpr);
        String sigString = molSig.toCanonicalString();
        System.out.println(sigString);
    }
    
    public Molecule makeChain(int length) {
        Molecule chain = new Molecule();
        int previous = -1;
        for (int i = 0; i < length; i++) {
            chain.addAtom("C");
            if (previous != -1) {
                chain.addSingleBond(previous, i);
            }
            previous = i;
        }
        return chain;
    }
    
    @Test
    public void testLongChains() {
        int length = 100;
        Molecule chain = makeChain(length);
        MoleculeSignature molSig = new MoleculeSignature(chain);
        String sigString = molSig.toCanonicalString();
        System.out.println(sigString);
    }

}
