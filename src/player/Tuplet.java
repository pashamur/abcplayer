package player;

public class Tuplet implements ABCmusic {
    private final Rational length;
    private final Rational noteLength;
    public final int size;
    private final ABCmusic[] elements;
    
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
    public Tuplet(int spec, ABCmusic[] n, Rational l) {
        size=spec;
        int len=n.length;
        if (spec!=len) throw new RuntimeException("Tuplet spec does not match notes.");
        if (spec==2) {
        	length=l.times(new Rational(3,1));
        	noteLength=l.times(new Rational(3,2));
        }
        else if (spec==3) {
        	length=l.times(new Rational(2,1));
        	noteLength=l.times(new Rational(2,3));
        }
        else if (spec==4) {
        	length=l.times(new Rational(3,1));
        	noteLength=l.times(new Rational(3,4));
        }
        else throw new RuntimeException("Tuplet-spec invalid.");        
        elements=new ABCmusic[spec];
        for (int i=0; i<len; i++) elements[i]=n[i];
    }
    public Rational getLength() {
        return length.clone();
    }
    public ABCmusic getElement(int i) {
        if (i>=size) throw new RuntimeException("Index out of bound in Tuplet.");
        return elements[i];
    }
    public Rational getNoteLength(){
    	return noteLength.clone();
    }
}
