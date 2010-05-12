package signature.chemistry;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import signature.SymmetryClass;

public class ChiralityTest {
    
    @Test
    public void spiranTest() {
        Molecule mol = new Molecule();
        mol.addAtom("N");
        mol.addMultipleAtoms(12, "C");
        mol.addMultipleAtoms(24, "H");
        mol.addMultipleSingleBonds(0, 1, 4, 5, 8);  // central N
        mol.addMultipleSingleBonds(1, 2, 33, 34);   // ring 1 - CA
        mol.addMultipleSingleBonds(2, 3, 11, 15);   // ring 1 - CB
        mol.addMultipleSingleBonds(3, 4, 12, 16);   // ring 1 - CB
        mol.addMultipleSingleBonds(4, 35, 36);      // ring 1 - CA
        mol.addMultipleSingleBonds(5, 6, 29, 30);   // ring 2 - CA
        mol.addMultipleSingleBonds(6, 7, 9, 13);    // ring 2 - CB
        mol.addMultipleSingleBonds(7, 8, 10, 14);   // ring 2 - CB
        mol.addMultipleSingleBonds(8, 31, 32);      // ring 2 - CA
        mol.addMultipleSingleBonds(9, 17, 18, 19);  // CH3 A
        mol.addMultipleSingleBonds(10, 20, 21, 22); // CH3 B
        mol.addMultipleSingleBonds(11, 23, 24, 25); // CH3 C
        mol.addMultipleSingleBonds(12, 26, 27, 28); // CH3 D
        
        // 7 symmetry classes - central N, CA, CB, CH3-C, HA, HB, CH3-H 
        MoleculeSignature molSig = new MoleculeSignature(mol);
        List<SymmetryClass> symmetryClasses = molSig.getSymmetryClasses();
        Assert.assertEquals(7, symmetryClasses.size());
        
        List<Integer> tetraChiralCenters = 
            ChiralCenterFinder.findTetrahedralChiralCenters(mol);
        Assert.assertEquals(4, tetraChiralCenters.size());
        Assert.assertEquals(2, (int)tetraChiralCenters.get(0));
        Assert.assertEquals(3, (int)tetraChiralCenters.get(1));
        Assert.assertEquals(6, (int)tetraChiralCenters.get(2));
        Assert.assertEquals(7, (int)tetraChiralCenters.get(3));
    }
    
    @Test
    public void dichlorocyclopropaneTest() {
        Molecule mol = new Molecule();
        mol.addAtom("C");
        mol.addAtom("C");
        mol.addAtom("C");
        mol.addAtom("Cl");
        mol.addAtom("Cl");
        mol.addAtom("H");
        mol.addAtom("H");
        mol.addAtom("H");
        mol.addAtom("H");
        mol.addMultipleSingleBonds(0, 1, 2, 3, 5);
        mol.addMultipleSingleBonds(1, 2, 4, 6);
        mol.addMultipleSingleBonds(2, 7, 8);
        
        // 5 symmetry classes - the non-chiral carbon, its hydrogens, the chiral
        // carbons, their hydrogens, and the chlorines.
        MoleculeSignature molSig = new MoleculeSignature(mol);
        List<SymmetryClass> symmetryClasses = molSig.getSymmetryClasses(); 
//        System.out.println(symmetryClasses);
        Assert.assertEquals(5, symmetryClasses.size());
        
        // only two possible chiral centers
        List<Integer> tetraChiralCenters = 
            ChiralCenterFinder.findTetrahedralChiralCenters(mol);
        Assert.assertEquals(2, tetraChiralCenters.size());
        Assert.assertEquals(0, (int)tetraChiralCenters.get(0));
        Assert.assertEquals(1, (int)tetraChiralCenters.get(1));
    }
    
    @Test
    public void dihydroxyCyclohexane() {
        Molecule mol = new Molecule();
        mol.addMultipleAtoms(6, "C");
        mol.addMultipleAtoms(2, "O");
        mol.addMultipleAtoms(12, "H");
        mol.addMultipleSingleBonds(0, 1, 5, 6, 11);
        mol.addMultipleSingleBonds(1, 2, 12, 13);
        mol.addMultipleSingleBonds(2, 3, 14, 15);
        mol.addMultipleSingleBonds(3, 4, 7, 10);
        mol.addMultipleSingleBonds(4, 5, 16, 17);
        mol.addMultipleSingleBonds(5, 18, 19);
        mol.addMultipleSingleBonds(6, 8);
        mol.addMultipleSingleBonds(7, 9);
        MoleculeSignature molSig = new MoleculeSignature(mol);
        List<SymmetryClass> symmetryClasses = molSig.getSymmetryClasses(); 
        System.out.println(symmetryClasses);
        Assert.assertEquals(6, symmetryClasses.size());
        
        // this method cannot find the linked chiral centers 
        List<Integer> tetraChiralCenters = 
            ChiralCenterFinder.findTetrahedralChiralCenters(mol);
        Assert.assertTrue(tetraChiralCenters.isEmpty());
    }

}
