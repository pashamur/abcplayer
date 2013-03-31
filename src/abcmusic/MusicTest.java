package abcmusic;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lexer.Header;
import lexer.Lexer;

import org.junit.Test;

import player.Main;
import test.TestHelpers;

public class MusicTest {
    @Test
    public void MusicTest_SingleVoice() {
    	Music music = TestHelpers.getMusicFromFile("sample_abc/paddy.abc");
        assertEquals(1, music.numberOfVoices);
        assertTrue(music.checkRep());
    }

    @Test
    public void MusicTest_InvalidRep0() {
    	Music music = TestHelpers.getMusicFromFile("sample_abc/testMusic0.abc");
        assertFalse(music.checkRep());
    }
    
    @Test
    public void MusicTest_InvalidRep1() {
    	Music music = TestHelpers.getMusicFromFile("sample_abc/testMusic1.abc");
        assertFalse(music.checkRep());
    }
    // check the correct number of voices are added, and the voices matches (checkRep())
    @Test
    public void MusicTest_MultiVoice2() {
    	Music music = TestHelpers.getMusicFromFile("sample_abc/invention.abc");
        assertEquals(2, music.numberOfVoices);
        assertTrue(music.checkRep());
    }
    
    @Test
    public void MusicTest_MultiVoice3() {
    	Music music = TestHelpers.getMusicFromFile("sample_abc/prelude.abc");
        assertEquals(3, music.numberOfVoices);
        assertTrue(music.checkRep());
    }
    
    
    
    
}
