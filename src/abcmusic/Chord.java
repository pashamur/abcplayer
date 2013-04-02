package abcmusic;

import java.util.List;
import player.Rational;


public class Chord implements ABCmusic {
    public final int size;
    private final Rational length;
    private final Note[] notes;
    public <R> R accept(Visitor<R> c) {
        return c.on(this);
    }
    /**
     * construct chord given list of notes.
     * @param n list of Note
     * @throws RuntimeException if n is empty
     * prints warming if n.size=1 (chord has only one note)
     */
    public Chord(List<Note> n) {
        size=n.size();
        if (size==0) throw new RuntimeException("Chord contains empty notes.");
        if (size==1) System.out.println("Warming: Chord contains only one note.");
        length=n.get(0).getLength();
        notes=new Note[size];
        for (int i=0;i<size;i++)
            notes[i]=n.get(i).clone();
    }
    public Rational getLength() {
        return length.clone();
    }
    public Note getNote(int i){
        if (i>=size) throw new RuntimeException("Index out of bound in Chord.");
        else return notes[i].clone();
    }
}
