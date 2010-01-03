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
import signature.chemistry.MoleculeFactory;
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
        boolean orderedA = molecule.bondsOrdered();
        MoleculeSignature sigM = new MoleculeSignature(molecule);
        List<SymmetryClass> symmetryClassesA = sigM.getSymmetryClasses();
        AbstractVertexSignature firstM = sigM.signatureForVertex(0);
        firstM.toCanonicalString();
        List<Integer> groupwiseM = 
            firstM.groupwiseCanonicalLabelling(symmetryClassesA);
        
        MoleculeBuilder builderA = new MoleculeBuilder();
        sigM.reconstructCanonicalGraph(sigM.signatureForVertex(0), builderA);
        String resultA = "";
        Molecule reconstructionA = builderA.getMolecule(); 
        if (reconstructionA.identical(molecule)) {
            resultA = "CANON2";
        }
        if (sigM.isCanonicallyLabelled()) {
            canonical.add(molecule);
            permutations.add(new int[] {});
            System.out.println(molecule + "\tCANON" 
                               + "\t" + orderedA
                               + "\t" + resultA
                               + "\t" + groupwiseM);
        } else {
            System.out.println(molecule 
                    + "\t" + orderedA + "\t" + groupwiseM + "\t" + resultA);
        }
        
        AtomPermutor permutor = new AtomPermutor(molecule);
        List<Molecule> examples = new ArrayList<Molecule>();
        examples.add(molecule);
        while (permutor.hasNext()) {
            Molecule permutation = permutor.next();
            
            int group = assignAutomorphism(permutation, examples);
            boolean ordered = permutation.bondsOrdered();
            MoleculeSignature sig = new MoleculeSignature(permutation);
            
//            MoleculeBuilder builder = new MoleculeBuilder();
//            sig.reconstructCanonicalGraph(sig.signatureForVertex(0), builder);
//            String result = "";
//            Molecule reconstruction = builder.getMolecule(); 
//            if (reconstruction.identical(permutation)) {
//                result = "CANON2";
//            }
            String canonicalEdgeString = sig.reconstructCanonicalEdgeString();
            String permutationEdgeString = permutation.toEdgeString();
            String result = "";
            if (canonicalEdgeString.equals(permutationEdgeString)) {
                result = "CANON2";
            }
            
            AbstractVertexSignature first = sig.signatureForVertex(0);
            first.toCanonicalString();
            List<SymmetryClass> symmetryClasses = sig.getSymmetryClasses();
            List<Integer> sorted = first.getCanonicalLabelMapping();
            List<Integer> unsorted = first.postorderCanonicalLabelling();
            List<Integer> groupwise = 
                first.groupwiseCanonicalLabelling(symmetryClasses);
            if (sig.isCanonicallyLabelled()) {
                System.out.println(permutor.getRank() + "\t" 
                        + permutation + "\t" 
                        + canonicalEdgeString + "\t"
                        + permutationEdgeString + "\t"
                        + Arrays.toString(permutor.getCurrentPermutation())
                        + "\t" + group
                        + "\tCANON" + result + "\t"
                        + "\t" + ordered
                        + "\t" + sorted + "\t" + sig.isInIncreasingOrder(sorted)
                        + "\t" + unsorted + "\t" + sig.isInIncreasingOrder(unsorted)
                        + "\t" + groupwise + "\t" + sig.isInIncreasingOrder(groupwise));
                if (ordered) {
                    canonical.add(permutation);
                }
                permutations.add(permutor.getCurrentPermutation());
            } else {
                System.out.println(permutor.getRank() + "\t" 
                        + permutation + "\t"
                        + canonicalEdgeString + "\t"
                        + permutationEdgeString + "\t"
                        + Arrays.toString(permutor.getCurrentPermutation())
                        + "\t" + group
                        + "\t" + result
                        + "\t" + ordered
                        + "\t" + sorted + "\t" + sig.isInIncreasingOrder(sorted)
                        + "\t" + unsorted + "\t" + sig.isInIncreasingOrder(unsorted)
                        + "\t" + groupwise + "\t" + sig.isInIncreasingOrder(groupwise));
            }
        }
        for (int i = 0; i < canonical.size(); i++) { 
//            System.out.println(canonical.get(i) 
//                    + "\t" + Arrays.toString(permutations.get(i)));
        }
//        Assert.assertTrue("No canonical example", canonical.size() > 0);
//        Assert.assertTrue("More than one canonical", canonical.size() == 1);
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
    public void testFiveCycle() {
        Molecule molecule = MoleculeFactory.fiveCycle();
        AtomSignature atomSignature = new AtomSignature(molecule, 0);
        System.out.println(atomSignature.toCanonicalString());
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
    public void testChainUnique() {
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
