package abcmusic;


public class ABCmusicEqual implements ABCmusic.Visitor<Boolean>{
    private ABCmusic other;
    public ABCmusicEqual(ABCmusic e) {
        other=e;
    }
    public Boolean on(Music mu) {
        if (!(other instanceof Music)) return false;
        Music that = (Music) other;
        if(that.numberOfVoices!=mu.numberOfVoices) return false;
        for (int i=0;i<mu.numberOfVoices;i++) 
            if (!abcmusicEqual(mu.getVoice(i),that.getVoice(i))) return false;
        return true;
    }
    public Boolean on(Voice v) {
        if (!(other instanceof Voice)) return false;
        Voice that=(Voice) other;
        if(that.size!=v.size) return false;
        for (int i=0;i<v.size;i++)
            if (!abcmusicEqual(v.getMajorSection(i),that.getMajorSection(i))) return false;
        return true;
    }
    public Boolean on(MajorSection ms) {
        if (!(other instanceof MajorSection)) return false;
        MajorSection that=(MajorSection) other;
        if(that.size!=ms.size) return false;
        for (int i=0;i<ms.size;i++)
            if (!abcmusicEqual(ms.getSection(i),that.getSection(i))) return false;
        return true;
    }
    public Boolean on(Section s) {
        if (!(other instanceof Section)) return false;
        Section that=(Section) other;
        if(that.sizeInMeasures!=s.sizeInMeasures) return false;
        for (int i=0;i<s.sizeInMeasures;i++)
            if (!abcmusicEqual(s.getMeasure(i),that.getMeasure(i))) return false;
        return true;
    }
    public Boolean on(Measure m) {
        if (!(other instanceof Measure)) return false;
        Measure that=(Measure) other;
        if(that.size!=m.size) return false;
        for (int i=0;i<m.size;i++)
            if (!abcmusicEqual(m.getElements(i),that.getElements(i))) return false;
        return true;
    }
    public Boolean on(Chord c) {
        if (!(other instanceof Chord)) return false;
        Chord that=(Chord) other;
        if(that.size!=c.size) return false;
        if(!that.getLength().equals(c.getLength())) return false;
        for (int i=0;i<c.size;i++)
            if (!abcmusicEqual(c.getNote(i),that.getNote(i))) return false;
        return true;
    }
    public Boolean on(Tuplet t) {
        if (!(other instanceof Tuplet)) return false;
        Tuplet that=(Tuplet) other;
        if(that.size!=t.size) return false;
        if(!that.getLength().equals(t.getLength())) return false;
        for (int i=0;i<t.size;i++)
            if (!abcmusicEqual(t.getElement(i),that.getElement(i))) return false;
        return true;
    }
    public Boolean on(Note n) {
        if (!(other instanceof Note)) return false;
        Note that=(Note) other;
        return (n.value==that.value && n.octave==that.octave && n.getHasAccidental()== that.getHasAccidental()
                && n.getAccidental()==that.getAccidental() && n.getLength().equals(that.getLength()));
                
    }
    public Boolean on(Rest r) {
        if (!(other instanceof Rest)) return false;
        Rest that=(Rest) other;
        return r.getLength().equals(that.getLength());
    }
    public static  Boolean abcmusicEqual(ABCmusic e1, ABCmusic e2){
        return e1.accept(new ABCmusicEqual(e2));
    }
}
