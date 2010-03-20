package signature.chemistry;

public class MoleculeFactory {
    
    public static Molecule methane() {
        Molecule molecule = new Molecule();
        molecule.addAtom("C");
        molecule.addAtom("H");
        molecule.addAtom("H");
        molecule.addAtom("H");
        molecule.addAtom("H");
        molecule.addSingleBond(0, 1);
        molecule.addSingleBond(0, 2);
        molecule.addSingleBond(0, 3);
        molecule.addSingleBond(0, 4);
        return molecule;
    }
    
    public static Molecule threeCycle() {
        Molecule molecule = new Molecule("C", 3);
        molecule.addSingleBond(0, 1);
        molecule.addSingleBond(0, 2);
        molecule.addSingleBond(1, 2);
        return molecule;
    }
    
    public static Molecule fourCycle() {
        Molecule molecule = new Molecule("C", 4);
        molecule.addSingleBond(0, 1);
        molecule.addSingleBond(0, 3);
        molecule.addSingleBond(1, 2);
        molecule.addSingleBond(2, 3);
        return molecule;
    }
    
    public static Molecule fiveCycle() {
        Molecule molecule = new Molecule("C", 5);
        molecule.addSingleBond(0, 1);
        molecule.addSingleBond(0, 4);
        molecule.addSingleBond(1, 2);
        molecule.addSingleBond(2, 3);
        molecule.addSingleBond(3, 4);
        return molecule;
    }
    
    public static Molecule sixCycle() {
        Molecule molecule = new Molecule("C", 6);
        molecule.addSingleBond(0, 1);
        molecule.addSingleBond(0, 5);
        molecule.addSingleBond(1, 2);
        molecule.addSingleBond(2, 3);
        molecule.addSingleBond(3, 4);
        molecule.addSingleBond(4, 5);
        return molecule;
    }
    
    public static Molecule threeStar() {
        Molecule molecule = new Molecule("C", 4);
        molecule.addSingleBond(0, 1);
        molecule.addSingleBond(0, 2);
        molecule.addSingleBond(0, 3);
        return molecule;
    }
    
    public static Molecule fourStar() {
        Molecule molecule = new Molecule("C", 5);
        molecule.addSingleBond(0, 1);
        molecule.addSingleBond(0, 2);
        molecule.addSingleBond(0, 3);
        molecule.addSingleBond(0, 4);
        return molecule;
    }
    
    public static Molecule fiveStar() {
        Molecule molecule = new Molecule("C", 6);
        molecule.addSingleBond(0, 1);
        molecule.addSingleBond(0, 2);
        molecule.addSingleBond(0, 3);
        molecule.addSingleBond(0, 4);
        molecule.addSingleBond(0, 5);
        return molecule;
    }
    
    public static Molecule propellane() {
        Molecule molecule = new Molecule("C", 5);
        molecule.addSingleBond(0, 1);
        molecule.addSingleBond(0, 4);
        molecule.addSingleBond(1, 2);
        molecule.addSingleBond(1, 3);
        molecule.addSingleBond(1, 4);
        molecule.addSingleBond(2, 4);
        molecule.addSingleBond(3, 4);
        return molecule;
    }
    
    public static Molecule methylatedCyclobutane() {
        Molecule molecule = new Molecule("C", 5);
//        molecule.addSingleBond(0, 1);
//        molecule.addSingleBond(1, 2);
//        molecule.addSingleBond(1, 3);
//        molecule.addSingleBond(1, 4);
//        molecule.addSingleBond(2, 4);
//        molecule.addSingleBond(3, 4);
        molecule.addSingleBond(0, 1);
        molecule.addSingleBond(0, 2);
        molecule.addSingleBond(0, 3);
        molecule.addSingleBond(0, 4);
        molecule.addSingleBond(2, 3);
        molecule.addSingleBond(3, 4);
        return molecule;
    }
    
    public static Molecule pseudopropellane() {
        Molecule molecule = new Molecule("C", 5);
        molecule.addSingleBond(0, 1);
        molecule.addSingleBond(0, 4);
        molecule.addSingleBond(1, 2);
        molecule.addSingleBond(1, 3);
        molecule.addSingleBond(2, 4);
        molecule.addSingleBond(3, 4);
        return molecule;
    }

    // note that this cannot physically exist, 
    // as the bond strain would be too high 
    public static Molecule sixCage() {
        Molecule molecule = new Molecule("C", 6);
        molecule.addSingleBond(0, 1);
        molecule.addSingleBond(0, 2);
        molecule.addSingleBond(0, 3);
        molecule.addSingleBond(1, 2);
        molecule.addSingleBond(1, 3);
        molecule.addSingleBond(2, 5);
        molecule.addSingleBond(3, 4);
        molecule.addSingleBond(3, 5);
        molecule.addSingleBond(4, 5);
        return molecule;
    }

}
