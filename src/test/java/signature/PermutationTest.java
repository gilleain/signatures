package signature;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import signature.chemistry.AtomPermutor;
import signature.chemistry.Molecule;
import signature.chemistry.MoleculeSignature;

public class PermutationTest {
    
    public void permuteCompletely(Molecule molecule) {
        String signature = 
            new MoleculeSignature(molecule).getMolecularSignature();
        int[] identity = new int[molecule.getAtomCount()];
        for (int i = 0; i < molecule.getAtomCount(); i++) { identity[i] = i; }
        System.out.println(molecule + "\t" 
                + Arrays.toString(identity) + "\t" + signature);
        
        AtomPermutor permutor = new AtomPermutor(molecule);
        while (permutor.hasNext()) {
            Molecule permutedMolecule = permutor.next();
            String permutedSignature = 
                new MoleculeSignature(permutedMolecule).getMolecularSignature();
            System.out.println(permutedMolecule 
                    + "\t" + Arrays.toString(permutor.getCurrentPermutation())
                    + "\t" + permutedSignature);
            Assert.assertEquals(signature, permutedSignature);
        }
    }
    
    @Test
    public void permuteCNOMolecule() {
        Molecule molecule = new Molecule();
        molecule.addAtom("C");
        molecule.addAtom("N");
        molecule.addAtom("O");
        molecule.addBond(0, 1, 1);
        molecule.addBond(1, 2, 1);
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
        molecule.addBond(0, 1, 2);
        molecule.addBond(1, 2, 1);
        molecule.addBond(2, 3, 1);
        molecule.addBond(3, 4, 1);
        molecule.addBond(4, 5, 1);
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
        molecule.addBond(0, 1, 1);
        molecule.addBond(1, 2, 1);
        molecule.addBond(2, 3, 1);
        molecule.addBond(3, 4, 1);
        molecule.addBond(4, 5, 2);
        permuteCompletely(molecule);
    }
    

}
