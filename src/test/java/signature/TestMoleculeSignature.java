package signature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import signature.chemistry.AtomPermutor;
import signature.chemistry.AtomSignature;
import signature.chemistry.Molecule;
import signature.chemistry.MoleculeBuilder;
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
   public void testColoredTreeCreation() {
       String signatureString = "[C]([C]([C,1])[C]([C,1]))";
       AtomSignature atomSignature = new AtomSignature(new Molecule(), 0);
       ColoredTree tree = atomSignature.parse(signatureString);
       Assert.assertEquals(signatureString, tree.toString());
   }
   
   @Test
   public void testOddCycleReadin() {
       String signatureString = "[C]([C]([C,2]([C,1]))[C]([C,1]))";
       AtomSignature atomSignature = new AtomSignature(new Molecule(), 0);
       ColoredTree tree = atomSignature.parse(signatureString);
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
       AtomSignature atomSignature = new AtomSignature(new Molecule(), 0);
       ColoredTree tree = atomSignature.parse(signatureString);
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
       molecule.addBond(0, 1, 1);
       molecule.addBond(0, 3, 1);
       molecule.addBond(1, 2, 1);
       molecule.addBond(2, 3, 1);
       AtomSignature atomSignature = new AtomSignature(molecule, 0);
       String signatureString = atomSignature.toCanonicalString();
       ColoredTree tree = atomSignature.parse(signatureString);
       MoleculeBuilder builder = new MoleculeBuilder();
       Molecule builtMolecule = builder.fromTree(tree);
//       Assert.assertEquals(molecule.toString(), builtMolecule.toString());
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
        
        Molecule molecule = MoleculeReader.readSDFFile(filename).get(0);
//        this.testCanonicalIsUnique(molecule);
    }
    
    public void testCanonicalIsUnique(Molecule molecule) {
        List<Molecule> canonical = new ArrayList<Molecule>();
        List<int[]> permutations = new ArrayList<int[]>();
        if (MoleculeSignature.isCanonicallyLabelled(molecule)) {
            canonical.add(molecule);
            permutations.add(new int[] {});
            System.out.println(molecule + "\tCANON");
        } else {
            System.out.println(molecule);
        }
        
        AtomPermutor permutor = new AtomPermutor(molecule);
        List<Molecule> examples = new ArrayList<Molecule>();
        examples.add(molecule);
        while (permutor.hasNext()) {
            Molecule permutation = permutor.next();
            int group = assignAutomorphism(permutation, examples);
            if (MoleculeSignature.isCanonicallyLabelled(permutation)) {
                System.out.println(permutor.getRank() + "\t" 
                        + permutation + "\t" 
                        + Arrays.toString(permutor.getCurrentPermutation())
                        + "\t" + group
                        + "\tCANON");
                canonical.add(permutation);
                permutations.add(permutor.getCurrentPermutation());
            } else {
                System.out.println(permutor.getRank() + "\t" 
                        + permutation + "\t" 
                        + Arrays.toString(permutor.getCurrentPermutation())
                        + "\t" + group);
            }
        }
        for (int i = 0; i < canonical.size(); i++) { 
//            System.out.println(canonical.get(i) 
//                    + "\t" + Arrays.toString(permutations.get(i)));
        }
        Assert.assertTrue("No canonical example", canonical.size() > 0);
        Assert.assertTrue("More than one canonical", canonical.size() == 1);
    }
    
    public int assignAutomorphism(Molecule molecule, List<Molecule> examples) {
        for (int i = 0; i < examples.size(); i++) {
            Molecule example = examples.get(i);
            if (molecule.identical(example)) {
                return i;
            }
        }
        int groupID = examples.size();
        examples.add(molecule);
        return groupID;
    }
    
    @Test
    public void testTriangleCanonicalIsUnique() {
        Molecule molecule = new Molecule();
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addBond(0, 1, 1);
        molecule.addBond(0, 2, 1);
        molecule.addBond(1, 2, 1);
        this.testCanonicalIsUnique(molecule);
    }
    
    @Test
    public void testSquareCanonicalIsUnique() {
        Molecule molecule = new Molecule();
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addBond(0, 1, 1);
        molecule.addBond(0, 3, 1);
        molecule.addBond(1, 2, 1);
        molecule.addBond(2, 3, 1);
        this.testCanonicalIsUnique(molecule);
    }
    
    @Test
    public void testPentagonCanonicalIsUnique() {
        Molecule molecule = new Molecule();
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addBond(0, 1, 1);
        molecule.addBond(0, 4, 1);
        molecule.addBond(1, 2, 1);
        molecule.addBond(2, 3, 1);
        molecule.addBond(3, 4, 1);
        this.testCanonicalIsUnique(molecule);
    }
    
    @Test
    public void testHexagonCanonicalIsUnique() {
        Molecule molecule = new Molecule();
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addAtom("C");
        molecule.addBond(0, 1, 1);
        molecule.addBond(0, 5, 1);
        molecule.addBond(1, 2, 1);
        molecule.addBond(2, 3, 1);
        molecule.addBond(3, 4, 1);
        molecule.addBond(4, 5, 1);
        this.testCanonicalIsUnique(molecule);
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
        molecule.addBond(0, 1, 1);
        molecule.addBond(1, 2, 1);
        molecule.addBond(1, 3, 1);
        molecule.addBond(2, 4, 1);
        molecule.addBond(2, 5, 1);
        this.testCanonicalIsUnique(molecule);
    }

}
