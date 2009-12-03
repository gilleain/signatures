package test;

import org.junit.Test;

import signature.implementation.chemistry.Molecule;
import signature.implementation.chemistry.MoleculeReader;
import signature.implementation.chemistry.MoleculeSignature;

public class TestMoleculeSignature {
    
    @Test
    public void testSDF() {
        //String filename = "data/test.sdf";
        String filename = "/home/lc/Molecules/ChEBI_complete.sdf";
        int molNr = 1;
        for (Molecule molecule : MoleculeReader.readSDFFile(filename)) {
        	System.out.println(++molNr);
            MoleculeSignature signature = new MoleculeSignature(molecule);
            System.out.println(signature.getGraphSignature());
            //System.out.println(signature.getVertexSignature(0));
        }
            
    }

}
