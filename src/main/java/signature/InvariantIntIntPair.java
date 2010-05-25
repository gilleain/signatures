package signature;

public class InvariantIntIntPair implements Comparable<InvariantIntIntPair> {
    
    public int label;
    
    public int value;
    
    public int originalIndex;
    
    public InvariantIntIntPair(int label, int value, int originalIndex) {
        this.label = label;
        this.value = value;
        this.originalIndex = originalIndex;
    }
    
    public boolean equals(int label, int value) {
        return this.value == value && this.label == label;
    }
    
    public boolean equals(InvariantIntIntPair o) {
        return this.value == o.value && this.label == o.label;
    }

    public int compareTo(InvariantIntIntPair o) {
        int c = (this.label == o.label)? 0 :
            ((this.label < o.label)? -1 : 1);
        if (c == 0) {
            if (this.value < o.value) {
                return -1;
            } else if (this.value > o.value){
                return 1;
            } else {
                return 0;
            }
        } else {
            return c;
        }
    }
    
    public int getOriginalIndex() {
        return originalIndex;
    }

    public String toString() {
        return this.label + "|" + this.value + "|" + this.originalIndex;
    }
    

}
