package abcmusic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import player.Rational;

public class ChordTest {
    @Test
    public void ChordTest_Basic() {
        List<Note> notes=new ArrayList<Note>();
        notes.add(new Note('C',0,0,false,new Rational(1,2)));
        notes.add(new Note('A',0,0,false,new Rational(1,4)));
        Chord result=new Chord(notes);
        assertTrue(result.getLength().equals(new Rational(1,2)));
        assertEquals(result.size,2);
        assertTrue(result.getNote(0).equals(new Note('C',0,0,false,new Rational(1,2))));
        assertTrue(result.getNote(1).equals(new Note('A',0,0,false,new Rational(1,4))));
    }
}
