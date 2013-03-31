package abcmusic;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lexer.Header;
import lexer.Lexer;
import lexer.Token;

import org.junit.Test;

import player.Main;
import player.Rational;
import test.TestHelpers;


public class MeasureTest {
    @Test
    public void MeasureTest_Basic() {
    	Measure measure = TestHelpers.getFirstMeasureFromFile("sample_abc/testMeasure1.abc");

    	assertTrue(measure.getLength().equals(new Rational(15,1)));
    	assertEquals(8,measure.size);
    	assertTrue(measure.getElements(0) instanceof Tuplet);
    	assertTrue(measure.getElements(7) instanceof Chord);
    	for (int i=1;i<7;i++) assertTrue(measure.getElements(i) instanceof Note);
    	assertEquals("(4E1(1)1C2(1)1D3(1)2E-1(1) F-1(1/2) D-1(1/2) 2E-1(1/2) F-1(1/2) G-1(1) G-2(1) [C-1(8)C-2(8)] ",
    			ABCmusicToString.abcmusicToString(measure));
    }
    //test if local accidental is applied correctly to following notes on the same octave
    @Test
    public void MeasureTest_LocalAccidental() {
    	Measure measure = TestHelpers.getFirstMeasureFromFile("sample_abc/testMeasure2.abc");
 
    	assertTrue(measure.getLength().equals(new Rational(3,1)));
    	assertEquals(1,measure.size);
    	assertTrue(measure.getElements(0) instanceof Tuplet);
    	assertEquals("(4E0(1)1E0(1)1E0(1)E1(1) ", ABCmusicToString.abcmusicToString(measure));
    }
    //test if local accidental overwriting the previous ones is applied
    @Test
    public void MeasureTest_LocalAccidentalChange() {
    	Measure measure = TestHelpers.getFirstMeasureFromFile("sample_abc/testMeasure3.abc");

    	assertTrue(measure.getLength().equals(new Rational(15,1)));
    	assertEquals(8,measure.size);
    	assertTrue(measure.getElements(0) instanceof Tuplet);
    	assertTrue(measure.getElements(7) instanceof Chord);
    	for (int i=1;i<7;i++) assertTrue(measure.getElements(i) instanceof Note);
    	assertEquals("(4E0(1)1E0(1)1E0(1)E1(1) E2(1/2) D-1(1/2) 0E0(1/2) E-1(1/2) 0E0(1) -2E0(1) [E1(8)-2E0(8)] ", ABCmusicToString.abcmusicToString(measure));
 
    }
    // test chord in tuplet
    @Test
    public void MeasureTest_TupletofChord() {
    	Measure measure = TestHelpers.getFirstMeasureFromFile("sample_abc/testMeasure4.abc");
        
    	assertTrue(measure.getLength().equals(new Rational(15,1)));
    	assertEquals(8,measure.size);
    	assertTrue(measure.getElements(0) instanceof Tuplet);
    	Tuplet tempT=(Tuplet)measure.getElements(0);
    	assertTrue(tempT.getElement(2) instanceof Chord);
    	assertTrue(measure.getElements(7) instanceof Chord);
    	for (int i=1;i<7;i++) assertTrue(measure.getElements(i) instanceof Note);
    	assertEquals("(4E0(1)1E0(1)[1E0(1)B1(1)D1(1)]E1(1) E2(1/2) D-1(1/2) 0E0(1/2) E-1(1/2) 0E0(1) -2E0(1) [E1(8)-2E0(8)] ", ABCmusicToString.abcmusicToString(measure));
    }
    
}