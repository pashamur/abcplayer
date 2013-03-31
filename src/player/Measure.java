package player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Measure implements ABCmusic {
    private List<ABCmusic> elements;
    private Rational length=new Rational(0,1);
    public final int size;
    private Map<Pair<Character,Integer>,Integer> accidentalList=new HashMap<Pair<Character,Integer>,Integer>();
    public <R> R accept(Visitor<R> m) {
        return m.on(this);
    }
    /**
     * group tokens in tk into one of note, rest, chord or tuplet and add to elements.
     * @param tk represents a measure. should contain only note. rest, multinote_start, multinote_end, tuplet_spec.
     * @throws if type is not one of the above, or other syntax error related to chord or tuplet
     */
    public Measure(List<Token> tk) {
        int point=0;
        int len=tk.size();
        Token current;
        if (len<1) throw new RuntimeException("Empty measure.");
        elements=new ArrayList<ABCmusic>();
        while (point<len) {
            current=tk.get(point);
            if (current.type.equals(Token.Type.note)) {
                Note note=current.getNote();
                checkAccidental(note);
                length=length.plus(note.getLength());
                elements.add(note);
            }
            else if (current.type.equals(Token.Type.rest)) {
                Rest rest=current.getRest();
                elements.add(rest);
                length=length.plus(rest.getLength());
            }
            else if (current.type.equals(Token.Type.tuplet_spec)) {
                Pair<Integer,Tuplet> tempP=findTuplet(point,current.getValue(),tk);
                point=tempP.first;
                Tuplet newTuplet=tempP.second;
                elements.add(newTuplet);
                length=length.plus(newTuplet.getLength());
            }
            else if (current.type.equals(Token.Type.multinote_start)) {
                Pair<Integer,Chord> tempP=findChord(point,tk);
                point=tempP.first;
                Chord newChord=tempP.second;
                elements.add(newChord);
                length=length.plus(newChord.getLength());
            }
            else throw new RuntimeException("Illegal token within a measure.");
            point++;
        }
        size=elements.size();   
    }
    private Pair<Integer,Tuplet> findTuplet(int point, int nTuplet, List<Token> tk) {
        int len=tk.size();
        Rational noteLength = null;
        Token tempT;
        Note tempN;
        Chord tempC;
        ABCmusic[] eList=new ABCmusic[nTuplet];
        for (int i=0;i<nTuplet;i++) {
            ++point;
            if (point>=len) throw new RuntimeException("Missing note in a tuplet.");
            tempT=tk.get(point);
            if (tempT.type.equals(Token.Type.note)) {
                tempN=tempT.getNote();
                checkAccidental(tempN);
                eList[i]=tempN;
                if (i==0) noteLength=tempN.getLength(); 
                else if (!noteLength.equals(tempN.getLength())) throw new RuntimeException("Tuplet notes have different length");
            }
            else if (tempT.type.equals(Token.Type.multinote_start)) {
                Pair<Integer,Chord> tempP=findChord(point,tk);
                point=tempP.first;
                tempC=tempP.second;
                eList[i]=tempC;
                if (i==0) noteLength=tempC.getLength(); 
                else if (!noteLength.equals(tempC.getLength())) throw new RuntimeException("Tuplet notes have different length");
            }
        }
        Tuplet newTuplet=new Tuplet(nTuplet,eList,noteLength);
        Pair<Integer,Tuplet> result=new Pair<Integer,Tuplet>(point,newTuplet);
        return result;
    }
    private Pair<Integer,Chord> findChord(int point, List<Token> tk) {
        int len=tk.size();
        Token temp;
        List<Note> notes=new ArrayList<Note>();
        Note note;
        Chord newChord=null;
        while ((++point)<len) {
            temp=tk.get(point);
            if (temp.type.equals(Token.Type.note)) {
                note=temp.getNote();
                checkAccidental(note);
                notes.add(note);
            }
            else if (temp.type.equals(Token.Type.multinote_end)) {
                newChord=new Chord(notes);
                break;
            }
            else throw new RuntimeException("Illegal token within a chord.");
        }
        if (point==len) throw new RuntimeException("Missing multinote_end.");
        Pair<Integer,Chord> result=new Pair<Integer,Chord>(point,newChord);
        return result;
    }
    /**
     * [Helper] change the accidental of note according to the previous accidentals recording in accidentalList.
     * this is a mutator method. If note contains accidental, updates the accidentalList.
     * @param note note to be changes.
     */
    private void checkAccidental(Note note) {
        Pair<Character, Integer> key=new Pair<Character,Integer>(note.value,note.octave);
        if (note.getHasAccidental()) accidentalList.put(key, note.getAccidental());
        else if (accidentalList.containsKey(key))
            note.setAccidental(accidentalList.get(key), true);        
    }
    public Rational getLength() {
        return length.clone();
    }
    public ABCmusic getElements(int i) {
        return elements.get(i);
    }
}
