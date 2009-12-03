package test;

import org.junit.Test;

import signature.implementation.chemistry.Molecule;
import signature.implementation.chemistry.MoleculeReader;
import signature.implementation.chemistry.MoleculeSignature;

public class TestMoleculeSignature {
    
    @Test
    public void testSDF() {
        String filename = "data/test.sdf";
        for (Molecule molecule : MoleculeReader.readSDFFile(filename)) {
            MoleculeSignature signature = new MoleculeSignature(molecule);
            System.out.println(signature.getGraphSignature());
            
        }
            
    }

}
