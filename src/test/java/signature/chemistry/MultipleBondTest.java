package signature.chemistry;

import junit.framework.Assert;

import org.junit.Test;

public class MultipleBondTest {
    
    @Test
    public void cocoTest() {
        Molecule moleculeA = new Molecule();
        int chainLength = 6;
        moleculeA.addAtom("O");
        moleculeA.addAtom("C");
        moleculeA.addAtom("C");
        moleculeA.addAtom("O");
        moleculeA.addAtom("C");
        moleculeA.addAtom("O");
        for (int i = 0; i < chainLength - 2; i++) {
            moleculeA.addSingleBond(i, i+1);
        }
        moleculeA.addBond(chainLength - 2, chainLength - 1, 2);
//        moleculeA.addBond(chainLength - 2, chainLength - 1, 1);
        
        Molecule moleculeB = new Molecule();
        moleculeB.addAtom("O");
        moleculeB.addAtom("C");
        moleculeB.addAtom("C");
        moleculeB.addAtom("C");
        moleculeB.addAtom("O");
        moleculeB.addAtom("O");
        moleculeB.addBond(0, 1, 1);
        moleculeB.addBond(1, 3, 1);
        moleculeB.addBond(2, 4, 1);
        moleculeB.addBond(2, 5, 2);
//        moleculeB.addBond(2, 5, 1);
        moleculeB.addBond(3, 4, 1);
        
        MoleculeSignature molSigA = new MoleculeSignature(moleculeA);
        String sigA = molSigA.signatureStringForVertex(3);
        System.out.println(sigA);
        System.out.println("--------------------------------------");
        MoleculeSignature molSigB = new MoleculeSignature(moleculeB);
        String sigB = molSigB.signatureStringForVertex(4);
        System.out.println(sigB);
        Assert.assertEquals(sigA, sigB);
    }
    
    @Test
    public void multipleBondedFragmentTest() {
        Molecule molA = new Molecule();
        molA.addAtom("C");
        molA.addAtom("C");
        molA.addAtom("C");
        molA.addAtom("H");
        molA.addBond(0, 1, 2);
        molA.addBond(0, 2, 1);
        molA.addBond(0, 3, 1);
        
        MoleculeSignature molSig = new MoleculeSignature(molA);
        String signatureFor0A = molSig.signatureStringForVertex(0);
        System.out.println(signatureFor0A);
        
        Molecule molB = new Molecule();
        molB.addAtom("C");
        molB.addAtom("C");
        molB.addAtom("C");
        molB.addAtom("H");
        molB.addBond(0, 1, 1);
        molB.addBond(0, 2, 2);  // NOTE change of bond order here
        molB.addBond(0, 3, 1);
        
        molSig = new MoleculeSignature(molB);
        String signatureFor0B = molSig.signatureStringForVertex(0);
        System.out.println(signatureFor0B);
        
        Assert.assertEquals(signatureFor0A, signatureFor0B);
    }

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
