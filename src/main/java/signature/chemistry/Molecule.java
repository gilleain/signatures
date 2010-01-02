package signature.chemistry;

import java.util.ArrayList;
import java.util.List;

/**
 * A trivial test molecule class, to show how to implement signatures for  
 * chemistry libraries.
 * 
 * @author maclean
 *
 */
public class Molecule {
    
    public class Atom {
        
        public int index;
        
        public String symbol;
        
        public Atom(int index, String symbol) {
            this.index = index;
            this.symbol = symbol;
        }
        
        public String toString() {
            return this.symbol + ":" + this.index;
        }
    }
    
    public class Bond {
        
        public Atom a;
        
        public Atom b;
        
        public int order;
        
        public Bond(Atom a, Atom b, int order) {
            this.a = a;
            this.b = b;
            this.order = order;
        }
        
        public int getConnected(int i) {
            if (a.index == i) {
                return this.b.index;
            } else if (b.index == i) {
                return this.a.index;
            } else {
                return -1;
            }
        }
        
        public boolean hasBoth(int atomIndexA, int atomIndexB) {
            return (this.a.index == atomIndexA && this.b.index == atomIndexB)
                || (this.b.index == atomIndexA && this.a.index == atomIndexB);
        }
        
        public String toString() {
            return this.a + "-" + this.b + "(" + this.order + ")";
        }
    }
    
    private List<Atom> atoms;
    
    private List<Bond> bonds;
    
    public Molecule() {
        this.atoms = new ArrayList<Atom>();
        this.bonds = new ArrayList<Bond>();
    }
    
    public Molecule(String atomSymbol, int count) {
        this();
        for (int i = 0; i < count; i++) {
            this.addAtom(atomSymbol);
        }
    }
    
    public Molecule(Molecule other) {
        this();
        for (Atom atom : other.atoms) {
            this.atoms.add(new Atom(atom.index, atom.symbol));
        }
        
        for (Bond bond : other.bonds) {
            Atom oA = this.atoms.get(bond.a.index);
            Atom oB = this.atoms.get(bond.b.index);
            this.bonds.add(new Bond(oA, oB, bond.order));
        }
    }
    
    public Molecule(Molecule other, int[] permutation) {
        this();
        Atom[] permutedAtoms = new Atom[permutation.length];
        for (Atom atom : other.atoms) {
            int index = permutation[atom.index];
            permutedAtoms[index] = new Atom(index, atom.symbol);
        }
        for (Atom atom : permutedAtoms) {
            this.atoms.add(atom);
        }
        
        for (Bond bond : other.bonds) {
            Atom oA = this.atoms.get(permutation[bond.a.index]);
            Atom oB = this.atoms.get(permutation[bond.b.index]);
            this.bonds.add(new Bond(oA, oB, bond.order));
        }

    }

    public int getAtomCount() {
        return this.atoms.size();
    }
    
    public int getBondCount() {
        return this.bonds.size();
    }
    
    public int[] getConnected(int atomIndex) {
        List<Integer> connectedList = new ArrayList<Integer>();
        
        for (Bond bond : this.bonds) {
            int connectedIndex = bond.getConnected(atomIndex);
            if (connectedIndex != -1) {
                connectedList.add(connectedIndex);
            }
        }
        int[] connected = new int[connectedList.size()];
        for (int i = 0; i < connectedList.size(); i++) {
            connected[i] = connectedList.get(i);
        }
        return connected;
    }
    
    public int getBondOrder(int atomIndex, int otherAtomIndex) {
        for (Bond bond : this.bonds) {
            if (bond.hasBoth(atomIndex, otherAtomIndex)) {
                return bond.order;
            }
        }
        return -1;
    }
    
    public String getSymbolFor(int atomIndex) {
        return this.atoms.get(atomIndex).symbol;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (Atom a : this.atoms) {
            buffer.append(a).append("|");
        }
        for (Bond b : this.bonds) {
            buffer.append(b).append("|");
        }
        return buffer.toString();
    }
    
    public void addAtom(String symbol) {
        int i = this.atoms.size();
        this.addAtom(i, symbol);
    }

    public void addAtom(int i, String symbol) {
        this.atoms.add(new Atom(i, symbol));
    }

    public void addBond(int atomNumberA, int atomNumberB, int order) {
        Atom a = this.atoms.get(atomNumberA);
        Atom b = this.atoms.get(atomNumberB);
        this.bonds.add(new Bond(a, b, order));
    }
    
    public boolean identical(Molecule other) {
        if (this.getBondCount() != other.getBondCount()) return false;
        for (Bond bond : this.bonds) {
            boolean hasPartner = false;
            for (Bond otherBond : other.bonds) {
                if ((bond.a.index == otherBond.a.index 
                        && bond.b.index == otherBond.b.index) ||
                    (bond.b.index == otherBond.a.index 
                        && bond.a.index == otherBond.b.index)) {
                    hasPartner = true;
                    continue;
                }
            }
            if (!hasPartner) {
                return false;
            }
        }
        return true;
    }
    
    public boolean bondsOrdered() {
        for (int i = 1; i < this.bonds.size(); i++) {
            Bond bondA = this.bonds.get(i - 1);
            Bond bondB = this.bonds.get(i);
            int aMin = Math.min(bondA.a.index, bondA.b.index);
            int aMax = Math.max(bondA.a.index, bondA.b.index);
            int bMin = Math.min(bondB.a.index, bondB.b.index);
            int bMax = Math.max(bondB.a.index, bondB.b.index);
            if (aMin < bMin || (aMin == bMin && aMax < bMax)) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
}
