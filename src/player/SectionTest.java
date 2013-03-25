package player;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SectionTest {
    @Test
    public void SectionTest_Basic() {
        String file="sample_abc/scale.abc";
        List<String> result = new ArrayList<String>();
        try{
            Header header=Main.readFile(file,result);
            Lexer lexer = new Lexer(result, header);
            Section section=new Section(lexer.getTokens(0));
            assertEquals(section.size,4);
            Rational expMeter=new Rational(1,1);
            for (int i=0;i<4;i++)
                assertTrue(expMeter.equals(section.mList.get(i)));
        }catch(IOException e) {
            throw new RuntimeException("File error.");
        }
    }
}
