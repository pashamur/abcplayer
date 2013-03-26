package player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Token {
    public static enum Type {
        tuplet_spec, nth_repeat, multinote_start, multinote_end, 
        simple_bar, major_section_start, major_section_end, 
        repeat_start, repeat_end, 
        rest, note;
    }
    public final Type type;
    private final List<String> text;
    private final int value; //used for type nth_repeat and tuplet_spec
    private final Note note; //used for type note
    private final Rest rest; //used for type rest
    @SuppressWarnings("serial")
    //map from accidental symbol to integer representation
    private static final Map<String, Integer> accidentalToInt = Collections
            .unmodifiableMap(new HashMap<String, Integer>() {{
                put("^^", 2); put("^", 1); put("=", 0);put("_", -1);put("__", -2);
            }});
    
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
     */
    public Token(Type t, List<String> s) {
        type = t;
        text = new ArrayList<String>(s);
        if (type == Type.tuplet_spec) {
            value = (int) (s.get(0).charAt(1) - '0');
            rest=null;
            note=null;
            if ( value<2 || value>4 ) throw new RuntimeException("Tuplet-sec value out of bound.");
        }
        else if (type == Type.nth_repeat) {
            value = (int) (s.get(0).charAt(1) - '0');
            rest=null;
            note=null;
            if ( value<1 || value>2 ) throw new RuntimeException("nth-repeat value out of bound.");            
        }            
        else {
            value = -1;
            int len = s.size();
            if (type == Type.rest) rest = new Rest(toLength(s.subList(1, len)));
            else rest=null;
            if (type == Type.note) note = toNote(s);
            else note=null;
        }
    }

    /**
     * [Helper] Convert List<String> s representing a rational number to a Rational.
     * 
     * @param s
     *            s.size()=0, 1 or 3. if size=0, return 1/1; 
     *            if size=1, first element represents an
     *            integer. if size=3, first element represents an valid, nonzero
     *            numerator, or an empty string which be default means 1; second
     *            element is "/"; third element is a valid, nonzero denominator,
     *            or an empty string which by default means 2;
     * 
     * @return Rational
     * @throws RuntimeException
     *             if s.size() is not 1 or 3, or does not match the above
     *             description
     */
    private Rational toLength(List<String> s) {
        int len = s.size();
        if (len==0) return new Rational(1,1);
        if (len == 1) {
            if (s.get(0).matches("[1-9]+[0-9]*"))
                return new Rational(Integer.parseInt(s.get(0)), 1);
            throw new RuntimeException("Integer length notation incorrect.");
        }
        if (len == 3) {
            String temp;
            int numerator;
            int denominator;
            temp = s.get(0);
            if (temp.length() == 0)
                numerator = 1;
            else if (temp.matches("[1-9]+[0-9]*"))
                numerator = Integer.parseInt(temp);
            else
                throw new RuntimeException("Numerator notation incorrect.");
            temp = s.get(1);
            if (!temp.equals("/"))
                throw new RuntimeException("Rational / notation incorrect.");
            temp = s.get(2);
            if (temp.length() == 0)
                denominator = 2;
            else if (temp.matches("[1-9]+[0-9]*"))
                denominator = Integer.parseInt(temp);
            else
                throw new RuntimeException("Numerator notation incorrect.");
            return new Rational(numerator, denominator);
        }
        throw new RuntimeException("Length input size incorrect.");
    }

    /**
     * [Helper] s represents note. s.size()=3, 4, or 6. s[0] represents accidental (must
     * be "^^", "^", "=", "_", "__", or empty string which means no accidental
     * is specified). s[1] represents basenote, [a-gA-G]. s[2] repreesnts
     * octave, ("'"+) | (","+), or empty string which means not octave is
     * specified. if size>3, the rest represents length.
     * 
     * @param s note and its accidental, octave, length strings
     * @return Note 
     */
    private Note toNote(List<String> s) {
        int len = s.size();
        boolean b;
        int a;
        int o=0;
        char v;
        String temp;
        if (len!=3 && len!=4 && len!=6) throw new RuntimeException("List of string for note is incorrect.");
        temp = s.get(0);
        if (temp.length() == 0) {
            b = false;
            a = 0;
        } else {
            b = true;
            if (accidentalToInt.containsKey(temp)) a = accidentalToInt.get(temp);
            else throw new RuntimeException("Accidental syntax error.");
        }
        temp=s.get(1);
        if (temp.matches("[a-g]")) {
            o++;
            v=(char) (temp.charAt(0)-32);
        }
        else if (temp.matches("[A-G]")) v=temp.charAt(0);
        else throw new RuntimeException("Basenote unrecognized.");
        temp=s.get(2);
        int tempLen=temp.length();
        if (tempLen!=0) {
            if (temp.matches("[']+")) o+=tempLen;
            else if (temp.matches("[,]+")) o-=tempLen;
            else throw new RuntimeException("Octave syntax error.");
        }
        return new Note(v,o,a,b,toLength(s.subList(3, len)));
    }
    /**
     * @return this.value
     * @throws RuntimeExeption if type is not nth_repeat or tuplet_spec.
     */
    public int getValue() {
        if (type.equals(Type.nth_repeat) || type.equals(Type.tuplet_spec)) return value;
        else throw new RuntimeException("Try to get value from a token that is neither nth-spec nor tuplet-spec.");
    }
    /**
     * @return a new copy of this.note
     * @throws RuntimeException if type is not note.
     */
    public Note getNote() {
        if (type.equals(Type.note)) return note.clone();
        else
            throw new RuntimeException("Try to get note from a token that is not of type note.");
    }
    /**
     * @return a new copy of this.rest
     * @throws RuntimeException if type is not rest.
     */
    public Rest getRest() {
        if (type.equals(Type.rest)) return rest.clone();
        else throw new RuntimeException("Try to get rest from a token that is not of type rest.");
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
