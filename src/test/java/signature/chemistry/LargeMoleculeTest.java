package signature.chemistry;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import signature.SymmetryClass;
import signature.chemistry.Molecule;
import signature.chemistry.MoleculeSignature;

public class LargeMoleculeTest {
    
    public void addRing(int atomToAttachTo, int ringSize, Molecule molecule) {
        int numberOfAtoms = molecule.getAtomCount();
        int previous = atomToAttachTo;
        for (int i = 0; i < ringSize; i++) {
            molecule.addAtom("C");
            int current = numberOfAtoms + i;
            molecule.addSingleBond(previous, current);
            previous = current;
        }
        molecule.addSingleBond(numberOfAtoms, numberOfAtoms + (ringSize - 1));
    }
    
    public Molecule makeMinimalMultiRing(int ringCount, int ringSize) {
        Molecule mol = new Molecule();
        mol.addAtom("C");
        for (int i = 0; i < ringCount; i++) {
            addRing(0, ringSize, mol);
        }
        return mol;
    }
    
    public Molecule makeTetrakisTriphenylPhosphoranylRhodium() {
        Molecule ttpr = new Molecule();
        ttpr.addAtom("Rh");
        int phosphateCount = 2;
        for (int i = 1; i <= phosphateCount; i++) {
            ttpr.addAtom("P");
            ttpr.addSingleBond(0, i);
        }
        
        int phenylCount = 3;
        for (int j = 1; j <= phosphateCount; j++) {
            for (int k = 0; k < phenylCount; k++) {
                addRing(j, 6, ttpr);
            }
        }
        return ttpr;
    }
    
    @Test
    public void ttprTest() {
        Molecule ttpr = makeTetrakisTriphenylPhosphoranylRhodium();
        MoleculeSignature molSig = new MoleculeSignature(ttpr);
//        String sigString = molSig.toCanonicalString();
        String sigString = molSig.signatureStringForVertex(0);
        System.out.println(sigString);
    }
    
    @Test
    public void testMinimalMol() {
        Molecule mol = makeMinimalMultiRing(5, 3);
        MoleculeSignature molSig = new MoleculeSignature(mol);
//        String sigString = molSig.toCanonicalString();
        String sigString = molSig.signatureStringForVertex(0);
        System.out.println(sigString);

        System.out.println(mol);
        System.out.println("result " + sigString);
    }
    
    public Molecule makeChain(int length) {
        Molecule chain = new Molecule();
        int previous = -1;
        for (int i = 0; i < length; i++) {
            chain.addAtom("C");
            if (previous != -1) {
                chain.addSingleBond(previous, i);
            }
            previous = i;
        }
        return chain;
    }
    
    @Test
    public void testLongChains() {
        int length = 10;
        Molecule chain = makeChain(length);
        MoleculeSignature molSig = new MoleculeSignature(chain);
        String sigString = molSig.toCanonicalString();
        System.out.println(sigString);
    }
    
    @Test
    public void buckyballTest() {
        Molecule molecule = MoleculeReader.readMolfile("data/buckyball.mol");
        MoleculeQuotientGraph mqg = new MoleculeQuotientGraph(molecule);
        System.out.println(mqg);
    }
    
    @Test
    public void buckyballWithoutMultipleBonds() {
        Molecule molecule = MoleculeReader.readMolfile("data/buckyball.mol");
        for (Molecule.Bond bond : molecule.bonds()) {
            bond.order = 1;
        }
        MoleculeQuotientGraph mqg = new MoleculeQuotientGraph(molecule);
        System.out.println(mqg);
    }
    
    @Test
    public void faulonsBuckySignatures() {
        Molecule mol = MoleculeReader.readMolfile("data/buckyball.mol");
        try {
//            String filename = "data/buckysigs.txt";
            String filename = "data/buckysigs3.txt";
            List<String> sigs = readSigs2(filename);
            MoleculeQuotientGraph mqg = new MoleculeQuotientGraph(mol, sigs);
            System.out.println(mqg);
        } catch (Exception e) {
            System.out.println(e);
            return;
        }
    }
    
    public List<String> readSigs(String filename) throws Exception {
        File file = new File(filename);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        List<String> sigs = new ArrayList<String>();
        while ((line = reader.readLine()) != null) {
            int index = line.indexOf(" ") + 1;
            int count = Integer.parseInt(line.substring(0, index - 1));
            String sig = line.substring(index);
            System.out.println(count);
            sigs.add(sig);
        }
        return sigs;
    }
    
    public List<String> readSigs2(String filename) throws Exception {
        File file = new File(filename);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        List<String> sigs = new ArrayList<String>();
        while ((line = reader.readLine()) != null) { 
            String[] bits = line.split("\\s+");
            String sig = bits[3];
            sigs.add(sig);
        }
        Collections.reverse(sigs);
        return sigs;
    }
    
    public static void main(String[] args) {
        new LargeMoleculeTest().testMinimalMol();
//        new LargeMoleculeTest().ttprTest();
    }

}
