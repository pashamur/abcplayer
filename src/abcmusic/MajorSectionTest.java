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


public class MajorSectionTest {
    // test in case of no repeat, only one section is added. e.g A -> A
    @Test
    public void MajorSectionTest_NoRepeat() {
        String file = "sample_abc/scale.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk = lexer.getTokens(0);
            MajorSection majorSection = new MajorSection(tk.subList(0,tk.size()-1));
            assertEquals(1, majorSection.size);
            Rational expMeter = new Rational(4, 1);
            for (int i = 0; i < 4; i++)
                assertTrue(expMeter.equals(majorSection.mList.get(i)));
            //System.out.println(ABCmusicToString.abcmusicToString(majorSection));
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
    // check that in case one single repeat appears at the end of the major section
    // e.g. A:| -> AA
    @Test
    public void MajorSectionTest_RepeatEnd() {
        String file = "sample_abc/testRepeat0.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk = lexer.getTokens(0);
            MajorSection majorSection = new MajorSection(tk);
            assertEquals(2, majorSection.size);
            assertTrue(ABCmusicEqual.abcmusicEqual(majorSection.getSection(0), majorSection.getSection(1)));
            Rational expMeter = new Rational(6, 1);
            for (int i = 0; i < 4; i++)
                assertTrue(expMeter.equals(majorSection.mList.get(i)));
            //System.out.println(ABCmusicToString.abcmusicToString(majorSection));
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
    // check one single repeat followed by a non-repeating section, e.g. A :| B -> AAB
    @Test
    public void MajorSectionTest_RepeatNotEnd() {
        String file = "sample_abc/testRepeat1.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk = lexer.getTokens(0);
            MajorSection majorSection = new MajorSection(tk);
            assertEquals(3, majorSection.size);
            assertTrue(ABCmusicEqual.abcmusicEqual(majorSection.getSection(0), majorSection.getSection(1)));
            assertFalse(ABCmusicEqual.abcmusicEqual(majorSection.getSection(0), majorSection.getSection(2)));
            Rational expMeter = new Rational(6, 1);
            for (int i = 0; i < 5; i++)
                assertTrue(expMeter.equals(majorSection.mList.get(i)));
            //System.out.println(ABCmusicToString.abcmusicToString(majorSection));
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
    // check nth-repeat, e.g. A |[1 B:| [2 C -> ABAC
    @Test
    public void MajorSectionTest_NthRepeat() {
        String file = "sample_abc/testRepeat2.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk = lexer.getTokens(0);
            MajorSection majorSection = new MajorSection(tk);
            assertEquals(4, majorSection.size);
            assertTrue(ABCmusicEqual.abcmusicEqual(majorSection.getSection(0), majorSection.getSection(2)));
            assertFalse(ABCmusicEqual.abcmusicEqual(majorSection.getSection(3), majorSection.getSection(1)));
            Rational expMeter = new Rational(6, 1);
            for (int i = 0; i < 4; i++)
                assertTrue(expMeter.equals(majorSection.mList.get(i)));
            //System.out.println(ABCmusicToString.abcmusicToString(majorSection));
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
    // check single repeat start, e.g. A |: B :| C -> ABBC
    @Test
    public void MajorSectionTest_RepeatStart() {
        String file = "sample_abc/testRepeat3.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk = lexer.getTokens(0);
            MajorSection majorSection = new MajorSection(tk);
            assertEquals(4, majorSection.size);
            assertTrue(ABCmusicEqual.abcmusicEqual(majorSection.getSection(2), majorSection.getSection(1)));
            Rational expMeter = new Rational(6, 1);
            for (int i = 0; i < 4; i++)
                assertTrue(expMeter.equals(majorSection.mList.get(i)));
            //System.out.println(ABCmusicToString.abcmusicToString(majorSection));
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
    // check nth-repeat with start A |: B |[1 C :| [2 D -> ABCBD
    @Test
    public void MajorSectionTest_NthRepeatStart() {
        String file = "sample_abc/testRepeat4.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk = lexer.getTokens(0);
            MajorSection majorSection = new MajorSection(tk);
            assertEquals(5, majorSection.size);
            assertTrue(ABCmusicEqual.abcmusicEqual(majorSection.getSection(3), majorSection.getSection(1)));
            assertFalse(ABCmusicEqual.abcmusicEqual(majorSection.getSection(2), majorSection.getSection(4)));
            Rational expMeter = new Rational(6, 1);
            for (int i = 0; i < 6; i++)
                assertTrue(expMeter.equals(majorSection.mList.get(i)));
            //System.out.println(ABCmusicToString.abcmusicToString(majorSection));
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
 // check nth-repeat with start A |: B |[1 C :| [2 D |: E |[1 F :| [2 G -> ABCBDEFEG
    @Test
    public void MajorSectionTest_NthRepeatFollowNthRepeat() {
        String file = "sample_abc/testRepeat5.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk = lexer.getTokens(0);
            MajorSection majorSection = new MajorSection(tk);
            assertEquals(9, majorSection.size);
            assertTrue(ABCmusicEqual.abcmusicEqual(majorSection.getSection(3), majorSection.getSection(1)));
            assertFalse(ABCmusicEqual.abcmusicEqual(majorSection.getSection(2), majorSection.getSection(4)));
            assertTrue(ABCmusicEqual.abcmusicEqual(majorSection.getSection(5), majorSection.getSection(7)));
            Rational expMeter = new Rational(6, 1);
            for (int i = 0; i < 6; i++)
                assertTrue(expMeter.equals(majorSection.mList.get(i)));
            //System.out.println(ABCmusicToString.abcmusicToString(majorSection));
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
}
