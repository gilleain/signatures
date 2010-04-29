package signature.chemistry;


import java.util.List;

import org.junit.Test;

import signature.SymmetryClass;
import signature.chemistry.Molecule;
import signature.chemistry.MoleculeSignature;

public class LargeMoleculeTest {
    
    public void addRing(int atomToAttachTo, int ringSize, Molecule molecule) {
        int numberOfAtoms = molecule.getAtomCount();
        int previous = atomToAttachTo;
        for (int i = 0; i < ringSize; i++) {
            molecule.addAtom("C");
            int current = numberOfAtoms + i;
            molecule.addSingleBond(previous, current);
            previous = current;
        }
        molecule.addSingleBond(numberOfAtoms, numberOfAtoms + (ringSize - 1));
    }
    
    public Molecule makeMinimalMultiRing(int ringCount, int ringSize) {
        Molecule mol = new Molecule();
        mol.addAtom("C");
        for (int i = 0; i < ringCount; i++) {
            addRing(0, ringSize, mol);
        }
        return mol;
    }
    
    public Molecule makeTetrakisTriphenylPhosphoranylRhodium() {
        Molecule ttpr = new Molecule();
        ttpr.addAtom("Rh");
        int phosphateCount = 2;
        for (int i = 1; i <= phosphateCount; i++) {
            ttpr.addAtom("P");
            ttpr.addSingleBond(0, i);
        }
        
        int phenylCount = 3;
        for (int j = 1; j <= phosphateCount; j++) {
            for (int k = 0; k < phenylCount; k++) {
                addRing(j, 6, ttpr);
            }
        }
        return ttpr;
    }
    
    @Test
    public void ttprTest() {
        Molecule ttpr = makeTetrakisTriphenylPhosphoranylRhodium();
        MoleculeSignature molSig = new MoleculeSignature(ttpr);
//        String sigString = molSig.toCanonicalString();
        String sigString = molSig.signatureStringForVertex(0);
        System.out.println(sigString);
    }
    
    @Test
    public void testMinimalMol() {
        Molecule mol = makeMinimalMultiRing(5, 3);
        MoleculeSignature molSig = new MoleculeSignature(mol);
//        String sigString = molSig.toCanonicalString();
        String sigString = molSig.signatureStringForVertex(0);
        System.out.println(sigString);

        System.out.println(mol);
        System.out.println("result " + sigString);
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
        int length = 10;
        Molecule chain = makeChain(length);
        MoleculeSignature molSig = new MoleculeSignature(chain);
        String sigString = molSig.toCanonicalString();
        System.out.println(sigString);
    }
    
    @Test
    public void buckballTest() {
        Molecule molecule = MoleculeReader.readMolfile("data/buckyball.mol");
        MoleculeSignature molSig = new MoleculeSignature(molecule);
//        String signature = molSig.getMolecularSignature();
//        System.out.println(signature);
        List<SymmetryClass> symmetryClasses = molSig.getSymmetryClasses();
        for (SymmetryClass symmetryClass : symmetryClasses) {
//            System.out.println(symmetryClass);
        }
    }
    
    public static void main(String[] args) {
        new LargeMoleculeTest().testMinimalMol();
//        new LargeMoleculeTest().ttprTest();
    }

}
