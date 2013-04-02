package abcmusic;

import player.Rational;

public class Tuplet implements ABCmusic {
    private final Rational length;//total length of tuplet (in L)
    private final Rational noteLength;//length of individual note (in L)
    public final int size;//number of notes in tuplet
    private final ABCmusic[] elements;//Note, Chord
    
    public <R> R accept(Visitor<R> t) {
        return t.on(this);
    }

    @SuppressWarnings("serial")
    private static class TupletException extends RuntimeException {
        public TupletException (String message) {
            super("TupletException: "+message);
        }
    }
    /**
     * Generate a tuplet of notes n. l is the length of individual note. spec is the tuplet-spec.
     * @param spec 2, 3, or 4
     * @param n Array of Note. n.length must equals to spec.
     * @param l length of individual note in n.
     * @throws TupletException("Tuplet spec does not match notes.") if length of note[] does not equal spec.
     */
    public Tuplet(int spec, ABCmusic[] n, Rational l) {
        size=spec;
        int len=n.length;
        if (spec!=len) throw new TupletException("Tuplet spec does not match notes.");
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
        else throw new TupletException("Tuplet-spec invalid.");
        elements=new ABCmusic[spec];
        for (int i=0; i<len; i++) elements[i]=n[i];
    }
    public Rational getLength() {
        return length.clone();
    }
    public ABCmusic getElement(int i) {
        if (i>=size) throw new TupletException("Index out of bound in Tuplet.");
        return elements[i];
    }
    public Rational getNoteLength(){
    	return noteLength.clone();
    }
}
