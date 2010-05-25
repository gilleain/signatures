package signature.chemistry;

import org.junit.Test;

public class NumberLabelsTest {
    
    @Test
    public void choTest() {
        Molecule mol = new Molecule();
        mol.addAtom("C");
        mol.addAtom("H");
        mol.addAtom("O");
        mol.addSingleBond(0, 1);
        mol.addSingleBond(0, 2);
        MoleculeSignature firstSig = new MoleculeSignature(mol, false);
        String firstSigString = firstSig.toCanonicalString();
        System.out.println(firstSigString);
        AtomPermutor permutor = new AtomPermutor(mol);
        while (permutor.hasNext()) {
            Molecule pMol = permutor.next();
            MoleculeSignature pSig = new MoleculeSignature(pMol, false);
            String pSigString = pSig.toCanonicalString();
            System.out.println(pSigString);
        }
    }

}
