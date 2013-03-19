package player;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private final List<Token>[] tk;
    //generate array of token list from music strings. each list representing a voice.
    public Lexer(List<String>){}
    //number of voices
    public int getLength() {
        return tk.length;
    }
    //get a clone of ith token list. i must be in [0,getLength()-1]
    public List<Token> getTokens(int i) {
        return new ArrayList<Token>(tk[i]);
    }
}
