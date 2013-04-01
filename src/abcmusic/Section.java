package abcmusic;

import java.util.ArrayList;
import java.util.List;
import lexer.Token;
import player.Rational;


public class Section implements ABCmusic{
    private List<Measure> measures;
    public final int size;
    public List<Rational> mList=new ArrayList<Rational>();
    
    public <R> R accept(Visitor<R> s) {
        return s.on(this);
    }
    
    /**
     * Initialize a new section from a list of tokens
     * 
     * @param tokens List of tokens (generated by the Lexer)
     * @throw RuntimeException if start with bar or have empty measure
     */
    public Section(List<Token> tokens) {
    	// Temporary variables used to look for measures within the input list of tokens
        int nextMeasureStart=0;
        int nextMeasureEnd=0;
        int numTokens=tokens.size();
        
        if (numTokens<1) 
        	throw new RuntimeException("Empty section.");
        
        if (tokens.get(numTokens-1).type.equals(Token.Type.simple_bar))
            throw new RuntimeException("Extra simple-bar.");
        
        measures=new ArrayList<Measure>();
        
        while (nextMeasureStart < numTokens) {
        	
        	// Look for the next simple bar in our list of tokens. That indicates we have found a new measure
            while (nextMeasureEnd<numTokens && !tokens.get(nextMeasureEnd).type.equals(Token.Type.simple_bar)) 
            	nextMeasureEnd++;
            
            if (nextMeasureEnd==nextMeasureStart) 
            	throw new RuntimeException("Extra simple-bar.");
            
            measures.add(new Measure(tokens.subList(nextMeasureStart,nextMeasureEnd)));
            nextMeasureStart=++nextMeasureEnd;
        }
        size=measures.size();
        
        for (int i=0; i<size; i++) {
            mList.add(measures.get(i).getLength());
        }
    }
    
    public Measure getMeasure(int i){
        return measures.get(i);
    }
}
