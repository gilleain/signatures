package signature.chemistry;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import signature.ColoredTree;
import signature.SymmetryClass;
import signature.chemistry.AtomPermutor;
import signature.chemistry.AtomSignature;
import signature.chemistry.Molecule;
import signature.chemistry.MoleculeBuilder;
import signature.chemistry.MoleculeFactory;
import signature.chemistry.MoleculeReader;
import signature.chemistry.MoleculeSignature;

public class MoleculeSignatureTest {
    
   @Test
    public void minimalTest() {
        Molecule molecule = new Molecule();
        molecule.addAtom(0, "C");
        molecule.addAtom(1, "N");
        molecule.addAtom(2, "Cl");
        molecule.addAtom(3, "O");
        molecule.addSingleBond(0, 1);
        molecule.addSingleBond(1, 2);
        molecule.addSingleBond(1, 3);
        MoleculeSignature signature = new MoleculeSignature(molecule);
        String moleculeSignature = signature.getGraphSignature();
        //System.out.println("molsig : " + moleculeSignature);
        
        Molecule permutation = new Molecule();
        permutation.addAtom(0, "C");
        permutation.addAtom(1, "N");
        permutation.addAtom(2, "O");
        permutation.addAtom(3, "Cl");
        permutation.addSingleBond(0, 1);
        permutation.addSingleBond(1, 2);
        permutation.addSingleBond(1, 3);
        MoleculeSignature permSignature = new MoleculeSignature(molecule);
        String permSignatureString = permSignature.getGraphSignature();
        //System.out.println("molsig : " + permSignatureString);
        
        Assert.assertEquals(moleculeSignature, permSignatureString);
    }
   
   @Test
   public void testColoredTreeCreation() {
       String signatureString = "[C]([C]([C,1])[C]([C,1]))";
       ColoredTree tree = AtomSignature.parse(signatureString);
       Assert.assertEquals(signatureString, tree.toString());
   }
   
   @Test
   public void testOddCycleReadin() {
       String signatureString = "[C]([C]([C,2]([C,1]))[C]([C,1]))";
       ColoredTree tree = AtomSignature.parse(signatureString);
       Assert.assertEquals(signatureString, tree.toString());
       Molecule molecule = new MoleculeBuilder().fromTree(tree);
       Assert.assertEquals(5, molecule.getAtomCount());
       Assert.assertEquals(5, molecule.getBondCount());
   }
   
   @Test
   public void testCage() {
       String signatureString = "[C]([C]([C,2]([C]([C,3][C,4]))[C]([C,5]" +
                                "[C,3]([C,6]([C,1]))))[C]([C]([C,7][C]" +
                                "([C,1][C,8]))[C,5]([C,8]([C,6])))[C]([C,2]" +
                                "[C,7]([C,4]([C,1]))))";
       ColoredTree tree = AtomSignature.parse(signatureString);
       Assert.assertEquals(signatureString, tree.toString());
       Molecule molecule = new MoleculeBuilder().fromTree(tree);
       Assert.assertEquals(16, molecule.getAtomCount());
       Assert.assertEquals(24, molecule.getBondCount());
   }
   
   @Test
   public void testRoundtrip() {
       Molecule molecule = new Molecule();
       molecule.addAtom("C");
       molecule.addAtom("C");
       molecule.addAtom("C");
       molecule.addAtom("C");
       molecule.addSingleBond(0, 1);
       molecule.addSingleBond(0, 3);
       molecule.addSingleBond(1, 2);
       molecule.addSingleBond(2, 3);
       
       AtomSignature atomSignature = new AtomSignature(molecule, 0);
       String signatureString = atomSignature.toCanonicalString();
       
       ColoredTree tree = AtomSignature.parse(signatureString);
       MoleculeBuilder builder = new MoleculeBuilder();
       Molecule builtMolecule = builder.fromTree(tree);
       Assert.assertEquals(molecule.toString(), builtMolecule.toString());
       
       // test that this can be done more than once
       builtMolecule = builder.fromTree(tree);
       Assert.assertEquals(molecule.toString(), builtMolecule.toString());
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
            System.out.println(molecule.getAtomCount());
            Assert.assertEquals(false, signature.isCanonicallyLabelled() );
        }

    	String filenameCanLabel = "data/multCycleCanLabel.sdf";
        for (Molecule molecule : MoleculeReader.readSDFFile(filenameCanLabel)) {
            MoleculeSignature signatureCanLabel = new MoleculeSignature(molecule);
            Assert.assertEquals(true, signatureCanLabel.isCanonicallyLabelled() );
        }
    }
    
    public void testCanonicalIsUnique(Molecule molecule) {
        System.out.println("isUnique?" + molecule);
        AtomPermutor permutor = new AtomPermutor(molecule);
        String canonicalStringForm = null;
        boolean isUnique = true;
        boolean atLeastOneCanonicalExample = false;
        
        MoleculeSignature initialMolSig = new MoleculeSignature(molecule);
        if (initialMolSig.isCanonicallyLabelled()) {
            atLeastOneCanonicalExample = true;
            canonicalStringForm = initialMolSig.reconstructCanonicalEdgeString();
        }
        
        while (permutor.hasNext()) {
            Molecule permutation = permutor.next();
            MoleculeSignature molSig = new MoleculeSignature(permutation);
            if (molSig.isCanonicallyLabelled()) {
                String stringForm = molSig.reconstructCanonicalEdgeString();
                if (canonicalStringForm == null) {
                    canonicalStringForm = stringForm;
                    atLeastOneCanonicalExample = true;
                } else {
                    if (canonicalStringForm.equals(stringForm)) {
                        continue;
                    } else {
                        // not unique if there is more than one string form
                        isUnique = false;
                        break;
                    }
                }
            }
        }
        Assert.assertTrue("Canonical example is not unique", isUnique);
        Assert.assertTrue("No canonical example", atLeastOneCanonicalExample);
    }
    
    @Test
    public void testFourCycle() {
        Molecule molecule = MoleculeFactory.fiveCycle();
        AtomSignature atomSignature = new AtomSignature(molecule, 0);
        System.out.println(atomSignature.toCanonicalString());
//        System.out.println(atomSignature);
    }
    
    @Test
    public void testFiveCycle() {
        Molecule molecule = MoleculeFactory.fiveCycle();
        AtomSignature atomSignature = new AtomSignature(molecule, 0);
        System.out.println(atomSignature.toCanonicalString());
//        System.out.println(atomSignature);
    }
    
    @Test
    public void testThreeStarCanonicalUnique() {
        Molecule molecule = MoleculeFactory.threeStar();
        this.testCanonicalIsUnique(molecule);
    }
    
    @Test
    public void testFourStarCanonicalUnique() {
        Molecule molecule = MoleculeFactory.fourStar();
        this.testCanonicalIsUnique(molecule);
    }
    
    @Test
    public void testFiveStarCanonicalUnique() {
        Molecule molecule = MoleculeFactory.fiveStar();
        this.testCanonicalIsUnique(molecule);
    }
    
    @Test
    public void testTriangleCanonicalIsUnique() {
        Molecule molecule = MoleculeFactory.threeCycle();
        this.testCanonicalIsUnique(molecule);
    }
    
    @Test
    public void testSquareCanonicalIsUnique() {
        Molecule molecule = MoleculeFactory.fourCycle();
        this.testCanonicalIsUnique(molecule);
    }
    
    @Test
    public void testPentagonCanonicalIsUnique() {
        Molecule molecule = MoleculeFactory.fiveCycle();
        this.testCanonicalIsUnique(molecule);
    }
    
    @Test
    public void testHexagonCanonicalIsUnique() {
        Molecule molecule = MoleculeFactory.sixCycle();
        this.testCanonicalIsUnique(molecule);
    }
    
    @Test
    public void testPropellaneCanonicalIsUnique() {
        Molecule molecule = MoleculeFactory.propellane();
        this.testCanonicalIsUnique(molecule);
    }
    
    @Test
    public void testPseudopropellaneCanonicalIsUnique() {
        Molecule molecule = MoleculeFactory.pseudopropellane();
        this.testCanonicalIsUnique(molecule);
    }
    
    @Test
    public void testMethylCyclobutaneCanonicalIsUnique() {
        Molecule molecule = MoleculeFactory.methylatedCyclobutane();
        this.testCanonicalIsUnique(molecule);
    }
    
    @Test
    public void testSixcageCanonicalIsUnique() {
        Molecule molecule = MoleculeFactory.sixCage();
        this.testCanonicalIsUnique(molecule);
    }
    
    @Test
    public void testCarbonChainUnique() {
        // single atom
        Molecule a = new Molecule("C", 1);
        this.testCanonicalIsUnique(a);
        
        // pair of connected atoms
        a.addAtom("C");
        a.addSingleBond(0, 1);
        this.testCanonicalIsUnique(a);
        
        // chain of three atoms
        a.addAtom("C");
        a.addSingleBond(0, 2);
        this.testCanonicalIsUnique(a);
        
        // copies for new level
        Molecule b = new Molecule(a);
        Molecule c = new Molecule(a);
        
        // 4-chain
        a.addAtom("C");
        a.addSingleBond(1, 3);
        this.testCanonicalIsUnique(a);
        
        // 3-star
        b.addAtom("C");
        b.addSingleBond(0, 3);
        this.testCanonicalIsUnique(b);
        
        // 3-cycle
        c.addSingleBond(1, 2);
        this.testCanonicalIsUnique(c);
    }
    
    @Test
    public void testCarbonHydrogenCanonicalChain() {
        Molecule a = new Molecule("C", 1);
        this.testCanonicalIsUnique(a);
        
        a.addAtom("H");
        a.addSingleBond(0, 1);
        this.testCanonicalIsUnique(a);
        
        a.addAtom("H");
        a.addSingleBond(0, 2);
        this.testCanonicalIsUnique(a);
        
        a.addAtom("H");
        a.addSingleBond(0, 3);
        this.testCanonicalIsUnique(a);
        
        a.addAtom("H");
        a.addSingleBond(0, 4);
        this.testCanonicalIsUnique(a);
    }
    
    @Test
    public void testMetheneFragmentIsCanonicallyUnique() {
        Molecule molecule = new Molecule();
        molecule.addAtom("C");
        molecule.addAtom("H");
        molecule.addAtom("H");
        molecule.addSingleBond(0, 1);
        molecule.addSingleBond(0, 2);
        this.testCanonicalIsUnique(molecule);
    }
    
    @Test
    public void testMethaneIsCanonicallyUnique() {
        Molecule molecule = MoleculeFactory.methane();
        this.testCanonicalIsUnique(molecule);
    }
    
    @Test
    public void testMethaneSignatures() {
        Molecule molecule = MoleculeFactory.methane();
        MoleculeSignature signature = new MoleculeSignature(molecule);
        List<SymmetryClass> symmetryClasses = signature.getSymmetryClasses();
        Assert.assertEquals(2, symmetryClasses.size());
        for (SymmetryClass symmetryClass : symmetryClasses) {
            if (symmetryClass.getSignatureString().startsWith("[H")) {
                Assert.assertEquals(4, symmetryClass.size());
            } else {
                Assert.assertEquals(1, symmetryClass.size());
            }
        }
    }
    
    @Test
    public void testMetheneFragmentSignatures() {
        Molecule molecule = new Molecule();
        molecule.addAtom("C");
        molecule.addAtom("H");
        molecule.addAtom("H");
        molecule.addSingleBond(0, 1);
        molecule.addSingleBond(0, 2);
        MoleculeSignature signature = new MoleculeSignature(molecule);
        List<SymmetryClass> symmetryClasses = signature.getSymmetryClasses();
        Assert.assertEquals(2, symmetryClasses.size());
        for (SymmetryClass symmetryClass : symmetryClasses) {
            if (symmetryClass.getSignatureString().startsWith("[H")) {
                Assert.assertEquals(2, symmetryClass.size());
            } else {
                Assert.assertEquals(1, symmetryClass.size());
            }
        }
    }
    
    @Test
    public void testMethyneFragmentSignatures() {
        Molecule molecule = new Molecule();
        molecule.addAtom("C");
        molecule.addAtom("H");
        molecule.addSingleBond(0, 1);
        MoleculeSignature signature = new MoleculeSignature(molecule);
        List<SymmetryClass> symmetryClasses = signature.getSymmetryClasses();
        Assert.assertEquals(2, symmetryClasses.size());
        for (SymmetryClass symmetryClass : symmetryClasses) {
            Assert.assertEquals(1, symmetryClass.size());
        }
    }
    
    @Test
    public void testCanonicalIsUnique() {
        Molecule molecule = new Molecule();
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addSingleBond(0, 1);
        molecule.addSingleBond(1, 2);
        molecule.addSingleBond(1, 3);
        molecule.addSingleBond(2, 4);
        molecule.addSingleBond(2, 5);
        this.testCanonicalIsUnique(molecule);
    }

}
