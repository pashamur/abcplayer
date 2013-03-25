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
     * construct a note from input. v must be [A-G]. 
     * @param v basenote. must be [A-G]
     * @param o octave
     * @param a accidental. must be -1, 0, 1. If hasAccidental is false, a should be 0 (default).
     * @param b hasAccidental (whether accidental is specified for the note or is default (unspecified)
     * @param l length of the note (in units of L)
     */
    public Note(char v, int o, int a, boolean b, Rational l) {
        hasAccidental=b;
        accidental=a;
        octave=o;
        value=v;
        length=l;
    }
    /**
     * return a clone of note. Do not modify the original
     */
    @Override
    public Note clone(){
        return new Note(value,octave,accidental,hasAccidental,length);
    }
    /**
     * reset the accidental and hasAccidenta to the default value.
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
    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Note)) return false;
        Note that=(Note) other;
        return (octave==that.octave && value==that.value && this.getHasAccidental()==that.getHasAccidental() 
                && this.getAccidental()==that.getAccidental() && this.getLength().equals(that.getLength()));
    }
}
