package abcmusic;

import java.util.ArrayList;
import java.util.List;
import lexer.Lexer;
import player.Rational;

public class Music implements ABCmusic {
    private List<Voice> voices;
    public final int size;
    public <R> R accept(Visitor<R> mu) {
        return mu.on(this);
    }
    @SuppressWarnings("serial")
    private static class MusicException extends RuntimeException {
        public MusicException(String message) {
            super("MusicException: "+message);
        }
    }
    /**
     * Generate a AST with music as the root.
     * @param lex each element of lex represents a voice.
     * @throw MusicException if lex.getLength()==0 (empty lexer).
     */
    
    public Music(Lexer lex) {
        size=lex.getLength();
        if (size==0) throw new MusicException("Empty lexer.");
        voices=new ArrayList<Voice>();
        for (int i=0;i<size;i++) 
        	voices.add(new Voice(lex.getTokens(i)));
    }
    // return ith voice
    public Voice getVoice(int i) {
        return voices.get(i);
    }
    
    // Check that all of the voices have the same number of 
    public boolean checkRep() {
        int nmeasures=voices.get(0).mList.size();
        List<Rational> mlist=voices.get(0).mList;
        for (int i=1;i<size;i++) {
            if (voices.get(i).mList.size()!=nmeasures) return false;
            List<Rational> temp=voices.get(i).mList;
            for (int j=0;j<nmeasures;j++) 
                if (!temp.get(j).equals(mlist.get(j))) return false;
        }
        return true;
    }
}