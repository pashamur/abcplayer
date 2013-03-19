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
    public Note(char v, int o, int a, boolean b, Rational l) {
        hasAccidental=b;
        accidental=a;
        octave=o;
        value=v;
        length=l;
    }
    @Override
    public Note clone(){
        return new Note(value,octave,accidental,hasAccidental,length);
    }
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
}
