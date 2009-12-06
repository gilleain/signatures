package test;

import org.junit.Test;

import signature.implementation.chemistry.Molecule;
import signature.implementation.chemistry.MoleculeReader;
import signature.implementation.chemistry.MoleculeSignature;

public class TestMoleculeSignature {
    
    @Test
    public void testSDF() {
        //String filename = "data/test.sdf";
        //String filename = "/home/lc/Molecules/ChEBI_complete.sdf";
        //String filename = "/home/lc/disconnected.sdf";
    	//String filename = "/home/lc/Molecules/atomPermuter.sdf";
    	//String filename = "/home/lc/Molecules/atomPermuterForOB0.sdf";
    	String filename = "/home/lc/Molecules/atomPermuterForOB4.sdf";
        int molNr = 0;
        for (Molecule molecule : MoleculeReader.readSDFFile(filename)) {
        	System.out.println(++molNr);
            MoleculeSignature signature = new MoleculeSignature(molecule);
            System.out.println(signature.getGraphSignature());
            //System.out.println(signature.getVertexSignature(0));
        }
            
    }

}
