package abcmusic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lexer.Header;
import lexer.Lexer;
import lexer.Token;

import org.junit.Test;

import player.Main;
import player.Rational;

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
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
    // missing end bar
    @Test (expected=RuntimeException.class)
    public void VoiceTest_MissingEndBar() {
        String file = "sample_abc/testVoice1.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk = lexer.getTokens(0);
            new Voice(tk);
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
    // repeated major section bar
    @Test (expected=RuntimeException.class)
    public void VoiceTest_RepeatedMajorSectionBar() {
        String file = "sample_abc/testVoice2.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            List<Token> tk = lexer.getTokens(0);
            new Voice(tk);
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
}
