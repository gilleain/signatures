package signature;

public class InvariantIntStringPair implements
        Comparable<InvariantIntStringPair> {

    public String string;

    public int index;

    public InvariantIntStringPair(String string, int index) {
        this.string = string;
        this.index = index;
    }
    
    public boolean equals(InvariantIntStringPair o) {
        return this.string.equals(o.string);
    }

    public int compareTo(InvariantIntStringPair o) {
        return this.string.compareTo(o.string);
    }

}
