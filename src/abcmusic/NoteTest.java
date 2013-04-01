package abcmusic;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import player.Rational;

public class NoteTest {
    /**
     * 0) Test constructor and StringToNote
     * 1) Test that we can call setAccidental on a valid note
     * 2) Test that we can clone a note to get an identical note
     * 3) Test that attempting to instantiate an invalid note causes a RuntimeException
     */
    @Test
    public void NoteTest_Basic0() {
        Note origin=new Note('C',1,0,false,new Rational(1,4));
        assertFalse(origin.getHasAccidental());
        assertEquals(0,origin.getAccidental());
        assertEquals(origin.value,'C');
        assertEquals(origin.octave,1);
        assertTrue(origin.getLength().equals(new Rational(1,4)));
    }
    @Test
    public void NoteTest_Basic1() {
        Note origin=new Note('A',-1,-1,true,new Rational(7,33));
        assertTrue(origin.getHasAccidental());
        assertEquals(-1,origin.getAccidental());
        assertEquals(origin.value,'A');
        assertEquals(origin.octave,-1);
        assertTrue(origin.getLength().equals(new Rational(7,33)));
    }
    @Test
    public void NoteTest_StringToNote0() {
        List<String> input=new ArrayList<String>();
        input.add("^^");
        input.add("A");
        input.add(",");
        input.add("");
        input.add("/");
        input.add("");
        Note result=Note.stringListToNote(input);
        Note expect=new Note('A',-1,2,true,new Rational(1,2));
        assertTrue(ABCmusicEqual.abcmusicEqual(expect,result));
    }
    @Test
    public void NoteTest_StringToNote1() {
        List<String> input=new ArrayList<String>();
        input.add("");
        input.add("c");
        input.add("'");
        Note result=Note.stringListToNote(input);
        Note expect=new Note('C',2,0,false,new Rational(1,1));
        assertTrue(ABCmusicEqual.abcmusicEqual(expect,result));
    }
    @Test
    public void NoteTest_StringToNote2() {
        List<String> input=new ArrayList<String>();
        input.add("_");
        input.add("G");
        input.add("");
        input.add("2");
        Note result=Note.stringListToNote(input);
        Note expect=new Note('G',0,-1,true,new Rational(2,1));
        assertTrue(ABCmusicEqual.abcmusicEqual(expect,result));
    }
	@Test
    public void NoteTest_setAccidental() {
        Note origin=new Note('C',0,0,false,new Rational(1,4));
        origin.setAccidental(1, true);
        assertTrue(origin.getHasAccidental());
        assertEquals(1,origin.getAccidental());
    }
    @Test
    public void NoteTest_copy() {
        Note origin=new Note('C',0,0,false,new Rational(1,4));
        Note copy=origin.clone();
        assertTrue(ABCmusicEqual.abcmusicEqual(copy, origin));
        copy.setAccidental(1,true);
        Note backup=new Note('C',0,0,false,new Rational(1,4));
        assertTrue(ABCmusicEqual.abcmusicEqual(origin,backup));
        assertFalse(ABCmusicEqual.abcmusicEqual(origin,copy));
        
    }
    // test invalid note
    @Test(expected=RuntimeException.class)
    public void NoteTest_invalidNote() {
        new Note('c',0,0,false,new Rational(1,4));
    }
}
