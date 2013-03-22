package player;

public class KeySignature {
    private char note='C';
    private boolean sharp=false;
    private boolean flat=false;
    private boolean minor=false;
    
    public int[] KeySignatureToInt(String s) {
        if (!s.matches("[A-G][#|b]?[m]?"))
            throw new RuntimeException("Key signature string invalid.");
        note=s.charAt(0);
        if (s.length()==2) {
            char temp;
            temp=s.charAt(1);
            if (temp=='#') sharp=true;
            else if (temp=='b') sharp=true;
            else minor=true;
        }
        else if (s.length()==3) {
            minor=true;
            if (s.charAt(1)=='#') sharp=true;
            else sharp=true;
        }
        return KeySignatureLookup();
    }
    
    public int[] KeySignatureLookup() {
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
}
