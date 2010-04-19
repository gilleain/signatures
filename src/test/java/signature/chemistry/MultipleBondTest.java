package signature.chemistry;

import junit.framework.Assert;

import org.junit.Test;

public class MultipleBondTest {

    @Test
    public void cyclobut_1_ene() {
        Molecule mol = new Molecule();
        for (int i = 0; i < 4; i++) {
            mol.addAtom("C");
        }
        mol.addBond(0, 1, 1);
        mol.addBond(0, 2, 2);
        mol.addBond(1, 3, 1);
        mol.addBond(2, 3, 1);
        MoleculeSignature molSignature = new MoleculeSignature(mol);
        System.out.println(molSignature.toCanonicalString());
    }

    
    @Test
    public void cyclobut_2_ene() {
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
    
    @Test
    public void benzeneTest() {
        Molecule mol = new Molecule();
        for (int i = 0; i < 6; i++) {
            mol.addAtom("C");
        }
        mol.addBond(0, 1, 1);
        mol.addBond(1, 2, 2);
        mol.addBond(2, 3, 1);
        mol.addBond(3, 4, 2);
        mol.addBond(4, 5, 1);
        mol.addBond(5, 0, 2);
        MoleculeSignature molSig = new MoleculeSignature(mol);
        String sigString0 = molSig.signatureStringForVertex(0);
        for (int i = 1; i < 6; i++) {
            String sigStringI = molSig.signatureStringForVertex(i);
            Assert.assertEquals(sigString0, sigStringI);
        }
    }

}
