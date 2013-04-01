package lexer;

import org.junit.Test;
import static org.junit.Assert.*;

public class KeySignatureTest {
    //test all kinds of valid key signature (major, minor, sharp, flat, sharp/flat minor)
    @Test
    public void KeySignatureTest_C(){
        int[] result=KeySignature.KeySignatureToInt("A");
        int[] expected={0,0,1,0,0,1,1};
        assertArrayEquals(result,expected);
    }
    @Test
    public void KeySignatureTest_Bm(){
        int[] result=KeySignature.KeySignatureToInt("Bm");
        int[] expected={0,0,1,0,0,1,0};
        assertArrayEquals(result,expected);
    }@Test
    public void KeySignatureTest_Csharp(){
        int[] result=KeySignature.KeySignatureToInt("C#");
        int[] expected={1,1,1,1,1,1,1};
        assertArrayEquals(result,expected);
    }
    @Test
    public void KeySignatureTest_Dflat(){
        int[] result=KeySignature.KeySignatureToInt("Db");
        int[] expected={-1,-1,0,-1,-1,0,-1};
        assertArrayEquals(result,expected);
    }    
    @Test
    public void KeySignatureTest_Eflatm(){
        int[] result=KeySignature.KeySignatureToInt("Ebm");
        int[] expected={-1,-1,-1,-1,-1,0,-1};
        assertArrayEquals(result,expected);
    }
    @Test
    public void KeySignatureTest_Fsharpm(){
        int[] result=KeySignature.KeySignatureToInt("F#m");
        int[] expected={0,0,1,0,0,1,1};
        assertArrayEquals(result,expected);
    }  
    // test exception for none-supported key signature
    @Test(expected=RuntimeException.class)
    public void KeySignatureTest_NoneExist(){
        KeySignature.KeySignatureToInt("G#b");
    }
    // note not in A-G
    @Test(expected=RuntimeException.class)
    public void KeySignatureTest_InvalidNote(){
        KeySignature.KeySignatureToInt("H");
    }  
    // containing invalid character
    @Test(expected=RuntimeException.class)
    public void KeySignatureTest_InvalidCharacter(){
        KeySignature.KeySignatureToInt("A&m");
    }  
    // containing white space
    @Test(expected=RuntimeException.class)
    public void KeySignatureTest_InvalidWhitespace(){
        KeySignature.KeySignatureToInt("A m");
    }
    // contains both sharp and flat
    @Test(expected=RuntimeException.class)
    public void KeySignatureTest_InvalidSharpFlat(){
        KeySignature.KeySignatureToInt("C#b");
    } 
    // note, sharp/flat and minor not given in the correct sequence
    @Test(expected=RuntimeException.class)
    public void KeySignatureTest_InvalidSequence(){
    	KeySignature.KeySignatureToInt("#Gm");
    } 
}
