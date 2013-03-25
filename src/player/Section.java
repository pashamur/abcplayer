package player;

import java.util.ArrayList;
import java.util.List;

public class Section implements ABCmusic{
    private List<Measure> measures;
    public final int size;
    public List<Rational> mList=new ArrayList<Rational>();
    public <R> R accept(Visitor<R> s) {
        return s.on(this);
    }
    public Section(List<Token> tk) {
        int ni=0;
        int nf=0;
        int len=tk.size();
        if (len<1) throw new RuntimeException("Empty section.");
        if (tk.get(len-1).type.equals(Token.Type.simple_bar))
            throw new RuntimeException("Extra simple-bar.");
        measures=new ArrayList<Measure>();
        while (ni<len) {
            while (nf<len && !tk.get(nf).type.equals(Token.Type.simple_bar)) nf++;
            if (nf==ni) throw new RuntimeException("Extra simple-bar.");
            measures.add(new Measure(tk.subList(ni,nf)));
            ni=++nf;
        }
        size=measures.size();
        for (int i=0;i<size; i++) {
            mList.add(measures.get(i).getLength());
        }
    }
}
