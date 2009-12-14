package signature;

import junit.framework.Assert;

import org.junit.Test;

import signature.chemistry.Molecule;
import signature.chemistry.MoleculeReader;
import signature.chemistry.MoleculeSignature;

public class TestMoleculeSignature {
    
   @Test
    public void minimalTest() {
        Molecule molecule = new Molecule();
        molecule.addAtom(0, "C");
        molecule.addAtom(1, "N");
        molecule.addAtom(2, "Cl");
        molecule.addAtom(3, "O");
        molecule.addBond(0, 1, 1);
        molecule.addBond(1, 2, 1);
        molecule.addBond(1, 3, 1);
        MoleculeSignature signature = new MoleculeSignature(molecule);
        String moleculeSignature = signature.getGraphSignature();
        //System.out.println("molsig : " + moleculeSignature);
        
        Molecule permutation = new Molecule();
        permutation.addAtom(0, "C");
        permutation.addAtom(1, "N");
        permutation.addAtom(2, "O");
        permutation.addAtom(3, "Cl");
        permutation.addBond(0, 1, 1);
        permutation.addBond(1, 2, 1);
        permutation.addBond(1, 3, 1);
        MoleculeSignature permSignature = new MoleculeSignature(molecule);
        String permSignatureString = permSignature.getGraphSignature();
        //System.out.println("molsig : " + permSignatureString);
        
        Assert.assertEquals(moleculeSignature, permSignatureString);
    }
    
    @Test
    public void testLargeExample() {
        String filename = "data/large_example.sdf";
        for (Molecule molecule : MoleculeReader.readSDFFile(filename)) {
            MoleculeSignature signature = new MoleculeSignature(molecule);
            System.out.println(signature.getGraphSignature());
        }
    }
    
    @Test
    public void testSDF() {
        String filename = "data/test.sdf";
        int molNr = 0;
        for (Molecule molecule : MoleculeReader.readSDFFile(filename)) {
        	System.out.println(++molNr);
            MoleculeSignature signature = new MoleculeSignature(molecule);
            System.out.println(signature.getGraphSignature());
            //System.out.println(signature.getVertexSignature(0));
        }
            
    }

    @Test
    public void testCanonicalLabelling() {
    	String filename = "data/multCycle.sdf";
        for (Molecule molecule : MoleculeReader.readSDFFile(filename)) {
            MoleculeSignature signature = new MoleculeSignature(molecule);
            Assert.assertEquals(false, signature.isCanonicallyLabelled() );
        }

    	String filenameCanLabel = "data/multCycleCanLabel.sdf";
        for (Molecule molecule : MoleculeReader.readSDFFile(filenameCanLabel)) {
            MoleculeSignature signature = new MoleculeSignature(molecule);
            Assert.assertEquals(true, signature.isCanonicallyLabelled() );
        }

    }

}
