package signature;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import signature.chemistry.AtomPermutor;
import signature.chemistry.Molecule;
import signature.chemistry.MoleculeSignature;
import signature.chemistry.Molecule.BondOrder;

public class PermutationTest {
    
    public String toSignatureString(Molecule molecule) {
        System.out.println(molecule);
        MoleculeSignature molSig = new MoleculeSignature(molecule);
        int i = 0;
        for (String signatureString : molSig.getVertexSignatureStrings()) {
            System.out.println(i + " " + signatureString);
            i++;
        }
        return molSig.getMolecularSignature();
    }
    
    public void printIdentity(Molecule molecule, String signature) {
        int[] identity = new int[molecule.getAtomCount()];
        for (int i = 0; i < molecule.getAtomCount(); i++) { identity[i] = i; }
        System.out.println(molecule + "\t" 
                + Arrays.toString(identity) + "\t" + signature);
    }
    
    public void testSpecificPermutation(Molecule molecule, int[] permutation) {
        String signature = 
            new MoleculeSignature(molecule).getMolecularSignature();
        printIdentity(molecule, signature);
        AtomPermutor permutor = new AtomPermutor(molecule);
        permutor.setPermutation(permutation);
        Molecule permutedMolecule = permutor.next();
        String permutedSignature = 
            new MoleculeSignature(permutedMolecule).getMolecularSignature();
        System.out.println(permutedMolecule 
                + "\t" + Arrays.toString(permutor.getCurrentPermutation())
                + "\t" + permutedSignature
                + "\t" + signature.equals(permutedSignature));
        Assert.assertEquals(signature, permutedSignature);
    }
    
    public void permuteCompletely(Molecule molecule) {
        String signature = 
            new MoleculeSignature(molecule).getMolecularSignature();
        printIdentity(molecule, signature);
        
        AtomPermutor permutor = new AtomPermutor(molecule);
        while (permutor.hasNext()) {
            Molecule permutedMolecule = permutor.next();
            String permutedSignature = 
                new MoleculeSignature(permutedMolecule).getMolecularSignature();
            System.out.println(permutedMolecule 
                    + "\t" + Arrays.toString(permutor.getCurrentPermutation())
                    + "\t" + permutedSignature
                    + "\t" + signature.equals(permutedSignature));
            Assert.assertEquals(signature, permutedSignature);
        }
    }
    
    @Test
    public void permuteCNOMolecule() {
        Molecule molecule = new Molecule();
        molecule.addAtom("C");
        molecule.addAtom("N");
        molecule.addAtom("O");
        molecule.addSingleBond(0, 1);
        molecule.addSingleBond(1, 2);
        permuteCompletely(molecule);
    }
    
    @Test
    public void permuteOCCCSC() {
        Molecule molecule = new Molecule();
        molecule.addAtom("O");
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addAtom("S");
        molecule.addAtom("C");
        molecule.addBond(0, 1, BondOrder.DOUBLE);
        molecule.addSingleBond(1, 2);
        molecule.addSingleBond(2, 3);
        molecule.addSingleBond(3, 4);
        molecule.addSingleBond(4, 5);
        permuteCompletely(molecule);
    }
    
    @Test
    public void permuteOCCOCO() {
        Molecule molecule = new Molecule();
        molecule.addAtom("O");
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addAtom("O");
        molecule.addAtom("C");
        molecule.addAtom("O");
        molecule.addSingleBond(0, 1);
        molecule.addSingleBond(1, 2);
        molecule.addSingleBond(2, 3);
        molecule.addSingleBond(3, 4);
        molecule.addBond(4, 5, BondOrder.DOUBLE);
//        molecule.addBond(4, 5, 1);
        permuteCompletely(molecule);
    }
    
    @Test
    public void doubleBondChainTest() {
        Molecule molecule = new Molecule();
        int chainLength = 6;
        molecule.addAtom("O");
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addAtom("O");
        molecule.addAtom("C");
        molecule.addAtom("O");
        for (int i = 0; i < chainLength - 2; i++) {
            molecule.addSingleBond(i, i+1);
        }
        molecule.addBond(chainLength - 2, chainLength - 1, BondOrder.DOUBLE);
        String sigA = toSignatureString(molecule);
//        permuteCompletely(molecule);
//        testSpecificPermutation(molecule, new int[] {0,1,3,4,2,5});
        Molecule moleculeB = new Molecule();
        moleculeB.addAtom("O");
        moleculeB.addAtom("C");
        moleculeB.addAtom("C");
        moleculeB.addAtom("C");
        moleculeB.addAtom("O");
        moleculeB.addAtom("O");
        moleculeB.addSingleBond(0, 1);
        moleculeB.addSingleBond(1, 3);
        moleculeB.addSingleBond(2, 4);
        moleculeB.addBond(2, 5, BondOrder.DOUBLE);
        moleculeB.addSingleBond(3, 4);
        
        String sigB = toSignatureString(moleculeB);
//        MoleculeSignature molSig = new MoleculeSignature(moleculeB);
//        molSig.signatureStringForVertex(1);
        Assert.assertEquals(sigA, sigB);
    }
}
