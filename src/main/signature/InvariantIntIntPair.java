package signature;

public class InvariantIntIntPair implements Comparable<InvariantIntIntPair> {
    
    public int invariant;
    
    public int index;
    
    public InvariantIntIntPair(int invariant, int index) {
        this.invariant = invariant;
        this.index = index;
    }

    public int compareTo(InvariantIntIntPair o) {
        if (this.invariant < o.invariant) {
            return -1;
        } else if (this.invariant > o.invariant) {
            return 1;
        } else {
            return 0;
        }
    }

}
