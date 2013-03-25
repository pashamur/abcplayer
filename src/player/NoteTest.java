package player;

import static org.junit.Assert.*;

import org.junit.Test;

public class NoteTest {
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
}
