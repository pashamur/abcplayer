package player;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class VoiceTest {
    // check that the correct number of majorSections are added
    @Test
    public void VoiceTest_Basic() {
        String file = "sample_abc/paddy.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk = lexer.getTokens(0);
            Voice voice = new Voice(tk);
            assertEquals(3, voice.size);
            Rational expMeter = new Rational(6, 1);
            assertEquals(48,voice.mList.size());
            for (int i = 0; i < voice.mList.size(); i++)
                assertTrue(expMeter.equals(voice.mList.get(i)));
            //System.out.println(ABCmusicToString.abcmusicToString(voice));
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
}
