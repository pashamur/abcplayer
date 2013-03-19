package player;


/**
 * Pair represents an immutable pair of values (x,y). x and y MAY NOT be null.
 * 
 * @param <X> Type of the first object
 * @param <Y> Type of the second object
 */
public class Pair<X extends Comparable<X>, Y extends Comparable<Y>>
    implements Comparable<Pair<X, Y>>{

    // Using public final fields here so that client can read first and second
    // directly, but can't assign to them.
    public final X first;
    public final Y second;

    // rep invariant: first, second != null

    /**
     * Make a pair.
     * 
     * @param first
     *            Requires first != null.
     * @param second
     *            Requires second != null.
     */
    public Pair(X first, Y second) {
        if (first == null || second == null)
            throw new NullPointerException();
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { // quick check
            return true;
        }

        if (o == null || !(o instanceof Pair<?, ?>)) {
            return false;
        }

        Object otherFirst = ((Pair<?, ?>) o).first;
        Object otherSecond = ((Pair<?, ?>) o).second;
        return this.first.equals(otherFirst) && this.second.equals(otherSecond);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + first.hashCode();
        result = 37 * result + second.hashCode();
        return result;
    }

    /**
     * Compares this object with otherPair for order. Return value is an
     * integer. If this is less than otherPair, the return value is negative;
     * if this and otherPair are equal, the return value is 0;
     * and if this is greater than otherPair, the return value is positive.  
     * 
     * Order is first determined by comparing this.first and otherPair.first.
     * If they are the same, this.second and otherPair.second are compared.
     * 
     * For example, {"a","b"}.compareTo({"a","c"}) should return < 0,
     *      since the first fields of each pair are equal,
     *      and the second field of this is less than the second field of otherPair.
     * 
     * @param otherPair The other Pair object to compare against. Must be non-null.
     * @returns an integer value: 0 if this and otherPair are equal,
     *          a negative value if this is less than otherPair,
     *          a positive value if this is greater than otherPair.
     */
    @Override
    public int compareTo(Pair<X ,Y> otherPair) {
        if (this.equals(otherPair)) return 0;
        int firstCompare=this.first.compareTo(otherPair.first);
        if(firstCompare==0) return this.second.compareTo(otherPair.second);
        return firstCompare;
    }
}
