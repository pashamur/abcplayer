package abcmusic;

import static org.junit.Assert.*;

import org.junit.Test;

import player.Rational;

public class NoteTest {
    /**
     * 0) Test constructor
     * 1) Test that we can call setAccidental on a valid note
     * 2) Test that we can clone a note to get an identical note
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
        assertTrue(copy.equals(origin));
        copy.setAccidental(1,true);
        Note backup=new Note('C',0,0,false,new Rational(1,4));
        assertTrue(origin.equals(backup));
        assertFalse(origin.equals(copy));
        
    }
    // test invalid note
    @Test
    public void NoteTest_invalidNote() {
        boolean flag=false;
        try {
            new Note('c',0,0,false,new Rational(1,4));
        } catch(RuntimeException e) {
            flag=true;
        }
        assertTrue(flag);
    }
}
