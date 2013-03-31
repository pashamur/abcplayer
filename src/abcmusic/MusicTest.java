package abcmusic;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lexer.Header;
import lexer.Lexer;

import org.junit.Test;

import player.Main;

public class MusicTest {
    @Test
    public void MusicTest_SingleVoice() {
    	Music music = getMusicFromFile("sample_abc/paddy.abc");
        assertEquals(1, music.numberOfVoices);
        assertTrue(music.checkRep());
    }

    @Test
    public void MusicTest_InvalidRep0() {
    	Music music = getMusicFromFile("sample_abc/testMusic0.abc");
        assertFalse(music.checkRep());
    }
    
    @Test
    public void MusicTest_InvalidRep1() {
    	Music music = getMusicFromFile("sample_abc/testMusic1.abc");
        assertFalse(music.checkRep());
    }
    // check the correct number of voices are added, and the voices matches (checkRep())
    @Test
    public void MusicTest_MultiVoice2() {
    	Music music = getMusicFromFile("sample_abc/invention.abc");
        assertEquals(2, music.numberOfVoices);
        assertTrue(music.checkRep());
    }
    
    @Test
    public void MusicTest_MultiVoice3() {
    	Music music = getMusicFromFile("sample_abc/prelude.abc");
        assertEquals(3, music.numberOfVoices);
        assertTrue(music.checkRep());
    }
    
    
    
    // Load a file and create a music instance from it
    private Music getMusicFromFile(String filename){
        List<String> result = new ArrayList<String>();
        try {
            Header header = Main.readFile(filename, result);
            Lexer lexer = new Lexer(result, header);
            Music music = new Music(lexer);
            return music;
        }
        catch (IOException e) {
            throw new RuntimeException("File error.");
        }
	}
}
