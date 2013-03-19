package player;

import java.util.List;

public class Chord implements ABCmusic {
    private final int spec;
    private final Rational length;
    private final Note[] notes;
    public <R> R accept(Visitor<R> c) {
        return c.on(this);
    }
    public Chord(List<Note> n) {
        spec=n.size();
        if (spec<2) throw new RuntimeException("Chord contains too few notes.");
        length=n.get(0).getLength();
        notes=new Note[spec];
        for (int i=0;i<spec;i++)
            notes[i]=n.get(i).clone();        
    }
    public Rational getLength() {
        return length.clone();
    }
}
