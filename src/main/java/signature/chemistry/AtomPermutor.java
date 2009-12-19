package signature.chemistry;

import java.util.Iterator;

import signature.Permutor;

/**
 * Utility class for permuting the atoms of a molecule - mainly for testing.
 * 
 * @author maclean
 *
 */
public class AtomPermutor extends Permutor implements Iterator<Molecule> {
    
    private Molecule molecule;

    /**
     * Make a permutor for the specified molecule.
     * 
     * @param molecule the molecule to permute
     */
    public AtomPermutor(Molecule molecule) {
        super(molecule.getAtomCount());
        this.molecule = molecule;
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#next()
     */
    public Molecule next() {
        int[] nextPermutation = super.getNextPermutation();
        Molecule nextMolecule = new Molecule(this.molecule, nextPermutation);
        return nextMolecule;
    }

    /* (non-Javadoc)
     * @see java.util.Iterator#remove()
     */
    public void remove() {
        super.setRank(super.getRank() + 1);
    }
}
