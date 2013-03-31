package player;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MusicTest {
    @Test
    public void MusicTest_SingleVoice() {
        String file = "sample_abc/paddy.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            Music music = new Music(lexer);
            assertEquals(1, music.size);
            //System.out.println(ABCmusicToString.abcmusicToString(music));
            //System.out.println(ABCmusicTicks.ABCMusicTicks(music));
            assertTrue(music.checkRep());
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
    // test 
    @Test
    public void MusicTest_InvalidRep0() {
        String file = "sample_abc/testMusic0.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            Music music = new Music(lexer);
            assertFalse(music.checkRep());
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
    @Test
    public void MusicTest_InvalidRep1() {
        String file = "sample_abc/testMusic1.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            Music music = new Music(lexer);
            assertFalse(music.checkRep());
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
    // check the correct number of voices are added, and the voices matches (checkRep())
    @Test
    public void MusicTest_MultiVoice2() {
        String file = "sample_abc/invention.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            Music music = new Music(lexer);
            assertEquals(2, music.size);
            assertTrue(music.checkRep());
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
    @Test
    public void MusicTest_MultiVoice3() {
        String file = "sample_abc/prelude.abc";
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(file, result);
            Lexer lexer = new Lexer(result, header);
            Music music = new Music(lexer);
            assertEquals(3, music.size);
            assertTrue(music.checkRep());
        } catch (IOException e) {
            throw new RuntimeException("File error.");
        }
    }
}
