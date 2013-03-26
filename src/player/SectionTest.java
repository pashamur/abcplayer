package player;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SectionTest {
    @Test
    public void SectionTest_scale() {
        String file="sample_abc/scale.abc";
        List<String> result = new ArrayList<String>();
        try{
            Header header=Main.readFile(file,result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk=lexer.getTokens(0);
            Section section=new Section(tk.subList(0,tk.size()-1));
            assertEquals(4,section.size);
            Rational expMeter=new Rational(4,1);
            for (int i=0;i<4;i++)
                assertTrue(expMeter.equals(section.mList.get(i)));
            //System.out.println(ABCmusicToString.abcmusicToString(section));
        }catch(IOException e) {
            throw new RuntimeException("File error.");
        }
    }
    @Test
    public void SectionTest_piece1() {
        String file="sample_abc/piece1.abc";
        List<String> result = new ArrayList<String>();
        try{
            Header header=Main.readFile(file,result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk=lexer.getTokens(0);
            Section section=new Section(tk.subList(0,tk.size()-1));
            assertEquals(4,section.size);
            Rational expMeter=new Rational(4,1);
            for (int i=0;i<4;i++)
                assertTrue(expMeter.equals(section.mList.get(i)));
            //System.out.println(ABCmusicToString.abcmusicToString(section));
        }catch(IOException e) {
            throw new RuntimeException("File error.");
        }
    }
    @Test
    public void SectionTest_piece2() {
        String file="sample_abc/piece2.abc";
        List<String> result = new ArrayList<String>();
        try{
            Header header=Main.readFile(file,result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk=lexer.getTokens(0);
            Section section=new Section(tk.subList(0,tk.size()-1));
            assertEquals(6,section.size);
            Rational expMeter=new Rational(4,1);
            for (int i=0;i<6;i++)
                assertTrue(expMeter.equals(section.mList.get(i)));
            //System.out.println(ABCmusicToString.abcmusicToString(section));
        }catch(IOException e) {
            throw new RuntimeException("File error.");
        }
    }
}
