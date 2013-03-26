package player;

import java.util.ArrayList;
import java.util.List;

public class Music implements ABCmusic {
    private List<Voice> voices;
    public final int size;
    public <R> R accept(Visitor<R> mu) {
        return mu.on(this);
    }
    /**
     * Generate a AST with music as the root.
     * @param tk each element of tk represents a voice.
     */
    public Music(Lexer lex) {
        size=lex.getLength();
        voices=new ArrayList<Voice>();
        for (int i=0;i<size;i++) voices.add(new Voice(lex.getTokens(i)));
    }
    public Voice getVoice(int i) {
        return voices.get(i);
    }
}
