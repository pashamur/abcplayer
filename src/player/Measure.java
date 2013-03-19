package player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Measure implements ABCmusic {
    private List<ABCmusic> elements;
    private Rational length=new Rational(0,1);
    private Map<Pair<Character,Integer>,Integer> accidentalList=new HashMap<Pair<Character,Integer>,Integer>();
    public <R> R accept(Visitor<R> m) {
        return m.on(this);
    }
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
                int nTuplet=current.getValue();
                Rational noteLength = null;
                Token temp;
                Note[] note=new Note[nTuplet];
                for (int i=0;i<nTuplet;i++) {
                    if ((point+i+2)>len) throw new RuntimeException("Missing note in a tuplet.");
                    temp=tk.get(point+i+1);
                    if (!temp.type.equals(Token.Type.note)) throw new RuntimeException("Missing note in a tuplet.");
                    note[i]=temp.getNote();
                    checkAccidental(note[i]);
                    if (i==0) noteLength=note[i].getLength(); 
                    else if (!noteLength.equals(note[i].getLength())) throw new RuntimeException("Tuplet notes have different length.");                    
                }
                Tuplet newTuplet=new Tuplet(nTuplet,note,noteLength);
                elements.add(newTuplet);
                length=length.plus(newTuplet.getLength());
                point=point+nTuplet;
            }
            else if (current.type.equals(Token.Type.multinote_start)) {
                Token temp;
                List<Note> n=new ArrayList<Note>();
                while ((++point)<len) {
                    temp=tk.get(point);
                    if (temp.type.equals(Token.Type.note)) n.add(temp.getNote());
                    else if (temp.type.equals(Token.Type.multinote_end)) {
                        Chord newChord=new Chord(n);
                        elements.add(newChord);
                        length=length.plus(newChord.getLength());
                        break;
                    }
                    else throw new RuntimeException("Illegal token within a chord.");
                }
                if (point==len) throw new RuntimeException("Missing multinote_end.");
            }
            else throw new RuntimeException("Illegal token within a measure.");
            point++;
        }
            
    }
    private void checkAccidental(Note note) {
        Pair<Character, Integer> key=new Pair<Character,Integer>(note.value,note.octave);
        if (note.getHasAccidental()) accidentalList.put(key, note.getAccidental());
        else if (accidentalList.containsKey(key))
            note.setAccidental(accidentalList.get(key), true);        
    }
}
