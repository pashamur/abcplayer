package player;

import java.util.List;
import java.util.Map;

public class Header {
    private final int[] keySignature; //saccidentals derived from key signature for A-G respectively.
    private final Map<String, Integer> voice;//from voice label to index.
    private final int numVoices;
    private final Rational L;
    private final Pair<Integer,Integer> M;
    private final int Q;
    /**
     * initialize all fields of Header. 
     * @throws RuntimeException if X, T, K does not appear at the right place.
     */
    public Header(List<String> input) {
        
    }
    /** Lex header and generate a list of field (K, M...) and their respective strings.
     * get rid of comments.
     * @param input to be lexed
     * @return List of (Field, Value)
     * @throws RuntimeException if syntax error within any one line.
     */
    private List<Pair<Character,String>> HeaderLexer(List<String> input) {
        
    }
    public int getQ() {
        return Q;
    }
    public Pair<Integer,Integer> getM() {
        return new Pair(M.first, M.second);
    }
    public Rational getL() {
        return L.clone();
    }
    public int getVoiceIndex(String v) {
        if (voice.containsKey(v)) return voice.get(v);
        else throw new RuntimeException("Voice not found.");
    } 
    public int getAccidental(char c) {
        if (c < 'A' || c > 'G') throw new RuntimeException("Basenote out of bouand.");
        return keySignature[c-'A'];
    }
    public int getNumVoices() {
        return numVoices;
    }
}
