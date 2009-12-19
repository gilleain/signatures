package signature;

import org.junit.Test;

import signature.chemistry.AtomPermutor;
import signature.chemistry.Molecule;

public class PermutationTest {
    
    @Test
    public void permuteMolecule() {
        Molecule molecule = new Molecule();
        molecule.addAtom("C");
        molecule.addAtom("N");
        molecule.addAtom("O");
        molecule.addBond(0, 1, 1);
        molecule.addBond(1, 2, 1);
        
        System.out.println(molecule);
        
        AtomPermutor permutor = new AtomPermutor(molecule);
        while (permutor.hasNext()) {
            Molecule permutedMolecule = permutor.next();
            System.out.println(permutedMolecule);
        }
    }

}
