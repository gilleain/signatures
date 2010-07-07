package signature.chemistry;

import junit.framework.Assert;

import org.junit.Test;

import signature.chemistry.Molecule.BondOrder;

public class MultipleBondTest {
    
    @Test
    public void aromaticTest() {
        Molecule benzene = new Molecule();
        benzene.addMultipleAtoms(6, "C");
        benzene.addBond(0, 1, BondOrder.AROMATIC);
        benzene.addBond(1, 2, BondOrder.AROMATIC);
        benzene.addBond(2, 3, BondOrder.AROMATIC);
        benzene.addBond(3, 4, BondOrder.AROMATIC);
        benzene.addBond(4, 5, BondOrder.AROMATIC);
        benzene.addBond(5, 0, BondOrder.AROMATIC);
        MoleculeSignature molSig = new MoleculeSignature(benzene);
        System.out.println(molSig.toCanonicalString());
    }
    
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
        moleculeA.addBond(chainLength - 2, chainLength - 1, BondOrder.DOUBLE);
//        moleculeA.addBond(chainLength - 2, chainLength - 1, 1);
        
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
//        moleculeB.addBond(2, 5, 1);
        moleculeB.addSingleBond(3, 4);
        
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
        molA.addBond(0, 1, BondOrder.DOUBLE);
        molA.addSingleBond(0, 2);
        molA.addSingleBond(0, 3);
        
        MoleculeSignature molSig = new MoleculeSignature(molA);
        String signatureFor0A = molSig.signatureStringForVertex(0);
        System.out.println(signatureFor0A);
        
        Molecule molB = new Molecule();
        molB.addAtom("C");
        molB.addAtom("C");
        molB.addAtom("C");
        molB.addAtom("H");
        molB.addSingleBond(0, 1);
        molB.addBond(0, 2, BondOrder.DOUBLE);
        molB.addSingleBond(0, 3);
        
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
        mol.addSingleBond(0, 1);
        mol.addBond(0, 2, BondOrder.DOUBLE);
        mol.addSingleBond(1, 3);
        mol.addSingleBond(2, 3);
        MoleculeSignature molSignature = new MoleculeSignature(mol);
        System.out.println(molSignature.toCanonicalString());
    }

    
    @Test
    public void cyclobut_2_ene() {
        Molecule mol = new Molecule();
        for (int i = 0; i < 4; i++) {
            mol.addAtom("C");
        }
        mol.addSingleBond(0, 1);
        mol.addBond(0, 2, BondOrder.DOUBLE);
        mol.addBond(1, 3, BondOrder.DOUBLE);
        mol.addSingleBond(2, 3);
        MoleculeSignature molSignature = new MoleculeSignature(mol);
        System.out.println(molSignature.toCanonicalString());
    }
    
    @Test
    public void benzeneTest() {
        Molecule mol = new Molecule();
        for (int i = 0; i < 6; i++) {
            mol.addAtom("C");
        }
        mol.addSingleBond(0, 1);
        mol.addBond(1, 2, BondOrder.DOUBLE);
        mol.addSingleBond(2, 3);
        mol.addBond(3, 4, BondOrder.DOUBLE);
        mol.addSingleBond(4, 5);
        mol.addBond(5, 0, BondOrder.DOUBLE);
        MoleculeSignature molSig = new MoleculeSignature(mol);
        String sigString0 = molSig.signatureStringForVertex(0);
        for (int i = 1; i < 6; i++) {
            String sigStringI = molSig.signatureStringForVertex(i);
            Assert.assertEquals(sigString0, sigStringI);
        }
    }

}
