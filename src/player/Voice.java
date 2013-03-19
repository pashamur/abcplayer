package player;

import java.util.ArrayList;
import java.util.List;

public class Voice implements ABCmusic {
    private List<ABCmusic> majorSections;
    public <R> R accept(Visitor<R> v) {
        return v.on(this);
    }
    public Voice(List<Token> tk) {
        int len=tk.size();
        if (len<1) throw new RuntimeException("Empty voice.");
        Token last=tk.get(len-1);
        if ((!last.equals(Token.Type.simple_bar)) || (!last.equals(Token.Type.repeat_end)) || (!last.equals(Token.Type.major_section_end))) 
            throw new RuntimeException("Missing bar at the end.");
        int ni=0;
        int nf=0;
        if (tk.get(ni).type.equals(Token.Type.major_section_start)) {
            ni++;
            nf++;
        }//ignore first major section start.
        majorSections=new ArrayList<ABCmusic>();
        while (ni<len) {
            while (nf<len && !tk.get(nf).type.equals(Token.Type.major_section_end) && 
                    !tk.get(nf).type.equals(Token.Type.major_section_start)) ++nf;
            if (ni==nf) throw new RuntimeException("Extra major-section bar.");
            else if ((nf==len) && !tk.get(nf).type.equals(Token.Type.repeat_end)) 
                majorSections.add(new MajorSection(tk.subList(ni,nf-1)));
                //if the last bar is repeat, keep it; otherwise get rid of the simple-bar at the end or all major-section-bar
            else majorSections.add(new MajorSection(tk.subList(ni,nf)));
            ni=++nf;
        }
    }
}
