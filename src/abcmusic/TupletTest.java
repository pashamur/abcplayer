package abcmusic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import player.Rational;


public class TupletTest {
    /*
     * 0) constructor for tuplet with 2, 3, and 4 notes. 
     * 1) tuplet containing chord
     * 2) RuntimeException if spec is out of bound
     * 3) RuntimeException if spec does not match number of notes
     */
    @Test
    public void TupletTest_Length2(){
        Note[] notes={new Note('C',0,0,false,new Rational(1,4)),new Note('A',0,0,false,new Rational(1,4))};
        Tuplet result=new Tuplet(2,notes,new Rational(1,4));
        assertTrue(result.getLength().equals(new Rational(3,4)));
        assertTrue(result.getNoteLength().equals(new Rational(3,8)));
        assertEquals(2,result.size);
        assertTrue(ABCmusicEqual.abcmusicEqual(result.getElement(0),new Note('C',0,0,false,new Rational(1,4))));
        assertTrue(ABCmusicEqual.abcmusicEqual(result.getElement(1),new Note('A',0,0,false,new Rational(1,4))));
    }
    @Test
    public void TupletTest_Length3(){
        Note[] notes={new Note('C',0,0,false,new Rational(1,8)),new Note('A',0,0,false,new Rational(1,8)),
                new Note('G',0,0,false,new Rational(1,8))};
        Tuplet result=new Tuplet(3,notes,new Rational(1,8));
        assertTrue(result.getLength().equals(new Rational(2,8)));
        assertTrue(result.getNoteLength().equals(new Rational(1,12)));
        assertEquals(3,result.size);
    }
    @Test
    public void TupletTest_Length4(){
        Note[] notes={new Note('C',0,0,false,new Rational(1,8)),new Note('A',0,0,false,new Rational(1,8)),
                new Note('G',0,0,false,new Rational(1,8)), new Note('B',0,0,false,new Rational(1,8))};
        Tuplet result=new Tuplet(4,notes,new Rational(1,8));
        assertTrue(result.getLength().equals(new Rational(3,8)));
        assertTrue(result.getNoteLength().equals(new Rational(3,32)));
        assertEquals(4,result.size);
    }
    @Test
    public void TupletTest_Chord(){
       List<Note> notes=new ArrayList<Note>();
       notes.add(new Note('C',0,0,false,new Rational(1,8)));
       notes.add(new Note('A',0,0,false,new Rational(1,4)));
       notes.add(new Note('G',0,0,false,new Rational(1,8)));
       Chord tempC=new Chord(notes);
       ABCmusic[] elements={new Note('C',0,1,false,new Rational(1,8)),tempC,tempC};
        Tuplet result=new Tuplet(3,elements,new Rational(1,8));
        assertTrue(result.getLength().equals(new Rational(2,8)));
        assertEquals(3,result.size);
        assertTrue(result.getElement(0) instanceof Note);
        assertTrue(result.getElement(1) instanceof Chord);
    }
    @Test(expected=RuntimeException.class)
    public void TupletTest_SpecOutOfBound() {
        Note[] notes={new Note('C',0,0,false,new Rational(1,8)),new Note('A',0,0,false,new Rational(1,8)),
                new Note('G',0,0,false,new Rational(1,8)), new Note('B',0,0,false,new Rational(1,8))};
        new Tuplet(5,notes,new Rational(1,8));
    }
    @Test(expected=RuntimeException.class)
    public void TupletTest_SpecNotMathcNotes() {
        Note[] notes={new Note('C',0,0,false,new Rational(1,8)),new Note('A',0,0,false,new Rational(1,8)),
                new Note('G',0,0,false,new Rational(1,8)), new Note('B',0,0,false,new Rational(1,8))};
        new Tuplet(3,notes,new Rational(1,8));
    }
}
