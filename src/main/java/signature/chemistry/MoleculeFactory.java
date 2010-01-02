package signature.chemistry;

public class MoleculeFactory {
    
    public static Molecule threeCycle() {
        Molecule molecule = new Molecule("C", 3);
        molecule.addBond(0, 1, 1);
        molecule.addBond(0, 2, 1);
        molecule.addBond(1, 2, 1);
        return molecule;
    }
    
    public static Molecule fourCycle() {
        Molecule molecule = new Molecule("C", 4);
        molecule.addBond(0, 1, 1);
        molecule.addBond(0, 3, 1);
        molecule.addBond(1, 2, 1);
        molecule.addBond(2, 3, 1);
        return molecule;
    }
    
    public static Molecule fiveCycle() {
        Molecule molecule = new Molecule("C", 5);
        molecule.addBond(0, 1, 1);
        molecule.addBond(0, 4, 1);
        molecule.addBond(1, 2, 1);
        molecule.addBond(2, 3, 1);
        molecule.addBond(3, 4, 1);
        return molecule;
    }
    
    public static Molecule sixCycle() {
        Molecule molecule = new Molecule("C", 6);
        molecule.addBond(0, 1, 1);
        molecule.addBond(0, 5, 1);
        molecule.addBond(1, 2, 1);
        molecule.addBond(2, 3, 1);
        molecule.addBond(3, 4, 1);
        molecule.addBond(4, 5, 1);
        return molecule;
    }
    
    public static Molecule threeStar() {
        Molecule molecule = new Molecule("C", 4);
        molecule.addBond(0, 1, 1);
        molecule.addBond(0, 2, 1);
        molecule.addBond(0, 3, 1);
        return molecule;
    }
    
    public static Molecule fourStar() {
        Molecule molecule = new Molecule("C", 5);
        molecule.addBond(0, 1, 1);
        molecule.addBond(0, 2, 1);
        molecule.addBond(0, 3, 1);
        molecule.addBond(0, 4, 1);
        return molecule;
    }
    
    public static Molecule fiveStar() {
        Molecule molecule = new Molecule("C", 6);
        molecule.addBond(0, 1, 1);
        molecule.addBond(0, 2, 1);
        molecule.addBond(0, 3, 1);
        molecule.addBond(0, 4, 1);
        molecule.addBond(0, 5, 1);
        return molecule;
    }
    
    public static Molecule propellane() {
        Molecule molecule = new Molecule("C", 5);
        molecule.addBond(0, 1, 1);
        molecule.addBond(0, 4, 1);
        molecule.addBond(1, 2, 1);
        molecule.addBond(1, 3, 1);
        molecule.addBond(1, 4, 1);
        molecule.addBond(2, 4, 1);
        molecule.addBond(3, 4, 1);
        return molecule;
    }
    
    public static Molecule pseudopropellane() {
        Molecule molecule = new Molecule("C", 5);
        molecule.addBond(0, 1, 1);
        molecule.addBond(1, 2, 1);
        molecule.addBond(1, 3, 1);
        molecule.addBond(1, 4, 1);
        molecule.addBond(2, 4, 1);
        molecule.addBond(3, 4, 1);
        return molecule;
    }


}
