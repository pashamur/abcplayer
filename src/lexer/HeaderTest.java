package lexer;

import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;
import abcmusic.Pair;
import player.Rational;
import test.TestHelpers;

public class HeaderTest {
	@Test
    public void HeaderTest1() throws IOException{
        Header header = TestHelpers.getFileHeader("sample_abc/invention.abc");
        
        assertTrue(header.getDefaultNoteLength().equals(new Rational(1,8)));
        assertTrue(header.getMeter().equals(new Pair<Integer,Integer>(4,4)));
        assertEquals(header.getTempo(),140);
        assertEquals(header.getNumVoices(),2);
        assertEquals(header.getVoiceIndex("1"),1);
        assertEquals(header.getVoiceIndex("2"),2);
        
        int[] expectedKeySignature= {0,0,0,0,0,0,0};
        assertArrayEquals(expectedKeySignature, header.getKeySignature()); 
        
         }

    @Test
    public void HeaderTest2() throws IOException{
    	Header header = TestHelpers.getFileHeader("sample_abc/prelude.abc");
        
        assertTrue(header.getDefaultNoteLength().equals(new Rational(1,16)));
        assertTrue(header.getMeter().equals(new Pair<Integer,Integer>(4,4)));
        assertEquals(header.getTempo(),280);
        assertEquals(header.getNumVoices(),3);
        assertEquals(header.getVoiceIndex("1"),1);
        assertEquals(header.getVoiceIndex("2"),2);
        assertEquals(header.getVoiceIndex("3"),3);        

        int[] expectedKeySignature= {0,0,0,0,0,0,0};
        assertArrayEquals(expectedKeySignature, header.getKeySignature()); 
    }
    
 
    @Test
    public void HeaderTest3() throws IOException{
    	Header header = TestHelpers.getFileHeader("sample_abc/little_night_music.abc");
       
        assertTrue(header.getDefaultNoteLength().equals(new Rational(1,8)));
        assertTrue(header.getMeter().equals(new Pair<Integer,Integer>(4,4)));
        assertEquals(header.getTempo(),280);

        int[] expectedKeySignature= {0,0,0,0,0,1,0};
        assertArrayEquals(expectedKeySignature, header.getKeySignature()); 
    }

    
    @Test
    public void HeaderTest4() throws IOException{
    	Header header = TestHelpers.getFileHeader("sample_abc/fur_elise.abc");
        
        assertTrue(header.getDefaultNoteLength().equals(new Rational(1,16)));
        assertTrue(header.getMeter().equals(new Pair<Integer,Integer>(3,8)));
        assertEquals(header.getTempo(),240);
        assertEquals(header.getNumVoices(),2);
        assertEquals(header.getVoiceIndex("1"),1);
        assertEquals(header.getVoiceIndex("2"),2);
        
        int[] expectedKeySignature= {0,0,0,0,0,0,0};
        assertArrayEquals(expectedKeySignature, header.getKeySignature()); 
        
    }
}

