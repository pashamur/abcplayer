package abcmusic;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import player.Rational;


public class TupletTest {
    @Test
    public void TupletTest_Length2(){
        Note[] notes={new Note('C',0,0,false,new Rational(1,4)),new Note('A',0,0,false,new Rational(1,4))};
        Tuplet result=new Tuplet(2,notes,new Rational(1,4));
        assertTrue(result.getLength().equals(new Rational(3,4)));
        assertEquals(2,result.size);
        assertTrue(result.getElement(0).equals(new Note('C',0,0,false,new Rational(1,4))));
        assertTrue(result.getElement(1).equals(new Note('A',0,0,false,new Rational(1,4))));
    }
    @Test
    public void TupletTest_Length3(){
        Note[] notes={new Note('C',0,0,false,new Rational(1,8)),new Note('A',0,0,false,new Rational(1,8)),
                new Note('G',0,0,false,new Rational(1,8))};
        Tuplet result=new Tuplet(3,notes,new Rational(1,8));
        assertTrue(result.getLength().equals(new Rational(2,8)));
        assertEquals(3,result.size);
    }
    @Test
    public void TupletTest_Length4(){
        Note[] notes={new Note('C',0,0,false,new Rational(1,8)),new Note('A',0,0,false,new Rational(1,8)),
                new Note('G',0,0,false,new Rational(1,8)), new Note('B',0,0,false,new Rational(1,8))};
        Tuplet result=new Tuplet(4,notes,new Rational(1,8));
        assertTrue(result.getLength().equals(new Rational(3,8)));
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
}
