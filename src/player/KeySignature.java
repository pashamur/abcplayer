package player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeySignature {
    
    public static int[] KeySignatureToInt(String s) {
        char note='C';
        boolean sharp=false;
        boolean flat=false;
        boolean minor=false;
        if (!s.matches("[A-G][#|b]?[m]?"))
            throw new RuntimeException("Key signature string invalid.");
        note=s.charAt(0);
        if (s.length()==2) {
            char temp;
            temp=s.charAt(1);
            if (temp=='#') sharp=true;
            else if (temp=='b') flat=true;
            else minor=true;
        }
        else if (s.length()==3) {
            minor=true;
            if (s.charAt(1)=='#') sharp=true;
            else flat=true;
        }
        return KeySignatureLookup(note,sharp,flat,minor);
    }
    
    private static int[] KeySignatureLookup(char note, boolean sharp, boolean flat, boolean minor) {
        switch(note){
        case 'A':
            if(minor){
                if(sharp){
                    int[] result={1,1,1,1,1,1,1}; // A#m, C#
                    return result;
                }else if(flat){
                    int[] result={-1,-1,-1,-1,-1,-1,-1}; // Abm, Cb
                    return result;
                }else{
                    int[] result={0,0,0,0,0,0,0}; // Am, C
                    return result;
                }
            }else{
                if(sharp){
                    throw new RuntimeException("Key signature is not supported."); // none
                }else if(flat){
                    int[] result={-1,-1,0,-1,-1,0,0}; // Ab, Fm
                    return result;
                }else{
                    int[] result={0,0,1,0,0,1,1}; // A, F#m
                    return result;
                }
            }
        case 'B':
            if(minor){
                if(sharp){
                    throw new RuntimeException("Key signature is not supported."); // none
                }else if(flat){
                    int[] result={-1,-1,0,-1,-1,0,-1}; // Bbm, Db
                    return result;
                }else{
                    int[] result={0,0,1,0,0,1,0}; // Bm, D
                    return result;
                }
            }else{
                if(sharp){
                    throw new RuntimeException("Key signature is not supported."); // none
                }else if(flat){
                    int[] result={0,-1,0,0,-1,0,0}; // Bb, Gm
                    return result;
                }else{
                    int[] result={1,0,1,1,0,1,1}; // B, G#m
                    return result;
                }
            }
        case 'C':
            if(minor){
                if(sharp){
                    int[] result={0,0,1,1,0,1,1}; // C#m, E
                    return result;
                }else if(flat){
                    throw new RuntimeException("Key signature is not supported."); // none
                }else{
                    int[] result={-1,-1,0,0,-1,0,0}; // Cm, Eb
                    return result;
                }
            }else{
                if(sharp){
                    int[] result={1,1,1,1,1,1,1}; // C#, A#m
                    return result;
                }else if(flat){
                    int[] result={-1,-1,-1,-1,-1,-1,-1}; // Cb, Abm
                    return result;
                }else{
                    int[] result={0,0,0,0,0,0,0}; // C, Am
                    return result;
                }
            }
        case 'D':
            if(minor){
                if(sharp){
                    int[] result={1,0,1,1,1,1,1,1}; // D#m, Fs
                    return result;
                }else if(flat){
                    throw new RuntimeException("Key signature is not supported."); // none
                }else{
                    int[] result={0,-1,0,0,0,0,0}; // Dm, F
                    return result;
                }
            }else{
                if(sharp){
                    throw new RuntimeException("Key signature is not supported."); // none
                }else if(flat){
                    int[] result={-1,-1,0,-1,-1,0,-1}; // Dm, F
                    return result;
                }else{
                    int[] result={0,0,1,0,0,1,0}; // D, Bm
                    return result;
                }
            }
        case 'E':
            if(minor){
                if(sharp){
                    throw new RuntimeException("Key signature is not supported."); // none
                }else if(flat){
                    int[] result={-1,-1,-1,-1,-1,0,-1}; // Ebm, Gb
                    return result;
                }else{
                    int[] result={0,0,0,0,0,1,0}; // Em, G
                    return result;
                }
            }else{
                if(sharp){
                    throw new RuntimeException("Key signature is not supported."); // none
                }else if(flat){
                    int[] result={-1,-1,0,0,-1,0,0}; // Eb, Cm
                    return result;
                }else{
                    int[] result={0,0,1,1,0,1,1}; // E, C#m
                    return result;
                }
            }
        case 'F':
            if(minor){
                if(sharp){
                    int[] result={0,0,1,0,0,1,1}; // F#m, A
                    return result;
                }else if(flat){
                    throw new RuntimeException("Key signature is not supported."); // none
                }else{
                    int[] result={-1,-1,0,-1,-1,0,0}; // Fm, Ab
                    return result;
                }
            }else{
                if(sharp){
                    int[] result={1,0,1,1,1,1,1}; // F#, D#m
                    return result;
                }else if(flat){
                    throw new RuntimeException("Key signature is not supported."); // none
                }else{
                    int[] result={0,-1,0,0,0,0,0}; // F, Dm
                    return result;
                }
            }
        case 'G':
            if(minor){
                if(sharp){
                    int[] result={1,0,1,1,0,1,1}; // G#m, B
                    return result;
                }else if(flat){
                    throw new RuntimeException("Key signature is not supported."); // none (Fsm)
                }else{
                    int[] result={0,-1,0,0,-1,0,0}; // Gm, Bb
                    return result;
                }
            }else{
                if(sharp){
                    throw new RuntimeException("Key signature is not supported."); // none
                }else if(flat){
                    int[] result={-1,-1,-1,-1,-1,0,-1}; // Gb, Ebm
                    return result;
                }else{
                    int[] result={0,0,0,0,0,1,0}; // G, Em
                    return result;
                }
            }
        default:
            throw new RuntimeException("Key signature is not supported.");
        }
    }
    public static void main(String args[]) {
        List<String> listKey=new ArrayList<String>();
        listKey.add("A");
        listKey.add("Am");
        listKey.add("Ab");
        listKey.add("A#m");
        listKey.add("Abm");
        
        listKey.add("B");
        listKey.add("Bm");
        listKey.add("Bb");
        listKey.add("Bbm");
        
        listKey.add("C");
        listKey.add("Cm");
        listKey.add("C#");
        listKey.add("Cb");
        listKey.add("C#m");
        
        listKey.add("D");
        listKey.add("Dm");
        listKey.add("Db");
        listKey.add("D#m");
        
        listKey.add("E");
        listKey.add("Em");
        listKey.add("Eb");
        listKey.add("Ebm");
        
        listKey.add("F");
        listKey.add("Fm");
        listKey.add("F#");
        listKey.add("F#m");
        
        listKey.add("G");
        listKey.add("Gm");
        listKey.add("Gb");
        listKey.add("G#m");
        
        for (int i=0;i<listKey.size();i++) System.out.println(listKey.get(i)+": "+Arrays.toString(KeySignatureToInt(listKey.get(i))));
    }
}
