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
    /**
     * Generate a AST with music as the root.
     * @param lex each element of lex represents a voice.
     */
    public Music(Lexer lex) {
        size=lex.getLength();
        voices=new ArrayList<Voice>();
        for (int i=0;i<size;i++) voices.add(new Voice(lex.getTokens(i)));
    }
    public Voice getVoice(int i) {
        return voices.get(i);
    }
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