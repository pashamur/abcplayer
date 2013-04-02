package lexer;

import java.util.ArrayList;
import java.util.List;
import player.Rational;
import abcmusic.Note;
import abcmusic.Rest;

public class Token {
    public static enum Type {
        tuplet_spec, nth_repeat, multinote_start, multinote_end, 
        simple_bar, major_section_start, major_section_end, 
        repeat_start, repeat_end, 
        rest, note;
    }
    public final Type type;
    private final List<String> text;
    private int value; //used for type nth_repeat and tuplet_spec
    private Note note = null; //used for type note
    private Rest rest = null; //used for type rest
    
    @SuppressWarnings("serial")
    private class TokenException extends RuntimeException {
        public TokenException(String message) {
            super("TokenException: "+message);
        }
    }
    /** construct a Token given type and the string s representing the Token.
     *  the specific type must match string s as specified in grammar.
     *  matching are (lexer is responsible to checking this syntax)
     *  t --- s
     *  simple_bar --- {"|"}
     *  repeat_start --- {"|:"}
     *  repeat_end --- {":|"}
     *  major_section_start --- {"[|"}
     *  major_section_stop --- {"||"} or {"|]"}
     *  multinote_start --- {"["}
     *  multinote_end --- {"]"}
     *  tuplet_spec --- {"(2"} or {"(3"} or {"(4"}
     *  nth_repeat --- {"[1"} or {"[2"}
     *  rest --- {"z"(,"[0-9]*"(,"/","[0-9]*"))} () means optional
     *  note --- {"^^|^|=|_|__","[a-gA-G]","[']*|[,]*"(,"[0-9]*"(,"/","[0-9]*"))}
     *  
     * @param t type of the token
     * @param s string representing the token.
     * @throw TokenException if token invalid.
     */
    public Token(Type t, List<String> s) {
        type = t;
        text = new ArrayList<String>(s);
        if (type == Type.tuplet_spec) {
            value = (int) (s.get(0).charAt(1) - '0');
            if ( value<2 || value>4 ) throw new TokenException("Tuplet-sec value out of bound.");
        }
        else if (type == Type.nth_repeat) {
            value = (int) (s.get(0).charAt(1) - '0');
            if ( value<1 || value>2 ) throw new TokenException("nth-repeat value out of bound.");
        }            
        else {
            value = -1;
            int len = s.size();
            if (type == Type.rest) rest = new Rest(Rational.stringListToRational(s.subList(1, len)));
            if (type == Type.note) note = Note.stringListToNote(s);
        }
    }


    
    /**
     * @return this.value
     * @throws TokenExeption if type is not nth_repeat or tuplet_spec.
     */
    public int getValue() {
        if (type.equals(Type.nth_repeat) || type.equals(Type.tuplet_spec)) return value;
        else throw new TokenException("Try to get value from a token that is neither nth-spec nor tuplet-spec.");
    }
    /**
     * @return a new copy of this.note
     * @throws TokenException if type is not note.
     */
    public Note getNote() {
        if (type.equals(Type.note)) return note.clone();
        else
            throw new TokenException("Try to get note from a token that is not of type note.");
    }
    /**
     * @return a new copy of this.rest
     * @throws RuntimeException if type is not rest.
     */
    public Rest getRest() {
        if (type.equals(Type.rest)) return rest.clone();
        else throw new TokenException("Try to get rest from a token that is not of type rest.");
    }
    /**
     * prints text to so.
     */
    public String print() {
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<text.size();i++) sb.append(text.get(i));
        return sb.toString();
    }
}
