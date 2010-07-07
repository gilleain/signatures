package signature.chemistry;

import org.junit.Test;

import signature.chemistry.Molecule.BondOrder;

public class WriterTest {
    
    @Test
    public void minimalTest() {
       Molecule mol = new Molecule();
       mol.addAtom("C");
       mol.addAtom("O");
       mol.addAtom("N");
       mol.addSingleBond(0, 1);
       mol.addBond(0, 2, BondOrder.DOUBLE);
       
       // TODO : test this somehow...
//       MoleculeWriter.writeToStream(System.out, mol);
    }

}
