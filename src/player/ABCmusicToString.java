package player;
/*
 * convert AST to string for print out. Format for note: [\d]*[A-G][\d]([\d(/\d)*]), 
 * e.g. 1A-1(1/2) translates to ^A,1/2.
 * The number preceeding basenote is accidental, and will be omitted if hasAccidental=false. 
 * The number following basenote is octave
 * The number in parentheses is length, and has the form [/d] if is integer, or [/d]/[/d] if is 
 * fractional.
 * 
 * measures are separated by |
 * sections and majorsections start on a new line
 * one voice is included in {V0: ... }
 */
public class ABCmusicToString implements ABCmusic.Visitor<String>{
    public String on(Music mu){
        int len=mu.size;
        StringBuilder s=new StringBuilder();
        s.append("MU:\n");
        for (int i=0;i<len;i++) {
            s.append("{V");
            s.append((char)(i+'0'));
            s.append(":\n");
            s.append(abcmusicToString(mu.getVoice(i)));
            s.append("}");
            s.append("\n");
        }
        return s.toString();
    }
    public String on(Voice v) {
        StringBuilder s=new StringBuilder();
        for (int i=0;i<v.size;i++) {
            s.append(abcmusicToString(v.getMajorSection(i)));
            s.append("|]\n");
        }
        return s.toString();
    }
    public String on(MajorSection ms) {
        StringBuilder s=new StringBuilder();
        for (int i=0;i<ms.size;i++) {
            s.append(abcmusicToString(ms.getSection(i)));
            if (i<(ms.size-1)) s.append("|\n");
        }
        return s.toString();
    }
    public String on(Section sc) {
        StringBuilder s=new StringBuilder();
        for (int i=0;i<sc.size;i++) {
            s.append(abcmusicToString(sc.getMeasure(i)));
            if (i<(sc.size-1)) s.append("| ");
        }
        return s.toString();
    }
    public String on(Measure m) {
        StringBuilder s=new StringBuilder();
        for (int i=0;i<m.size;i++) {
            s.append(abcmusicToString(m.getElements(i)));
            s.append(" ");
        }
        return s.toString();
    }
    public String on(Chord c) {
        StringBuilder s=new StringBuilder("[");
        for (int i=0;i<c.size;i++) {
            s.append(abcmusicToString(c.getNote(i)));
        }
        s.append("]");
        return s.toString();
    }
    public String on(Tuplet t) {
        StringBuilder s=new StringBuilder("(");
        s.append(Integer.toString(t.size));
        for (int i=0;i<t.size;i++) s.append(abcmusicToString(t.getNote(i)));
        return s.toString();
    }
    public String on(Note n) {
        StringBuilder s=new StringBuilder();
        if (n.getHasAccidental()) s.append(Integer.toString(n.getAccidental()));
        s.append(n.value);
        s.append(Integer.toString(n.octave));
        s.append('(');
        s.append(n.getLength().toString());
        s.append(')');
        return s.toString();
    }
    public String on(Rest r) {
        StringBuilder s=new StringBuilder("z");
        s.append('(');
        s.append(r.getLength().toString());
        s.append(')');
        return s.toString();
    }
    public static String abcmusicToString(ABCmusic e){
        return e.accept(new ABCmusicToString());
    }
}
