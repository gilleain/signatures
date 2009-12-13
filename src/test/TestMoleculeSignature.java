package test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import signature.implementation.chemistry.Molecule;
import signature.implementation.chemistry.MoleculeReader;
import signature.implementation.chemistry.MoleculeSignature;

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
        //String filename = "data/test.sdf";
        //String filename = "/home/lc/Molecules/ChEBI_complete.sdf";
        //String filename = "/home/lc/Molecules/fourAtoms.sdf";
        //String filename = "/home/lc/Molecules/fourAtomsCanLabel.sdf";
    	//String filename = "/home/lc/Molecules/atomPermuter.sdf";
    	//String filename = "/home/lc/Molecules/atomPermuterForOB0.sdf";
    	//String filename = "/home/lc/TestSignatures/atomPermuterForOB0Perm.sdf";
    	//String filename = "/home/lc/Molecules/atomPermuterForOB0CanLabel.sdf";
    	//String filename = "/home/lc/Molecules/atomPermuterForOB4.sdf";
    	//String filename = "/home/lc/TestSignatures/unlabeled.sdf";
    	//String filename = "/home/lc/TestSignatures/unlabeledCanLabel.sdf";
    	//String filename = "/home/lc/TestSignatures/simple.sdf";
    	//String filename = "/home/lc/TestSignatures/simplePerm.sdf";
    	//String filename = "/home/lc/TestSignatures/multCycle.sdf";
    	//String filename = "/home/lc/TestSignatures/multCyclePerm.sdf";
    	String filename = "/home/lc/TestSignatures/cyclicButane.sdf";
    	int molNr = 0;
        for (Molecule molecule : MoleculeReader.readSDFFile(filename)) {
        	//System.out.println(++molNr);
            MoleculeSignature signature = new MoleculeSignature(molecule);
            signature.canonicalLabelMapping = new ArrayList<List<Integer>>();

            System.out.println(signature.getGraphSignature());
            //System.out.println(signature.getVertexSignature(0));
            System.out.println(signature.canonicalLabelMapping.toString());
        }
            
    }
    
    @Test
    public void testHeightVertexSignature() {
    	// This generates a height two vertex signature of a graph defined by a single modlecule in an SD-file.
    	String filename = "/home/lc/Molecules/atomPermuterForOB0.sdf";
    	
    	List<String> vertexSignatures;
    	
    	
    }

    
    @Test
    public void unlabelledTest() {
        Molecule molecule = new Molecule();
        molecule.addAtom(0, "C");
        molecule.addAtom(1, "C");
        molecule.addAtom(2, "C");
        molecule.addBond(0, 2, 1);
        molecule.addBond(1, 2, 1);
        MoleculeSignature signature = new MoleculeSignature(molecule);
        signature.canonicalLabelMapping = new ArrayList<List<Integer>>();
        String moleculeSignature = signature.getGraphSignature();
        System.out.println("molsig : " + moleculeSignature);
        
        Molecule permutation = new Molecule();
        permutation.addAtom(0, "C");
        permutation.addAtom(1, "C");
        permutation.addAtom(2, "C");
        permutation.addBond(0, 1, 1);
        permutation.addBond(1, 2, 1);
        MoleculeSignature permSignature = new MoleculeSignature(molecule);
        permSignature.canonicalLabelMapping = new ArrayList<List<Integer>>();
        String permSignatureString = permSignature.getGraphSignature();
        System.out.println("permMolsig : " + permSignatureString);
        
        Assert.assertEquals(moleculeSignature, permSignatureString);
    }

}
