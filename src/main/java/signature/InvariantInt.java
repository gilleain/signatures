package signature;

public class InvariantInt implements Comparable<InvariantInt> {
    
    public int invariant;
    
    public int index;
    
    public InvariantInt(int invariant, int index) {
        this.invariant = invariant;
        this.index = index;
    }

    public int compareTo(InvariantInt o) {
        if (this.invariant < o.invariant) {
            return -1;
        } else if (this.invariant > o.invariant) {
            return 1;
        } else {
            return 0;
        }
    }
    
    public String toString() {
        return invariant + "/" + index;
    }

}
