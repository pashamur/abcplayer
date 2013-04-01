package abcmusic;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import player.Rational;

public class Note implements ABCmusic {
    private boolean hasAccidental;
    private int accidental;
    public final int octave;
    public final char value;
    private final Rational length;
    
    // Map from accidental symbol to integer representation
    @SuppressWarnings("serial")
    private static final Map<String, Integer> accidentalToInt = Collections
            .unmodifiableMap(new HashMap<String, Integer>() {{
                put("^^", 2); put("^", 1); put("=", 0);put("_", -1);put("__", -2);
            }});
    
    public <R> R accept(Visitor<R> n) {
        return n.on(this);
    }
    /**
     * Construct a note from individual components
     * @param val basenote. must be [A-G]
     * @param oct octave
     * @param acc accidental. must be -1, 0, 1. If hasAccidental is false, a should be 0 (default).
     * @param hasAcc hasAccidental (whether accidental is specified for the note or is default (unspecified)
     * @param len length of the note (in units of L)
     * @throw RuntimeException ins val is not in [A-G]
     */
    public Note(char val, int oct, int acc, boolean hasAcc, Rational len) {
        if (val<'A' || val>'G') throw new RuntimeException("Basenote out of bound.");
		hasAccidental = hasAcc;
		octave = oct;
		accidental = acc;
		value = val;
		length = len;
    }
    /**
     * Return a copy of the current note without modifying the original
     */
    @Override
    public Note clone(){
        return new Note(value,octave,accidental,hasAccidental,length);
    }
    /**
     * Set the accidental and hasAccidental fields to the given values.
     * @param a new accidental 
     * @param b new hasAccidental
     */
    public void setAccidental(int a, boolean b) {
        accidental=a;
        hasAccidental=b;
    }
    public boolean getHasAccidental() {
        return hasAccidental;
    }
    public int getAccidental() {
        return accidental;
    }
    public Rational getLength() {
        return length.clone();
    }
    
    
    /**
     * noteParts list represents a note. noteParts.size()=3, 4, or 6. noteParts[0] represents accidental (must
     * be "^^", "^", "=", "_", "__", or empty string which means no accidental
     * is specified). noteParts[1] represents basenote, [a-gA-G]. noteParts[2] represents
     * octave, ("'"+) | (","+), or empty string which means not octave is
     * specified. if size>3, the rest represents length.
     * 
     * @param noteParts note and its accidental, octave, length strings
     * @return Note The Note instantiated with the fields in noteParts
     */
    public static Note stringListToNote(List<String> noteParts) {
        int len = noteParts.size();
        boolean hasAccidental;
        int accidental;
        int octave=0;
        char basenote;
        
        if (len!=3 && len!=4 && len!=6) 
        	throw new RuntimeException("List of string for note is incorrect.");
        
        // accidentalString is either empty or one of "^^", "^", "=", "_", "__"
        String accidentalString = noteParts.get(0);
        if (accidentalString.length() == 0) {
            hasAccidental = false;
            accidental = 0;
        } 
        else {
            hasAccidental = true;
            if (accidentalToInt.containsKey(accidentalString)) 
            	accidental = accidentalToInt.get(accidentalString);
            else 
            	throw new RuntimeException("Accidental syntax error.");
        }
        
        // baseNoteString is a character, A-G or a-g. If the character is lower case, we convert it
        // an upper case character with an octave, since our internal representation only deals with upper case basenotes
        String basenoteString = noteParts.get(1);
        if (basenoteString.matches("[a-g]")) {
            octave++;
            basenote=(char) (basenoteString.charAt(0)-32);
        }
        else if (basenoteString.matches("[A-G]")) 
        	basenote=basenoteString.charAt(0);
        else 
        	throw new RuntimeException("Basenote unrecognized.");
        
        // octaveString should be either empty, a series of apostrophes (''''') or commas (,,,,,)
        String octaveString = noteParts.get(2);
        int numberOfOctaves = octaveString.length();
        if (numberOfOctaves != 0) {
            if (octaveString.matches("[']+")) 
            	octave+=numberOfOctaves;
            else if (octaveString.matches("[,]+")) 
            	octave-=numberOfOctaves;
            else 
            	throw new RuntimeException("Octave syntax error.");
        }
        Rational noteLength = Rational.stringListToRational(noteParts.subList(3, len));
        
        return new Note(basenote,octave,accidental,hasAccidental,noteLength);
    }
    
    /**
     * Check for equality by comparing octaves, values, accidentals, and lengths
     * @return true if the current note is equal to the other note
     */
    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Note)) return false;
        Note that=(Note) other;
        return (octave==that.octave && value==that.value && this.getHasAccidental()==that.getHasAccidental() 
                && this.getAccidental()==that.getAccidental() && this.getLength().equals(that.getLength()));
    }
}
