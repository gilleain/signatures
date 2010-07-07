package signature.display;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;

import junit.framework.Assert;

import org.junit.Test;

import signature.ColoredTree;
import signature.chemistry.AtomSignature;
import signature.chemistry.Molecule;
import signature.chemistry.MoleculeSignature;

public class TestMultipleTrees {
    
    @Test
    public void dummyTest() {
        Assert.assertTrue(true);
    }
    
    public static void makePanel(JFrame f, String signature, int w, int h) {
        ColoredTree tree = AtomSignature.parse(signature);;
        f.add(new ColoredTreePanel(tree, w, h));
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        JFrame f = new JFrame();
        int width = 200;
        int height = 300;
        Molecule molecule = new Molecule();
        molecule.addAtom("C");
        molecule.addAtom("H");
        molecule.addAtom("N");
        molecule.addAtom("O");
        molecule.addAtom("P");
        molecule.addAtom("S");
        molecule.addSingleBond(0, 1);
        molecule.addSingleBond(0, 2);
        molecule.addSingleBond(2, 3);
        molecule.addSingleBond(2, 4);
        molecule.addSingleBond(3, 5);
        
        f.setLayout(new GridLayout(1, 6));
        MoleculeSignature molSig = new MoleculeSignature(molecule);
        for (String signature : molSig.getVertexSignatureStrings()) {
            TestMultipleTrees.makePanel(f, signature, width, height);
        }
        f.setPreferredSize(new Dimension(6 * width, height));
        f.pack();
        f.setVisible(true);
    }
}
