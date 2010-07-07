package signature.chemistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A trivial test molecule class, to show how to implement signatures for  
 * chemistry libraries.
 * 
 * @author maclean
 *
 */
public class Molecule {
    
    public enum BondOrder { NONE, SINGLE, DOUBLE, TRIPLE, AROMATIC }
    
    public class Atom {
        
        public int index;
        
        public String symbol;
        
        public Atom(int index, String symbol) {
            this.index = index;
            this.symbol = symbol;
        }
        
        public Atom(Atom other) {
            this.index = other.index;
            this.symbol = other.symbol;
        }
        
        public boolean equals(Atom other) {
            return this.index == other.index 
                && this.symbol.equals(other.symbol);
        }
        
        public String toString() {
            return this.index + this.symbol;
        }
    }
    
    public class Bond implements Comparable<Bond> {
        
        public Atom a;
        
        public Atom b;
        
        public BondOrder order;
        
        public Bond(Atom a, Atom b, BondOrder order) {
            this.a = a;
            this.b = b;
            this.order = order;
        }
        
        public Bond(Bond other) {
            this.a = new Atom(other.a);
            this.b = new Atom(other.b);
            this.order = other.order;
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
        
        public boolean equals(Object o) {
            Bond other = (Bond) o;
            return (this.a.equals(other.a) && this.b.equals(other.b)) 
                || (this.a.equals(other.b) && this.b.equals(other.a)); 
                    
        }
        
        public boolean hasBoth(int atomIndexA, int atomIndexB) {
            return (this.a.index == atomIndexA && this.b.index == atomIndexB)
                || (this.b.index == atomIndexA && this.a.index == atomIndexB);
        }
        
        public String toString() {
            if (a.index < b.index) { 
                return this.a + "-" + this.b + "(" + this.order + ")";
            } else {
                return this.b + "-" + this.a + "(" + this.order + ")";
            }
        }

        public int compareTo(Bond o) {
            int thisMin = Math.min(this.a.index, this.b.index);
            int thisMax = Math.max(this.a.index, this.b.index);
            int oMin = Math.min(o.a.index, o.b.index);
            int oMax = Math.max(o.a.index, o.b.index);
            if (thisMin < oMin) {
                return -1;
            } else if (thisMin == oMin){
                if (thisMax < oMax) {
                    return -1;
                } else if (thisMax == oMax) {
                    return 0;
                } else {
                    return 1;
                }
            } else {
                return 1;
            }
        }
    }
    
    private List<Atom> atoms;
    
    private List<Bond> bonds;
    
    public String name;
    
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
    
    public List<Bond> bonds() {
        return bonds; 
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
    
    public boolean isConnected(int i, int j) {
        for (Bond bond : bonds) {
            if (bond.hasBoth(i, j)) {
                return true;
            }
        }
        return false;
    }
    
    public BondOrder getBondOrder(int atomIndex, int otherAtomIndex) {
        for (Bond bond : this.bonds) {
            if (bond.hasBoth(atomIndex, otherAtomIndex)) {
                return bond.order;
            }
        }
        return BondOrder.NONE;
    }
    
    public int convertBondOrderToInt(BondOrder bondOrder) {
        switch (bondOrder) {
            case NONE : return 0;
            case SINGLE : return 1;
            case DOUBLE : return 2;
            case TRIPLE : return 3;
            case AROMATIC : return 4;   // hmmm...
            default : return 1;
        }
    }
    
    public int getTotalOrder(int atomIndex) {
        int totalOrder = 0;
        for (Bond bond : bonds) {
            if (bond.a.index == atomIndex || bond.b.index == atomIndex) {
                totalOrder += convertBondOrderToInt(bond.order);
            }
        }
        return totalOrder;
    }
    
    public String getSymbolFor(int atomIndex) {
        return this.atoms.get(atomIndex).symbol;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (Atom a : this.atoms) {
            buffer.append(a).append("|");
        }
        Collections.sort(bonds);
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
    
    public void addMultipleAtoms(int count, String symbol) {
        for (int i = 0; i < count; i++) {
            addAtom(symbol);
        }
    }
    
    public void addSingleBond(int atomNumberA, int atomNumberB) {
        this.addBond(atomNumberA, atomNumberB, BondOrder.SINGLE);
    }

    public void addMultipleSingleBonds(int i, int...js) {
        for (int j : js) {
            addSingleBond(i, j);
        }
    }

    public void addBond(int atomNumberA, int atomNumberB, BondOrder order) {
        Atom a = this.atoms.get(atomNumberA);
        Atom b = this.atoms.get(atomNumberB);
        this.bonds.add(new Bond(a, b, order));
    }
    
    public boolean identical(Molecule other) {
        if (this.getBondCount() != other.getBondCount()) return false;
        for (Bond bond : this.bonds) {
            if (other.bonds.contains(bond)) {
                continue;
            } else {
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

    public String toEdgeString() {
        StringBuffer edgeString = new StringBuffer();
        List<Bond> listCopy = new ArrayList<Bond>();
        for (Bond bond : this.bonds) {
            listCopy.add(new Bond(bond));
        }
        Collections.sort(listCopy);
        for (Bond bond : listCopy) {
            if (bond.a.index < bond.b.index) {
                edgeString.append(bond.a).append(":").append(bond.b);
            } else {
                edgeString.append(bond.b).append(":").append(bond.a);
            }
            edgeString.append(",");
        }
        return edgeString.toString();
    }

    public int getFirstInBond(int bondIndex) {
        return bonds.get(bondIndex).a.index;
    }
    
    public int getSecondInBond(int bondIndex) {
        return bonds.get(bondIndex).b.index;
    }

    public BondOrder getBondOrder(int bondIndex) {
        return bonds.get(bondIndex).order;
    }
    
    public int getBondOrderAsInt(int bondIndex) {
        return convertBondOrderToInt(bonds.get(bondIndex).order);
    }

}
