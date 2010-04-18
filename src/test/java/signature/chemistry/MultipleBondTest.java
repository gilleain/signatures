package signature.chemistry;

import org.junit.Test;

public class MultipleBondTest {
    
    @Test
    public void cyclobutene() {
        Molecule mol = new Molecule();
        for (int i = 0; i < 4; i++) {
            mol.addAtom("C");
        }
        mol.addBond(0, 1, 1);
        mol.addBond(0, 2, 2);
        mol.addBond(1, 3, 2);
        mol.addBond(2, 3, 1);
        MoleculeSignature molSignature = new MoleculeSignature(mol);
        System.out.println(molSignature.toCanonicalString());
    }

}
