package signature;

public class InvariantIntStringPair implements Comparable<InvariantIntStringPair> {

    public String string;

    public int value;
    
    public int originalIndex;

    public InvariantIntStringPair(String string, int value, int originalIndex) {
        this.string = string;
        this.value = value;
        this.originalIndex = originalIndex;
    }
    
    public boolean equals(String string, int value) {
        return this.value == value && this.string.equals(string);
    }
    
    public boolean equals(InvariantIntStringPair o) {
        if (this.string == null || o.string == null) return false;
        return this.value == o.value && this.string.equals(o.string);
    }

    public int compareTo(InvariantIntStringPair o) {
        if (this.string == null || o.string == null) return 0;
        int c = this.string.compareTo(o.string);
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
        return this.string + "|" + this.value + "|" + this.originalIndex;
    }
}
