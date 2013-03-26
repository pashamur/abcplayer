package player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MajorSectionTest {
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
            System.out.println(ABCmusicToString.abcmusicToString(majorSection));
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }

    @Test
    public void MajorSectionTest_RepeatEnd() {
        String file = "sample_abc/testRepeat0.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk = lexer.getTokens(0);
            MajorSection majorSection = new MajorSection(tk.subList(0, tk.size()));
            assertEquals(2, majorSection.size);
            Rational expMeter = new Rational(6, 1);
            for (int i = 0; i < 4; i++)
                assertTrue(expMeter.equals(majorSection.mList.get(i)));
            System.out.println(ABCmusicToString.abcmusicToString(majorSection));
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }

    @Test
    public void MajorSectionTest_RepeatNotEnd() {
        String file = "sample_abc/testRepeat1.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk = lexer.getTokens(0);
            MajorSection majorSection = new MajorSection(tk.subList(0, tk.size()));
            assertEquals(3, majorSection.size);
            Rational expMeter = new Rational(6, 1);
            for (int i = 0; i < 5; i++)
                assertTrue(expMeter.equals(majorSection.mList.get(i)));
            System.out.println(ABCmusicToString.abcmusicToString(majorSection));
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
    @Test
    public void MajorSectionTest_NthRepeat() {
        String file = "sample_abc/testRepeat2.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk = lexer.getTokens(0);
            MajorSection majorSection = new MajorSection(tk.subList(0, tk.size()));
            assertEquals(4, majorSection.size);
            Rational expMeter = new Rational(6, 1);
            for (int i = 0; i < 4; i++)
                assertTrue(expMeter.equals(majorSection.mList.get(i)));
            System.out.println(ABCmusicToString.abcmusicToString(majorSection));
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
    @Test
    public void MajorSectionTest_RepeatStart() {
        String file = "sample_abc/testRepeat3.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk = lexer.getTokens(0);
            MajorSection majorSection = new MajorSection(tk.subList(0, tk.size()));
            assertEquals(4, majorSection.size);
            Rational expMeter = new Rational(6, 1);
            for (int i = 0; i < 4; i++)
                assertTrue(expMeter.equals(majorSection.mList.get(i)));
            System.out.println(ABCmusicToString.abcmusicToString(majorSection));
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
    @Test
    public void MajorSectionTest_NthRepeatStart() {
        String file = "sample_abc/testRepeat4.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk = lexer.getTokens(0);
            MajorSection majorSection = new MajorSection(tk.subList(0, tk.size()));
            assertEquals(5, majorSection.size);
            Rational expMeter = new Rational(6, 1);
            for (int i = 0; i < 6; i++)
                assertTrue(expMeter.equals(majorSection.mList.get(i)));
            System.out.println(ABCmusicToString.abcmusicToString(majorSection));
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
}
