package signature;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InvariantList implements Comparable<InvariantList>{
    
    public List<Integer> invariants;
    
    public int originalIndex;
    
    public InvariantList(int originalIndex) {
        this.invariants = new ArrayList<Integer>();
        this.originalIndex = originalIndex;
    }
    
    public boolean equals(List<Integer> other) {
    	// Check the size first. If it differs return false.
    	if ( !(this.invariants.size() == other.size()) ){
    		return false;
    	}
        for (int i = 0; i < this.invariants.size(); i++) {
            if (this.invariants.get(i) == other.get(i)) {
                continue;
            } else {
                return false;
            }
        }
        return true;
    }
    
    public void add(int i) {
        this.invariants.add(i);
    }
    
    public void addAll(Collection<Integer> other) {
        this.invariants.addAll(other);
    }
    
    public boolean equals(Object o) {
        if (o instanceof InvariantList) {
            InvariantList other = (InvariantList) o;
            return this.equals(other.invariants);
        }
        return false;
    }

    public int compareTo(InvariantList o) {
        int lA = this.invariants.size();
        int lB = o.invariants.size();
        if (lA < lB) {
            return -1;
        } else if (lA > lB) {
            return 1;
        } else {
            for (int i = 0; i < this.invariants.size(); i++) {
                if (this.invariants.get(i) < o.invariants.get(i)) {
                    return -1;
                } else if (this.invariants.get(i) > o.invariants.get(i)) {
                    return 1;
                }
            }
            return 0;
        }
    }
    
    public String toString() {
        return originalIndex + " " + invariants;
    }

}
