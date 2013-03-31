package player;

import static org.junit.Assert.*;

import org.junit.Test;

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
}
