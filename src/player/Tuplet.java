package player;

public class Tuplet implements ABCmusic {
    private final Rational length;
    public final int size;
    private final Note[] notes;
    
    public <R> R accept(Visitor<R> t) {
        return t.on(this);
    }
    /**
     * Generate a tuplet of notes n. l is the length of individual note. spec is the tuplet-spec.
     * @param spec 2, 3, or 4
     * @param n Array of Note. n.length must equals to spec.
     * @param l length of individual note in n.
     * @throws RuntimeException("Tuplet spec does not match notes.") if length of note[] does not equal spec.
     */
    public Tuplet(int spec, Note[] n, Rational l) {
        size=spec;
        int len=n.length;
        if (spec!=len) throw new RuntimeException("Tuplet spec does not match notes.");
        if (spec==2) length=l.times(new Rational(3,1));
        else if (spec==3) length=l.times(new Rational(2,1));
        else if (spec==4) length=l.times(new Rational(3,1));
        else throw new RuntimeException("Tuplet-spec invalid.");        
        notes=new Note[spec];
        for (int i=0; i<len; i++) notes[i]=n[i].clone();
    }
    public Rational getLength() {
        return length.clone();
    }
    public Note getNote(int i) {
        if (i>=size) throw new RuntimeException("Index out of bound in Tuplet.");
        return notes[i].clone();
    }
}
