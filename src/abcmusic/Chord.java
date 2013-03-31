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
    public Chord(List<Note> n) {
        size=n.size();
        if (size<2) throw new RuntimeException("Chord contains too few notes.");
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
