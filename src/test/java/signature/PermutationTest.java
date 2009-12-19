package signature;

import java.util.Arrays;

import junit.framework.Assert;

import org.junit.Test;

import signature.chemistry.AtomPermutor;
import signature.chemistry.Molecule;
import signature.chemistry.MoleculeSignature;

public class PermutationTest {
    
    @Test
    public void permuteMolecule() {
        Molecule molecule = new Molecule();
        molecule.addAtom("C");
        molecule.addAtom("N");
        molecule.addAtom("O");
        molecule.addBond(0, 1, 1);
        molecule.addBond(1, 2, 1);
        
        String signature = 
            new MoleculeSignature(molecule).getMolecularSignature();
        int[] identity = new int[] {0, 1, 2};
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

}
