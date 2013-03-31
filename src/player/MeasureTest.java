package player;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MeasureTest {
    @Test
    public void MeasureTest_Basic() {
        String file = "sample_abc/testMeasure1.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk = lexer.getTokens(0);
            Measure measure = new Measure(tk);
            assertTrue(measure.getLength().equals(new Rational(15,1)));
            assertEquals(8,measure.size);
            assertTrue(measure.getElements(0) instanceof Tuplet);
            assertTrue(measure.getElements(7) instanceof Chord);
            for (int i=1;i<7;i++) assertTrue(measure.getElements(i) instanceof Note);
            assertEquals("(4E1(1)1C2(1)1D3(1)2E-1(1) F-1(1/2) D-1(1/2) 2E-1(1/2) F-1(1/2) G-1(1) G-2(1) [C-1(8)C-2(8)] ",
                    ABCmusicToString.abcmusicToString(measure));
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
    //test if local accidental is applied correctly to following notes on the same octave
    @Test
    public void MeasureTest_LocalAccidental() {
        String file = "sample_abc/testMeasure2.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk = lexer.getTokens(0);
            Measure measure = new Measure(tk);
            assertTrue(measure.getLength().equals(new Rational(3,1)));
            assertEquals(1,measure.size);
            assertTrue(measure.getElements(0) instanceof Tuplet);
            assertEquals("(4E0(1)1E0(1)1E0(1)E1(1) ", ABCmusicToString.abcmusicToString(measure));
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
    //test if local accidental overwriting the previous ones is applied
    @Test
    public void MeasureTest_LocalAccidentalChange() {
        String file = "sample_abc/testMeasure3.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk = lexer.getTokens(0);
            Measure measure = new Measure(tk);
            assertTrue(measure.getLength().equals(new Rational(15,1)));
            assertEquals(8,measure.size);
            assertTrue(measure.getElements(0) instanceof Tuplet);
            assertTrue(measure.getElements(7) instanceof Chord);
            for (int i=1;i<7;i++) assertTrue(measure.getElements(i) instanceof Note);
            assertEquals("(4E0(1)1E0(1)1E0(1)E1(1) E2(1/2) D-1(1/2) 0E0(1/2) E-1(1/2) 0E0(1) -2E0(1) [E1(8)-2E0(8)] ", ABCmusicToString.abcmusicToString(measure));
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
    // test chord in tuplet
    @Test
    public void MeasureTest_TupletofChord() {
        String file = "sample_abc/testMeasure4.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk = lexer.getTokens(0);
            Measure measure = new Measure(tk);
            assertTrue(measure.getLength().equals(new Rational(15,1)));
            assertEquals(8,measure.size);
            assertTrue(measure.getElements(0) instanceof Tuplet);
            Tuplet tempT=(Tuplet)measure.getElements(0);
            assertTrue(tempT.getElement(2) instanceof Chord);
            assertTrue(measure.getElements(7) instanceof Chord);
            for (int i=1;i<7;i++) assertTrue(measure.getElements(i) instanceof Note);
            assertEquals("(4E0(1)1E0(1)[1E0(1)B1(1)D1(1)]E1(1) E2(1/2) D-1(1/2) 0E0(1/2) E-1(1/2) 0E0(1) -2E0(1) [E1(8)-2E0(8)] ", ABCmusicToString.abcmusicToString(measure));
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
}