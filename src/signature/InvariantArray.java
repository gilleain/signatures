package signature;

public class InvariantArray implements Comparable<InvariantArray>{
    
    public final int[] invariants;
    
    public final int originalIndex;
    
    public InvariantArray(int[] invariants, int originalIndex) {
        this.invariants = invariants;
        this.originalIndex = originalIndex;
    }
    
    public boolean equals(int[] other) {
        for (int i = 0; i < this.invariants.length; i++) {
            if (this.invariants[i] == other[i]) {
                continue;
            } else {
                return false;
            }
        }
        return true;

    }
    
    public boolean equals(Object o) {
        if (o instanceof InvariantArray) {
            InvariantArray other = (InvariantArray) o;
            return this.equals(other.invariants);
        }
        return false;
    }

    public int compareTo(InvariantArray o) {
        for (int i = 0; i < this.invariants.length; i++) {
            if (this.invariants[i] < o.invariants[i]) {
                return -1;
            } else if (this.invariants[i] > o.invariants[i]) {
                return 1;
            }
        }
        return 0;
    }
    
    public String toString() {
        return this.invariants + ", " + this.originalIndex;
    }

}
