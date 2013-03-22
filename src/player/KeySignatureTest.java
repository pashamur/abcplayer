package player;

import org.junit.Test;
import static org.junit.Assert.*;

public class KeySignatureTest {
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
    @Test
    public void KeySignatureTest_NoneExist(){
        boolean flag=false;
        try{
            KeySignature.KeySignatureToInt("G#b");
        }catch(RuntimeException e) {
            flag=true;
        }
        assertTrue(flag);
    }
    @Test
    public void KeySignatureTest_InvalidNote(){
        boolean flag=false;
        try{
            KeySignature.KeySignatureToInt("H");
        }catch(RuntimeException e) {
            flag=true;
        }
        assertTrue(flag);
    }  
    @Test
    public void KeySignatureTest_InvalidCharacter(){
        boolean flag=false;
        try{
            KeySignature.KeySignatureToInt("A&m");
        }catch(RuntimeException e) {
            flag=true;
        }
        assertTrue(flag);
    }  
    @Test
    public void KeySignatureTest_InvalidWhitespace(){
        boolean flag=false;
        try{
            KeySignature.KeySignatureToInt("A m");
        }catch(RuntimeException e) {
            flag=true;
        }
        assertTrue(flag);
    }  
}
