package player;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ABCPlayerTest {

	// Test a simple scale first 
	
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

}
