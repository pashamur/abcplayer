package player;

public class Note implements ABCmusic {
    private boolean hasAccidental;
    private int accidental;
    public final int octave;
    public final char value;
    private final Rational length;
    
    public <R> R accept(Visitor<R> n) {
        return n.on(this);
    }
    /**
     * Construct a note from individual components
     * @param val basenote. must be [A-G]
     * @param oct octave
     * @param acc accidental. must be -1, 0, 1. If hasAccidental is false, a should be 0 (default).
     * @param hasAcc hasAccidental (whether accidental is specified for the note or is default (unspecified)
     * @param len length of the note (in units of L)
     * @throw RuntimeException ins val is not in [A-G]
     */
    public Note(char val, int oct, int acc, boolean hasAcc, Rational len) {
        if (val<'A' || val>'G') throw new RuntimeException("Basenote out of bound.");
		hasAccidental = hasAcc;
		octave = oct;
		accidental = acc;
		value = val;
		length = len;
    }
    /**
     * Return a copy of the current note without modifying the original
     */
    @Override
    public Note clone(){
        return new Note(value,octave,accidental,hasAccidental,length);
    }
    /**
     * Set the accidental and hasAccidental fields to the given values.
     * @param a new accidental 
     * @param b new hasAccidental
     */
    public void setAccidental(int a, boolean b) {
        accidental=a;
        hasAccidental=b;
    }
    public boolean getHasAccidental() {
        return hasAccidental;
    }
    public int getAccidental() {
        return accidental;
    }
    public Rational getLength() {
        return length.clone();
    }
    
    /**
     * Check for equality by comparing octaves, values, accidentals, and lengths
     * @return true if the current note is equal to the other note
     */
    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Note)) return false;
        Note that=(Note) other;
        return (octave==that.octave && value==that.value && this.getHasAccidental()==that.getHasAccidental() 
                && this.getAccidental()==that.getAccidental() && this.getLength().equals(that.getLength()));
    }
}
